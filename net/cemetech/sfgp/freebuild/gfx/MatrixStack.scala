package net.cemetech.sfgp.freebuild.gfx

import org.lwjgl.util.vector.Matrix4f
import org.lwjgl.util.vector.Vector3f
import scala.collection.immutable.List

class MatrixStack(base:Matrix4f = null) {
	private var stack:List[Matrix4f] = List[Matrix4f]()
	stack = (if(base == null){ new Matrix4f() } else {base}) +: stack
	def peek():Matrix4f = { stack.head }
	def push(m:Matrix4f) = { stack = m +: stack }
	def pop():Matrix4f = {
	  val top = stack.head
	  stack = stack.tail
	  top
	}
	
}

object MatrixUtils {
  
  def orthoProjection(left:Float, right:Float, bottom:Float, top:Float, near:Float, far:Float) = {
	  val xdom = 1/(right-left)
	  val ydom = 1/(top-bottom)
	  val zdom = 1/(far-near)
	  (new Matrix4f)
	  .scale(new Vector3f(2*xdom,2*ydom,-2*zdom))
	  .translate(new Vector3f(-(right+left)*xdom,-(top+bottom)*ydom,-(near+far)*zdom))
	}
}
