package net.cemetech.sfgp.freebuild.gfx

import org.lwjgl.BufferUtils
import org.lwjgl.opengl._

class InferredRenderer(width:Int, height:Int) {
	val gbuffer = new FBO
	val lbuffer = new FBO
	
	gbuffer.bind

	private val hWidth = width / 2
	private val hHeight = height / 2
	
	private val d32Buf = BufferUtils.createShortBuffer(4 * hWidth * hHeight)
	BufferUtils.zeroBuffer(d32Buf)
	private val dF32Buf = BufferUtils.createFloatBuffer(hWidth * hHeight)
	BufferUtils.zeroBuffer(dF32Buf)
	
	private val ndsfPUBO = new BufferObject(reqTarget=GL21.GL_PIXEL_UNPACK_BUFFER)
	ndsfPUBO.bind
	ndsfPUBO.bufferData(d32Buf,GL15.GL_STREAM_DRAW)
	
	val ndsfTexture = new Texture
	ndsfTexture.bind
	ndsfTexture.allocate2D(0, GL11.GL_RGBA16, hWidth, hHeight, 0, GL11.GL_RGBA, GL11.GL_SHORT, 0) // Read back with half-float
	gbuffer.attach2D(ndsfTexture, GL30.GL_COLOR_ATTACHMENT0, 0)
	
	private val dPUBO = new BufferObject(reqTarget=GL21.GL_PIXEL_UNPACK_BUFFER)
	dPUBO.bind
	dPUBO.bufferData(dF32Buf, GL15.GL_STREAM_DRAW)
	
	val dTexture = new Texture
	dTexture.bind
	dTexture.allocate2D(0, GL14.GL_DEPTH_COMPONENT32, hWidth, hHeight, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, 0)
	gbuffer.attach2D(dTexture, GL30.GL_DEPTH_ATTACHMENT, 0)
	dTexture.unbind
	
	dPUBO.unbind
  
	gbuffer.check
	gbuffer.unbind
	
	ndsfPUBO.delete
	dPUBO.delete
	
	private val inferredGeoStage:Shader = new Shader("shaders/inferredGeo.vert","shaders/inferredGeo.frag")
	
	def render(scene:Array[Mesh]) = {
		gbuffer.bind
		inferredGeoStage.use
		
		
		inferredGeoStage.unuse
		gbuffer.unbind
	}
	
	def cleanup = {
		gbuffer.delete
		lbuffer.delete
		
		ndsfTexture.delete
		dTexture.delete
	}
}