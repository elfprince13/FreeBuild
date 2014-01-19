package net.cemetech.sfgp.freebuild.drivers

import org.python.util.PythonInterpreter
import org.python.core._

abstract class AbstractDriver {
	
	@scala.reflect.BeanProperty
	var settings:PyDictionary = new PyDictionary()
	protected var console:PythonInterpreter = null
	
	def mainloop():Int
	
	
	def setConsole( npc:PythonInterpreter ):Unit = { console = npc }
	def getConsole():PythonInterpreter = { console }
	
	def initDefaultSetting(hierarchy:Array[PyString], defaultValue:PyObject):PyObject = {
		var curDepth:PyObject = settings
		var i = 0 // this is ugly and should probably be rewritten in a more functional way
		// but I'm not sure about the scoping, so it stays for now.
		try{
			while(i < hierarchy.length){
				curDepth = curDepth.asInstanceOf[PyDictionary].setdefault(
						hierarchy(i), 
						if (i + 1 == hierarchy.length) {
							defaultValue
						} else {
							new PyDictionary()
						}
				)
				i = i+1
			}
			curDepth
		} catch {
			case e:ClassCastException => 
				System.err.println("settings hierarchy broken by non-dictionary type:")
				System.err.print("\tinspected settings")
				for(j <- 0 until i) {
					System.err.printf("[%s]",hierarchy(j).__repr__().asString())
				}
				System.err.printf(" and found %s instead",curDepth.__repr__().asString())
				e.printStackTrace()
				defaultValue
		}
	}
	
	def checkSetting(hierarchy:Array[PyString]):PyObject = {
		var curDepth:PyObject = settings
		var i = 0
		try{
			while(i < hierarchy.length){
				curDepth = curDepth.asInstanceOf[PyDictionary].get(hierarchy(i), Py.None)
				i = i + 1
			}
			curDepth
		} catch {
			case e: ClassCastException =>
				System.err.println("settings hierarchy broken by non-dictionary type:")
				System.err.print("\tinspected settings")
				for(j <- 0 until i) {
					System.err.printf("[%s]",hierarchy(j).__repr__().asString())
				}
				System.err.printf(" and found %s instead",curDepth.__repr__().asString())
				e.printStackTrace()
				Py.None
		}
	}
	
	def cleanup():Unit = {	setConsole(null) }

	//public void replaceSettings(PyDictionary newSettings){ settings = newSettings; }
	//public PyDictionary getSettings(){ return settings; }


}
