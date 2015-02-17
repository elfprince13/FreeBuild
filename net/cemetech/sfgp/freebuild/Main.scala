package net.cemetech.sfgp.freebuild

import net.cemetech.sfgp.freebuild.drivers._
import net.cemetech.sfgp.freebuild.platform._
import net.cemetech.sfgp.freebuild.console._

import java.util.logging.Logger

import org.python.util.InteractiveInterpreter

object Main {
	def main(args:Array[String]) = {
		Console.siezeSystemStreams
		val ls = new FileLoggingService(ConventionMinder.findLogPath,ConventionMinder.findLogPath);
		Console.subscribe(ls.getOut);
		Console.subscribe(ls.getErr,true);
		
		
		
		var retcode = 0;
		
		Drivers.registerNamedDriver("GFXDriver", classOf[GFXDriver]);
		Drivers.registerNamedDriver("DedicatedDriver", classOf[DedicatedDriver]);
		
		val interpreter = net.cemetech.sfgp.freebuild.console.Python.init(args,new InteractiveInterpreter(_,_))
		
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