package net.cemetech.sfgp.freebuild.gfx

import org.lwjgl.opengl._

import org.lwjgl.util.vector.Matrix4f

/*
 * If inVao == null && inAttrs == null
 * -> Initialize from scratch
 * If inVao != null && inAttrs != null
 * -> We're cloning an already configured VAO
 */

class Mesh(vao:VAO, attribs:Array[BufferObject], indices:BufferObject, material:AnyRef=null) {
	
	
	
	def instance(materialParams:AnyRef=null, transform:Matrix4f, isStatic:Boolean=true):Int = {
		0
	}
	
	def deinstance(id:Int) = {
		
	}
	
	def stepUpdate = {
		
	}
}
