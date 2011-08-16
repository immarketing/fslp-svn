package com.algo.smartgwt.client;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSRequest;
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
        
        DSRequest dsr = new DSRequest();
        
        Map<String,String> m = new HashMap<String,String>();
        m.put("aaa", "bbb");
        
        dsr.setParams(m);
        ret.setRequestProperties(dsr);
        
        ret.setSendMetaData(Boolean.TRUE);
        ret.setSendExtraFields(Boolean.TRUE);
        ret.setAttribute("aaaa", Boolean.TRUE, true);
		
		return ret;		
	}

	public static RestDataSource getChaptersDataSet(){
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
        
        DataSourceIntegerField chapterID = new DataSourceIntegerField("id", "ID");
        chapterID.setPrimaryKey(Boolean.TRUE);
        DataSourceTextField chapterName = new DataSourceTextField("chapterName", "Chapter");  
        
        ret.setFields(chapterID, chapterName);
        
        ret.setFetchDataURL("/smartgwt/chapter/fetch");  
        ret.setAddDataURL("/smartgwt/chapter/add");  
        ret.setUpdateDataURL("/smartgwt/chapter/update");  
        ret.setRemoveDataURL("/smartgwt/chapter/remove");
        
        ret.setDataFormat(DSDataFormat.JSON);
        
        ret.setPrettyPrintJSON(false);
        
        DSRequest dsr = new DSRequest();
        dsr.setParams(Context.get().getChapterRequestParams());
        ret.setRequestProperties(dsr);
        
        ret.setSendMetaData(Boolean.TRUE);
        ret.setSendExtraFields(Boolean.TRUE);
        //ret.setAttribute("aaaa", Boolean.TRUE, true);
		
		return ret;		
	}
}
