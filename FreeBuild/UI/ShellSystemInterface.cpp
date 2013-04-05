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
typedef bool (Rocket::Core::Context::*glfwUpDownFunc)(Rocket::Core::Input::KeyIdentifier,int);
static glfwToggleFunc glfwToggle[2] = {&glfwDisable, &glfwEnable};
static glfwUpDownFunc keyResponse[2] = {&Rocket::Core::Context::ProcessKeyDown,&Rocket::Core::Context::ProcessKeyUp};

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

void ShellSystemInterface::HandleKeyToggle(int k, int s){
	// This seems like a recipe for badness
	//glfwUpDownFunc todo = keyResponse[s];
	//uiHandle.get()->*todo((Rocket::Core::Input::KeyIdentifier)(keybindings[k]),0);
}

void ShellSystemInterface::HandleCharToggle(int cp, int s){
	
}

void ShellSystemInterface::HandleMousePosition(int x, int y){
	
}

void ShellSystemInterface::HandleMouseToggle(int b, int s){
	
}

void ShellSystemInterface::HandleScrollWheel(int np){
	
}

void ShellSystemInterface::setActiveUIHandle(boost::shared_ptr<Rocket::Core::Context> nhandle){
	uiHandle = nhandle;
}
boost::shared_ptr<Rocket::Core::Context> ShellSystemInterface::getActiveUIHandle(){ return uiHandle; }
