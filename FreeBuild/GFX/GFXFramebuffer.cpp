//
//  GFXFramebuffer.cpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/8/13.
//
//

#include "GFXFramebuffer.h"

GFX::Framebuffer::~Framebuffer(){
	//std::cout << "Releasing framebuffer" << std::endl;
	this->deleteObj();
}

GFX::Framebuffer::Framebuffer(int w, int h) : GLObject(){
	this->handleResize(w, h);
}

void GFX::Framebuffer::deleteObj(){
	this->unbind();
	gl::DeleteFramebuffers(1, name.get());
	*name = 0;
}

void GFX::Framebuffer::handleResize(int w, int h){
	this->w = w;
	this->h = h;
	//std::cout << "Resizing framebuffer" << std::endl;
	if((*name)){
		this->deleteObj();
	}
	gl::GenFramebuffers(1, name.get());
}

void GFX::Framebuffer::bind(GLenum target){
	this->bound = true;
	this->target = target;
	gl::BindFramebuffer(target, *name);
}

void GFX::Framebuffer::unbind(){
	if(this->bound){
		this->bound = false;
		gl::BindFramebuffer(this->target, 0);
	}
}