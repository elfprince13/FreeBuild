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

#define HANDLE_PY_ERR(PF,rv) rv = boost::python::object(); \
try \
{ \
rv = PF; \
} \
catch(const boost::python::error_already_set &) \
{ \
if(PyErr_Occurred()!=NULL){\
PyErr_Print();\
PyErr_Clear();\
} else{\
std::cerr << "A mysterious exception occured while executing HANDLE_PY_ERR(" << #PF << ",";\
std::cerr << #rv << ")" <<std::endl;\
}\
}

#define HANDLE_PY_ERR_NO_RET(PF) try \
{ \
PF; \
} \
catch(const boost::python::error_already_set &) \
{ \
if(PyErr_Occurred()!=NULL){\
PyErr_Print();\
PyErr_Clear();\
} else{\
std::cerr << "A mysterious exception occured while executing HANDLE_PY_ERR_NO_RET(" << #PF << ")" <<std::endl;\
}\
}


using namespace boost::python;
namespace Console{
	class Python{
	public:
		Python(int argc, char* argv[]);
		virtual ~Python();
		object eval(str expression, object globals = object(), object locals = object());
		object exec(str code, object globals = object(), object locals = object());
		object exec_file(str filename, object globals = object(), object locals = object());
		bool check_py_callable(object obj);
	};
};

#endif /* defined(__FreeBuild__PyInterpreter__) */
