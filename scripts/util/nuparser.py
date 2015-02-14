from org.fit.cssbox.io import DOMSource
from nu.validator.htmlparser.dom import HtmlDocumentBuilder
from org.xml.sax import InputSource

class NUDomSource(DOMSource):
    def __init__(self, src):
        DOMSource.__init__(self, src)
        
    def parse(self):
        dbuilder = HtmlDocumentBuilder()
        return dbuilder.parse(InputSource(self.getDocumentSource().getInputStream()))
