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

abstract class StandardSemanticsLoggingService extends LoggingService {
	@scala.beans.BeanProperty
	var warnLevel:Int = 0
	@scala.beans.BeanProperty
	var messageLevel:Int = 0
	@scala.beans.BeanProperty
	var errorLevel:Int = 0
	
	def warn(msg:String, level:Int) = {
		if (level >= warnLevel) {
			getErr.print("Warning: ");
			getErr.println(msg)
		}
	}

	def error(msg:String, level:Int) = {
		if (level >= errorLevel) {
			getErr.print("Error: ");
			getErr.println(msg)
		}
	}

	def message(msg:String, level:Int) = {
		if (level >= messageLevel) {
			getOut.println(msg)
		}
	}
}

class GenericLoggingService(msgLoggingStream:PrintStream, errLoggingStream:PrintStream=null) extends StandardSemanticsLoggingService {
	@scala.beans.BeanProperty
	var out = msgLoggingStream
	@scala.beans.BeanProperty
	var err = if(errLoggingStream == null){ out } else { errLoggingStream }
}

class FileLoggingService(msgLoggingPath:String, errLoggingPath:String="") extends {
	private val outLogFile = new File(msgLoggingPath)
	private val outStream = new PrintStream(outLogFile)
	private val errLogFile = if(errLoggingPath == "" || errLoggingPath == msgLoggingPath){ outLogFile } else { new File(errLoggingPath) }
	private val errStream = if(errLogFile == outLogFile) { outStream } else { new PrintStream(errLogFile) }
} with GenericLoggingService(outStream, errStream)

class StringLoggingService(separateOutErr:Boolean = false) extends {
	private val outBAOS = new ByteArrayOutputStream
	private val errBAOS = if(separateOutErr){ new ByteArrayOutputStream } else { outBAOS }
	private val outStream = new PrintStream(outBAOS)
	private val errStream = if(separateOutErr){ new PrintStream(errBAOS) } else { outStream }
} with GenericLoggingService(outStream, errStream) {
	def getLogString(wantErr:Boolean = true):String = {
		if(wantErr) {
			errBAOS.toString()
		} else {
			if(separateOutErr) {
				outBAOS.toString
			} else {
				throw new IllegalArgumentException("Can't decline error text in a combined stream")
			}
		}
	}
}
