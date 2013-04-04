//
//  DriverPyImpl.h
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/2/13.
//
//

#ifndef __FreeBuild__DriverPyImpl__
#define __FreeBuild__DriverPyImpl__

#include <iostream>
#include <stdlib.h>
#include <boost/shared_ptr.hpp>

#endif /* defined(__FreeBuild__DriverPyImpl__) */

using namespace boost;

class AbstractDriver;
extern "C" shared_ptr<AbstractDriver> mainDriver;
extern "C" void setMainDriver(shared_ptr<AbstractDriver> driver);
extern "C" void clearMainDriver();
extern "C" shared_ptr<AbstractDriver> getMainDriver();