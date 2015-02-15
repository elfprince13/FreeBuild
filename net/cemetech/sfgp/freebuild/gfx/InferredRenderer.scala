package net.cemetech.sfgp.freebuild.gfx

import org.lwjgl.BufferUtils
import org.lwjgl.opengl._

class InferredRenderer(width:Int, height:Int) {
	val gbuffer = new FBO
	val lbuffer = new FBO

	gbuffer.bind

	private val hWidth = width / 2
	private val hHeight = height / 2

	// We don't need to pre-initialize these to be empty
	// We always write before reading
	val ndsfTexture = new Texture
	ndsfTexture.bind
	ndsfTexture.allocate2D(0, GL11.GL_RGBA16, hWidth, hHeight, 0, GL11.GL_RGBA, GL11.GL_SHORT) // Read back with half-float
	gbuffer.attach2D(ndsfTexture, GL30.GL_COLOR_ATTACHMENT0, 0)

	val dTexture = new Texture
	dTexture.bind
	dTexture.allocate2D(0, GL14.GL_DEPTH_COMPONENT32, hWidth, hHeight, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT)
	gbuffer.attach2D(dTexture, GL30.GL_DEPTH_ATTACHMENT, 0)
	dTexture.unbind

	gbuffer.check
	gbuffer.unbind

	GFX.checkNoGLErrors("Error creating textures for inferred rendering:")

	private val inferredGeoStage:GLSLProgram = ShaderManager.programFromBundle("data/shaders/inferredGeo.glsl")
	GFX.checkNoGLErrors("Error creating shaders for inferred rendering:")

	def render(scene:Array[Mesh],shaderVars:Map[String,Any]) = {
		gbuffer.bind
		inferredGeoStage.use
		inferredGeoStage.setVariables(shaderVars)
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
