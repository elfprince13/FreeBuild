package net.cemetech.sfgp.freebuild.console

import java.io._

// This should be more interesting later on
trait LoggingService {
	def setWarnLevel(level:Int)
	def setMessageLevel(level:Int)
	def setErrorLevel(level:Int)
	
	def getWarnLevel:Int
	def getMessageLevel:Int
	def getErrorLevel:Int
	
	def warn(msg:String, level:Int)
	def error(msg:String, level:Int)
	def message(msg:String, level:Int)
	
	def setOut(p:PrintStream)
	def setErr(p:PrintStream)
	
	def getOut:PrintStream
	def getErr:PrintStream
}

class FileLoggingService(msgLoggingPath:String, errLoggingPath:String="") extends LoggingService {
  @scala.beans.BeanProperty
  var warnLevel:Int = 0
  @scala.beans.BeanProperty
  var messageLevel:Int = 0
  @scala.beans.BeanProperty
  var errorLevel:Int = 0
  
  
  	
  	val outLogFile = new File(msgLoggingPath)
	@scala.beans.BeanProperty
	var out = new PrintStream(outLogFile)
    val errLogFile = if(errLoggingPath == "" || errLoggingPath == msgLoggingPath){ outLogFile } else { new File(errLoggingPath) }
	@scala.beans.BeanProperty
	var err = if(errLogFile == outLogFile) { out } else { new PrintStream(errLogFile) }
	
	def warn(msg:String, level:Int) = {
		if (level >= warnLevel) {
		  err.print("Warning: ");
		  err.println(msg)
		}
    }
	
	def error(msg:String, level:Int) = {
    	if (level >= errorLevel) {
		  err.print("Warning: ");
		  err.println(msg)
		}
    }
	
	def message(msg:String, level:Int) = {
    	if (level >= messageLevel) {
		  out.println(msg)
		}
    }
}