package net.cemetech.sfgp.freebuild.gfx

import org.lwjgl.opengl._
import org.lwjgl._
import org.lwjgl.BufferUtils
import java.nio.IntBuffer;

object FBOManager {
	var boundNow: FBO = null;
	def genMany(requested: IntBuffer): IntBuffer = {
		GL30.glGenFramebuffers(requested)
		requested
	}

}

class FBO(reqID: Int = 0) {
	val id = if (reqID == 0) GL30.glGenFramebuffers() else reqID
	var ownedTextures: Map[Int, Texture] = Map[Int, Texture]()

	def bound = { FBOManager.boundNow == this }
	def bind() = {
		if (!bound) {
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, id)
			FBOManager.boundNow = this;
		}
	}

	def unbind() = {
		if (bound) {
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0)
			FBOManager.boundNow = null;
		}
	}

	def attach2D(tex: Texture, attachment: Int, level: Int, own: Boolean = false) = {
		if (bound) {
			GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, attachment, tex.target, tex.id, level)
			if (ownedTextures.contains(attachment)) {
				ownedTextures(attachment).delete
				ownedTextures = ownedTextures - attachment
			}
			if (own) {
				ownedTextures = ownedTextures + (attachment -> tex)
			}
		} else { warnUnbound }
	}

	def delete = {
		if (bound) unbind
		GL30.glDeleteFramebuffers(id)
		ownedTextures.foreach {
			case (attachment, tex) => tex.delete
		}
		ownedTextures = Map[Int, Texture]()
	}

	def check = {
		val framebuffer: Int = GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER);
		framebuffer match {
			case GL30.GL_FRAMEBUFFER_COMPLETE => Unit
			case GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT => throw new LWJGLException("FrameBuffer: " + id + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exception")
			case GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT => throw new LWJGLException("FrameBuffer: " + id + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT exception")
			case GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER => throw new LWJGLException("FrameBuffer: " + id + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT exception")
			case GL30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER => throw new LWJGLException("FrameBuffer: " + id + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT exception")
			case _ => throw new LWJGLException("Unexpected reply from glCheckFramebufferStatusEXT: " + framebuffer)
		}
	}

	def warnUnbound = {
	}
}