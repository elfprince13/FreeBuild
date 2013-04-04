//
//  GFXDriver.cpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/2/13.
//
//

#include "GFXDriver.h"
#include "GFXPipeline.h"

GFX::Driver::Driver(): AbstractDriver(), gfxCtx(NULL){
	gfxCtx = GFX::init();
}

GFX::Driver::~Driver(){
	;
}

int GFX::Driver::mainloop()
{
	int ret = 0;
	if(gfxCtx.get())
	{
		while (gfxCtx->open())
		{
			/* Render here */
			
			/* Swap front and back buffers and process events */
			gfxCtx->swapBuffers();
		}
	} else{
		std::cerr << "Entering GFX::Driver::mainloop() but no context exists" << std::endl;
		ret = -1;
	}
	return ret;
}

shared_ptr<GFX::Context> GFX::Driver::swapContext(shared_ptr<GFX::Context> nctx){
	shared_ptr<GFX::Context> octx = gfxCtx;
	gfxCtx = nctx;
	return octx;
}

