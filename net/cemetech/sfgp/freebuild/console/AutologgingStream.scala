package net.cemetech.sfgp.freebuild.console

import java.io.Writer
import java.util.logging._
import org.apache.commons.io.output.WriterOutputStream

class AutologgingWriter(logger:Logger, level:Level) extends Writer {

	val buf = new StringBuilder
	var flushed = true
	var closed = false
	
	// We'll assume each line is a separate log entry
	// For more sophisticated behavior, you really ought to be calling the logger directly
	override def write(b:Array[Char], off:Int, len:Int) = this.synchronized{
		if(!closed){
			val workFrom = b.slice(off,off+len)
			
			val (log, remainder) = workFrom.span(e=> e != '\n' && e != '\r')
			flushed = false
			buf.appendAll(log)
			if(log.size < len) {
				flush
			}
			val nNL = remainder.dropWhile(e => e == '\n' || e == '\r')
			if(nNL.size > 0) {
				write(nNL, 0, nNL.size)
			}
		}
	}
	
	override def close() = this.synchronized{
		flush
		closed = true
	}
	
	override def flush() = this.synchronized{
		if(!flushed){
			val out = buf.mkString
			if(out != ""){
				logger.log(level, out)	
			}
			buf.clear
			flushed = true	
		}
	}
}

class AutologgingOutputStream(logger:Logger, level:Level) extends WriterOutputStream(new AutologgingWriter(logger, level))