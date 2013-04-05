import _rocketcore
import _rocketcontrols

def init(context):
	logo_doc = context.LoadDocument("/data/ui/logo.rml")
	logo_doc.Show()
	context.Update()