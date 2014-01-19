package net.cemetech.sfgp.freebuild

import net.cemetech.sfgp.freebuild.drivers._
import net.cemetech.sfgp.freebuild.platform._
import net.cemetech.sfgp.freebuild.console._

import org.python.util.PythonInterpreter

object Main {
	def main(args:Array[String]) = {
		Console.siezeSystemStreams
		val ls = new FileLoggingService(ConventionMinder.findLogPath,ConventionMinder.findLogPath);
		Console.out.subscribe(ls.getOut());
		Console.err.subscribe(ls.getErr());
		
		var retcode = 0;
		
		Drivers.registerNamedDriver("GFXDriver", classOf[GFXDriver]);
		
		val interpreter = net.cemetech.sfgp.freebuild.console.Python.init(args)
		
		val md:AbstractDriver = Drivers.getMainDriver
		
		if(md != null){
			md.setConsole(interpreter);
			retcode = md.mainloop
			Drivers.clearMainDriver
			md.cleanup
		}
		interpreter.cleanup
		Console.releaseSystemStreams
		System.exit(retcode);
	}
}