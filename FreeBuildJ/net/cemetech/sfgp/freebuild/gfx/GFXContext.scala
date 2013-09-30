package net.cemetech.sfgp.freebuild.gfx

import org.lwjgl._
import org.lwjgl.opengl._
import org.lwjgl.opengl.GL11._


import scala.util.Sorting

class GFXContext {
  
  val gbuffer = new FBO
  val lbuffer = new FBO
  
  gbuffer.bind
  
  private def hWidth = {Display.getWidth / 2}
  private def hHeight = {Display.getHeight / 2}
  
  private val d32Buf = BufferUtils.createShortBuffer(4 * hWidth * hHeight)
  BufferUtils.zeroBuffer(d32Buf)
  private val dF32Buf = BufferUtils.createFloatBuffer(hWidth * hHeight)
  BufferUtils.zeroBuffer(dF32Buf)
  
  private val ndsfPUBO = GL15.glGenBuffers()
  GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, ndsfPUBO)
  d32Buf.rewind
  GL15.glBufferData(GL21.GL_PIXEL_UNPACK_BUFFER, d32Buf, GL15.GL_STREAM_DRAW)
  
  val ndsfTexture = new Texture
  ndsfTexture.bind
  ndsfTexture.allocate2D(0, GL11.GL_RGBA16, hWidth, hHeight, 0, GL11.GL_RGBA, GL11.GL_SHORT, 0) // Read back with half-float
  gbuffer.attach2D(ndsfTexture, GL30.GL_COLOR_ATTACHMENT0, 0)
  
  private val dPUBO = GL15.glGenBuffers()
  GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, dPUBO)
  dF32Buf.rewind
  GL15.glBufferData(GL21.GL_PIXEL_UNPACK_BUFFER, dF32Buf, GL15.GL_STREAM_DRAW)
  
  val dTexture = new Texture
  dTexture.bind
  dTexture.allocate2D(0, GL14.GL_DEPTH_COMPONENT32, hWidth, hHeight, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, 0)
  gbuffer.attach2D(dTexture, GL30.GL_DEPTH_ATTACHMENT, 0)
  dTexture.unbind
  
  GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, 0)
  
  gbuffer.check
  gbuffer.unbind
  
  
  val matrices = new MatrixStack
	
	def swapBuffers():Unit = {
		Display.update
	}
	
	def open():Boolean = {
	  !Display.isCloseRequested
	}
	
	def cleanup():Unit = {
	  gbuffer.delete
	  lbuffer.delete
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
    		    (new ContextAttribs(3, 2)).withProfileCore(true)
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