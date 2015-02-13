package net.cemetech.sfgp.freebuild.boxui

import org.fit.cssbox.render.BoxRenderer
import org.lwjgl._
import org.lwjgl.opengl._
import org.fit.cssbox.layout.ElementBox
import org.fit.cssbox.layout.Viewport
import java.awt.Color
import org.newdawn.slick.opengl.Texture
import scala.collection.JavaConverters._

/**
 * A renderer that produces OpenGL output.
 *
 * @author elfprince13
 */

class GLBoxRenderer(width:Int, height:Int) extends BoxRenderer {
	
	// Necessary geometry:
	// Border!
	// Background tiles
	// Text content <- rendered to texture?
	
	var boxVAOs = Map[ElementBox, Int]()
	def close(): Unit = {
		GL30.glBindVertexArray(0)
		boxVAOs.values.foreach{
			vaoID =>
				GL30.glDeleteVertexArrays(vaoID)
		}
		boxVAOs = Map[ElementBox, Int]()
	}
	def finishElementContents(x$1: ElementBox): Unit = {}
	
	def testQuad() = {
		val vaoID = GL30.glGenVertexArrays
		drawBGQuad(vaoID, 0, null, 40, 40, 100, 100)
		GL30.glDeleteVertexArrays(vaoID)
	}
	
	def drawBGQuad(vaoID:Int, texID:Int, color:Color, sx:Float, sy:Float, ex:Float, ey:Float):Unit = {
		GL30.glBindVertexArray(vaoID)
		val vertsArray = Array[Float](sx, sy, sx, ey, ex, ey, ex, sy)
		val vertsBuffer = BufferUtils.createFloatBuffer(vertsArray.length)
		vertsBuffer.put(vertsArray)
		vertsBuffer.flip
		
		val uvArray = Array[Float](0,0,0,1,1,1,1,0) // This should be the same for literally every quad we do this way.... move it elsewhere?
		val uvBuffer = BufferUtils.createFloatBuffer(uvArray.length)
		uvBuffer.put(uvArray)
		uvBuffer.flip		
		
		val iArray = Array[Byte](0,1,2,2,3,0) // This should be the same for literally every quad we do this way.... move it elsewhere?
		val iBuffer = BufferUtils.createByteBuffer(iArray.length)
		iBuffer.put(iArray)
		iBuffer.flip
		
		val vboId = GL15.glGenBuffers
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId)
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertsBuffer, GL15.GL_STATIC_DRAW)
		
		// Put the VBO in the attributes list at index 0
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);
		
		val uvboId = GL15.glGenBuffers
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, uvboId)
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvBuffer, GL15.GL_STATIC_DRAW)

		// Put the VBO in the attributes list at index 0
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);

		
		// Deselect (bind to 0) the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		// Create a new VBO for the indices and select it (bind)
		val iboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, iBuffer, GL15.GL_STATIC_DRAW);
		// Deselect (bind to 0) the VBO
		//GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		GL20.glEnableVertexAttribArray(0)
		GL20.glEnableVertexAttribArray(1)
		//GL11.glDrawElements(GL11.GL_TRIANGLES, iArray.length, GL11.GL_UNSIGNED_BYTE, 0)
		
		// Deselect (bind to 0) the IBO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0)
		
		// Disable the VBO index from the VAO attributes list
		GL20.glDisableVertexAttribArray(0)
		GL20.glDisableVertexAttribArray(1)

		// Delete the vertex VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
		GL15.glDeleteBuffers(vboId)
		GL15.glDeleteBuffers(uvboId)

		// Delete the index VBO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0)
		GL15.glDeleteBuffers(iboId)

		// Unbind the VAO
		GL30.glBindVertexArray(0)
	}
	
	def renderElementBackground(elm: ElementBox): Unit = {
		val vaoID = boxVAOs.getOrElse(elm, GL30.glGenVertexArrays)
		if (!boxVAOs.contains(elm)){
			boxVAOs = boxVAOs + (elm -> vaoID)
		}
		
		val bb = if (elm.isInstanceOf[Viewport]){
			elm.getClippedBounds(); //for the root box (Viewport), use the whole clipped content
		} else {
			elm.getAbsoluteBorderBounds
		}
		val bg = if(elm.getBgcolor == null){
			new Color(255,255,255,0)
		} else {
			elm.getBgcolor()
		}
		
		val bgImgs = elm.getBackgroundImages
		val bgTexture:Int = (if(bgImgs != null){
			bgImgs.asScala.take(1).map{
				bgImg =>
					val img = bgImg.getBufferedImage // <- If we have performance issues, this should be done on GPU!
					// Even if we don't, it probably still should be, for memory reasons
					if(img == null){
						throw new IllegalStateException("got background image with no image inside")
					}
					
					
					 val pixels = new Array[Int](img.getWidth * img.getHeight)
					 img.getRGB(0, 0, img.getWidth, img.getHeight, pixels, 0, img.getWidth)
					 
					 val buffer = BufferUtils.createByteBuffer(img.getWidth * img.getHeight * 4); //4 for RGBA, 3 for RGB
					 (0 until img.getHeight).foreach{
						 y =>
							 (0 until img.getWidth).foreach{
								 x =>
									 val pixel = pixels(y * img.getWidth + x)
									 buffer.put(((pixel >> 16) & 0xFF).asInstanceOf[Byte])	// Red component
									 buffer.put(((pixel >> 8) & 0xFF).asInstanceOf[Byte])	// Green component
									 buffer.put((pixel & 0xFF).asInstanceOf[Byte])			// Blue component
									 buffer.put(((pixel >> 24) & 0xFF).asInstanceOf[Byte])	// Alpha component. Only for RGBA
							 }
					 }
					 
					 buffer.flip //FOR THE LOVE OF GOD DO NOT FORGET THIS
					 val texID = GL11.glGenTextures
					 // You now have a ByteBuffer filled with the color data of each pixel.
					 // Now just create a texture ID and bind it. Then you can load it using 
					 // whatever OpenGL method you want, for example:
					 GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, img.getWidth, img.getHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer)
					texID
			}
		} else {
			List[Int](0)
		}).head
		
		val ix = bb.x + elm.getBorder.left
		val iy = bb.y + elm.getBorder.top
		val iw = bb.width - elm.getBorder.right - elm.getBorder.left
		val ih = bb.height - elm.getBorder.bottom - elm.getBorder.top
		
		drawBGQuad(vaoID, bgTexture, bg, ix, iy, ix+iw, iy+ih)
		
	}
	def renderReplacedContent(x$1: org.fit.cssbox.layout.ReplacedBox): Unit = {}
	def renderTextContent(x$1: org.fit.cssbox.layout.TextBox): Unit = {}
	
	def startElementContents(elm: ElementBox): Unit = {
	}
	
}