//
//  GFXPipeline.cpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/1/13.
//
//

#include "GFXPipeline.h"

#include <glload/gl_all.hpp>
#include <glload/gll.hpp>
#include <GL/glfw.h>

#include "GFXBufferManager.h"

void GFX::Context::swapBuffers(){	glfwSwapBuffers();	}
bool GFX::Context::open(){	return glfwGetWindowParam(GLFW_OPENED);	}

GFX::Context::~Context(){
	;
}

GFX::Context::Context(){
	;
}

shared_ptr<GFX::Context> GFX::init(){
	int retcode = 0;
	//*
	if (!glfwInit()){
        retcode = -1;
		std::cerr << "Unable to init GLFW" << std::endl;
	}
    /* Create a windowed mode window and its OpenGL context */
    else if (!(glfwOpenWindowHint(GLFW_WINDOW_NO_RESIZE, gl::GL_TRUE),glfwOpenWindow(640, 480, 8, 8, 8, 0, 24, 0, GLFW_WINDOW))){
		retcode = -1;
		std::cerr << "Unable to open GLFW window" << std::endl;
	}
	glfwSetWindowTitle("FreeBuild Engine Demo");
	
	if(glload::LoadFunctions() == glload::LS_LOAD_FAILED){
		retcode = -1;
		std::cerr << "Unable to load OpenGL functions";
	} else{
		if(!glext_ARB_draw_instanced){
			retcode = -1;
			std::cerr << "No support for draw instanced";
		}
		if(!glext_ARB_instanced_arrays){
			retcode = -1;
			std::cerr << "No support for instanced vertex attributes";
		}
		if(!glext_ARB_framebuffer_object){
			retcode = -1;
			std::cerr << "No support for framebuffer objects";
		}
		if(!retcode){
			glfwSetWindowSizeCallback(&BufferManager::resizeCallback);
		}
		
	}

	return (!retcode)? shared_ptr<Context>(new Context()) : shared_ptr<Context>();
}