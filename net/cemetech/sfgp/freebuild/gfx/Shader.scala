package net.cemetech.sfgp.freebuild.gfx

import java.io.File
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.filefilter.SuffixFileFilter
import scala.io.Source
import org.lwjgl.opengl._
import java.nio._
import org.lwjgl.BufferUtils
import org.lwjgl.LWJGLException
import org.lwjgl.util.vector.Matrix4f

import scala.collection.JavaConverters._

object ShaderManager {
	var usedNow: GLSLProgram = null;
	val bundleExt2Kinds = Map("vert" -> GL20.GL_VERTEX_SHADER, "geom" -> GL32.GL_GEOMETRY_SHADER, "frag" -> GL20.GL_FRAGMENT_SHADER)
	def shaderFromFile(shaderFilename:String, kind:Int):Shader = {
		val sHandle = Source.fromFile(shaderFilename)
		val sSrc = sHandle.getLines mkString "\n"
		sHandle.close
		new Shader(sSrc, kind)
	}
	def programFromBundle(bundleName:String):GLSLProgram = {
		val bundleFile = new File(bundleName)
		if(bundleFile.isDirectory){
			val prog = new GLSLProgram
					val shaders = bundleFile.listFiles((new SuffixFileFilter(bundleExt2Kinds.keys.map{
						kExt => s".$kExt"
					}.toList.asJava).asInstanceOf[java.io.FilenameFilter])).map{
				file =>
				shaderFromFile(file.getAbsolutePath, bundleExt2Kinds(FilenameUtils.getExtension(file.getName)))
			}
			prog.attach(shaders.map{
				shader =>
				(shader -> true)
			}.toMap)
			prog.link
			//prog.validate
			prog
		} else {
			System.err.println("No such shader bundle: " + bundleName)
			null
		}
	}
	def checkShader(shaderId:Int, statusFlag:Int, errPrefix:String) = {
		GFX.warnUncheckedErrors("Someone left a dangling error before calling checkShader");
		val result = GL20.glGetShaderi(shaderId, statusFlag);
		GFX.checkNoGLErrors("Error retrieving statusFlag")
		if (result != GL11.GL_TRUE) {
			val loglen = GL20.glGetShaderi(shaderId, GL20.GL_INFO_LOG_LENGTH);
			GFX.checkNoGLErrors("Error retrieving info-log-length")
			val error = GL20.glGetShaderInfoLog(shaderId, loglen)
			GFX.checkNoGLErrors("Error retrieving info-log")
			throw new LWJGLException(s"$errPrefix:\n $error")
		}
	}


	def deleteShader(shaderId:Int) = {
		if (GL20.glIsShader(shaderId)) {
			GL20.glDeleteShader(shaderId)
		}
	}
	
	def compileShader(kind: Int, src: String): Int = {
		GFX.warnUncheckedErrors("Someone left a dangling error before calling compileShader");

		val id = GL20.glCreateShader(kind)
		GFX.checkNoGLErrors("Create shader failed")
		
		try{
			GL20.glShaderSource(id, src)
			GFX.checkNoGLErrors("Shader source buffering failed")

			GL20.glCompileShader(id);
			GFX.checkNoGLErrors("Shader compile failed")

			ShaderManager.checkShader(id, GL20.GL_COMPILE_STATUS, "Compile failed")
			id
		} catch {
			case e:LWJGLException =>
			System.err.println(e.getMessage)
			System.err.flush
			ShaderManager.deleteShader(id)
			0
		}
	}
}

class GLSLProgram(val progId:Int) {
	def this() = this(GL20.glCreateProgram())
	
	val f16Buffer = BufferUtils.createFloatBuffer(16);
	
	// We should make this pull attachments dynamically at creation?
	// That seems scary though because we really want Shader's to be singleton objects.
	// Food for thought....
	var attached:Map[Shader,Boolean] = Map()  

	def attach(shaders:Map[Shader,Boolean]) = {
		shaders.map{
			case (shader, shouldOwn) =>
			GL20.glAttachShader(progId, shader.shaderId)
			attached = attached + (shader -> shouldOwn)
		}
	}
	def detach(shaders:Set[Shader]) = {
		shaders.map{
			shader =>
			if(attached.contains(shader)){
				GL20.glDetachShader(progId, shader.shaderId)
				if(attached(shader)){
					ShaderManager.deleteShader(shader.shaderId)
				}
				attached = attached - shader
			} else {
				throw new LWJGLException("Can't detach a shader that isn't attached here!")
			}
		}

	}

	def link() = {
		GL20.glLinkProgram(progId)
		checkProgram(GL20.GL_LINK_STATUS)
	}

	def validate:Boolean = {
		GL20.glValidateProgram(progId)
		try{
			checkProgram(GL20.GL_VALIDATE_STATUS)
			true
		} catch {
			case e:LWJGLException => 
			System.err.println(e.getMessage)
			false
		}
	}

	def used = { ShaderManager.usedNow == this }
	def use() = {
		if (!used) {
			GL20.glUseProgram(progId)
			ShaderManager.usedNow = this
		}
	}
	def unuse() = {
		if (used) {
			GL20.glUseProgram(0)
			ShaderManager.usedNow = null
		}
	}
	def checkProgram(statusFlag: Int) {
		val result = GL20.glGetProgrami(progId, statusFlag);
		if (result != GL11.GL_TRUE) {
			val loglen = GL20.glGetProgrami(progId, GL20.GL_INFO_LOG_LENGTH);
			val error = GL20.glGetProgramInfoLog(progId, loglen);
			throw new LWJGLException(error);
		}
	}
	def setVariables(vars: Map[String, Any]) = {
		vars.foreach {
		case (name: String, value: Any) =>
		val uni = GL20.glGetUniformLocation(progId, name)
		if (uni == -1) throw new LWJGLException(name + " was invalid, or missing from this shader program (" + progId + ")")
		GFX.checkNoGLErrors("Unable to bind shader variable")
		value match {
		case mat: Matrix4f => mat.store(f16Buffer); f16Buffer.flip(); GL20.glUniformMatrix4(uni, false, f16Buffer)
		}

		}
	}
	
	def delete() = {
		if(GL20.glIsProgram(progId)){
			detach(attached.keySet)
			GL20.glDeleteProgram(progId)
		}
	}

}

class Shader(val shaderId:Int) {
	def this(src:String, kind:Int) = this(ShaderManager.compileShader(kind, src))
	def delete = ShaderManager.deleteShader(shaderId)
}