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

#ifndef _CONSOLE_BASE_TYPE_H_
#include "console/consoleTypes.h"
#endif

#include <string>
#include <deque>

#define LDRAW_DIRECTORY_PREFIX "@ldrawDir="

DefineConsoleType(TypeLDrawDir, const char *)

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
	extern std::deque<char*> gLDrawDirectories;
	void initConsole();
	void shutdown();
}

#endif /* ldraw_hpp */
