
# Constants for permissioning/binding precedence down the road.

DEFAULT = 0
USER = 1
MOD = 2

bindingRegistry = {}

class BoundKey(object):
	def __init__(self,)

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