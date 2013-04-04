import sys
import Drivers
def main(*argv):
	Drivers.clearMainDriver()
	print Drivers.getMainDriver()
	print ""
	drive = Drivers.GFXDriver()
	Drivers.setMainDriver(drive)
	import _rocketcore
	import _rocketcontrols
	print dir(_rocketcontrols)
	print dir(_rocketcore)

if __name__ == "__main__":
	main(*sys.argv)