package net.cemetech.sfgp.freebuild.gfx

import org.lwjgl.util.vector.Matrix4f
import scala.collection.immutable.Stack

class MatrixStack(base:Matrix4f = null) {
	private var stack:Stack[Matrix4f] = new Stack[Matrix4f]()
	stack = (if(base == null){ new Matrix4f() } else {base}) +: stack
	def peek():Matrix4f = { stack.head }
	def push(m:Matrix4f) = { stack = m +: stack }
	def pop():Matrix4f = {
	  val top = stack.pop2
	  stack = top._2
	  top._1
	}
	
	
}