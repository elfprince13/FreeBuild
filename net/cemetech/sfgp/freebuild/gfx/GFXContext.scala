package net.cemetech.sfgp.freebuild.gfx

import org.lwjgl._
import org.lwjgl.opengl._
import org.lwjgl.opengl.GL11._

import scala.util.Sorting

class GFXContext {
	val renderer = new InferredRenderer(Display.getWidth, Display.getHeight)

	val projection = new MatrixStack
	val view = new MatrixStack
	val model = new MatrixStack

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

}

object GFX {
	def init(title:String = "FreeBuild/J Engine Demo"): GFXContext = {
		var retcode: Int = 0
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
					if (askFor.getBitsPerPixel < mode.getBitsPerPixel) askFor = mode
			}
			Display.setDisplayMode(askFor)
			// there's a bug here with how LWJGL requests contexts
			// this should really be 3,3, but it doesn't return one 
			Display.create(new PixelFormat,
				(new ContextAttribs(3, 2)).withProfileCore(true))
			Console.println("OpenGL Context Initialized with Properties: \n" + checkGLVersions())
			Display.setTitle(title)

		} catch {
			case e: LWJGLException =>
				print("Unable to initialize requisite OpenGL environment")
				e.printStackTrace()
				retcode = -1
		}
		return if (retcode == 0) (new GFXContext) else null
	}

	def checkNoGLErrors(prepend: String) = {
		val err = GL11.glGetError()
		if (err != GL11.GL_NO_ERROR) {
			throw new LWJGLException(prepend + ", GL Error: 0x" + Integer.toHexString(err) + " (" + Util.translateGLErrorString(err) + ")")
		}
	}
	
	def warnUncheckedErrors(msg:String) = {
		try{ 		  
			GFX.checkNoGLErrors(msg) 
		} catch {
			case e:LWJGLException =>
				System.err.println(e.getMessage)
		}
	}

	def checkGLVersions(): String = {
		"GL Version: " + GL11.glGetString(GL11.GL_VERSION) + "\nGLSL Version: " + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION)
	}

}