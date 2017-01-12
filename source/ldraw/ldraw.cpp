//
//  ldraw.cpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 1/11/17.
//
//

#include "ldraw.hpp"
#include "console/engineAPI.h"

#include <numeric>
#include <algorithm>

//-----------------------------------------------------------------------------
// TypeLDrawDir
//-----------------------------------------------------------------------------

IMPLEMENT_CLASS(LDrawLibraryPathElement, "A 2-tuple of a size_t and a c-string")
	PROPERTY( pathLen, 1, "Length of the path element.", 0 )
	PROPERTY( pathStr, 1, "Textual representation of the path element.", 0)
END_IMPLEMENT_CLASS;

IMPLEMENT_CLASS(LDrawLibraryPath, "A queue of `LDrawLibraryPathElement`s")
	PROPERTY( element, 0, "The actual elements.", 0)
END_IMPLEMENT_CLASS


ConsoleType( ldrawDir, TypeLDrawLibraryPath, LDrawLibraryPath, LDRAW_DIRECTORY_PREFIX )

static size_t sumPathTuple(size_t headSize, const LDrawLibraryPathElement &o2) {
	return headSize + o2.first;
}

ConsoleGetType( TypeLDrawLibraryPath )
{
	const LDrawLibraryPath::ElemQueueType& deqRef = ((LDrawLibraryPath*)dptr)->elements;
	size_t bufSize = std::accumulate(deqRef.cbegin(), deqRef.cend(), deqRef.size(), sumPathTuple);
	char * returnString = Con::getReturnBuffer(bufSize);
	char * dst = returnString;
	std::for_each(deqRef.cbegin(), deqRef.cend(),
				  [&](const LDrawLibraryPathElement& elem){
					  dStrcpy(dst, elem.second);
					  dst += elem.first;
					  (dst++)[0] = ';';
				  });
	dst[-1] = '\0';
	return returnString;
}

static void addNextPath(LDrawLibraryPath::ElemQueueType& deqRef, char *theStr, char* next){
	next[0] = '\0';
	if((next - 1 >= theStr) && (next[-1] == '/')){
		next[-1] = '\0';
	}
	LDrawLibraryPathElement elem(dStrlen(theStr), StringTable->insert(theStr));
	deqRef.push_back(elem);
}

ConsoleSetType( TypeLDrawLibraryPath )
{
	if(argc == 1)
	{
		LDrawLibraryPath::ElemQueueType& deqRef = ((LDrawLibraryPath*)dptr)->elements;
		
		if(deqRef.size() > 0){
			deqRef.clear();
		}
		
		char *buf = dStrdup(argv[0]); // The args are const, so we have to make a temporary buffer
		char *next, *theStr = buf;
		while((next = strchr(theStr, ';')) != nullptr){
			addNextPath(deqRef, theStr, next);
			theStr = next+1;
		}
		next = theStr + dStrlen(theStr);
		addNextPath(deqRef, theStr, next);
		dFree(buf);
	} else
		Con::printf("(TypeLDrawLibraryPath) Cannot set multiple args to a single string.");
}


MODULE_BEGIN( LDRAW )

MODULE_INIT
{
	LDRAW::initConsole();
}

MODULE_SHUTDOWN
{
	LDRAW::shutdown();
}

MODULE_END;

namespace LDRAW {
	std::deque<std::string> gLDrawInstallation;
	LDrawLibraryPath gLDrawDirectories;
	StringTableEntry primsSTE;
	StringTableEntry partsSTE;
	StringTableEntry modelSTE;
	StringTableEntry blankSTE;
	bool gLDrawPrintRemarks;
	bool gLDrawPrintCacheStats;
	bool gLDrawPrintCacheTree;
	bool gLDrawRGBOnly;
	StringTableEntry gLDrawConfigPath;
	StringTableEntry gLDrawScriptPath;

	
	void initConsole(){
		gLDrawInstallation.clear();
		gLDrawInstallation.resize(0);
		
		gLDrawDirectories.elements.clear();
		gLDrawDirectories.elements.resize(0);
		
		primsSTE = StringTable->insert("P");
		partsSTE = StringTable->insert("PARTS");
		modelSTE = StringTable->insert("MODELS");
		blankSTE = StringTable->EmptyString();
		
		gLDrawConfigPath = blankSTE;
		gLDrawScriptPath = blankSTE;
		
		Con::addVariable( "$pref::LDraw::Debug::PrintRemarks", TypeBool, &gLDrawPrintRemarks );
		Con::addVariable( "$pref::LDraw::Debug::PrintCacheStats", TypeBool, &gLDrawPrintCacheStats );
		Con::addVariable( "$pref::LDraw::Debug::PrintCacheTree", TypeBool, &gLDrawPrintCacheTree );
		Con::addVariable( "$pref::LDraw::Debug::RGBOnly", TypeBool, &gLDrawRGBOnly );
		
		// We don't want a TypeStringFilename, because it will get sandboxed,
		// and this needs to access anywhere
		Con::addVariable( "$pref::LDraw::ConfigPath", TypeString, &gLDrawConfigPath);
		Con::addVariable( "$pref::LDraw::ScriptPath", TypeString, &gLDrawScriptPath);
		Con::addVariable( "$pref::LDraw::LibraryPath", TypeLDrawLibraryPath, &gLDrawDirectories);
		
		
		/*
		S32 i;
		
		for (i=0; i<MAX_INCLUDE_DEPTH; i++) {
			cached_file_stack[i] = NULL;
			deferred_flag[i] = 0;
		}
		
		LDCacheNode::clearLDCache();
		
		
		zcolor_init();
		 */
		
	}
	
	bool checkLDrawDirectory(){
		std::deque<std::string> ppmPaths;
		Con::printf("Initializing LDraw Subsystem...");
		ppmPaths.clear();
		
		std::for_each(gLDrawDirectories.elements.begin(), gLDrawDirectories.elements.end(),
					  [&](const LDrawLibraryPathElement& currentDir){
						  const char * fullPath = currentDir.second;
						  size_t fullPathLen = currentDir.first;
						  
						  bool fpL, fpPr, fpPa, fpMd;
						  fpL = fpPr = fpPa = fpMd = false;
						  fpL = Platform::isDirectory( fullPath );
						  if(fpL){
							  fpPr = Platform::isSubDirectory(fullPath, primsSTE);
							  fpPa = Platform::isSubDirectory(fullPath, partsSTE);
							  fpMd = Platform::isSubDirectory(fullPath, modelSTE);
						  }
						  if(	Platform::isDirectory( fullPath ) &&
							 Platform::isSubDirectory(fullPath, primsSTE) &&
							 Platform::isSubDirectory(fullPath, partsSTE) &&
							 Platform::isSubDirectory(fullPath, modelSTE)
							 ){
							  
							  // construct the full file paths
							  U32 primsLen = fullPathLen + 3;
							  U32 partsLen = fullPathLen + 7;
							  U32 modelLen = fullPathLen + 8;
							  char* primsPath = new char[primsLen];
							  char* partsPath = new char[partsLen];
							  char* modelPath = new char[modelLen];
							  
							  Platform::makeFullPathName(primsSTE, primsPath, primsLen, fullPath);
							  Platform::makeFullPathName(partsSTE, partsPath, partsLen, fullPath);
							  Platform::makeFullPathName(modelSTE, modelPath, modelLen, fullPath);
							  
							  ppmPaths.push_back(std::string(primsPath));
							  ppmPaths.push_back(std::string(partsPath));
							  ppmPaths.push_back(std::string(modelPath));
							  
							  delete [] primsPath;
							  delete [] partsPath;
							  delete [] modelPath;
						  } else{
							  Con::errorf("Did not find an LDraw installation at %s (in $pref::LDraw::LibraryPath), so moving on.\nMake sure you have P, PARTS and MODELS subdirectories.", fullPath);
						  }
					  });
		
		if(!ppmPaths.size()){
			Con::errorf("Please point $pref::LDraw::LibraryPath to at least one valid LDraw installation. Multiple directories may be specified as a ';' separated list.");
			Con::executef("onLDrawDirFailed");
			gLDrawInstallation.clear();
			gLDrawInstallation.resize(0);
			return false;
		} else{
			gLDrawInstallation = ppmPaths;
			return true;
		}
	}
	
	void shutdown(){
		gLDrawDirectories.elements.clear();
		gLDrawInstallation.clear();
		
		//LDCacheNode::clearLDCache();
		
	}
	
	
}

ConsoleFunctionGroupBegin(LDrawFuncs, "These are things from LDraw.cpp");

DefineConsoleFunction(initLDraw, bool, (), , "() Initializes the LDraw system, and checks for a valid LDraw directory")
{
	return LDRAW::checkLDrawDirectory(); // && LDParse::loadColorTable();
}

DefineConsoleFunction(findLDrawFile, const char *, (const char * fname, const char * sfname),(""), "(filename) looks for a file"){
	Vector<StringTableEntry> tsubfiles(5);
	StringTableEntry ste = StringTable->insert(fname);
	
	StringTableEntry ste2 = StringTable->insert(sfname);
	if(ste2 != StringTable->EmptyString()) {
		tsubfiles.push_back(ste2);
	}
	/*
	Vector<LDFileData*> dummy;
	dummy.clear();
	
	LDFileSearchInfo * sInfo = LDParse::findLDrawFile(ste,dummy);
	const char * ret = sInfo->path;
	delete sInfo;
	return ret;
	//*/
	return "";
}

DefineConsoleFunction(parseLDrawFile, S32, (const char * fname), , "(filename)"){
	return -1;//LDRAW::initParse(fname);
}

DefineConsoleFunction(setLDrawColour, void, (S32 index, ColorI colorArg), , "(index, {color: r,g,b[,a]})"){
	return;
	//LDRAW::setColour(colorArg, i);
}

ConsoleFunctionGroupEnd(LDrawFuncs);
