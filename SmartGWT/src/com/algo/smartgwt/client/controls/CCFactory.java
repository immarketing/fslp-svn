package com.algo.smartgwt.client.controls;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CCFactory {
	private Map<String, ControlCanvas> registred = new HashMap<String, ControlCanvas>();
	
	private CCFactory(){		
	}
	
	private static CCFactory singleton = new CCFactory();
	
	public static CCFactory get(){
		return singleton;
	}
	
	public ControlCanvas getControlCanvas (String name){
		return  registred.get(name);
	}
	public void registerControlCanvas(ControlCanvas cCanvas){
		try {
			String n = cCanvas.getName();
			registred.put(n, cCanvas);			
		} catch (Exception ex) {
			ex.printStackTrace();
			//ex
		}
	}

	public Collection<ControlCanvas> getControlCanvases() {
		return registred.values();
	}

}
