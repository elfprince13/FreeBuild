package net.cemetech.sfgp.freebuild.drivers

import java.util.logging.Logger
import org.python.util.PythonInterpreter


import org.python.core._
import org.python.util.InteractiveInterpreter

import net.cemetech.sfgp.freebuild.platform.ConventionMinder

abstract class AbstractDriver {

	@scala.beans.BeanProperty
	var settings: PyDictionary = new PyDictionary()
	protected var console: InteractiveInterpreter = null
	
	val logger = Logger.getLogger(ConventionMinder.getSubpackageString("drivers"))

	def mainloop(): Int

	def setConsole(npc: InteractiveInterpreter): Unit = { console = npc }
	def getConsole(): InteractiveInterpreter = { console }

	private def handleBrokenDictHierarchy(hierarchy:Array[PyString], i:Int, curDepth:PyObject, retVal:PyObject) = {
		logger.warning(f"settings hierarchy broken by non-dictionary type:\n\tinspected settings${(0 until i).map( j => f"[${hierarchy(j).__repr__().asString()}%s]").mkString}%s  and found ${curDepth.__repr__().asString()}%s instead")		
		retVal
	}
	
	private def recurseDictLayer(dict:PyObject, hierarchy:Array[PyString],depth:Int, dictProbe:(PyDictionary, Array[PyString], Int) => PyObject):Option[PyObject] = {
		dict match {
			case d:PyDictionary =>
				Some(dictProbe(d, hierarchy, depth))
			case _ =>
				None
		}
	}
	
	def traverseDictByLayers(dict:PyObject, hierarchy:Array[PyString], depth:Int, dictProbe:(PyDictionary, Array[PyString], Int) => PyObject, default:PyObject):PyObject = {
		if(depth == hierarchy.length){
			dict
		} else {
			recurseDictLayer(dict, hierarchy, depth, dictProbe) match {
				case Some(obj) => traverseDictByLayers(obj, hierarchy, depth + 1, dictProbe, default)
				case None => handleBrokenDictHierarchy(hierarchy,depth,dict,default)
			}
			
		}
	}
	
	def initDefaultSetting(hierarchy: Array[PyString], defaultValue: PyObject): PyObject = {
		traverseDictByLayers(settings, hierarchy, 0, {
			(dict:PyDictionary, hierarchy:Array[PyString], i:Int) => 
				dict.setdefault(hierarchy(i),
						if(i + 1 == hierarchy.length){
							defaultValue
						} else {
							new PyDictionary()
						})
			}, defaultValue)
	}

	def checkSetting(hierarchy: Array[PyString]): PyObject = {
		traverseDictByLayers(settings, hierarchy, 0, {
			(dict:PyDictionary, hierarchy:Array[PyString], i:Int) => dict.get(hierarchy(i), Py.None)
		}, Py.None)
	}

	def cleanup(): Unit = { setConsole(null) }
}
