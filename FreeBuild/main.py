import sys
import os,os.path
import Drivers

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


def main(*argv):
	Drivers.clearMainDriver()
	driver = Drivers.GFXDriver()
	driver.settings["ui_defs"] = "scripts.ui"
	Drivers.setMainDriver(driver)

if __name__ == "__main__":
	main(*sys.argv)