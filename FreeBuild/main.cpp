//
//  main.cpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/1/13.
//
//

#include <iostream>
#include <stdlib.h>
#include "GFX/GFXPipeline.h"
#include "Console/PyInterpreter.h"
#include "Drivers/AbstractDriver.h"
#include <boost/shared_ptr.hpp>

//#include <dlfcn.h>
#include "DriverPyImpl.h"

using namespace std;

int main(int argc, char * argv[])
{
	shared_ptr<AbstractDriver> md;
	md = getMainDriver();
	shared_ptr<Console::Python> pcon = shared_ptr<Console::Python>(new Console::Python(argc,argv));
	md = getMainDriver();
	int retcode = 0;
	if(md.get()){
		md->setConsole(pcon);
		retcode = md->mainloop();
		clearMainDriver();
	}
    return retcode;
}

