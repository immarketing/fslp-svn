package com.algo.smartgwt.client.controls.admin;

import com.algo.smartgwt.client.Context;
import com.algo.smartgwt.client.controls.ControlCanvas;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;

public class QuestionsCanvas extends ControlCanvas {
	private Context context;
	private HLayout lout;

	public QuestionsCanvas(String name, Context context) {
		super(name, name);
		create(context);
		
	}
	
	private void create(Context context){
		this.context = context;
		lout = new HLayout();
	}

	@Override
	public Canvas getCanvas() {
		return lout;
	}

}
