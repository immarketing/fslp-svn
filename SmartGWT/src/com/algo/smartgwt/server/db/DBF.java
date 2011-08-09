package com.algo.smartgwt.server.db;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

class Reply {
	@SuppressWarnings("unused")
	public int status = 0;
	@SuppressWarnings({ "unused" })
	public int startRow = 0;
	@SuppressWarnings({ "unused" })
	public int endRow = 1;
	@SuppressWarnings("unused")
	public int totalRows = 1;
	@SuppressWarnings("unused")
	public Object data[] = { null };
};

class AReply {
	@SuppressWarnings("unused")
	public Reply response = new Reply();
}

class DataRequestData {
	String countryCode;
	String countryName;
	String capital;
	String continent;
	
}

class DataRequest {
	String dataSource;
	String operationType;
	String componentId;
	String oldValues;
	DataRequestData data ;
}

class DataRequestT<T> {
	String dataSource;
	String operationType;
	String componentId;
	String oldValues;
	T data ;
}

/*
{
	"dataSource":"isc_ARestDataSource_0",
	"operationType":"add",
	"componentId":"isc_ListGrid_0",
	"data":{
		"countryCode":"555",
		"countryName":"666",
		"capital":"777",
		"continent":"888"
	},
	"oldValues":null
}
 */
public class DBF {
	public static void testDeJSON (String js){
		Gson gson = new Gson();
		DataRequest o=gson.fromJson(js, DataRequest.class);
		
		if (o != null) {
			
		}
	}
	
	public static <T> T testDeJSON (String js, T tObject){
		Gson gson = new Gson();
		//int DataRequestT;
		Type typeOfT = new TypeToken<DataRequestT<T>>(){}.getType();
		DataRequestT<T> o=gson.fromJson(js, typeOfT);
		
		if (o != null) {
			return o.data;
		} 
		
		return null;
	}
	
	public static Objectify getObjectify(){
		return ObjectifyService.begin();
	}
	
	public static Object prepareJSONReply(List reply){
		AReply ar = new AReply();
		
		ar.response.totalRows = reply.size();
		ar.response.endRow = reply.size()-1;
		ar.response.data = reply.toArray();
		
		return ar;		
	}
	
	public static Object prepareJSONReply(Object reply){
		//Object ret = null;
		//gson.
		//final Object intRepl = reply;
		
		AReply ar = new AReply();
		
		ar.response.data[0] = reply;
		
		return ar;
	}
	
	static {
		ObjectifyService.register(Country.class);
		ObjectifyService.register(Car.class);
	}

}
