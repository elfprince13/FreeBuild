package net.cemetech.sfgp.freebuild.drivers

import java.io.PrintWriter
import java.io.IOException

import org.python.core._

import jline.Terminal
import jline.ConsoleReader

class DedicatedDriver extends AbstractDriver {

	private var ret: Int = 0
	private var exitRequest: Boolean = false
	private var tc: Int = 3 // This could be a pref
	def exit(r:Int) = {
		ret = r
		exitRequest = true
	}
	
	def mainloop():Int = {
		println("Attempting to initiate mainloop.")
		
		Terminal.setupTerminal
		val consoleReader = new ConsoleReader(System.in, new PrintWriter(System.out))
		def raw_input(prompt:PyObject):String = {
	        val line = try {
	            consoleReader.readLine(prompt.toString)
	        } catch {
	        	case io:IOException =>
	        		throw Py.IOError(io)
	            	null
	        }
	        if(line == null) {
	            throw Py.EOFError("Ctrl-D exit")
	        }
	        if(line.endsWith("\n")){
	        	line.substring(0, line.length - 1) 	
	        } else {
	        	line
	        }
	    }
		
		def push(line:String):Boolean = {
	        if(console.buffer.length > 0){
	        	console.buffer.append("\n")
	        }
	        console.buffer.append(line);
	        val more = console.runsource(console.buffer.toString, console.filename);
	        if(!more){
	        	console.resetbuffer
	        }
	        return more;
	    }
		
		var more = false;		
		do {
			val prompt = if(more){
				console.getSystemState.ps2
			} else {
				console.getSystemState.ps1
			}
			try{
				val line = raw_input(prompt)
				tc = 3
				more = push(line)
			} catch {
				case exc:PyException =>
					if(Py.matchException(exc, Py.EOFError)){
						tc -= 1
						if(tc == 0){
							exitRequest = true
						} else {
							console.write(s"Received Ctrl-D.\nUse DedicatedDriver.exit() to exit (or press Ctrl-D ${tc} more times)\n")
						}	
					} else {
						throw exc
					}
			}
		} while(!exitRequest)
		ret
	}
	
}