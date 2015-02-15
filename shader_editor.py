import sys
import os,os.path
from net.cemetech.sfgp.freebuild.drivers import Drivers
from java.lang import System

def main(*argv):
	print "//---------------------------------------------"
	print
	print "Parsing startup arguments for shader editor"
	Drivers.clearMainDriver()
	
	from net.cemetech.sfgp.freebuild.gfx import GFX
	gfxCtx = GFX.init("Shader Editor Viz. Frame")
	if gfxCtx != None:
		print "Success!"
	else:
		print "Initialization failed."

	print 
	print "---------------------------------------------//"
	print
	
	from scripts.editor import shaderUI
	from java.util.concurrent import TimeUnit
	compiler_hook = shaderUI.init_editor()
	
	
	if gfxCtx != None:
		while gfxCtx.open():
			task = compiler_hook.tasks.poll(300,TimeUnit.MILLISECONDS) # This blocks too hard
			if task != None:
				task.run()

if __name__ == "__main__":
	main(*sys.argv[1:])
