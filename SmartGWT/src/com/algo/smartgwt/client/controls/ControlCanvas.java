package com.algo.smartgwt.client.controls;

import com.smartgwt.client.widgets.Canvas;


public abstract class ControlCanvas {
	//private String id;
	private String name;
	private String title;
	
	abstract public Canvas getCanvas();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
