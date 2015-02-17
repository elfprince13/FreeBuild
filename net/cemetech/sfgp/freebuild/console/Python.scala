package net.cemetech.sfgp.freebuild.console


import org.python.util.PythonInterpreter
import org.python.util.InteractiveInterpreter
import org.python.core._

import java.util._
import java.io.File

object Python {
	private val defaultName = "main.py"
  
    def init[T <: PythonInterpreter](in_argv:Array[String],TCon:(PyDictionary,PySystemState) => T):T = {
		
    	val (name:String, args:Array[String]) = if (in_argv.length < 1){
			(defaultName,Array(defaultName))
		} else {
			(in_argv(0),in_argv)
		}
		val interp = clean_init(args,TCon)
		
		if((new File(name)).canRead()){
			interp.execfile(name)
		} else {
			println("Warning: Couldn't initialize main script. Check that it exists, and that you have permission to read it.")
		}
		
		return interp
    }
  
    def clean_init[T <: PythonInterpreter](in_argv:Array[String],TCon:(PyDictionary,PySystemState) => T):T = {
    	val initProps:Properties = setDefaultPythonPath(PySystemState.getBaseProperties)
				
		PySystemState.initialize(initProps,new Properties,in_argv)
		
		val systemState:PySystemState = Py.getSystemState()
		val interp = TCon(new PyDictionary,systemState)
		
		val mainModule:PyObject = __builtin__.__import__("__main__")
		val mainNameSpace:PyObject = mainModule.__getattr__(new PyString("__dict__"))
		
		return interp
    }
	
	
	def setDefaultPythonPath(props:Properties):Properties = {
		val pythonPathProp:String = props.getProperty("python.path");
    	
    	props.setProperty("python.path",if (pythonPathProp==null) {
    		System.getProperty("user.dir");
    	} else {
    		s"$pythonPathProp${java.io.File.pathSeparator}${System.getProperty("user.dir")}${java.io.File.pathSeparator}"
    	})
    	return props
	}
	
}