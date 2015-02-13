package net.cemetech.sfgp.freebuild;

import net.cemetech.sfgp.freebuild.gfx._
import org.lwjgl.LWJGLException
import org.lwjgl.opengl._
import org.fit.cssbox.layout.BrowserCanvas
import javax.swing.JComponent
import org.python.core._
import net.cemetech.sfgp.freebuild.drivers.AbstractDriver;
import org.lwjgl.util.vector.Matrix4f

class GFXDriver extends AbstractDriver {

	var gfxCtx: GFXContext = null
	@scala.beans.BeanProperty
	var uiHandle = null

	print("Initializing graphics drivers... ")
	gfxCtx = GFX.init()
	if (gfxCtx != null) {
		println("Success!")
	} else {
		println("Initialization failed.")
	}

	def mainloop(): Int = {
		var ret: Int = 0

		println("Attempting to initiate mainloop.")

		var ui_defs_val: PyObject = settings.get(new PyString("ui_defs"), Py.None)
		var ui_defs_sval: String = ui_defs_val.toString
		var ui_module: PyObject = Py.None
		if (ui_defs_val != Py.None) {
			ui_module = imp.importName(ui_defs_sval, false)
			if (ui_module == Py.None) {
				println("settings['ui_defs'] is defined as '" + ui_defs_sval + "', but couldn't be imported.")
				ret = -1
			}
		} else {
			println("settings['ui_defs'] is undefined or unable to interpreted as a string.")
			ret = -1
		}

		if (ret == 0) {
			println("Successfully imported '" + ui_defs_sval + "' to begin UI definition.")
			var py_conf_f: PyFunction = ui_module.__getattr__(new PyString("configure_ui")).asInstanceOf[PyFunction]
			var py_conf_val: PyObject = py_conf_f.__call__(new PyInteger(640), new PyInteger(480))
			if (!(py_conf_val.asInstanceOf[PyBoolean].__nonzero__())) {
				ret = -1
				println("Couldn't call configure_ui(), or got a bad result")
			} else if (uiHandle == null) {
				ret = -1
				println("Called configure_ui() but uiHandle was not initialized.")
			} else {
			  /*
				if (uiHandle.getDrawableComponent() == null) {
					ret = -1
					println("uiHandle was initialized, but it has no interface to render");
				}
				*/
			}
		}

		if (ret == 0) {

			if (gfxCtx != null) {
				// Set up the GL state.
				GL11.glClearColor(0, 0, 0, 1)
				//GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY)
				//GL11.glEnableClientState(GL11.GL_COLOR_ARRAY)

				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

				gfxCtx.projection.push(MatrixUtils.orthoProjection(0, 640, 480, 0, -1, 1))

				val vpMat = new Matrix4f()
				val vars3d = Map[String, Any]("ViewProjection" -> vpMat)

				while (gfxCtx.open()) {
					GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
					/* Render here */

					// uiHandle.paint(uiHandle.getGraphics())
					// ^- enable this once we're ready to do the UI stuff

					Matrix4f.mul(gfxCtx.projection.peek, gfxCtx.view.peek, vpMat)
					gfxCtx.renderer.render(Array[Mesh](), vars3d)

					/* Swap front and back buffers and process events */
					gfxCtx.swapBuffers();
				}
				//ShellSystemInterface::UnRegisterInputDevices();
				//ShellSystemInterface::clearActiveUIHandle();
			} else {
				println("Entering GFX::Driver::mainloop() but no context exists")
				ret = -1;
			}
		} else {
			println("Couldn't import a UI, so exiting early!")
		}

		ret
	}

	override def cleanup(): Unit = {
		super.cleanup()
		if (gfxCtx != null) gfxCtx.cleanup()
	}

}
