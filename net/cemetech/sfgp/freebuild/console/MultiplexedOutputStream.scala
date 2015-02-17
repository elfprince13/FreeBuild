package net.cemetech.sfgp.freebuild.console

import java.io.OutputStream
import scala.collection.mutable.HashSet

class MultiplexedOutputStream extends OutputStream {
	val outputStreams = new HashSet[OutputStream]
	
	override def close = {
	  outputStreams.map(_.close)
	  outputStreams.clear
	}
	
	override def flush = {
	  outputStreams.map(_.flush)
	}
	
	override def write(b:Array[Byte]) = {
	  outputStreams.map(_.write(b))
	}
	
	override def write(b:Array[Byte], off:Int, len:Int) = {
	  outputStreams.map(_.write(b,off,len))
	}
	
	override def write(b:Int) = {
	  outputStreams.map(_.write(b))
	}
	
	def subscribe(o:OutputStream) = {
	  outputStreams += o
	}
	
	def unsubscribe(o:OutputStream) = {
	  outputStreams -= o
	}
	
}