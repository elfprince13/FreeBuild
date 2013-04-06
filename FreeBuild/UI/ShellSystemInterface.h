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

#ifndef ROCKETSHELLSYSTEMINTERFACE_H
#define ROCKETSHELLSYSTEMINTERFACE_H

#include <boost/shared_ptr.hpp>
#include <boost/unordered_map.hpp>
#include <Rocket/Core/SystemInterface.h>

/**
	A custom system interface for Rocket. This provides timing information.
	@author Lloyd Weehuizen
 */
namespace GFX{
	class Driver;
};
namespace Rocket{
	namespace Core{
		class Context;
	};
};


class ShellSystemInterface : public Rocket::Core::SystemInterface
{
public:
	/// Get the number of seconds elapsed since the start of the application
	/// @returns Seconds elapsed
	enum KeyboardMode {
		Text,
		Keys,
		Text_And_Keys
	};
	ShellSystemInterface(GFX::Driver *o);
	virtual float GetElapsedTime();
	
	static void RegisterInputDevices();
	static void registerKeyboardInput();
	static void UnRegisterInputDevices();
	
	static void ToggleSystemKeys(bool on);
	static void ToggleKeyboardMode(KeyboardMode nmode=Text_And_Keys);
	static void ToggleKeyRepeat(bool on);
	
	static void ToggleSystemMouse(bool on);
	static void ToggleSoftMouse(bool on);
	
	static int getActiveModifiers();
	
	static void HandleKeyToggle(int k, int s);
	static void HandleCharToggle(int cp, int s);
	
	static void HandleMousePosition(int x, int y);
	static void HandleMouseToggle(int b, int s);
	static void HandleScrollWheel(int np);
	
	static void setActiveUIHandle(boost::shared_ptr<Rocket::Core::Context> nhandle);
	static boost::shared_ptr<Rocket::Core::Context> getActiveUIHandle();
	static void clearActiveUIHandle();
	
	
	static inline void clearOwner(){	owner = NULL;	}
	static inline GFX::Driver * getOwner(){ return owner;	}
	static inline void setOwner(GFX::Driver *o){
		if(owner != NULL){
			std::cerr << "Warning: A ShellSystemInterface probably already exists, ";
			std::cerr << "because the class is already owned by a GFX::Driver" << std::endl;
			std::cerr << "Expect some problems." << std::endl;
		}
		owner = o;
	}
	
protected:
	static KeyboardMode keyboard_mode;// = Text_And_Keys;
	static int wheel_pos;// = 0;
	static boost::shared_ptr<Rocket::Core::Context> uiHandle;
	static GFX::Driver * owner;// = NULL;
	static bool inputRegistered;// = false;
};


extern "C" boost::unordered_map<int,int> keymap;

#endif
