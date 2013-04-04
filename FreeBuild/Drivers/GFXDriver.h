//
//  GFXDriver.h
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/2/13.
//
//

#ifndef __FreeBuild__GFXDriver__
#define __FreeBuild__GFXDriver__

#include <iostream>
#include "AbstractDriver.h"
#include <stdlib.h>
#include <boost/shared_ptr.hpp>

class ShellSystemInterface;
class ShellRenderInterfaceOpenGL;
class ShellFileInterface;

namespace Rocket{
	namespace Core{
		class Context;
		
	}
}

using namespace boost;


namespace GFX{
	class Context;

	class Driver : public AbstractDriver
	{
	public:
		Driver();
		virtual ~Driver();
		virtual int mainloop();
		virtual shared_ptr<Context> swapContext(shared_ptr<Context> nctx);
		//python::object uiHandle;
		shared_ptr<Rocket::Core::Context> uiHandle;
		
	protected:
		shared_ptr<Context> gfxCtx;
		shared_ptr<ShellSystemInterface> systemInterface;
		shared_ptr<ShellRenderInterfaceOpenGL> renderInterface;
		shared_ptr<ShellFileInterface> fileInterface;
		bool rocketOnline = false;
		
	};
};
#endif /* defined(__FreeBuild__GFXDriver__) */
