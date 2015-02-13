package net.cemetech.sfgp.freebuild.boxui
import org.lwjgl._
import org.lwjgl.opengl._
import org.lwjgl.opengl.GL11._
import scala.util.Sorting
import org.lwjgl.util.vector.Matrix4f
import org.fit.cssbox.io.DefaultDocumentSource
import org.fit.cssbox.io.DefaultDOMSource
import org.fit.cssbox.css.DOMAnalyzer
import org.fit.cssbox.css.CSSNorm
import org.fit.cssbox.layout.BrowserCanvas
import java.net.URL
import org.fit.cssbox.layout.Viewport
import cz.vutbr.web.css.MediaSpec
import net.cemetech.sfgp.freebuild.gfx.MatrixStack
import net.cemetech.sfgp.freebuild.gfx.MatrixUtils

object UITest {
	
	def setupDoc(urlstring:String):Viewport = {
		//Open the network connection 
		val docSource = new DefaultDocumentSource(urlstring)

		//Parse the input document
		val parser = new DefaultDOMSource(docSource)
		val doc = parser.parse() //doc represents the obtained DOM

		val ourMedia = new MediaSpec("screen")

		//specify some media feature values
		ourMedia.setDimensions(640, 480) //set the visible area size in pixels
		ourMedia.setDeviceDimensions(640, 480) //set the display size in pixels

		val da = new DOMAnalyzer(doc, docSource.getURL())
		da.setMediaSpec(ourMedia)
		da.attributesToStyles() //convert the HTML presentation attributes to inline styles
		da.addStyleSheet(null, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT) //use the standard style sheet
		da.addStyleSheet(null, CSSNorm.userStyleSheet(), DOMAnalyzer.Origin.AGENT) //use the additional style sheet
		da.addStyleSheet(null, CSSNorm.formsStyleSheet(), DOMAnalyzer.Origin.AGENT) //(optional) use the forms style sheet
		da.getStyleSheets() //load the author style sheets
		
		val browser = new BrowserCanvas(da.getRoot(), da, new URL(urlstring))
		//... modify the browser configuration here ...
		browser.createLayout(new java.awt.Dimension(640, 480))
		
		val viewport = browser.getViewport
		viewport
	}

	def main(args: Array[String]): Unit = {
		val projection = new MatrixStack
		val view = new MatrixStack
		val model = new MatrixStack
		var retcode: Int = 0
		val renderer = new GLBoxRenderer(640, 480)
		val viewport = setupDoc("file:///Users/thomas/Documents/sc_eclipse_workspace/FreeBuild/data/ui/test.html")
		try {
			var modes: Array[DisplayMode] = Display.getAvailableDisplayModes()
			Sorting.stableSort(modes, (dm1: DisplayMode, dm2: DisplayMode) =>
				(
						(dm1.getWidth < dm2.getWidth) ||
						((dm1.getWidth == dm2.getWidth) &&
						(
							(dm1.getHeight < dm2.getHeight) ||
							((dm1.getHeight == dm2.getHeight) && (dm1.getBitsPerPixel < dm2.getBitsPerPixel))))))
			var askFor: DisplayMode = modes(0)
			modes.foreach {
				mode: DisplayMode =>
					println("available mode: " + mode)
					if (askFor.getBitsPerPixel < mode.getBitsPerPixel){
						askFor = mode
					} 
			}
			Display.setDisplayMode(askFor)
			// there's a bug here with how LWJGL requests contexts
			// this should really be 3,3, but it doesn't return one 
			Display.create(new PixelFormat, (new ContextAttribs(3, 2)).withProfileCore(true))
			Console.println("OpenGL Context Initialized with Properties: \n" + checkGLVersions())
			Display.setTitle("BoxUI test")

			// Set up the GL state.
			GL11.glClearColor(0, 0, 0, 1)
			//GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY)
			//GL11.glEnableClientState(GL11.GL_COLOR_ARRAY)
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			projection.push(MatrixUtils.orthoProjection(0, 640, 480, 0, -1, 1))
			val vpMat = new Matrix4f()
			val vars3d = Map[String, Any]("ViewProjection" -> vpMat)
			while (open) {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				/* Render here */

				// uiHandle.paint(uiHandle.getGraphics())
				// ^- enable this once we're ready to do the UI stuff

				Matrix4f.mul(projection.peek, view.peek, vpMat)
				viewport.draw(renderer)
				//renderer.testQuad()

				/* Swap front and back buffers and process events */
				swapBuffers();
			}

		} catch {
			case e: LWJGLException =>
				print("Unable to initialize requisite OpenGL environment")
				e.printStackTrace()
				retcode = -1
		}
		System.exit(retcode)
	}

	def swapBuffers(): Unit = {
			Display.update
			Display.sync(60)
	}

	def open(): Boolean = {
			!Display.isCloseRequested
	}

	def cleanup(): Unit = {
			//renderer.cleanup
			Display.destroy
	}

	def checkNoGLErrors(prepend: String) = {
		val err = GL11.glGetError()
				if (err != GL11.GL_NO_ERROR) {
					throw new LWJGLException(prepend + ", GL Error: 0x" + Integer.toHexString(err))
				}
	}

	def checkGLVersions(): String = {
			"GL Version: " + GL11.glGetString(GL11.GL_VERSION) + "\nGLSL Version: " + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION)
	}

}