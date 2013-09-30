package net.cemetech.sfgp.freebuild;

public class FreeBuildGLListener {
	
	boolean disposed;
	
	public FreeBuildGLListener(){
		disposed = false;
	}


	public void dispose() {
		synchronized(this){
			disposed = true;
			notifyAll();
		}
	}

}
