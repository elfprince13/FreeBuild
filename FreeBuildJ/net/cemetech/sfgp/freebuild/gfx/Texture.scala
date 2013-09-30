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
	
	def allocate2D(level:Int, fmt:Int, width:Int, height:Int, border:Int, format:Int, ttype:Int, offset:Long){
	  GL11.glTexImage2D(target,level,fmt,width,height,border,format,ttype,offset)
	}
	
	def delete = {
	  GL11.glDeleteTextures(id)
	  TextureManager.boundNow = TextureManager.boundNow.filterNot(pair => pair._2.id == id)
	}
}