package com.algo.testabk.client;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class NewKurs extends DialogBox {

	public NewKurs() {
		setGlassEnabled(true);
		setAnimationEnabled(true);
		setHTML("New dialog");
		
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		setWidget(dockLayoutPanel);
		dockLayoutPanel.setSize("223px", "208px");
		
		Label lblNewLabel = new Label("\u041D\u0435\u043E\u0431\u0445\u043E\u0434\u0438\u043C\u043E \u0432\u0432\u0435\u0441\u0442\u0438 \u0437\u043D\u0430\u0447\u0435\u043D\u0438\u044F");
		dockLayoutPanel.addNorth(lblNewLabel, 2.0);
		
		FormPanel formPanel = new FormPanel();
		dockLayoutPanel.add(formPanel);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		formPanel.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		FlowPanel flowPanel = new FlowPanel();
		verticalPanel.add(flowPanel);
		
		Label lblNewLabel_1 = new Label("New label");
		flowPanel.add(lblNewLabel_1);
		lblNewLabel_1.setWidth("45%");
		
		TextBox textBox = new TextBox();
		flowPanel.add(textBox);
		textBox.setWidth("45%");
		
		FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		verticalPanel.setCellHeight(flexTable, "10");
		verticalPanel.setCellWidth(flexTable, "45%");
		flexTable.setWidth("100%");
		
		Grid grid = new Grid(2, 2);
		grid.setCellSpacing(2);
		grid.setCellPadding(2);
		verticalPanel.add(grid);
		grid.setWidth("100%");
		verticalPanel.setCellWidth(grid, "50%");
		
		Label lblNewLabel_3 = new Label("New label");
		grid.setWidget(0, 0, lblNewLabel_3);
		lblNewLabel_3.setWidth("50%");
		
		TextBox textBox_1 = new TextBox();
		grid.setWidget(0, 1, textBox_1);
		
		Label lblNewLabel_2 = new Label("New label");
		grid.setWidget(1, 0, lblNewLabel_2);
		lblNewLabel_2.setWidth("50%");
		
		RadioButton rdbtnNewRadioButton = new RadioButton("new name", "New radio button");
		grid.setWidget(1, 1, rdbtnNewRadioButton);
		grid.getCellFormatter().setWidth(1, 1, "100%");
		grid.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
	}

}
