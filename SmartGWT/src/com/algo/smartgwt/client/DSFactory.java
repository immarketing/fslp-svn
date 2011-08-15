package com.algo.smartgwt.client;

import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;

public class DSFactory {
	public static RestDataSource getCoursesDataSet(){
		RestDataSource ret = new RestDataSource();
		
        OperationBinding fetch = new OperationBinding();  
        fetch.setOperationType(DSOperationType.FETCH);  
        fetch.setDataProtocol(DSProtocol.POSTMESSAGE);
        
        OperationBinding add = new OperationBinding();  
        add.setOperationType(DSOperationType.ADD);  
        add.setDataProtocol(DSProtocol.POSTMESSAGE);
        
        OperationBinding update = new OperationBinding();  
        update.setOperationType(DSOperationType.UPDATE);  
        update.setDataProtocol(DSProtocol.POSTMESSAGE);
        
        OperationBinding remove = new OperationBinding();  
        remove.setOperationType(DSOperationType.REMOVE);  
        remove.setDataProtocol(DSProtocol.POSTMESSAGE);
        
        ret.setOperationBindings(fetch, add, update, remove);
        
        DataSourceIntegerField courseID = new DataSourceIntegerField("id", "ID");
        courseID.setPrimaryKey(Boolean.TRUE);
        DataSourceTextField courseName = new DataSourceTextField("courseName", "Course");  
        
        ret.setFields(courseID, courseName);
        
        ret.setFetchDataURL("/smartgwt/course/fetch");  
        ret.setAddDataURL("/smartgwt/course/add");  
        ret.setUpdateDataURL("/smartgwt/course/update");  
        ret.setRemoveDataURL("/smartgwt/course/remove");
        
        ret.setDataFormat(DSDataFormat.JSON);
        ret.setPrettyPrintJSON(false);
        ret.setAttribute("aaaa", Boolean.TRUE, true);
		
		return ret;		
	}

}
