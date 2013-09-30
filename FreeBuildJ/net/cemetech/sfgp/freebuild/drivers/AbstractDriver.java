package net.cemetech.sfgp.freebuild.drivers;

import org.python.util.PythonInterpreter; 
import org.python.core.*;

public abstract class AbstractDriver {
	
	public PyDictionary settings;
	protected PythonInterpreter console;
	
	public abstract int mainloop();
	public AbstractDriver(){
		settings = new PyDictionary();
		console = null;
	}
	
	public void setConsole(PythonInterpreter npc){ console = npc; }
	public PythonInterpreter getConsole(){ return console; }
	
	public PyObject initDefaultSetting(PyString[] hierarchy, PyObject defaultValue){
		PyObject ret = defaultValue;
		PyObject curDepth = settings;
		int i = 0;
		try{
			for(; i < hierarchy.length; i++){
				curDepth = ((PyDictionary)curDepth).setdefault(hierarchy[i], (i + 1 == hierarchy.length) ? defaultValue : (new PyDictionary()));
			}
			ret = curDepth;
		} catch(ClassCastException e) {
			System.err.println("settings hierarchy broken by non-dictionary type:");
			System.err.print("\tinspected settings");
			for(int j = 0; j < i; j++) {
				System.err.printf("[%s]",hierarchy[j].__repr__().toString());
			}
			System.err.printf(" and found %s instead",curDepth.__repr__().toString());
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public PyObject checkSetting(PyString[] hierarchy){
		PyObject curDepth = settings;
		PyObject ret = Py.None;
		int i = 0;
		try{
			for(; i < hierarchy.length; i++){
				curDepth = ((PyDictionary)curDepth).get(hierarchy[i], Py.None);
			}
			ret = curDepth;
		} catch(ClassCastException e) {
			System.err.println("settings hierarchy broken by non-dictionary type:");
			System.err.print("\tinspected settings");
			for(int j = 0; j < i; j++) {
				System.err.printf("[%s]",hierarchy[j].__repr__().toString());
			}
			System.err.printf(" and found %s instead",curDepth.__repr__().toString());
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public void cleanup(){	setConsole(null); }

	//public void replaceSettings(PyDictionary newSettings){ settings = newSettings; }
	//public PyDictionary getSettings(){ return settings; }


}
