from scripts.nuparser import NUDomSource
from org.fit.cssbox.io import DefaultDocumentSource

from org.fit.cssbox.css import DOMAnalyzer, CSSNorm

from java.net import URL
from java.io import File as JFile
from java.awt import Dimension
from org.fit.cssbox.layout import BrowserCanvas

#import _rocketcore
#import _rocketcontrols

import keybindings as kb

def test_keyA():
	print "HelloA"
def test_keyB():
	print "HelloB"

def showDebugger():
	print "Showing debugger"

def showConsole():
	print "Showing console"

def init(context,dim):
	
	docSource = DefaultDocumentSource(JFile("./data/ui/logo.html").toURI().normalize().toURL())
	parser = NUDomSource(docSource)
	doc = parser.parse()
	da = DOMAnalyzer(doc, docSource.getURL())
	da.attributesToStyles() #convert the HTML presentation attributes to inline styles
	da.addStyleSheet(None, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT) #use the standard style sheet
	da.addStyleSheet(None, CSSNorm.userStyleSheet(), DOMAnalyzer.Origin.AGENT) #use the additional style sheet
	da.getStyleSheets() #load the author style sheets
	browser = BrowserCanvas(da.getRoot(),da,dim,JFile("./data/ui").toURI().normalize().toURL())
	docSource.close()
	context.setDrawableComponent(browser)
	
	imgTest = browser.getImage()
	#from javax.imageio import ImageIO
	#from java.io import File
	#ImageIO.write(imgTest,"png",File("fleepasheep.png"))
	
	#browser.setSize(200,300)
	#browser.setLocation(200,150)
	
	#from javax.swing import JButton
	#mbutton = JButton("Howdy doo fellers")
	#context.setDrawableComponent(mbutton);
	
	#browser.add(mbutton)
	#mbutton.setSize(300,50)
	#mbutton.setLocation(50,50)
	
	#logo_doc = context.LoadDocument("/data/ui/logo.rml")
	test_binding = kb.BoundKey(test_keyA,kb.named_keys['A'],[kb.named_modifiers['shift']],[kb.named_modifiers['ctrl']] )
	test_binding2 = kb.BoundKey(test_keyB,kb.named_keys['B'])
	
	debuggerBinding = kb.BoundKey(showDebugger,kb.named_keys['`~'],[kb.named_modifiers['ctrl']],[kb.named_modifiers['shift']])
	consoleBinding = kb.BoundKey(showConsole,kb.named_keys['`~'],[kb.named_modifiers['ctrl'],kb.named_modifiers['shift']])
	
	kb.bindingRegistry[kb.DEFAULT].anywhere.append(test_binding)
	kb.bindingRegistry[kb.DEFAULT].anywhere.append(test_binding2)
	kb.bindingRegistry[kb.DEFAULT].anywhere.append(debuggerBinding)
	kb.bindingRegistry[kb.DEFAULT].anywhere.append(consoleBinding)
	#logo_doc.AddEventListener("keydown",kb.globalKeyHandler,True)
	#logo_doc.AddEventListener("keyup",kb.globalKeyHandler,False)
	#logo_doc.Show()
	#context.Update()
	