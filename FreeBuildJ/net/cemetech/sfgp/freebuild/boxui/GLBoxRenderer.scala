package net.cemetech.sfgp.freebuild.boxui

import org.fit.cssbox.render.BoxRenderer

import org.lwjgl._
import org.lwjgl.opengl._

/**
 * A renderer that produces OpenGL output.
 *
 * @author elfprince13
 */

class GLBoxRenderer(width:Int, height:Int) extends BoxRenderer {
	
	def open(): Unit = {
		
	}
	def close(): Unit = {}
	def finishElementContents(x$1: org.fit.cssbox.layout.ElementBox): Unit = {}
	def renderElementBackground(x$1: org.fit.cssbox.layout.ElementBox): Unit = {}
	def renderReplacedContent(x$1: org.fit.cssbox.layout.ReplacedBox): Unit = {}
	def renderTextContent(x$1: org.fit.cssbox.layout.TextBox): Unit = {}
	def startElementContents(x$1: org.fit.cssbox.layout.ElementBox): Unit = {}
}