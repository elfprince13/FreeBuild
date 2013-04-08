
# Constants for permissioning/binding precedence down the road.

DEFAULT = 0
USER = 1
MOD = 2

KEY_RELEASED = 0
KEY_PRESSED = 1
state_map = {'keyup' : KEY_RELEASED, 'keydown' : KEY_PRESSED}

# Need to choose prefs appropriately!
from scripts.prefs.keymaps import getModifiersByIndex, getModifiersByName, getEffectiveKeyNames, getEffectiveKeyIndices
named_modifiers = getModifiersByName()
indexed_modifiers = getModifiersByIndex()
named_keys = getEffectiveKeyNames()
indexed_keys = getEffectiveKeyIndices()

class BoundKey(object):
	def __init__(self,callback,key_index,required_modifiers=[],forbidden_modifiers=[],action_states=(KEY_PRESSED,)):
		self.key_index = key_index
		self.required = required_modifiers
		self.forbidden = forbidden_modifiers
		self.callback = callback
		
		self.action_states = action_states
		
		self.required_mask = sum(self.required)
		self.forbidden_mask = sum(self.forbidden)

	def test(self,key,masks,state):
		return (self.key_index == key) and ((masks & self.required_mask) or not self.required_mask) and (not (masks & self.forbidden_mask)) and (state in self.action_states)

	def __repr__(self):
		return "BoundKey(%s,%s,required_modifiers=%s,forbidden_modifiers=%s)" % \
				(repr(self.callback), repr(self.key_index), repr(self.required), repr(self.forbidden))

	def __str__(self):
		return ("%s %s %s" % (
							  ("-[%s]" % " ".join([indexed_modifiers[m] for m in self.forbidden]))
							  if self.forbidden else "",
							  ("[%s]" % " ".join([indexed_modifiers[m] for m in self.required]))
							  if self.required else ""
							  ,indexed_keys[self.key_index])).strip()
			

	def __call__(self,key,modifiers,state,*args,**kwargs):
		if(self.test(key,modifiers,state)):
			self.callback(*args,**kwargs)
			ret = True
		else:
			ret = False
		return ret

class BindingSet(object):
	def __init__(self,anywhere=[],byDocument={},byElement={}):
		self.anywhere = anywhere
		self.byDocument = byDocument
		self.byElement = byElement

	def __repr__(self):
		return "BindingSet(anywhere=%s,byDocument=%s,byElement=%s" % (repr(self.anywhere),repr(self.byDocument),repr(self.byElement))

	def __str__(self):
		return """BindingSet:\n\tAnywhere: %s\n\tBy document: %s\n\tBy Element: %s""" % (str(self.anywhere),str(self.byDocument), str(self.byElement))

bindingRegistry =	{
						DEFAULT : BindingSet(),
						USER : BindingSet(),
						MOD: BindingSet(),
						USER|MOD : BindingSet()
					}

def globalKeyHandler():
	global event,element,self
	key =  event.parameters['key_identifier']
	modifiers = [value for value,name in sorted(indexed_modifiers.iteritems()) if event.parameters['%s_key' % (name)]]
	mask = sum(modifiers)
	for bound_level,bound_set in sorted(bindingRegistry.iteritems(),reverse=True):
		# Hoping for now this is the correct way to compare element/document references:
		for bound_key in bound_set.anywhere:
			if bound_key(key,mask,state_map[event.type]):
				event.StopPropagation()
				break
		else:
			for bound_key in bound_set.byDocument.get(self,[]):
				if bound_key(key,mask,state_map[event.type]):
					event.StopPropagation()
					break
			else:
				if "element" not in globals():
					continue
				else:
					for bound_key in bound_set.byElement.get(element,[]):
						if bound_key(key,mask,state_map[event.type]):
							event.StopPropagation()
							break
					else:
						continue
		break
