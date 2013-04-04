//
//  GFXDriver.cpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/2/13.
//
//

#include "GFXDriver.h"
#include "../GFX/GFXPipeline.h"
#include "PyInterpreter.h"

namespace bp = boost::python;

GFX::Driver::Driver(): AbstractDriver(), gfxCtx(NULL){
	std::cout << "Initializing graphics drivers... ";
	gfxCtx = GFX::init();
	if(gfxCtx.get()){ std::cout << "Success!" << std::endl; }
	else{ std::cerr << "Initialization failed." << std::endl; }
}

GFX::Driver::~Driver(){
	;
}

int GFX::Driver::mainloop()
{
	int ret = 0;
	std::cout << "Attempting to initiate mainloop." << std::endl;
	bp::object ui_defs_val;
	bp::object main_menu_module;
	HANDLE_PY_ERR(settings["ui_defs"],ui_defs_val);
	bp::extract<std::string> ui_defs_sxval(ui_defs_val);
	std::string ui_defs_sval("");
	if(ui_defs_sxval.check()){
		ui_defs_sval = ui_defs_sxval;
		HANDLE_PY_ERR(bp::import(bp::str(ui_defs_sval)),main_menu_module);
		if(main_menu_module == bp::object()){ // Not None!
			ret = -1;
			std::cerr << "settings['ui_defs'] is defined as '" <<ui_defs_sval <<"', but couldn't be imported." << std::endl;
		}
	} else{
		ret = -1;
		std::cerr << "settings['ui_defs'] is undefined or unable to interpreted as a string." << std::endl;
	}
	
	if(!ret){
		std::cout << "Successfully imported '" << ui_defs_sval << "' to begin UI definition." << std::endl;
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
	} else{
		std::cerr << "Couldn't import a UI, so exiting early!" << std::endl;
	}
	

	return ret;
}

shared_ptr<GFX::Context> GFX::Driver::swapContext(shared_ptr<GFX::Context> nctx){
	shared_ptr<GFX::Context> octx = gfxCtx;
	gfxCtx = nctx;
	return octx;
}

