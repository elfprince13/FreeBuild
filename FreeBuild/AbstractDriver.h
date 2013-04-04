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


using namespace boost;

namespace Console{
	class Python;
};

class AbstractDriver{
public:
	AbstractDriver();
	virtual ~AbstractDriver();
	virtual int mainloop() = 0;
	virtual void setConsole(shared_ptr<Console::Python> npc);
	virtual shared_ptr<Console::Python> getConsole();
protected:
	shared_ptr<Console::Python> pcon;
};
#endif /* defined(__FreeBuild__AbstractDriver__) */

