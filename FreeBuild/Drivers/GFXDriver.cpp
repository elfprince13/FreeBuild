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
#include <Rocket/Core.h>
#include "../UI/ShellSystemInterface.h"
#include "../UI/ShellRenderInterfaceOpenGL.h"
#include "../UI/ShellFileInterface.h"
#include <unistd.h>
namespace bp = boost::python;

GFX::Driver::Driver() : AbstractDriver(),
gfxCtx(NULL),
uiHandle(),
renderInterface(new ShellRenderInterfaceOpenGL()),
systemInterface(new ShellSystemInterface(this)){
	char buf[512];
	
	
	std::cout << "GFX::Driver claiming ownership of ShellSystemInterface" << std::endl;
	std::cout << "Initializing graphics drivers... ";
	gfxCtx = GFX::init();
	if(getcwd(buf,512)!=NULL){
		fileInterface = shared_ptr<ShellFileInterface>(new ShellFileInterface(buf));
		Rocket::Core::SetSystemInterface(systemInterface.get());
		Rocket::Core::SetRenderInterface(renderInterface.get());
		Rocket::Core::SetFileInterface(fileInterface.get());
		if(gfxCtx.get() &&
		   (rocketOnline = Rocket::Core::Initialise())){
			std::cout << "Success!" << std::endl;
		} else{ std::cerr << "Initialization failed." << std::endl; }
	} else{
		std::cerr << "Initialization failed." << std::endl;
	}
	
}

GFX::Driver::~Driver(){
	if(systemInterface.get() && systemInterface->getOwner() == this){
		std::cout << "GFX::Driver releasing ownership of ShellSystemInterface" << std::endl;
		systemInterface->clearOwner();
	}
	if(rocketOnline){
		this->uiHandle.reset();// = bp::object();
		Rocket::Core::Shutdown();
		rocketOnline = false;
	}
}

int GFX::Driver::mainloop()
{
	int ret = 0;
	
	std::cout << "Attempting to initiate mainloop." << std::endl;
	bp::object ui_defs_val;
	bp::object ui_module;
	HANDLE_PY_ERR(settings["ui_defs"],ui_defs_val);
	bp::extract<std::string> ui_defs_sxval(ui_defs_val);
	std::string ui_defs_sval("");
	if(ui_defs_sxval.check()){
		ui_defs_sval = ui_defs_sxval;
		HANDLE_PY_ERR(bp::import(bp::str(ui_defs_sval)),ui_module);
		if(ui_module == bp::object()){ // Not None!
			ret = -1;
			std::cerr << "settings['ui_defs'] is defined as '" <<ui_defs_sval <<"', but couldn't be imported." << std::endl;
		}
	} else{
		ret = -1;
		std::cerr << "settings['ui_defs'] is undefined or unable to interpreted as a string." << std::endl;
	}
	bp::object renderUI;
	if(!ret){
		std::cout << "Successfully imported '" << ui_defs_sval << "' to begin UI definition." << std::endl;
		
		object py_conf_val;
		HANDLE_PY_ERR(ui_module.attr("configure_ui")(800,600),py_conf_val);
		bp::extract<bool> b_conf_val(py_conf_val);
		if(!b_conf_val.check()){
			ret = -1;
			std::cerr << "Couldn't call configure_ui(), or got a bad result" << std::endl;
		} else if(this->uiHandle == bp::object()){
			ret = -1;
			std::cerr << "Called configure_ui() but uiHandle was not initialized." << std::endl;
		} else{
			//HANDLE_PY_ERR(this->uiHandle.attr("Render"),renderUI);
			//if(!(this->pcon->check_py_callable(renderUI))){
			if(!this->uiHandle.get()){
				ret = -1;
				std::cerr << "uiHandle was initialized, but attribute Render does not exist or is not callable" << std::endl;
			}
		}
	}
	if(!ret){
		
		if(gfxCtx.get())
		{
			// Set up the GL state.
			gl::ClearColor(0, 0, 0, 1);
			gl::EnableClientState(gl::GL_VERTEX_ARRAY);
			gl::EnableClientState(gl::GL_COLOR_ARRAY);
			
			gl::Enable(gl::GL_BLEND);
			gl::BlendFunc(gl::GL_SRC_ALPHA, gl::GL_ONE_MINUS_SRC_ALPHA);
			
			gl::MatrixMode(gl::GL_PROJECTION);
			gl::LoadIdentity();
			gl::Ortho(0, 800, 600, 0, -1, 1);
			
			gl::MatrixMode(gl::GL_MODELVIEW);
			gl::LoadIdentity();

			while (gfxCtx->open())
			{
				gl::Clear(gl::GL_COLOR_BUFFER_BIT);
				/* Render here */
				//HANDLE_PY_ERR_NO_RET(renderUI());
				uiHandle->Update();
				uiHandle->Render();
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

