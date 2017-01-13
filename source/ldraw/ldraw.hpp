//
//  ldraw.hpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 1/11/17.
//
//

#ifndef ldraw_hpp
#define ldraw_hpp

#include "console/console.h"
#include "core/color.h"

#include <LDParse/Model.hpp>

#ifndef _CONSOLE_BASE_TYPE_H_
#include "console/consoleTypes.h"
#endif

#include "console/engineObject.h"

#include <string>
#include <deque>
#include <tuple>
#include <vector>
#include <memory>

#define LDRAW_DIRECTORY_PREFIX "@ldrawDir="

namespace LDRAW {
	class LibraryPathElement : public EngineObject {
		DECLARE_CLASS(LibraryPathElement, EngineObject);
		size_t first;
		StringTableEntry second;
		LibraryPathElement(size_t len, const char * str, bool debugValidate = true)
		: first(len), second(str) {
			AssertFatal(!debugValidate || (len = dStrlen(str)), "str/len mismatch");
		}
		LibraryPathElement() : first(0), second(NULL) {}
		
		size_t getPathLen() const {	return first; }
		void setPathLen(size_t len){ first = len; }
		
		StringTableEntry getPathStr() const { return second; }
		void setPathStr(StringTableEntry str) { second = str; }
	};
	
	class LibraryPath : public EngineObject {
		DECLARE_CLASS(LibraryPath, EngineObject);
		typedef std::deque<LibraryPathElement> ElemQueueType;
		ElemQueueType elements;
		LibraryPath() : elements() {}
		LibraryPath(ElemQueueType init) : elements(init) {}
		
		S32 getElementCount() const { return elements.size(); }
		LibraryPathElement getElementElement(S32 i) const { return elements[i]; }
		void setElementElement(S32 i, LibraryPathElement elem) { elements[i] = elem; }
	};
	
	using FileData = LDParse::Model;
	struct FileSearchInfo {
		StringTableEntry name;
		StringTableEntry path;
		bool subFile;
		const FileData& file;
	};
}

// N.B. We don't currently do any escaping, so member paths can't contain ";"
DefineConsoleType(TypeLDrawLibraryPath, LDRAW::LibraryPath)



namespace LDRAW {
	bool checkLDrawDirectory();
	extern StringTableEntry primsSTE;
	extern StringTableEntry partsSTE;
	extern StringTableEntry modelSTE;
	extern StringTableEntry blankSTE;
	extern bool gLDrawPrintRemarks;
	extern bool gLDrawPrintCacheStats;
	extern bool gLDrawPrintCacheTree;
	extern bool gLDrawRGBOnly;
	extern StringTableEntry gLDrawConfigPath;
	extern StringTableEntry gLDrawScriptPath;
	extern std::deque<std::string> gLDrawInstallation;
	extern LibraryPath gLDrawDirectories;
	
	std::unique_ptr<const FileSearchInfo> findLDrawFile(const char *filename,  const std::vector<const FileData*>& subfileSearchStack);
	
	void initConsole();
	void shutdown();
}

#endif /* ldraw_hpp */
