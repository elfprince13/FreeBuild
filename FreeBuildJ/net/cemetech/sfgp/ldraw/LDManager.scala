package net.cemetech.sfgp.ldraw

import org.python.core._
import net.cemetech.sfgp.freebuild.drivers._
import net.cemetech.sfgp.freebuild.platform.ConventionMinder

import scala.collection.immutable.List
import java.io.File

object LDManager {

	val ppmSuffix = List("P","PARTS","MODELS")

	val prefInit = List(Tuple2(Array("LDraw","Debug","PrintRemarks"), Py.False),
					Tuple2(Array("LDraw","Debug","PrintCacheStats"), Py.False),
					Tuple2(Array("LDraw","Debug","PrintCacheTree"), Py.False),
					Tuple2(Array("LDraw","Debug","RGBOnly"), Py.True),
					Tuple2(Array("LDraw","ConfigPath"), new PyString("")),
					Tuple2(Array("LDraw","ScriptPath"), new PyString("")),
					Tuple2(Array("LDraw","Directory"), new PyList));
	
	private var ldDirCache:List[String] = List()
	
	def init = {
		System.out.println("Initializing LDraw Subsystem...")
		prefInit.foreach(pTuple => Drivers.getMainDriver().initDefaultSetting(pTuple._1.map(new PyString(_)), pTuple._2) )
		validateDirectories
		
	}

	def teardown = {

	}
	
	def validateDirectories = {
		val dirInitPref = prefInit(prefInit.size - 1)
		val directory_pref:PyList = try {
			 Drivers.getMainDriver().checkSetting(dirInitPref._1.map(new PyString(_))).asInstanceOf[PyList]
		} catch {
			case e: ClassCastException =>
				System.err.println("settings['LDraw']['Directory'] must be a list!")
				dirInitPref._2.asInstanceOf[PyList]
		}
		
		ldDirCache = directory_pref.getArray.iterator.collect({case s : PyString if validateDirectory(s.asString) => s.asString}).flatMap(ppmDirs(_)).toList
		if(ldDirCache.size < 1 ){
			System.err.println("No valid LDraw directories were found")
		}/* else {
			System.out.println(ldDirCache.foldLeft("")((a,b) => a + " " + b))
		}*/
		
	}
	
	def ppmDirs(dirname:String):List[String] = {
		dirname :: ppmSuffix.map(dirname + ConventionMinder.fsep + _)
	}
	
	def validateDirectory(dirname:String):Boolean = {
		ppmDirs(dirname).foldLeft(true)((a,b) => a && (new File(dirname)).isDirectory())
	}
		
}
