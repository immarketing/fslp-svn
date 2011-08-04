package com.algo.smartgwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SmartGWT implements EntryPoint {
	private Canvas getNewGrid(){
		VLayout layout = new VLayout();
		Label label = new Label();
		label.setHeight(30);  
        label.setPadding(10);  
        label.setAlign(Alignment.CENTER);  
        label.setValign(VerticalAlignment.CENTER);  
        label.setWrap(false);  
        label.setShowEdges(true);  
        label.setContents("<i>Approved</i> for release");
        
        layout.setAlign(Alignment.CENTER);
        layout.addMember(label);
        
        ListGrid grid = new ListGrid();  
        grid.setWidth100();  
        grid.setHeight("200");
        grid.setAlign(Alignment.CENTER);
        layout.addMember(grid);
        
        
        HLayout hLayout = new HLayout();
        hLayout.setAlign(Alignment.CENTER); 
        hLayout.setWidth100();
        hLayout.setMembersMargin(10);
        hLayout.setLayoutMargin(10);
        hLayout.addMember(new IButton("New"));
        hLayout.addMember(new IButton("Update"));
        hLayout.addMember(new IButton("Detele"));
		
        layout.addMember(hLayout);

        return layout;
		
	}
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
		layout.addMember(new IButton("Hello World 1"));
		layout.addMember(new IButton("Hello World 2"));
		layout.addMember(getNewGrid());
		layout.addMember(getNewGrid());
		layout.setShowEdges(true);  
		//layout.setMembersMargin(5);  
		layout.setLayoutMargin(10);  
		

		layout.draw();

		// or
		// RootPanel.get().add(button);
	}
}
