//
//  DriverPyImpl.cpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/2/13.
//
//

#include "DriverPyImpl.h"
#include "GFXDriver.h"
#include <Rocket/Core.h>
#include <Rocket/Core/Input.h>
#include <boost/python.hpp>

shared_ptr<AbstractDriver> mainDriver;
unordered_map<int,int> keymap;

void clearKeyMap(){
	keymap.clear();
}

void mapKey(int glfwk, int rocketk){
	keymap[glfwk] = rocketk;
}

void setMainDriver(shared_ptr<AbstractDriver> driver){
	mainDriver = driver;
}

shared_ptr<AbstractDriver> getMainDriver(){
	return mainDriver;
}

void clearMainDriver(){	mainDriver.reset(); }


struct AbstractDriverWrap : AbstractDriver, boost::python::wrapper<AbstractDriver>
{
    int mainloop()
    {
        return this->get_override("mainloop")();
    }
};

struct GFXDriverWrap : GFX::Driver, boost::python::wrapper<GFX::Driver>
{
    int mainloop()
    {
        if (boost::python::override mainloop = this->get_override("mainloop"))
            return mainloop(); // *note*
        return GFX::Driver::mainloop();
    }
	
    int default_mainloop() { return this->GFX::Driver::mainloop(); }
};

BOOST_PYTHON_MODULE(Drivers)
{
	using namespace boost::python;
    class_<AbstractDriverWrap, shared_ptr<AbstractDriverWrap>, boost::noncopyable>("AbstractDriver", no_init)
	.def("mainloop", pure_virtual(&AbstractDriver::mainloop))
	.def_readwrite("settings",&AbstractDriver::settings)
    ;
	
	class_<GFXDriverWrap, shared_ptr<GFXDriverWrap>, boost::noncopyable, bases<AbstractDriver> >("GFXDriver",init<>())
	.def("mainloop", &GFX::Driver::mainloop,&GFXDriverWrap::default_mainloop)
	.def_readwrite("uiHandle",&GFX::Driver::uiHandle)
	;
	
	implicitly_convertible<shared_ptr<GFX::Driver>,shared_ptr<AbstractDriver>>();
	register_ptr_to_python<shared_ptr<AbstractDriver>>();
	register_ptr_to_python<shared_ptr<GFX::Driver>>();
	register_ptr_to_python<shared_ptr<Rocket::Core::Context>>();
	 
	def("setMainDriver",setMainDriver);
	def("getMainDriver",getMainDriver);
	def("clearMainDriver",clearMainDriver);
	
	def("clearKeyMap",clearKeyMap);
	def("mapKey",mapKey);
}