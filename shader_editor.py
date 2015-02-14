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
	from org.lwjgl.opengl import Display
		
	gfxCtx = GFX.init("Shader Editor Viz. Frame")
	if gfxCtx != None:
		print "Success!"
	else:
		print "Initialization failed."

	print 
	print "---------------------------------------------//"
	print
	
	Display.releaseContext()
	
	from net.cemetech.sfgp.glsl.editor import GLSLEditorPane
	
	editor_args = (os.getcwd(),)+argv
	GLSLEditorPane.main(editor_args)
	
	if gfxCtx != None:
		while gfxCtx.open():
			pass

if __name__ == "__main__":
	main(*sys.argv[1:])
