package net.cemetech.sfgp.freebuild;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.* ;


public class TestMain {
  
  
  public static void main (String args[]) {
    TestMain test = new TestMain() ;
    test.init() ;
  }
  
  void init() {
    setDisplayMode() ;
    doRenderLoop() ;
  }

  private static FloatBuffer
    vertBuffer = BufferUtils.createFloatBuffer(9) ;
  
  private void doRenderLoop() {
    while (true) {
      
      GL11.glMatrixMode(GL11.GL_PROJECTION) ;
      GL11.glLoadIdentity() ;
      GL11.glOrtho(-2, 2,  -2, 2,  -1, 1) ;
      GL11.glMatrixMode(GL11.GL_MODELVIEW) ;
      GL11.glLoadIdentity() ;
      
      GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY) ;
      vertBuffer.clear() ;
      vertBuffer.put(new float[] { 0, 0, 0,  1, 0, 0,  1, 1, 0 }) ;
      vertBuffer.flip() ;
      GL11.glVertexPointer(3, 0, vertBuffer) ;
      GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3) ;
      Display.update() ;
      
      if (Display.isCloseRequested()) {
        Display.destroy() ;
        System.exit(0) ;
      }
    }
  }
  

  private boolean setDisplayMode() {
    try {
      DisplayMode chosen = Display.getAvailableDisplayModes()[0] ;
      final int
        wide = chosen.getWidth(),
        high = chosen.getHeight() ;
      Display.setDisplayMode(chosen) ;
      Display.setFullscreen(false) ;
      Display.create(new PixelFormat(),
  		    (new ContextAttribs(3, 2)).withProfileCore(false)) ;
    }
    catch (Exception e) {
      e.printStackTrace() ;
    }
    return false ;
  }
}