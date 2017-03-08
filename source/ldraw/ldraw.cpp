//
//  ldraw.cpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 1/11/17.
//
//

#include "ldraw.hpp"
#include "console/engineAPI.h"
#include <LDParse/Cache.hpp>

#include <numeric>
#include <algorithm>
#include <boost/optional.hpp>

//-----------------------------------------------------------------------------
// TypeLDrawDir
//-----------------------------------------------------------------------------

namespace LDRAW {
	IMPLEMENT_CLASS(LibraryPathElement, "A 2-tuple of a size_t and a c-string")
	PROPERTY( pathLen, 1, "Length of the path element.", 0 )
	PROPERTY( pathStr, 1, "Textual representation of the path element.", 0)
	END_IMPLEMENT_CLASS;
	
	IMPLEMENT_CLASS(LibraryPath, "A queue of `LDrawLibraryPathElement`s")
	PROPERTY( element, 0, "The actual elements.", 0)
	END_IMPLEMENT_CLASS
}


ConsoleType( ldrawDir, TypeLDrawLibraryPath, LDRAW::LibraryPath, LDRAW_DIRECTORY_PREFIX )

ConsoleGetType( TypeLDrawLibraryPath )
{
	using namespace LDRAW;
	static auto sumPathTuple = [](size_t headSize, const LibraryPathElement &o2) {
		return headSize + o2.first;
	};
	const LibraryPath::ElemQueueType& deqRef = ((LibraryPath*)dptr)->elements;
	size_t bufSize = std::accumulate(deqRef.cbegin(), deqRef.cend(), deqRef.size(), sumPathTuple);
	char * returnString = Con::getReturnBuffer(bufSize);
	char * dst = returnString;
	std::for_each(deqRef.cbegin(), deqRef.cend(),
				  [&](const LibraryPathElement& elem){
					  dStrcpy(dst, elem.second);
					  dst += elem.first;
					  (dst++)[0] = ';';
				  });
	dst[-1] = '\0';
	return returnString;
}

ConsoleSetType( TypeLDrawLibraryPath )
{
	using namespace LDRAW;
	static auto addNextPath = [](LibraryPath::ElemQueueType& deqRef, char *theStr, char* next){
		next[0] = '\0';
		if((next - 1 >= theStr) && (next[-1] == '/')){
			next[-1] = '\0';
		}
		LibraryPathElement elem(dStrlen(theStr), StringTable->insert(theStr));
		deqRef.push_back(elem);
	};
	
	if(argc == 1)
	{
		LibraryPath::ElemQueueType& deqRef = ((LibraryPath*)dptr)->elements;
		
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
	LibraryPath gLDrawDirectories;
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
	
	using LDCacheNode = LDParse::Cache::CacheNode<const FileData>;
	LDCacheNode* gLDrawCacheRoot = LDCacheNode::makeRoot();
	
	std::unique_ptr<const FileSearchInfo> findLDrawFile(const char * filename, const std::vector<const FileData*> &subfileSearchStack)
	{
		
		
		U32 i;
		U32 cacheDepth = gLDrawPrintCacheTree ? 0 : -1;
		StringTableEntry file = StringTable->insert(filename);
		
		LDCacheNode * cacheRoot = gLDrawCacheRoot;
		boost::optional<const FileData&> cachedFile = boost::none;
		std::unique_ptr<FileSearchInfo> ret = nullptr;
		
		U32 ldis = gLDrawInstallation.size();
		if(ldis % 3) {
			Con::errorf("findLDrawFile called before any entries in $pref::LDraw::LibraryPath were validated. Please call checkLDrawDirectory()");
			
		} else if(!subfileSearchStack.empty()) {
			auto foundPred = [&](const FileData* subFileSearchItem){
				std::shared_ptr<const LDCacheNode> subFileList = subFileSearchItem->getSubFileCache();
				// Did we get passed some sub-files?
				// And can we find what we're looking for among them?
				return (subFileList != nullptr) && ((cachedFile = subFileList->find(file,cacheDepth)) != boost::none);
			};
			auto maybeFound = std::find_if(subfileSearchStack.cbegin(), subfileSearchStack.cend(), foundPred);
			
			if(maybeFound != subfileSearchStack.cend()) {
				ret = std::unique_ptr<FileSearchInfo>(new FileSearchInfo({file,
					StringTable->insert((*cachedFile).getPath().c_str()),
					true, *cachedFile}));
			}
		}
		
		// FIXME: THIS LOGIC NEEDS TO BE CLEANED UP, IT'S DEFINITELY WRONG, BUT EVERYTHING COMPILES SO COOL TO COMMIT AND SAVE PROGRESS.
		if(ret == nullptr){
			if((cacheRoot != nullptr) &&
			   ((cachedFile = cacheRoot->find(file,cacheDepth)) != boost::none)) {
				ret = std::unique_ptr<FileSearchInfo>(new FileSearchInfo({file,
				StringTable->insert((*cachedFile).getPath().c_str()),
				false, *cachedFile}));
			} else {
				
				//Assumes a beginning->end traversal to ensure proper precedence.
				
				StringTableEntry path;
				char pReturn[1024];
				
				for(std::deque<std::string>::const_iterator itr = gLDrawInstallation.begin();
					itr!=gLDrawInstallation.end();
					++itr
					) {
					path = StringTable->insert(itr->c_str());
					Platform::makeFullPathName(file, pReturn, 1024, path);
					if(Platform::isFile(pReturn) && !Platform::isDirectory(pReturn)){
						ret->name = file;
						ret->path = StringTable->insert(pReturn);
						break;
					}
				}
				
				
			}
		}
		
		
		if(ret->name == NULL) Con::warnf("File '%s' not found in LDraw directories",file);
		return std::move(ret);
	}

	
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
					  [&](const LibraryPathElement& currentDir){
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