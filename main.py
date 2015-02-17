import sys
import os,os.path
from net.cemetech.sfgp.freebuild.drivers import Drivers
from net.cemetech.sfgp.ldraw import LDManager
	
from java.lang import System
from org.lwjgl import LWJGLException

def common_setup(driver,*argv):
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
	
	LDManager.init()
	print driver.settings()

def gfx_setup():
		LDManager.parseModel("car.dat")
		
		from scripts.gfx import framebuffer_builder
		framebuffer_builder.exec_test("data/shaders/framebuffer-test.json")

def main(*argv):
	print "//---------------------------------------------"
	print
	print "Parsing startup arguments"
	if not argv or "--dedicated" not in argv:
		Drivers.clearMainDriver()
		GFXDriver = Drivers.getNamedDriver("GFXDriver")
		driver = GFXDriver()
		Drivers.setMainDriver(driver)
		
		common_setup(driver)
		gfx_setup()
	else:
		Drivers.clearMainDriver()
		DedicatedDriver = Drivers.getNamedDriver("DedicatedDriver")
		driver = DedicatedDriver()
		Drivers.setMainDriver(driver)
		common_setup(driver)
	print 
	print "---------------------------------------------//"
	print

if __name__ == "__main__":
	main(*sys.argv[1:])
