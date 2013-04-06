
# Constants for permissioning/binding precedence down the road.

DEFAULT = 0
USER = 1
MOD = 2

bindingRegistry = {}

from scripts.prefs.keymaps.default import getModifiersByName
modifiers = getModifiersByName()

class BoundKey(object):
	def __init__(self,callback,name,required_modifiers=[],forbidden_modifiers=[])
		self.name = name
		self.required = required_modifiers
		self.forbidden = forbidden_modifiers
		self.callback = callback

		self.required_mask = sum(modifiers[mod] for mod in self.required)
		self.forbidden_mask = sum(modifiers[mod] for mod in self.forbidden)

	def __repr__(self):
		return "BoundKey(%s,%s,required_modifiers=%s,forbidden_modifiers=%s)" %
				(repr(self.callback), repr(self.name), repr(self.required), repr(self.forbidden))

	def __str__(self):
		return ("%s %s %s" % ("-[]","[-.-.-.-]",self.name)).strip()

	def __call__(self,*args,**kwargs):
		return self.callback(*args,**kwargs)

class BindingSet(object):
	def __init__(self,anywhere={},byDocument={},byElement={}):
		self.anywhere = anywhere
		self.byDocument = byDocument
		self.byElement = byElement

	def __repr__(self):
		return "BindingSet(anywhere=%s,byDocument=%s,byElement=%s" % (repr(self.anywhere),repr(self.byDocument),repr(self.byElement))

	def __str__(self):
		return """BindingSet:\n\tAnywhere: %s\n\tBy document: %s\n\tBy Element: %s""" % (str(self.anywhere),str(self.byDocument), str(self.byElement))

def globalKeyHandler():
	global event,element,self