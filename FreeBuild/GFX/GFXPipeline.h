//
//  GFXPipeline.h
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/1/13.
//
//

#ifndef __FreeBuild__GFXPipeline__
#define __FreeBuild__GFXPipeline__
#include <iostream>
#include <stdlib.h>
#include <boost/shared_ptr.hpp>

using namespace boost;
namespace GFX{
	
	class Context{
	public:
		virtual ~Context();
		Context();
		bool open();
		void swapBuffers();
	};
	
	shared_ptr<Context> init();
};
#endif /* defined(__FreeBuild__GFXPipeline__) */

