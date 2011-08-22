package com.algo.smartgwt.client;

import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
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
	public static Canvas createChapterCanvas(){
		VLayout layout = new VLayout();
		
		Label label = createHeaderLabel("Разделы курсов");
        layout.setAlign(Alignment.CENTER);
        layout.addMember(label);
        
        final ListGrid grid = LGFactory.getChapterListGrid();
        Context.get().setChaptersListGrid(grid);

        RestDataSource ds = DSFactory.getChaptersDataSet();
        setChapterDataSource(ds);
        //grid.setDataSource(ds);
        
        layout.addMember(grid);
        
        grid.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {
			
			@Override
			public void onSelectionUpdated(SelectionUpdatedEvent event) {
				Context.get().setCurrentChapterID(grid.getSelectedRecord().getAttribute("id"));
				
		        RestDataSource ds = DSFactory.getPagesDataSet();
		        setPagesDataSource(ds);

		        Context.get().getPagesListGrid().invalidateCache();
		        Context.get().getPagesListGrid().fetchData();
			}
		});
        
        HLayout hLayout = new HLayout();
        hLayout.setAlign(Alignment.CENTER); 
        hLayout.setWidth100();
        hLayout.setMembersMargin(10);
        hLayout.setLayoutMargin(10);

        IButton btnNew = createInsertButton(grid, "Создать");//new IButton("New");
        hLayout.addMember(btnNew);
        
        final IButton btnUpdate = createUpdateButton(grid, "Редактировать");
        hLayout.addMember(btnUpdate);
        
        IButton btnDelete = createDeleteButton(grid, "Удалить");//new IButton("Detele");
        hLayout.addMember(btnDelete);
		
        IButton btnRefresh = createRefreshButton(grid, "Обновить");//new IButton("Обновить");
        hLayout.addMember(btnRefresh);
        
        layout.addMember(hLayout);
        
        Context.get().setChaptersCanvas(layout);

        return layout;
		
	}

	public static Canvas createCourseCanvas(){
		VLayout layout = new VLayout();
		
		Label label = createHeaderLabel("Учебные курсы");
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
				//SC.say("My mess", "Q!"+grid.getSelectedRecord().getAttribute("id"));
				Context.get().setCurrentCourseID(grid.getSelectedRecord().getAttribute("id"));
				
		        RestDataSource ds = DSFactory.getChaptersDataSet();
		        setChapterDataSource(ds);

		        Context.get().getChaptersListGrid().invalidateCache();
		        Context.get().getChaptersListGrid().fetchData();
				
			}
		});
        
        HLayout hLayout = new HLayout();
        hLayout.setAlign(Alignment.CENTER); 
        hLayout.setWidth100();
        hLayout.setMembersMargin(10);
        hLayout.setLayoutMargin(10);

        IButton btnNew = createInsertButton(grid, "Создать");//new IButton("New");
        hLayout.addMember(btnNew);
        
        final IButton btnUpdate = createUpdateButton(grid, "Редактировать");
        hLayout.addMember(btnUpdate);
        
        IButton btnDelete = createDeleteButton(grid, "Удалить");//new IButton("Detele");
        hLayout.addMember(btnDelete);
		
        IButton btnRefresh = createRefreshButton(grid, "Обновить");//new IButton("Обновить");
        hLayout.addMember(btnRefresh);
        
        layout.addMember(hLayout);
        
        Context.get().setCoursesCanvas(layout);
        Context.get().setCourcesListGrid(grid);
        Context.get().setCourcesDataSource(ds);

        return layout;
		
	}
	
	private static IButton createDeleteButton(final ListGrid grid, String text) {
        IButton btnDelete = new IButton(text);
        
        btnDelete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.removeSelectedData();
				//grid.rem
			}
		});
        
        return btnDelete;
		
	}

	private static Label createHeaderLabel(String text) {
		Label label = new Label();
		label.setHeight(30);  
        label.setPadding(10);  
        label.setAlign(Alignment.CENTER);  
        label.setValign(VerticalAlignment.CENTER);  
        label.setWrap(false);  
        label.setShowEdges(true);  
        label.setContents("<i>Approved</i> for release");
        return label;
	}
	
	/*
	public static Canvas getCourseCanvas(){
		synchronized (Context.get()){
			Context cntx = Context.get();
			if (cntx.getCoursesCanvas() == null ) {
				createCourseCanvas();
			}
			return cntx.getCoursesCanvas();
		}
		
	}
	
	public static Canvas getChapterCanvas(){
		synchronized (Context.get()){
			Context cntx = Context.get();
			if (cntx.getChaptersCanvas() == null ) {
				createChapterCanvas();
			}
			return cntx.getChaptersCanvas();
		}
		
	}
	
	*/

	private static IButton createInsertButton(final ListGrid grid, String text) {
        final IButton btnNew = new IButton(text);
        
        btnNew.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.startEditingNew();				
			}
		});
        
        
        return btnNew;		
	}
	
	public static Canvas createPageCanvas(){
		VLayout layout = new VLayout();
		
		Label label = createHeaderLabel("Страницы курсов");
        layout.setAlign(Alignment.CENTER);
        layout.addMember(label);
        
        final ListGrid grid = LGFactory.getPageListGrid();
        Context.get().setPagesListGrid(grid);

        RestDataSource ds = DSFactory.getPagesDataSet();
        setPagesDataSource(ds);
        
        layout.addMember(grid);
        
        grid.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {
			@Override
			public void onSelectionUpdated(SelectionUpdatedEvent event) {
				// TODO Auto-generated method stub
				//SC.say("My mess", "Q!"+grid.getSelectedRecord().getAttribute("id"));
			}
		});
        
        HLayout hLayout = new HLayout();
        hLayout.setAlign(Alignment.CENTER); 
        hLayout.setWidth100();
        hLayout.setMembersMargin(10);
        hLayout.setLayoutMargin(10);

        IButton btnNew = createInsertButton(grid, "Создать");//new IButton("New");
        hLayout.addMember(btnNew);
        
        final IButton btnUpdate = createUpdateButton(grid, "Редактировать");
        hLayout.addMember(btnUpdate);
        
        IButton btnDelete = createDeleteButton(grid, "Удалить");//new IButton("Detele");
        hLayout.addMember(btnDelete);
		
        IButton btnRefresh = createRefreshButton(grid, "Обновить");//new IButton("Обновить");
        hLayout.addMember(btnRefresh);
        
        layout.addMember(hLayout);
        
        Context.get().setPageCanvas(layout);

        return layout;
		
	}

	private static IButton createRefreshButton(final ListGrid grid, String text) {
        IButton btnRefresh = new IButton(text);
        
        btnRefresh.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.invalidateCache();
			}
		});
        
        return btnRefresh;
		
	}

	private static IButton createUpdateButton(final ListGrid grid, String text) {
        final IButton btnUpdate = new IButton(text);
        
        btnUpdate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
        
        return btnUpdate;		
	}
	
	private static ListGrid getChapterListGrid(){
		ListGrid result = new ListGrid();
        ListGridField idField = new ListGridField("id");
        idField.setCanEdit(new Boolean(false));
        
        ListGridField nameField = new ListGridField("chapterName");
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
        
        return result;

	}
	
	protected static ListGrid getListGrid(){
		ListGrid ret = new ListGrid();
		
		ret.setWidth100();  
		ret.setHeight100();
		ret.setAlign(Alignment.CENTER);
        
        return ret;
	}
	
	private static ListGrid getPageListGrid(){
		ListGrid result = new ListGrid();
        ListGridField idField = new ListGridField("id");
        idField.setCanEdit(Boolean.FALSE);
        
        ListGridField nameField = new ListGridField("pageName");
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

	private static void setChapterDataSource(RestDataSource ds){
		Context.get().getChaptersListGrid().setDataSource(ds);
        Context.get().setChaptersDataSource(ds);
	}

	private static void setPagesDataSource(RestDataSource ds){
		Context.get().getPagesListGrid().setDataSource(ds);
        Context.get().setPagesDataSource(ds);
	}
}
