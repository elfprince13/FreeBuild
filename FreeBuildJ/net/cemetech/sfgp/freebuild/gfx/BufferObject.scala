package net.cemetech.sfgp.freebuild.gfx

import org.lwjgl.opengl._
import org.lwjgl.BufferUtils
import java.nio.{Buffer,ByteBuffer,DoubleBuffer,FloatBuffer,IntBuffer,ShortBuffer}

class BufferObject(reqID:Int = 0, reqTarget:Int=GL15.GL_ARRAY_BUFFER) {
	val id = if(reqID == 0) GL15.glGenBuffers() else reqID
	val target = reqTarget
	
	// Too many potential buffer targets for hand-holding, unlike Textures/FBOs/etc
	def bind() = {
	    GL15.glBindBuffer(target, id)
	}
	
	def unbind() = {
	    GL15.glBindBuffer(target, 0)
	}
	
	def bufferData(b:Buffer, usage:Int) = {
		b.rewind
		b match { case b : ByteBuffer => GL15.glBufferData(target, b, usage)
			case b : DoubleBuffer => GL15.glBufferData(target, b,usage)
			case b : ShortBuffer => GL15.glBufferData(target, b,usage)
			case b : FloatBuffer => GL15.glBufferData(target, b,usage)
			case b : IntBuffer => GL15.glBufferData(target, b,usage)
			case _ => System.err.println("Invalid buffer type, buffering no data into " + id + " targeted at " + target)
		}
	}
	
	def delete = {
	  GL15.glDeleteBuffers(id)
	}
	
}