package com.algo.smartgwt.client;

import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * ListGrid Factory
 * @author 31985
 *
 */
public class LGFactory {
	protected static ListGrid getListGrid(){
		ListGrid ret = new ListGrid();
		
		ret.setWidth100();  
		ret.setHeight100();
		ret.setAlign(Alignment.CENTER);
        
        return ret;
	}

	private static ListGrid getCourseListGrid(){
		ListGrid result = new ListGrid();
        ListGridField idField = new ListGridField("id");
        idField.setCanEdit(new Boolean(false));
        
        ListGridField nameField = new ListGridField("courseName");
        result.setFields(idField, nameField);  

        result.setSortField(1);
        result.setCanEdit(Boolean.TRUE);
        result.setDataPageSize(50);  
        result.setAutoFetchData(true);          
        result.setEmptyCellValue("--");
        result.setCanRemoveRecords(Boolean.TRUE);
        //result.set
        
        return result;

	}
	
	private static Canvas createCourseCanvas(){
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
        
        final ListGrid grid = LGFactory.getCourseListGrid();
        RestDataSource ds = DSFactory.getCoursesDataSet();
        grid.setDataSource(ds);
        
        layout.addMember(grid);
        
        grid.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {
			
			@Override
			public void onSelectionUpdated(SelectionUpdatedEvent event) {
				// TODO Auto-generated method stub
				SC.say("My mess", "Q!"+grid.getSelectedRecord().getAttribute("id"));
				
			}
		});
        
        grid.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
			}        	
        });
        
        
        HLayout hLayout = new HLayout();
        hLayout.setAlign(Alignment.CENTER); 
        hLayout.setWidth100();
        hLayout.setMembersMargin(10);
        hLayout.setLayoutMargin(10);

        IButton btnNew = new IButton("New");
        hLayout.addMember(btnNew);
        btnNew.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.startEditingNew();				
			}
		});
        
        final IButton btnUpdate = new IButton("Update");
        hLayout.addMember(btnUpdate);
        
        btnUpdate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//grid.sta
				//grid.getSelectedRecord(). startEditing();
			}
		});
        
        IButton btnDelete = new IButton("Detele");
        hLayout.addMember(btnDelete);
        
        btnDelete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.removeSelectedData();
				//grid.rem
			}
		});
		
        IButton btnRefresh = new IButton("Обновить");
        hLayout.addMember(btnRefresh);
        
        btnRefresh.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//grid.fetchData();
				grid.invalidateCache();
				//grid.getDataSource().
				//grid.rem
			}
		});

        layout.addMember(hLayout);
        
        Context.get().setCoursesCanvas(layout);
        Context.get().setCourcesListGrid(grid);
        Context.get().setCourcesDataSource(ds);

        return layout;
		
	}
	
	public static Canvas getCourseCanvas(){
		synchronized (Context.get()){
			Context cntx = Context.get();
			if (cntx.getCoursesCanvas() == null ) {
				createCourseCanvas();
			}
			return cntx.getCoursesCanvas();
		}
		
	}
}
