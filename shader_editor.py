import sys
import os,os.path
from net.cemetech.sfgp.freebuild.drivers import Drivers
from java.lang import System


def is_package_dir(parent,path):
	rpath = os.path.join(parent,path)
	return path if (os.path.exists(rpath) and \
					os.path.isdir(rpath) and \
					os.path.exists(os.path.join(rpath,"__init__.py"))) else ""

def is_python_module(path):
	lp = len(path)
	if (lp > 3 and path[-3:] in [".py",".so"]):
		ret = path[:-3]
	elif (lp > 4 and path[-4:] in [".pyc",".pyd",".pyo"]):
		ret = path[:-4]
	else:
		ret = ""
	return ret

def not_excluded_name(path):
	return path not in [".","..","__init__.py","__init__.pyc"]

def determine_package(path):
	if os.path.isdir(path):
		return list(set(is_package_dir(path,fname) or is_python_module(fname) for fname in os.listdir(path) if (is_package_dir(path,fname) or is_python_module(fname)) and not_excluded_name(fname)))
	else:
		raise ValueError("'%s' is not a valid directory" % (path))

# Note the differing default semantics as compared to __import__
def export_symbols(module_name,globals,locals,fromlist,level=0):
	temp_module = __import__(module_name,globals,locals,fromlist,level)
	for symbol in fromlist:
		globals[symbol] = temp_module.__dict__[symbol]


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
