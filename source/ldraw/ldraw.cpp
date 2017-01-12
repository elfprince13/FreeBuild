//
//  ldraw.cpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 1/11/17.
//
//

#include "ldraw.hpp"
#include "console/engineAPI.h"
#include "console/consoleInternal.h"
#include "console/ast.h"

//-----------------------------------------------------------------------------
// TypeLDrawDir
//-----------------------------------------------------------------------------
ConsoleType( ldrawDir, TypeLDrawDir, const char*, LDRAW_DIRECTORY_PREFIX )

ConsoleGetType( TypeLDrawDir )
{
	return *((const char **)(dptr));
}

ConsoleSetType( TypeLDrawDir )
{
	if(argc == 1)
	{
		char * FullPath = dStrdup(argv[0]);
		if( FullPath[ dStrlen( FullPath ) - 1 ] == '/' )
			FullPath[ dStrlen( FullPath ) - 1 ] = 0x00;
		*((const char **) dptr) = StringTable->insert(FullPath);
		dFree(FullPath);
	} else
		Con::printf("(TypeLDrawDir) Cannot set multiple args to a single string.");
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
	std::deque<char*> gLDrawDirectories;
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
		
		gLDrawDirectories.clear();
		gLDrawDirectories.resize(0);
		
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
		bool tryNext;
		char * fullPath;
		char * varNameBuf = new char[512];
		U32 i = 0;
		
		Dictionary::Entry * var;
		StringTableEntry varName;
		std::deque<std::string> ppmPaths;
		
		Con::printf("Initializing LDraw Subsystem...");
		ppmPaths.clear();
		
		
		do{
			tryNext = false;
			dSprintf(varNameBuf, 512, "$pref::LDraw::Directory%d",i);
			varName = StringTable->insert(varNameBuf);
			if(*Con::getVariable(varName) != '\0'){
				var = gEvalState.globalVars.lookup(varName);
				AssertFatal(var != NULL, "LDParse::checkLDrawDirectory - We found the variable already, this shouldn't be a problem");
				if(var->value.type != TypeLDrawDir){
					dStrcpy(varNameBuf, var->getStringValue());
					dFree(var->value.sval);
					var->value.sval = typeValueEmpty;
					
					char * dirStore = new char[dStrlen(varNameBuf)+1];
					gLDrawDirectories.push_back(dirStore);
					var->value.dataPtr = dirStore;
					var->value.type = TypeLDrawDir;
					var->setStringValue(varNameBuf);
				}
				tryNext = true;
				fullPath = dStrdup(Con::getVariable(varName));
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
					U32 primsLen = dStrlen(fullPath) + 3;
					U32 partsLen = dStrlen(fullPath) + 7;
					U32 modelLen = dStrlen(fullPath) + 8;
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
					Con::errorf("Did not find an LDraw installation at %s (in %s), so moving on.\nMake sure you have P, PARTS and MODELS subdirectories.", fullPath, varName);
				}
				
				dFree(fullPath);
			}
			
			
		} while(tryNext && (++i)); // This second test is a sanity check of sorts. I can't imagine when it would happen, but lets make sure it doesn't.
		
		delete [] varNameBuf;
		
		
		if(!ppmPaths.size()){
			Con::errorf("Please point the $pref::LDraw::Directory array to at least one valid LDraw installation");
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
		char* dir;
		for(std::deque<char*>::const_iterator itr = gLDrawDirectories.begin();
			itr!=gLDrawDirectories.end();
			++itr
			) {
			dir = *itr;
			if(dir != NULL) delete [] dir;
		}
		
		
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
