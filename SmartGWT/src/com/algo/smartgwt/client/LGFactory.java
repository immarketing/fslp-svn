package com.algo.smartgwt.client;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

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

	public static ListGrid getCourseListGrid(){
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
}
