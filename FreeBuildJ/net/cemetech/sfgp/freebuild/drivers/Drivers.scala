package net.cemetech.sfgp.freebuild.drivers;

import scala.collection._

object Drivers {
	
	protected var mainDriver:AbstractDriver = null
	protected var knownDrivers:Map[String, Class[_ <:  AbstractDriver]] = Map[String, Class[_ <: AbstractDriver]]()
		
	def setMainDriver(nd:AbstractDriver):Unit = { mainDriver = nd }
	def getMainDriver():AbstractDriver = { mainDriver }
	def clearMainDriver():Unit = { mainDriver = null }
	
	def getNamedDriver(name:String):Class[_ <: AbstractDriver] = { knownDrivers.getOrElse(name,null) }
	
	def registerNamedDriver(name:String, newDriver:Class[_ <: AbstractDriver]):Class[_ <: AbstractDriver] = { 
		val current = getNamedDriver(name)
		knownDrivers = knownDrivers + (name -> newDriver)
		current
	}
	
	def unregisterDriver(name:String) = {
		val current = getNamedDriver(name)
		knownDrivers = knownDrivers - name
		current
	}
	
}
