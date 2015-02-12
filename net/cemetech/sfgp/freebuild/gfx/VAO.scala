package net.cemetech.sfgp.freebuild.gfx

import org.lwjgl.opengl._
import org.lwjgl.BufferUtils
import java.nio.IntBuffer;



object VAOManager {
  var boundNow:VAO = null;
  def genMany(requested:IntBuffer):IntBuffer = {
    GL30.glGenVertexArrays(requested)
    requested
  }
  
}

class VAO(reqID:Int = 0) {
	val id = if(reqID == 0) GL30.glGenVertexArrays() else reqID
	
	def bound = {VAOManager.boundNow == this}
	def bind() = {
	  if(!bound){
	    GL30.glBindVertexArray(id)
	    VAOManager.boundNow = this;
	  }
	}
	
	def unbind() = {
	  if(bound) {
	    GL30.glBindVertexArray(0)
	    VAOManager.boundNow = null;
	  }
	}
	
	def toggleAttribArray(index:Int, on:Boolean) = {
	  if(bound) {
	    (if(on) {
	      GL20.glEnableVertexAttribArray _
	    } else {
	      GL20.glDisableVertexAttribArray _
	  	}).asInstanceOf[(Int) => Unit](index)
	  } else{ warnUnbound }
	}
	
	def setAttribDivisor(index:Int, divisor:Int) = {
		if(bound) {
			GL33.glVertexAttribDivisor(index, divisor)
		} else { warnUnbound} 	
	}
	
	def setAttribArrayPointer(index:Int, size:Int, ttype:Int, normalized:Boolean, stride:Int, offset:Long) = {
		if(bound) {
			GL20.glVertexAttribPointer(index, size, ttype, normalized, stride, offset)
		} else { warnUnbound }
	}
	
	def delete = {
	  if(bound) unbind
	  GL30.glDeleteVertexArrays(id)
	}
	
	def warnUnbound = {
	}
}