//
//  PyInterpreter.cpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/2/13.
//
//

#include "PyInterpreter.h"
#include <unistd.h>
#include <boost/python/detail/wrap_python.hpp>

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

extern "C" void initDrivers();
extern "C" void init_rocketcore();
extern "C" void init_rocketcontrols();

Console::Python::Python(int argc, char* argv[]){
	char buf[512];
	if(getcwd(buf,512) != NULL){
		Py_SetPythonHome(buf);
	} else{
		std::cerr << "Warning, couldn't determine current working directory for PYTHONHOME" << std::endl;
	}
	Py_SetProgramName(argv[0]);
	//char * args[2] = {"FreeBuild","main.py"};
	PyImport_AppendInittab( "Drivers", &initDrivers );
	PyImport_AppendInittab( "_rocketcore", &init_rocketcore );
	PyImport_AppendInittab( "_rocketcontrols", &init_rocketcontrols );
	Py_Initialize();
	//Py_Main(2,args);
	
	object main_module = import("__main__");
	object main_namespace = main_module.attr("__dict__");
	char * name = "main.py";
	if(argc <= 1)
	{
		argc = 1;
		char * nargv[1];
		nargv[0]= name;
		argv = nargv;
	} else{
		name = argv[1];
		argc = argc-1;
		argv = &(argv[1]);
	}
	
	if( access( name, F_OK | R_OK ) != -1 ) {
		PySys_SetArgvEx(argc, argv, true);
		exec_file(name,main_namespace);
	} else {
		// file doesn't exist
		std::cerr<<	"Warning: Couldn't initialize main script. "
					"Check that it exists, and that you have "
					"permission to read it."<<std::endl;
	}
}

Console::Python::~Python(){
	// Do not call Py_Finalize(); until Boost is fixed
}

object Console::Python::eval(str expression, object globals, object locals){
	object ret; // Should be none!
	HANDLE_PY_ERR(boost::python::eval(expression, globals, locals),ret);
	return ret;
}

object Console::Python::exec(str code, object globals, object locals){
	object ret; // Should be none!
	HANDLE_PY_ERR(boost::python::exec(code, globals,locals),ret);
	return ret;
}

object Console::Python::exec_file(str filename, object globals, object locals){
	object ret; // Should be none!
	HANDLE_PY_ERR(boost::python::exec_file(filename, globals, locals),ret);
	return ret;
}