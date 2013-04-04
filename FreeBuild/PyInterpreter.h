//
//  PyInterpreter.h
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/2/13.
//
//

#ifndef __FreeBuild__PyInterpreter__
#define __FreeBuild__PyInterpreter__

#include <iostream>
#include <boost/python.hpp>

using namespace boost::python;
namespace Console{
	class Python{
	public:
		Python(int argc, char* argv[]);
		virtual ~Python();
		object eval(str expression, object globals = object(), object locals = object());
		object exec(str code, object globals = object(), object locals = object());
		object exec_file(str filename, object globals = object(), object locals = object());
	};
};

#endif /* defined(__FreeBuild__PyInterpreter__) */
