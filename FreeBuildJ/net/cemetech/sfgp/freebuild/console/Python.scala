package net.cemetech.sfgp.freebuild.console


import org.python.util.PythonInterpreter 
import org.python.core._

import java.util._
import java.io.File

object Python {
  
    def init(in_argv:Array[String]):PythonInterpreter = {
    	var args:Array[String] = in_argv
		var initProps:Properties = PySystemState.getBaseProperties
		initProps = setDefaultPythonPath(initProps)
		//println(initProps)
		
		var name:String = "main.py"
		if (args.length < 1){
			args = Array(name)
		} else {
			name = args(0)
			args = args.drop(0)
		}
		
    	/*
    	args.foreach{
		    arg:String => println("arg: " + arg)
		
    	}
    	// */		
		PySystemState.initialize(initProps,new Properties,args)
		
		var systemState:PySystemState = Py.getSystemState()
		var interp:PythonInterpreter = new PythonInterpreter(new PyDictionary,systemState)
		
		var mainModule:PyObject = __builtin__.__import__("__main__")
		var mainNameSpace:PyObject = mainModule.__getattr__(new PyString("__dict__"))
		
		if((new File(name)).canRead()){
			interp.execfile(name)
		} else {
			println("Warning: Couldn't initialize main script. Check that it exists, and that you have permission to read it.")
		}
		
		//var ui_module:PyObject = __builtin__.__import__("scripts.ui")
		//interp.exec("import scripts.ui")
		return interp
    }
  
    def clean_init(in_argv:Array[String]):PythonInterpreter = {
    	var args:Array[String] = in_argv
		var initProps:Properties = PySystemState.getBaseProperties
		initProps = setDefaultPythonPath(initProps)
		//println(initProps)
		
		
    	/*
    	args.foreach{
		    arg:String => println("arg: " + arg)
		
    	}
    	// */		
		PySystemState.initialize(initProps,new Properties,args)
		
		var systemState:PySystemState = Py.getSystemState()
		var interp:PythonInterpreter = new PythonInterpreter(new PyDictionary,systemState)
		
		var mainModule:PyObject = __builtin__.__import__("__main__")
		var mainNameSpace:PyObject = mainModule.__getattr__(new PyString("__dict__"))
		
		//var ui_module:PyObject = __builtin__.__import__("scripts.ui")
		//interp.exec("import scripts.ui")
		return interp
    }
	
	
	def setDefaultPythonPath(props:Properties):Properties = {
		var pythonPathProp:String = props.getProperty("python.path");
    	
    	props.setProperty("python.path",if (pythonPathProp==null) {
    		System.getProperty("user.dir");
    	} else {
    		pythonPathProp + java.io.File.pathSeparator + System.getProperty("user.dir") + java.io.File.pathSeparator
    	})
    	return props
	}
	
}