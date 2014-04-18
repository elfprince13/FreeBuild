package net.cemetech.sfgp.freebuild.gfx

import org.lwjgl._
import org.lwjgl.opengl._
import org.lwjgl.opengl.GL11._


import scala.util.Sorting

class GFXContext {
	//val renderer = new InferredRenderer(Display.getWidth, Display.getHeight)
  
  val matrices = new MatrixStack
	
	def swapBuffers():Unit = {
		Display.update
	}
	
	def open():Boolean = {
	  !Display.isCloseRequested
	}
	
	def cleanup():Unit = {
		//renderer.cleanup
	  Display.destroy
	}
	
}

object GFX {
  def init():GFXContext = {
    	var retcode:Int = 0
    	try{
    		var modes:Array[DisplayMode] = Display.getAvailableDisplayModes()
    		Sorting.stableSort(modes, (dm1:DisplayMode, dm2:DisplayMode) => 
    		  (
    		      (dm1.getWidth < dm2.getWidth) || 
    		      ((dm1.getWidth == dm2.getWidth) && 
    		          (
    		              (dm1.getHeight < dm2.getHeight) || 
    		              ((dm1.getHeight == dm2.getHeight) && (dm1.getBitsPerPixel < dm2.getBitsPerPixel))
    		           )
    		       )
    		   )
    		)
    		var askFor:DisplayMode = modes(0)
    	    modes.foreach {
    		  mode:DisplayMode => println("available mode: " + mode)
    		  if (askFor.getBitsPerPixel < mode.getBitsPerPixel) askFor = mode
    		}
    		Display.setDisplayMode(askFor)
    		Display.create(new PixelFormat,
    		    (new ContextAttribs(3, 3)).withProfileCore(true)
    		)
    		Display.setTitle("FreeBuild/J Engine Demo")
    		
    		
    	} catch {
    	  case e:LWJGLException => print("Unable to initialize requisite OpenGL environment")
    	  e.printStackTrace()
    	  retcode = -1
    	}
    return if (retcode == 0) (new GFXContext) else null
  }
  
  def checkNoGLErrors(prepend:String) = {
    val err = GL11.glGetError()
    if (err != GL11.GL_NO_ERROR) {
      throw new LWJGLException(prepend +", GL Error: 0x" + Integer.toHexString(err))
    }
  }
}