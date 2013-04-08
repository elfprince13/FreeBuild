import _rocketcore
import _rocketcontrols

import keybindings as kb

def test_keyA():
	print "HelloA"
def test_keyB():
	print "HelloB"

def init(context):
	logo_doc = context.LoadDocument("/data/ui/logo.rml")
	test_binding = kb.BoundKey(test_keyA,kb.named_keys['A'],[kb.named_modifiers['shift']],[kb.named_modifiers['ctrl']] )
	test_binding2 = kb.BoundKey(test_keyB,kb.named_keys['B'])
	kb.bindingRegistry[kb.DEFAULT].anywhere.append(test_binding)
	kb.bindingRegistry[kb.DEFAULT].anywhere.append(test_binding2)
	logo_doc.AddEventListener("keydown",kb.globalKeyHandler,True)
	logo_doc.AddEventListener("keyup",kb.globalKeyHandler,False)
	logo_doc.Show()
	context.Update()