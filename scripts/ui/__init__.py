print "Importing %s..."%(__name__),
import os.path
from main import determine_package
path = os.path.abspath(__file__)
dir_path = os.path.dirname(path)
__all__=determine_package(dir_path)

def configure_ui(width,height):
	print "Executing configure_ui(%d,%d)" % (width,height)
	#from _rocketcore import CreateContext, Vector2i
	from net.cemetech.sfgp.freebuild.drivers import Drivers
	from org.fit.cssbox.layout import BrowserCanvas
	from java.awt import Dimension
	driver = Drivers.getMainDriver()
	if driver == None or width <= 0 or height <= 0:
		ret = False
	else:
		dim = Dimension(width,height)
		#driver.setUiHandle()#GLG2DCanvas())
		#driver.getUiHandle().setSize(dim)
		#CreateContext("primary_ui_context",Vector2i(width,height))
		print " Initializing keymap (GLFW -> libRocket)..."
		from scripts.prefs.keymaps import getKeyMap
		#Drivers.clearKeyMap()
		#for g,r in getKeyMap().iteritems():
		#	Drivers.mapKey(g,r)
		print " Finished initalizing keymap."
		print " Initializing default fonts..."
		from scripts.prefs.fonts import loadDefaultFonts
		loadDefaultFonts()
		print " Finished initializing default fonts."
		
		import main_menu
		main_menu.init(driver.getUiHandle(),dim)
		ret = True
	return ret


print "Success!"
print " Contains these submodules: %s"%(", ".join(__all__))