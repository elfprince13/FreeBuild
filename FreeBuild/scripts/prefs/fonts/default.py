# Load up DejaVu fonts as a nice default

import os, os.path, _rocketcore

font_path = "./data/ui/fonts/dejavu"

def loadDefaultFonts():
	if os.path.exists(font_path) and os.path.isdir(font_path):
		paths = os.listdir(font_path)
		font_files = [os.path.join(font_path,name) for name in paths if len(name)>4 and name[-4:].lower() == ".ttf"]
		for font in font_files:
			_rocketcore.LoadFontFace(font)
	else:
		print "  Could not load fonts"