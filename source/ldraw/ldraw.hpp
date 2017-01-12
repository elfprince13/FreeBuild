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

#ifndef _CONSOLE_BASE_TYPE_H_
#include "console/consoleTypes.h"
#endif

#include "console/engineObject.h"

#include <string>
#include <deque>
#include <tuple>

#define LDRAW_DIRECTORY_PREFIX "@ldrawDir="

namespace LDRAW {
	class LDrawLibraryPathElement : public EngineObject {
		DECLARE_CLASS(LDrawLibraryPathElement, EngineObject);
		size_t first;
		StringTableEntry second;
		LDrawLibraryPathElement(size_t len, const char * str, bool debugValidate = true)
		: first(len), second(str) {
			AssertFatal(!debugValidate || (len = dStrlen(str)), "str/len mismatch");
		}
		LDrawLibraryPathElement() : first(0), second(NULL) {}
		
		size_t getPathLen() const {	return first; }
		void setPathLen(size_t len){ first = len; }
		
		StringTableEntry getPathStr() const { return second; }
		void setPathStr(StringTableEntry str) { second = str; }
	};
	
	class LDrawLibraryPath : public EngineObject {
		DECLARE_CLASS(LDrawLibraryPath, EngineObject);
		typedef std::deque<LDrawLibraryPathElement> ElemQueueType;
		ElemQueueType elements;
		LDrawLibraryPath() : elements() {}
		LDrawLibraryPath(ElemQueueType init) : elements(init) {}
		
		S32 getElementCount() const { return elements.size(); }
		LDrawLibraryPathElement getElementElement(S32 i) const { return elements[i]; }
		void setElementElement(S32 i, LDrawLibraryPathElement elem) { elements[i] = elem; }
	};
}

// N.B. We don't currently do any escaping, so member paths can't contain ";"
DefineConsoleType(TypeLDrawLibraryPath, LDRAW::LDrawLibraryPath)



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
	extern LDrawLibraryPath gLDrawDirectories;
	void initConsole();
	void shutdown();
}

#endif /* ldraw_hpp */
