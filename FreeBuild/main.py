import sys
import Drivers
def main(*argv):
	Drivers.clearMainDriver()
	driver = Drivers.GFXDriver()
	driver.settings["ui_defs"] = "scripts.ui.main_menu"
	Drivers.setMainDriver(driver)

if __name__ == "__main__":
	main(*sys.argv)