package net.cemetech.sfgp.freebuild.drivers

import java.io.PrintWriter
import jline.ConsoleReader

class DedicatedDriver extends AbstractDriver {

	private var ret: Int = 0
	private var exitRequest: Boolean = false
	def exit(r:Int) = {
		ret = r
		exitRequest = true
	}
	
	def mainloop():Int = {
		println("Attempting to initiate mainloop.")
		val console = new ConsoleReader(System.in, new PrintWriter(System.out))
		do {
			console.printString(">>> ")
			val read = console.readLine
			System.err.println(read)
			System.err.flush()
		} while(!exitRequest)
		ret
	}
}