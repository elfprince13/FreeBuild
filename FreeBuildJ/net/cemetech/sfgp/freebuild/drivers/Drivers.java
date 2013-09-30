package net.cemetech.sfgp.freebuild.drivers;

import java.util.*;

public class Drivers {

	private static Drivers instance = null;
	
	private AbstractDriver mainDriver;
	private HashMap<String,Class<? extends AbstractDriver>> knownDrivers;
	
	protected Drivers(){
		mainDriver = null;
		knownDrivers = new HashMap<String,Class<? extends AbstractDriver>>();
	}
	
	public static Drivers manager(){
		return (instance == null) ? (instance = new Drivers()) : instance; 
	}
	
	public void setMainDriver(AbstractDriver nd){
		mainDriver = nd;
	}
	
	public AbstractDriver getMainDriver(){
		return mainDriver;
	}
	
	public void clearMainDriver(){
		mainDriver = null;
	}
	
	public Class<? extends AbstractDriver> getNamedDriver(String name){
		return knownDrivers.get(name);
	}
	
	public Class<? extends AbstractDriver> registerNamedDriver(String name, Class<? extends AbstractDriver> newDriver){
		return knownDrivers.put(name,newDriver);
	}
	
	public Class<? extends AbstractDriver> unregisterDriver(String name){
		return knownDrivers.remove(name);
	}
	
}
