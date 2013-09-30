package net.cemetech.sfgp.freebuild.gfx

import scala.io.Source
import org.lwjgl.opengl._
import java.nio._
import org.lwjgl.BufferUtils
import org.lwjgl.LWJGLException

object ShaderManager {
  var usedNow:Shader = null;
}

class Shader(vertShaderFilename:String, fragShaderFilename:String, geoShaderFilename:String = "") {
  val vHandle = Source.fromFile(vertShaderFilename)
  val vSrc = vHandle.getLines mkString "\n"
  vHandle.close
  
  val fHandle = Source.fromFile(fragShaderFilename)
  val fSrc = fHandle.getLines mkString "\n"
  fHandle.close
  
  
  val gHandle = if(geoShaderFilename == ""){ null } else { Source.fromFile(geoShaderFilename)}
  val gSrc = if(gHandle == null){ "" } else { gHandle.getLines mkString "\n" }
  if(gHandle != null) gHandle.close
  
  val progId:Int = GL20.glCreateProgram()

  
  val vertSId = compileShader(GL20.GL_VERTEX_SHADER, vSrc)
  GL20.glAttachShader(progId, vertSId)
  
  val geoSId = if (gSrc != "") {
    val geoSId = compileShader(GL32.GL_GEOMETRY_SHADER, gSrc)
    GL20.glAttachShader(progId, geoSId)
    geoSId
  } else { 0 }

  val fragSId = compileShader(GL20.GL_FRAGMENT_SHADER, fSrc)
  GL20.glAttachShader(progId, fragSId)
  
  
    
  GL20.glLinkProgram(progId)
  checkProgram(GL20.GL_LINK_STATUS)
  GL20.glValidateProgram(progId)
  checkProgram(GL20.GL_VALIDATE_STATUS)
  
  
  def compileShader(kind:Int, src:String):Int = {
    val id = GL20.glCreateShader(kind)
    
    val fullsrc = src.getBytes()
    val srcBuf = BufferUtils.createByteBuffer(fullsrc.length)
    srcBuf.clear()
    srcBuf.put(fullsrc)
    srcBuf.rewind()
    GL20.glShaderSource(id, src)
    GFX.checkNoGLErrors("Shader source buffering failed")

    GL20.glCompileShader(id);
    GFX.checkNoGLErrors("Shader compile failed")
   
    checkShader(id)
    return id;
  }
  
  def checkShader(shaderId:Int) = {
	  val result = GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS);
	  if (result != GL11.GL_TRUE) {
		val loglen = GL20.glGetShaderi(shaderId, GL20.GL_INFO_LOG_LENGTH);
	    val error = GL20.glGetShaderInfoLog(shaderId, loglen)
	    throw new LWJGLException(error);
	  }
  }

  def checkProgram(statusFlag:Int) {
    val result = GL20.glGetProgrami(progId, statusFlag);
    if (result != GL11.GL_TRUE) {
      val loglen = GL20.glGetShaderi(progId, GL20.GL_INFO_LOG_LENGTH);
	  val error = GL20.glGetShaderInfoLog(progId, loglen);
	  throw new LWJGLException(error);
    }
  }
  def used() = { ShaderManager.usedNow == this}
  def use() = {
    if(!used){
      GL20.glUseProgram(progId)
      ShaderManager.usedNow = this
    }
  }
  def unuse() = {
    if(used){
      GL20.glUseProgram(0)
      ShaderManager.usedNow = null
    }
  }

  def delete() = {
    GL20.glDeleteProgram(progId);
    if (GL20.glIsShader(vertSId)) {
      GL20.glDeleteShader(vertSId)
    }
    if (GL20.glIsShader(geoSId)) {
      GL20.glDeleteShader(geoSId)
    }
    if (GL20.glIsShader(fragSId)) {
      GL20.glDeleteShader(fragSId)
    }
  }
    
}