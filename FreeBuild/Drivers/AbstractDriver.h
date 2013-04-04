//
//  AbstractDriver.h
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/2/13.
//
//

#ifndef __FreeBuild__AbstractDriver__
#define __FreeBuild__AbstractDriver__

#include <iostream>
#include <stdlib.h>
#include <boost/shared_ptr.hpp>
#include <boost/python.hpp>

namespace Console{
	class Python;
};

using namespace boost;

class AbstractDriver{
public:
	AbstractDriver();
	virtual ~AbstractDriver();
	virtual int mainloop() = 0;
	virtual void setConsole(shared_ptr<Console::Python> npc);
	virtual shared_ptr<Console::Python> getConsole();
	python::dict settings;
protected:
	shared_ptr<Console::Python> pcon;
};
#endif /* defined(__FreeBuild__AbstractDriver__) */

