package com.algo.testabk.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.ToolbarMenuButton;
import com.gwtext.client.widgets.tree.TreePanel;
import com.gwtext.client.widgets.tree.TreeNode;

public class MainComposite extends Composite {

	public MainComposite() {
		
		Panel pnlNewPanel = new Panel("New Panel");
		pnlNewPanel.setFrame(true);
		pnlNewPanel.setLayout(new BorderLayout());
		
		Toolbar toolbar = new Toolbar();
		toolbar.setAutoWidth(true);
		
		ToolbarMenuButton tlbrmnbtnNewMenubutton = new ToolbarMenuButton("New MenuButton");
		toolbar.addButton(tlbrmnbtnNewMenubutton);
		pnlNewPanel.add(toolbar, new BorderLayoutData(RegionPosition.NORTH));
		
		TreePanel treePanel = new TreePanel();
		
		TreeNode trndroot = new TreeNode("(Root)", "");
		
		TreeNode treeNode = new TreeNode("New TreeNode", "");
		
		TreeNode treeNode_2 = new TreeNode("New TreeNode", "");
		treeNode.appendChild(treeNode_2);
		
		TreeNode treeNode_1 = new TreeNode("New TreeNode", "");
		treeNode.appendChild(treeNode_1);
		trndroot.appendChild(treeNode);
		treePanel.setRootNode(trndroot);
		treePanel.setTitle("");
		pnlNewPanel.add(treePanel, new BorderLayoutData(RegionPosition.WEST));
		initWidget(pnlNewPanel);
	}

}
