import _rocketcore
import _rocketcontrols

import keybindings as kb

def test_key():
	print "Hello"

def init(context):
	logo_doc = context.LoadDocument("/data/ui/logo.rml")
	test_binding = kb.BoundKey(test_key,kb.named_keys['A'],[kb.named_modifiers['shift']],[kb.named_modifiers['ctrl']] )
	print test_binding
	kb.bindingRegistry[kb.DEFAULT].anywhere.append(test_binding)
	logo_doc.AddEventListener("keydown",kb.globalKeyHandler,True)
	logo_doc.AddEventListener("keyup",kb.globalKeyHandler,False)
	logo_doc.Show()
	context.Update()