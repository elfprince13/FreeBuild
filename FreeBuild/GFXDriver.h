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
	protected:
		shared_ptr<Context> gfxCtx;
		
	};
};
#endif /* defined(__FreeBuild__GFXDriver__) */
