package net.cemetech.sfgp.freebuild;

import net.cemetech.sfgp.freebuild.drivers.*;
import net.cemetech.sfgp.freebuild.platform.*;
import net.cemetech.sfgp.freebuild.console.*;


import org.python.util.PythonInterpreter; 
import org.python.core.*; 

public class Main {
	public static void main(String[] args) {
		Console.siezeSystemStreams();
		FileLoggingService ls = new FileLoggingService(ConventionMinder.findLogPath(),ConventionMinder.findLogPath());
		Console.out().subscribe(ls.getOut());
		Console.err().subscribe(ls.getErr());
		
		int retcode = 0;
		
		Drivers dManager = Drivers.manager();
		dManager.registerNamedDriver("GFXDriver", GFXDriver.class);
		
		PythonInterpreter interpreter = net.cemetech.sfgp.freebuild.console.Python.init(args);
		
		AbstractDriver md = dManager.getMainDriver();
		
		if(md != null){
			md.setConsole(interpreter);
			retcode = md.mainloop();
			dManager.clearMainDriver();
			md.cleanup();
		}
		interpreter.cleanup();
		Console.releaseSystemStreams();
		System.exit(retcode);
		
	}
}
