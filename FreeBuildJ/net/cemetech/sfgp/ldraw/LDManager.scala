package net.cemetech.sfgp.ldraw

import org.python.core._
import net.cemetech.sfgp.freebuild.drivers._

import scala.collection.immutable.List

object LDManager {
  val prefInit = List(Tuple2(Array("LDraw","Debug","PrintRemarks"), Py.False),
      Tuple2(Array("LDraw","Debug","PrintCacheStats"), Py.False),
      Tuple2(Array("LDraw","Debug","PrintCacheTree"), Py.False),
      Tuple2(Array("LDraw","Debug","RGBOnly"), Py.True),
      Tuple2(Array("LDraw","ConfigPath"), new PyString("")),
      Tuple2(Array("LDraw","ScriptPath"), new PyString("")),
      Tuple2(Array("LDraw","Directory"), new PyList()));
	def init = {
	  prefInit.foreach(pTuple => Drivers.manager().getMainDriver().initDefaultSetting(psContents(pTuple._1), pTuple._2) )
	}
	
	def teardown = {
	  
	}
	
	def psContents(aS:Array[String]):Array[PyString] = {
	  for (s <- aS) yield new PyString(s)
	}
}