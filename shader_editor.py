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
	shaderUI.init_editor()
	
	
	if gfxCtx != None:
		while gfxCtx.open():
			pass

if __name__ == "__main__":
	main(*sys.argv[1:])
