package com.algo.smartgwt.client;

import java.util.List;
import java.util.ArrayList;

import com.algo.smartgwt.client.controls.CCFactory;
import com.algo.smartgwt.client.controls.ControlCanvas;
import com.algo.smartgwt.client.controls.admin.CoursesCanvas;
import com.algo.smartgwt.client.controls.admin.QuestionsCanvas;
import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class SmartGWT implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	
	class ControlCanvasTreeNode extends TreeNode implements SelectionUpdatedHandler{
		ControlCanvasTreeNode (){
			super();
		}
		
		private ControlCanvas controlCanvas;
		private Layout viewport;
		
		ControlCanvasTreeNode (ControlCanvas controlCanvas, Layout viewport){
			this (controlCanvas.getName());
			this.controlCanvas = controlCanvas;
			this.viewport = viewport;
		}

		public ControlCanvasTreeNode(String name) {
			super(name);
		}

		@Override
		public void onSelectionUpdated(SelectionUpdatedEvent event) {
			TreeGrid treeGrid = (TreeGrid) event.getSource();
			
			viewport.setMembers(controlCanvas.getCanvas());
			/*
			SC.say(treeGrid.getSelectedRecord().getAttribute("name") + " "
					+ event.getX() + " " + event.getY());
					*/
			
			
		}
	}
	
	public void onModuleLoad() {
		CCFactory.get().registerControlCanvas(new CoursesCanvas("Учебные курсы", Context.get()));
		CCFactory.get().registerControlCanvas(new QuestionsCanvas("Вопросы", Context.get()));
		
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

		// button.draw();

		layout.addMember(button);
		button.hide();

		final TreeGrid treeGrid = new TreeGrid();
		treeGrid.setWidth("20%");
		treeGrid.setShowConnectors(true);
		treeGrid.setShowResizeBar(true);

		Tree data = new Tree();

		//TreeNode tn = new MyTreeNode("ТестНода");
		
		TreeNode rootNode = new TreeNode("root");
		
		Layout viewPortLayout = new Layout();		
		
		List<ControlCanvasTreeNode> ar = new ArrayList<ControlCanvasTreeNode>();
		for (ControlCanvas cc : CCFactory.get().getControlCanvases()) {
			ControlCanvasTreeNode tn = new ControlCanvasTreeNode(cc, viewPortLayout);
			ar.add(tn);
		}
		rootNode.setChildren(ar.toArray(new ControlCanvasTreeNode[0]));
		
		/*
		data.setModelType(TreeModelType.CHILDREN);
		data.setRoot(new TreeNode("root", new TreeNode("Курсы"), new TreeNode(
				"Тесты"), new TreeNode("Вопросы"), new TreeNode("Ответы"), tn));

		*/
		data.setModelType(TreeModelType.CHILDREN);
		data.setRoot(rootNode);
		treeGrid.setData(data);

		treeGrid.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {
			@Override
			public void onSelectionUpdated(SelectionUpdatedEvent event) {
				Object o = treeGrid.getSelectedRecord();
				try {
					SelectionUpdatedHandler s = (SelectionUpdatedHandler) o;
					s.onSelectionUpdated(event);
				} catch (Exception ex) {

				}
				// SC.say(treeGrid.getSelectedRecord().getAttribute("name")+ " "
				// + event.getX() + " " + event.getY());
			}
		});

		TreeGridField field = new TreeGridField("Выберите раздел");
		field.setCellFormatter(new CellFormatter() {
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				return record.getAttribute("name");
			}
		});
		treeGrid.setFields(field);

		layout.addMember(treeGrid);
		
		viewPortLayout.setWidth100();
		viewPortLayout.setHeight100();
		
		layout.addMember(viewPortLayout);
		
		/*
		

		// layout.addMember(new IButton("Hello World 1"));
		// layout.addMember(new IButton("Hello World 2"));
		layout.addMember(Context.get().getCourseCanvas());
		layout.addMember(Context.get().getChaptersCanvas());
		layout.addMember(Context.get().getPageCanvas());
		// layout.addMember(getNewGrid());
		layout.setShowEdges(true);
		// layout.setMembersMargin(5);
		layout.setLayoutMargin(10);
		*/

		layout.draw();

		// or
		// RootPanel.get().add(button);
	}

	private List ArrayList() {
		// TODO Auto-generated method stub
		return null;
	}
}
