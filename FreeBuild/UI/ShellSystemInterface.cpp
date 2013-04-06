/*
 * This source file is part of libRocket, the HTML/CSS Interface Middleware
 *
 * For the latest information, see http://www.librocket.com
 *
 * Copyright (c) 2008-2010 CodePoint Ltd, Shift Technology Ltd
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

#include "ShellSystemInterface.h"
#include "Shell.h"
#include <GL/glfw.h>
#include <Rocket/Core.h>
#include <iostream.h>

typedef void (*glfwToggleFunc)(int);
typedef bool (Rocket::Core::Context::*KeyUpDownFunc)(Rocket::Core::Input::KeyIdentifier,int);
typedef void (Rocket::Core::Context::*MouseUpDownFunc)(int,int);

static glfwToggleFunc glfwToggle[2] = {&glfwDisable, &glfwEnable};

static KeyUpDownFunc keyResponse[2] = {&Rocket::Core::Context::ProcessKeyUp,&Rocket::Core::Context::ProcessKeyDown};

static MouseUpDownFunc mouseResponse[2] = {&Rocket::Core::Context::ProcessMouseButtonUp,&Rocket::Core::Context::ProcessMouseButtonDown};

ShellSystemInterface::KeyboardMode ShellSystemInterface::keyboard_mode = Text_And_Keys;
int ShellSystemInterface::wheel_pos = 0;
boost::shared_ptr<Rocket::Core::Context> ShellSystemInterface::uiHandle;
GFX::Driver * ShellSystemInterface::owner = NULL;
bool ShellSystemInterface::inputRegistered = false;

ShellSystemInterface::ShellSystemInterface(GFX::Driver *o) {	setOwner(o);	}

void ShellSystemInterface::registerKeyboardInput(){
	if(!uiHandle.get()){
		std::cerr << "Warning, unsafe to activate event listening without at least";
		std::cerr << " one Rocket::Core::Context attached to ShellSystemInterface." << std::endl;
	}
	switch(keyboard_mode){
		case Text:
			glfwSetCharCallback(&HandleCharToggle); break;
		case Keys:
			glfwSetKeyCallback(&HandleKeyToggle); break;
		case Text_And_Keys:
			glfwSetCharCallback(&HandleCharToggle);
			glfwSetKeyCallback(&HandleKeyToggle);
			break;
		default:
			std::cerr << "Error: Reached evil state in ShellSystemInterface::RegisterInputDevices()" << std::endl;
	}
}

// Get the number of seconds elapsed since the start of the application
float ShellSystemInterface::GetElapsedTime()
{
	return (float)glfwGetTime();
	
}

void ShellSystemInterface::RegisterInputDevices(){
	registerKeyboardInput();
	
	glfwSetMousePosCallback(&HandleMousePosition);
	glfwSetMouseButtonCallback(&HandleMouseToggle);
	glfwSetMouseWheelCallback(&HandleScrollWheel);
	inputRegistered = true;
	
}

void ShellSystemInterface::UnRegisterInputDevices(){
	glfwSetCharCallback(NULL);
	glfwSetKeyCallback(NULL);
	
	glfwSetMousePosCallback(NULL);
	glfwSetMouseButtonCallback(NULL);
	glfwSetMouseWheelCallback(NULL);
	inputRegistered = false;
}

void ShellSystemInterface::ToggleSystemKeys(bool on){	glfwToggle[on](GLFW_SYSTEM_KEYS);	}

void ShellSystemInterface::ToggleKeyboardMode(KeyboardMode nmode){
	keyboard_mode = nmode;
	if(inputRegistered){
		glfwSetCharCallback(NULL);
		glfwSetKeyCallback(NULL);
		registerKeyboardInput();
	}
}

void ShellSystemInterface::ToggleKeyRepeat(bool on){ glfwToggle[on](GLFW_KEY_REPEAT);	}
void ShellSystemInterface::ToggleSystemMouse(bool on){ glfwToggle[on](GLFW_MOUSE_CURSOR);	}
void ShellSystemInterface::ToggleSoftMouse(bool on){
	if(uiHandle.get()){
		uiHandle->ShowMouseCursor(on);
	} else{
		std::cerr << "Warning, ShellSystemInterface::ToggleSoftMouse(" << on;
		std::cerr << ") called with no Rocket::Core::Context" << std::endl;
	}
}

int ShellSystemInterface::getActiveModifiers(){
	return (glfwGetKey(GLFW_KEY_LCTRL) | glfwGetKey(GLFW_KEY_RCTRL)) * Rocket::Core::Input::KM_CTRL |
	(glfwGetKey(GLFW_KEY_LSHIFT) | glfwGetKey(GLFW_KEY_RSHIFT)) * Rocket::Core::Input::KM_SHIFT |
	(glfwGetKey(GLFW_KEY_LALT) | glfwGetKey(GLFW_KEY_RALT)) * Rocket::Core::Input::KM_ALT |
	(glfwGetKey(GLFW_KEY_LSUPER) | glfwGetKey(GLFW_KEY_RSUPER)) * Rocket::Core::Input::KM_META |
	(glfwGetKey(GLFW_KEY_CAPS_LOCK)) * Rocket::Core::Input::KM_CAPSLOCK |
	(glfwGetKey(GLFW_KEY_KP_NUM_LOCK)) * Rocket::Core::Input::KM_NUMLOCK |
	(glfwGetKey(GLFW_KEY_SCROLL_LOCK)) * Rocket::Core::Input::KM_SCROLLLOCK;
}

void ShellSystemInterface::HandleKeyToggle(int k, int s){
	// This seems like a recipe for badness
	// How does Rocket handle KeyIdentifiers with potentially invalid values?
	(uiHandle.get()->*keyResponse[s])((Rocket::Core::Input::KeyIdentifier)(keymap[k]),getActiveModifiers());
}

void ShellSystemInterface::HandleCharToggle(int cp, int s){
	if(s){
		unsigned short ucs2v = (unsigned short)(unsigned int)cp;
		if(ucs2v != (unsigned int)cp){
			std::cerr << "Warning: truncating UTF-32 value into the ";
			std::cerr << "Basic Multilingual Plane for UCS-2 conversion" << std::endl;
		}
		uiHandle->ProcessTextInput(ucs2v);
	}
}

void ShellSystemInterface::HandleMousePosition(int x, int y){
	uiHandle->ProcessMouseMove(x, y, getActiveModifiers());
}

void ShellSystemInterface::HandleMouseToggle(int b, int s){
	(uiHandle.get()->*mouseResponse[s])(b,getActiveModifiers());
}

void ShellSystemInterface::HandleScrollWheel(int np){
	int delta = (wheel_pos - np); // may need a - here for Apple "natural scrolling"
	wheel_pos = np;
	uiHandle->ProcessMouseWheel(delta, getActiveModifiers());
}

void ShellSystemInterface::setActiveUIHandle(boost::shared_ptr<Rocket::Core::Context> nhandle){	uiHandle = nhandle;	}
void ShellSystemInterface::clearActiveUIHandle(){	uiHandle = boost::shared_ptr<Rocket::Core::Context>();	}
boost::shared_ptr<Rocket::Core::Context> ShellSystemInterface::getActiveUIHandle(){ return uiHandle; }
