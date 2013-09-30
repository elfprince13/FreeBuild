print "Importing %s..."%(__name__),
import os.path
from main import determine_package,export_symbols
from net.cemetech.sfgp.freebuild.drivers import Drivers
path = os.path.abspath(__file__)
dir_path = os.path.dirname(path)
__all__=determine_package(dir_path)

export_symbols(Drivers.manager().getMainDriver().settings.get("keymap","%s.default" %(__name__)),
			   globals(),
			   locals(),
			   [
				"getKeyMap",
				"getEffectiveKeyNames",
				"getEffectiveKeyIndices",
				"getBackendKeyNames",
				"getBackendKeyIndices",
				"getModifiersByName",
				"getModifiersByIndex"
				])

# We want to make it so that we only have to
# import prefs.keymaps to get the right keymap
# Let's think about this some more! :)

print "Success!"
print " Contains these submodules: %s"%(", ".join(__all__))