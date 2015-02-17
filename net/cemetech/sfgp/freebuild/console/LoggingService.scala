package net.cemetech.sfgp.freebuild.console

import java.io._

import java.util.logging._

import java.nio.charset.Charset

// This should be more interesting later on
trait LoggingService {
	def getOut:PrintStream
	def getErr:PrintStream
	
	def setLevel(l:Level)
}

object LSManager extends ErrorManager {
	var ps = System.err
	override def error(msg:String,ex:Exception, code:Int) = {
		ps.println(s"Our logger blew up with code $code: $msg")
		ex.printStackTrace(ps)
	}
        
}

class MessageOnlyFormatter extends Formatter {
	override def format(record:LogRecord):String = {
		record.getMessage
	}
	override def formatMessage(record:LogRecord):String = {
		record.getMessage
	}
	
	override def getTail(handler:Handler):String = {
		""	
	}
	override def getHead(handler:Handler):String = {
		""
	}
}

abstract class StandardSemanticsLoggingService extends Handler with LoggingService {
	setErrorManager(LSManager)
	override def close() = {
		flush
		getOut.close
		getErr.close
	}
	
	override def flush() = {
		getOut.flush
		getErr.flush
	}
	
	override def publish(record:LogRecord)
}

class GenericLoggingService(msgLoggingStream:OutputStream, errLoggingStream:OutputStream=null, fmt:Formatter = null) extends StandardSemanticsLoggingService {
	
	private def ensurePrintStream(stream:OutputStream):PrintStream = {
		stream match {
			case ps:PrintStream => ps
			case _ => new PrintStream(stream)
		}
	}
	
	@scala.beans.BeanProperty
	val out = ensurePrintStream(msgLoggingStream)
	@scala.beans.BeanProperty
	val err = if(errLoggingStream == null || errLoggingStream == msgLoggingStream){ out } else { ensurePrintStream(errLoggingStream) }
	
	val formatter = if(fmt == null){ new MessageOnlyFormatter } else { fmt }
	setErrorManager(LSManager)
	
	override def publish(record:LogRecord) = {
		if(isLoggable(record)){
			if(record.getLevel.intValue <= Level.INFO.intValue){
				out.println(formatter.format(record))
			} else {
				err.println(formatter.format(record))
			}
		}
	}
	
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
} with GenericLoggingService(outBAOS, errBAOS) {
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
