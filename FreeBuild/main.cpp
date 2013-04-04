//
//  main.cpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/1/13.
//
//

#include <iostream>
#include <stdlib.h>
#include "GFXPipeline.h"
#include "PyInterpreter.h"
#include "AbstractDriver.h"
#include "GFXDriver.h"
#include <boost/shared_ptr.hpp>

//#include <dlfcn.h>
#include "DriverPyImpl.h"

using namespace std;

int main(int argc, char * argv[])
{
	shared_ptr<AbstractDriver> md;//, * (*getMainDriver)(void);
	// void * driverModule, (*clearMainDriver)(void);
	/*
	driverModule = dlopen("Drivers.so",RTLD_NOW|RTLD_GLOBAL);
	*(void **)(&getMainDriver) = dlsym(driverModule,"getMainDriver");
	*(void **)(&clearMainDriver) = dlsym(driverModule,"clearMainDriver");
	 */
	md = getMainDriver();
	shared_ptr<Console::Python> pcon = shared_ptr<Console::Python>(new Console::Python(argc,argv));
	md = getMainDriver();
	int retcode = 0;
	 //*/
	// insert code here...
	if(md.get()){
		//GFX::Driver * gd = static_cast<GFX::Driver *>(md);
		//GFX::Context * otx = gd->swapContext(NULL);
		//gd->swapContext(otx);
		//std::cout << (void*)(gd->getConsole()) << ", " << (void *)otx << std::endl;
		md->setConsole(pcon);
		retcode = md->mainloop();
		clearMainDriver();
	}
	//dlclose(driverModule);
    return retcode;
}

