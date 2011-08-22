package com.algo.smartgwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class SmartGWT implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		HLayout layout = new HLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.setMembersMargin(20);

		IButton button = new IButton("Hello World 0");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				SC.say("Hello World from SmartGWT");
			}
		});

		//button.draw();
		
		layout.addMember(button);
		button.hide();
		//layout.addMember(new IButton("Hello World 1"));
		//layout.addMember(new IButton("Hello World 2"));
		layout.addMember(Context.get().getCourseCanvas());
		layout.addMember(Context.get().getChaptersCanvas());
		layout.addMember(Context.get().getPageCanvas());
		//layout.addMember(getNewGrid());
		layout.setShowEdges(true);  
		//layout.setMembersMargin(5);  
		layout.setLayoutMargin(10);  
		

		layout.draw();

		// or
		// RootPanel.get().add(button);
	}
}
