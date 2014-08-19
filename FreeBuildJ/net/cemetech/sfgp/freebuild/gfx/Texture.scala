package net.cemetech.sfgp.freebuild.gfx

import org.lwjgl.LWJGLException
import org.lwjgl.opengl._
import org.lwjgl.BufferUtils
import java.nio._

import scala.collection._


// Need to support multiple Texture image units! We'll get there :)
object TextureManager {
  var boundNow:Map[Int,Texture] = Map[Int,Texture]();
  def genMany(requested:IntBuffer):IntBuffer = {
    GL11.glGenTextures(requested)
    requested
  }
  
  def deleteMany(requested:IntBuffer) = {
    requested.rewind()
    val delA:Array[Int] = new Array[Int](requested.limit())
    requested.get(delA)
    requested.rewind()
    
    val delS:Set[Int] = Set() ++ delA
    
    GL11.glDeleteTextures(requested)
    TextureManager.boundNow = TextureManager.boundNow.filterNot(pair => delS contains pair._2.id)
  }
  
}

class Texture(reqID:Int=0, reqTarget:Int=GL11.GL_TEXTURE_2D) {
	val id = if(reqID == 0) GL11.glGenTextures() else reqID
	val target = reqTarget
	
	def bound = {(TextureManager.boundNow getOrElse (target,null)) == this}
	def bind = {
	  if(!bound){
	    GL11.glBindTexture(target, id)
	    TextureManager.boundNow = TextureManager.boundNow + (target -> this);
	  }
	}
	
	def unbind = {
	  if(!bound) {
	    GL11.glBindTexture(target, 0)
	    TextureManager.boundNow = TextureManager.boundNow - target;
	  }
	}
	
	def allocate2D(level:Int, internalFormat:Int, width:Int, height:Int, border:Int, format:Int, ttype:Int, offset:Option[Long]=None){
	  offset match {
	    case Some(ofs) => GL11.glTexImage2D(target,level,internalFormat,width,height,border,format,ttype,ofs)
	    case None => ttype match {
	      case GL11.GL_BYTE | GL11.GL_UNSIGNED_BYTE
	      | GL12.GL_UNSIGNED_BYTE_3_3_2 | GL12.GL_UNSIGNED_BYTE_2_3_3_REV
	      	=> GL11.glTexImage2D(target,level,internalFormat,width,height,border,format,ttype,null.asInstanceOf[ByteBuffer])
	      case GL11.GL_SHORT | GL11.GL_UNSIGNED_SHORT
	      | GL12.GL_UNSIGNED_SHORT_5_6_5 | GL12.GL_UNSIGNED_SHORT_5_6_5_REV
	      | GL12.GL_UNSIGNED_SHORT_4_4_4_4 | GL12.GL_UNSIGNED_SHORT_4_4_4_4_REV 
	      | GL12.GL_UNSIGNED_SHORT_5_5_5_1 | GL12.GL_UNSIGNED_SHORT_1_5_5_5_REV
	      	=> GL11.glTexImage2D(target,level,internalFormat,width,height,border,format,ttype,null.asInstanceOf[ShortBuffer])
	      case GL11.GL_INT | GL11.GL_UNSIGNED_INT
	      | GL12.GL_UNSIGNED_INT_8_8_8_8 | GL12.GL_UNSIGNED_INT_8_8_8_8_REV
	      | GL12.GL_UNSIGNED_INT_10_10_10_2 | GL12.GL_UNSIGNED_INT_2_10_10_10_REV
	      	=> GL11.glTexImage2D(target,level,internalFormat,width,height,border,format,ttype,null.asInstanceOf[IntBuffer])
	      case GL11.GL_FLOAT
	      	=> GL11.glTexImage2D(target,level,internalFormat,width,height,border,format,ttype,null.asInstanceOf[FloatBuffer])
	      case _ => throw new LWJGLException("Invalid ttype argument for Texture.allocate2D")
	    }
	  }
	}
	
	def delete = {
	  GL11.glDeleteTextures(id)
	  TextureManager.boundNow = TextureManager.boundNow.filterNot(pair => pair._2.id == id)
	}
}