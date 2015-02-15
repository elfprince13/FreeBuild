import sys
import os,os.path
from net.cemetech.sfgp.freebuild.drivers import Drivers
from java.lang import System
from org.lwjgl import LWJGLException


def main(*argv):
	print "//---------------------------------------------"
	print
	print "Parsing startup arguments"
	if not argv or "--dedicated" not in argv[1:]:
		Drivers.clearMainDriver()
		GFXDriver = Drivers.getNamedDriver("GFXDriver")
		driver = GFXDriver()
		
		# Here we should explicitly load a settings file
		import json
		with open("data/prefs/defaults.json",'r') as defaults:
			for k,v in json.load(defaults).items(): driver.settings()[k] = v
		if os.path.isfile("data/prefs/prefs.json"):
			with open("data/prefs/prefs.json",'r') as prefs:
				for k,v in json.load(prefs).items(): driver.settings()[k] = v
		else:
			with open("data/prefs/prefs.json",'w') as prefs:
				json.dump(driver.settings(), prefs)
							
		Drivers.setMainDriver(driver)
		
		from net.cemetech.sfgp.ldraw import LDManager
		LDManager.init()
		LDManager.parseModel("car.dat")
		print driver.settings()
		
		from scripts.gfx import framebuffer_builder
		framebuffer_builder.exec_test("data/shaders/framebuffer-test.json")
	else:
		print "Dedicated server requested"
		print "But no such driver exists"
	print 
	print "---------------------------------------------//"
	print

if __name__ == "__main__":
	main(*sys.argv)