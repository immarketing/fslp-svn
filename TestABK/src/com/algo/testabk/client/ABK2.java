package com.algo.testabk.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class ABK2 implements EntryPoint {
	private void doStartLearning(){
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
		
		dialogBox.center();
		closeButton.setFocus(true);
	}

	private void doStopLearning(){
		
	}
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.PCT);
		rootPanel.add(dockLayoutPanel, 0, 0);
		dockLayoutPanel.setSize("100%", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		dockLayoutPanel.addNorth(menuBar, 13.1);
		
		MenuItem mntmNewItem = new MenuItem("\u041D\u0430\u0447\u0430\u0442\u044C \u043E\u0431\u0443\u0447\u0435\u043D\u0438\u0435 ", false, new Command() {
			public void execute() {
				doStartLearning();
			}
		});
		menuBar.addItem(mntmNewItem);
		
		MenuItem mntmNewItem_1 = new MenuItem("\u0417\u0430\u043A\u043E\u043D\u0447\u0438\u0442\u044C \u043E\u0431\u0443\u0447\u0435\u043D\u0438\u0435 ", false, new Command() {
			public void execute() {
				doStopLearning();
			}
		});
		menuBar.addItem(mntmNewItem_1);

		Tree tree = new Tree();
		dockLayoutPanel.addWest(tree, 15.9);
		
		TreeItem treeItem = new TreeItem("\u0423\u0447\u0435\u0431\u043D\u044B\u0439 \u043A\u0443\u0440\u0441 1");
		tree.addItem(treeItem);
		
		TreeItem treeItem_2 = new TreeItem("\u0423\u0447\u0435\u0431\u043D\u044B\u0439 \u043A\u0443\u0440\u0441");
		treeItem.addItem(treeItem_2);
		
		TreeItem treeItem_3 = new TreeItem("\u0420\u0430\u0437\u0434\u0435\u043B 1");
		treeItem_2.addItem(treeItem_3);
		
		TreeItem trtmNewItem = new TreeItem("Глава 1");
		trtmNewItem.setText("\u0413\u043B\u0430\u0432\u0430 1 ");
		treeItem_3.addItem(trtmNewItem);
		
		TreeItem trtmNewItem_1 = new TreeItem("Глава 2");
		trtmNewItem_1.setText("\u0413\u043B\u0430\u0432\u0430 2 ");
		treeItem_3.addItem(trtmNewItem_1);
		
		TreeItem trtmNewItem_2 = new TreeItem("Глава 3");
		trtmNewItem_2.setText("\u0413\u043B\u0430\u0432\u0430 3 ");
		treeItem_3.addItem(trtmNewItem_2);
		treeItem_3.setState(true);
		
		TreeItem treeItem_4 = new TreeItem("\u0420\u0430\u0437\u0434\u0435\u043B 2");
		treeItem_2.addItem(treeItem_4);
		
		TreeItem trtmNewItem_3 = new TreeItem("Глава 4");
		trtmNewItem_3.setText("\u0413\u043B\u0430\u0432\u0430 4 ");
		treeItem_4.addItem(trtmNewItem_3);
		treeItem_4.setState(true);
		treeItem_2.setState(true);
		treeItem.setState(true);
		
		TreeItem treeItem_1 = new TreeItem("\u0423\u0447\u0435\u0431\u043D\u044B\u0439 \u043A\u0443\u0440\u0441 2");
		tree.addItem(treeItem_1);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		dockLayoutPanel.add(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		
		HTML htmlNewHtml = new HTML("New HTML", true);
		htmlNewHtml.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		scrollPanel.setWidget(htmlNewHtml);
		htmlNewHtml.setSize("100%", "100%");
		
	}
}
