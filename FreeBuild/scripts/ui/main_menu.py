import _rocketcore
import _rocketcontrols

import keybindings as kb

def test_key():
	print "Hello"

def init(context):
	logo_doc = context.LoadDocument("/data/ui/logo.rml")
	#callback,key_index,required_modifiers=[],forbidden_modifiers=[],action_states=(KEY_PRESSED,)
	kb.bindingRegistry[kb.DEFAULT].anywhere.append(kb.BoundKey(test_key,kb.named_keys['A'],[kb.named_modifiers['shift']],[kb.named_modifiers['ctrl']] ))
	logo_doc.AddEventListener("keydown",kb.globalKeyHandler,True)
	logo_doc.AddEventListener("keyup",kb.globalKeyHandler,False)
	logo_doc.Show()
	context.Update()