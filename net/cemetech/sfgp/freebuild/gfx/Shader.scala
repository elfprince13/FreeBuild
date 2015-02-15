package net.cemetech.sfgp.freebuild.gfx

import scala.io.Source
import org.lwjgl.opengl._
import java.nio._
import org.lwjgl.BufferUtils
import org.lwjgl.LWJGLException
import org.lwjgl.util.vector.Matrix4f

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
		null
	}
}

class GLSLProgram {
	val f16Buffer = BufferUtils.createFloatBuffer(16);

	val progId: Int = GL20.glCreateProgram()
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
						shader.delete
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
	
	def validate = {
		GL20.glValidateProgram(progId)
		checkProgram(GL20.GL_VALIDATE_STATUS)
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

}

class Shader(val src:String, val kind:Int) {
	val shaderId = compileShader(kind, src)

	def compileShader(kind: Int, src: String): Int = {
		val id = GL20.glCreateShader(kind)

		try{
			GL20.glShaderSource(id, src)
			GFX.checkNoGLErrors("Shader source buffering failed")

			GL20.glCompileShader(id);
			GFX.checkNoGLErrors("Shader compile failed")

			checkShader(GL20.GL_COMPILE_STATUS)
			id
		} catch {
			case e:LWJGLException =>
				System.err.println(e.getMessage())
				if (GL20.glIsShader(id)) { // Can't call delete because shaderId isn't set yet!
					GL20.glDeleteShader(id)
				}
				0
		}
	}

	def checkShader(statusFlag:Int) = {
		val result = GL20.glGetShaderi(shaderId, statusFlag);
		if (result != GL11.GL_TRUE) {
			val loglen = GL20.glGetShaderi(shaderId, GL20.GL_INFO_LOG_LENGTH);
			val error = GL20.glGetShaderInfoLog(shaderId, loglen)
			throw new LWJGLException(error);
		}
	}


	def delete() = {
		if (GL20.glIsShader(shaderId)) {
			GL20.glDeleteShader(shaderId)
		}
	}

}