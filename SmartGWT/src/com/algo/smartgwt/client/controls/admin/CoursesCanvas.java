package com.algo.smartgwt.client.controls.admin;

import com.algo.smartgwt.client.Context;
import com.algo.smartgwt.client.controls.ControlCanvas;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;

public class CoursesCanvas extends ControlCanvas {
	private Canvas controlCanvas;
	private Context context;
	
	public CoursesCanvas (String name, Context context ){
		super(name,name);
		create(context);
	}

	private void create(Context context) {
		this.context = context;
		HLayout layout = new HLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.setMembersMargin(20);

		layout.addMember(context.getCourseCanvas());
		layout.addMember(context.getChaptersCanvas());
		layout.addMember(context.getPageCanvas());
		layout.setShowEdges(true);
		layout.setLayoutMargin(10);
		
		controlCanvas = layout; 
	}
	
	@Override
	public Canvas getCanvas() {
		return controlCanvas;
	}

}
