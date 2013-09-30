package net.cemetech.sfgp.freebuild;

import net.cemetech.sfgp.freebuild.gfx._;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl._;

import org.jogamp.glg2d.GLGraphics2D
import org.jogamp.glg2d.GLG2DCanvas

import org.fit.cssbox.layout.BrowserCanvas
import javax.swing.JComponent

import org.python.core._;

import net.cemetech.sfgp.freebuild.drivers.AbstractDriver;

class GFXDriver extends AbstractDriver {
	
	var gfxCtx:GFXContext = null
	@scala.reflect.BeanProperty
	var uiHandle:GLG2DCanvas = null
	
	print("Initializing graphics drivers... ")
	gfxCtx = GFX.init()
	if (gfxCtx != null) {
		println("Success!")
	} else {
		println("Initialization failed.")
	}
	
	def mainloop():Int = {
		var ret:Int = 0
		
		println("Attempting to initiate mainloop.")
		
		
		var ui_defs_val:PyObject = settings.get(new PyString("ui_defs"),Py.None)
		var ui_defs_sval:String = ui_defs_val.toString
		var ui_module:PyObject = Py.None
		if(ui_defs_val != Py.None){
			ui_module = imp.importName(ui_defs_sval,false)
			if (ui_module == Py.None){
				println("settings['ui_defs'] is defined as '" + ui_defs_sval +"', but couldn't be imported.")
				ret = -1
			}
		} else {
			println("settings['ui_defs'] is undefined or unable to interpreted as a string.")
			ret = -1
		}
		
		if(ret == 0){
			println("Successfully imported '" + ui_defs_sval + "' to begin UI definition.")
			var py_conf_f:PyFunction = ui_module.__getattr__(new PyString("configure_ui")).asInstanceOf[PyFunction]
			var py_conf_val:PyObject = py_conf_f.__call__(new PyInteger(640),new PyInteger(480))
			if(! (py_conf_val.asInstanceOf[PyBoolean].__nonzero__())) {
				ret = -1
				println("Couldn't call configure_ui(), or got a bad result")
			} else if(uiHandle == null){
				ret = -1
				println("Called configure_ui() but uiHandle was not initialized.")
			} else{
				if(uiHandle.getDrawableComponent() == null){
					ret = -1
					println("uiHandle was initialized, but it has no interface to render");
				}
			}
		}
		
		if(ret == 0) {
		
			if(gfxCtx != null ) {
				// Set up the GL state.
				GL11.glClearColor(0, 0, 0, 1)
				//GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY)
				//GL11.glEnableClientState(GL11.GL_COLOR_ARRAY)
			
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glOrtho(0, 640, 480, 0, -1, 1);
				
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
				
				while (gfxCtx.open())
				{
					GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
					/* Render here */
					//HANDLE_PY_ERR_NO_RET(renderUI());
					//uiHandle->Update();
					
					uiHandle.paint(uiHandle.getGraphics())
					
					/* Swap front and back buffers and process events */
					gfxCtx.swapBuffers();
				}	
				//ShellSystemInterface::UnRegisterInputDevices();
				//ShellSystemInterface::clearActiveUIHandle();
			} else{
				println("Entering GFX::Driver::mainloop() but no context exists")
				ret = -1;
			}
		} else{
			println("Couldn't import a UI, so exiting early!")
		}

		
		ret
	}
	
	override def cleanup():Unit = {
		super.cleanup()
		if(gfxCtx != null) gfxCtx.cleanup()
	}

}
