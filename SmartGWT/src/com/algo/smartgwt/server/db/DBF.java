package com.algo.smartgwt.server.db;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

class Reply {
	public int status = 0;
	@SuppressWarnings({ })
	public int startRow = 0;
	@SuppressWarnings({ })
	public int endRow = 1;
	public int totalRows = 1;
	public Object data[] = { null };
};

class AReply {
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
	DataRequestData data;
}

class DataRequestT<T> {
	public String dataSource;
	public String operationType;
	public String componentId;
	public String oldValues;
	public T data;
	
	@SuppressWarnings("unchecked")
	public DataRequestT(Class<? extends Object> aClass){
		/*
		T.class.getClass().newInstance();
		data = new T();
		*/
		try {
			data = (T) aClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	T getData() {
		return data;
	}
}

class DataRequestCourse {
	String dataSource;
	String operationType;
	String componentId;
	Course oldValues;
	Course data;
	public DataRequestCourse(){
		oldValues=null;
		data=null;
	}
}

class DataRequestCourses {
	String dataSource;
	String operationType;
	String componentId;
	String oldValues;
	Course data[];
	public DataRequestCourses(){
		data = new Course[0];
	}
}

class DataRequestPage {
	String dataSource;
	String operationType;
	String componentId;
	Page oldValues;
	Page data;
	public DataRequestPage(){
		oldValues=null;
		data=null;
	}
}

class DataRequestPages {
	String dataSource;
	String operationType;
	String componentId;
	String oldValues;
	Page data[];
	public DataRequestPages(){
		data = new Page[0];
	}
}

class DataRequestChapter {
	String dataSource;
	String operationType;
	String componentId;
	Chapter oldValues;
	Chapter data;
	public DataRequestChapter(){
		oldValues=null;
		data=null;
	}
}

class DataRequestCharters {
	String dataSource;
	String operationType;
	String componentId;
	String oldValues;
	Chapter data[];
	public DataRequestCharters(){
		data = new Chapter[0];
	}
}


/*
 * { "dataSource":"isc_ARestDataSource_0", "operationType":"add",
 * "componentId":"isc_ListGrid_0", "data":{ "countryCode":"555",
 * "countryName":"666", "capital":"777", "continent":"888" }, "oldValues":null }
 */
public class DBF {
	public static void testDeJSON(String js) {
		Gson gson = new Gson();
		DataRequest o = gson.fromJson(js, DataRequest.class);

		if (o != null) {

		}
	}

	/*
	@Deprecated
	static class IdInstanceCreator<T> implements InstanceCreator<DataRequestT> {
		public DataRequestT createInstance(Type type) {
			return new DataRequestT<T>();
		}
	}
	*/

	public static Course[] deJSONOldNewCourse(String js) {
		Course res[] = new Course[2];
		Gson gson = new Gson();
		DataRequestCourse c = gson.fromJson(js,DataRequestCourse.class);
		if (c.data != null) {
			res[0] = c.oldValues;
			res[1] = c.data;
		}
		return res;		
	}
	
	public static Chapter[] deJSONOldNewChapter(String js) {
		Chapter res[] = new Chapter[2];
		Gson gson = new Gson();
		DataRequestChapter c = gson.fromJson(js,DataRequestChapter.class);
		if (c.data != null) {
			res[0] = c.oldValues;
			res[1] = c.data;
		}
		return res;		
	}
	
	public static Page[] deJSONOldNewPage(String js) {
		Page res[] = new Page[2];
		Gson gson = new Gson();
		DataRequestPage c = gson.fromJson(js,DataRequestPage.class);
		if (c.data != null) {
			res[0] = c.oldValues;
			res[1] = c.data;
		}
		return res;		
	}

	public static List<Course> deJSONCourse(String js) {
		List<Course> res = new ArrayList<Course>();
		Gson gson = new Gson();
		DataRequestCourse c = gson.fromJson(js,DataRequestCourse.class);
		if (c.data != null) {
			res.add(c.data);
		}
		return res;		
	}
	
	public static List<Chapter> deJSONChapter(String js) {
		List<Chapter> res = new ArrayList<Chapter>();
		Gson gson = new Gson();
		DataRequestChapter c = gson.fromJson(js,DataRequestChapter.class);
		if (c.data != null) {
			res.add(c.data);
		}
		return res;		
	}
	
	public static List<Page> deJSONPage(String js) {
		List<Page> res = new ArrayList<Page>();
		Gson gson = new Gson();
		DataRequestPage c = gson.fromJson(js,DataRequestPage.class);
		if (c.data != null) {
			res.add(c.data);
		}
		return res;		
	}
	
	private static <T> T doTestDeJSON_(String js , T tObject){
		Gson gson = new Gson();
		//gson = new GsonBuilder().registerTypeAdapter(DataRequestT.class, new IdInstanceCreator<T>()).create();
		// int DataRequestT;
		Type typeOfT = new TypeToken<T>() {
		}.getType();
		T o = gson.fromJson(js, typeOfT);
		//o.getClass().getName()
		if (o != null) {
			//T ttt = (T)o.getData();// (T)o.data;
			return o ; //ttt;// o.data;
		}

		return null;
		
	}


	@Deprecated	
	public static <T> T testDeJSON_ (String js , T tObject) {
		/*
class DataRequestT<T> {
	String dataSource;
	String operationType;
	String componentId;
	String oldValues;
	T data;

	T getData() {
		return data;
	}
}
		 * 
		 */
		
		JsonParser parser = new JsonParser();
	    if (parser.parse(js).isJsonObject()){
	    	JsonObject array = parser.parse(js).getAsJsonObject();
	    	
	    	DataRequestT<T> ttt = new DataRequestT<T>(tObject.getClass());
	    	
	    	if (array.has("dataSource")) {
	    		ttt.dataSource = array.get("dataSource").getAsString(); 
	    	}
		    
	    	if (array.has("operationType")) {
	    		ttt.operationType = array.get("operationType").getAsString(); 
	    	}

	    	if (array.has("componentId")) {
	    		ttt.componentId = array.get("componentId").getAsString(); 
	    	}

	    	if (array.has("data")) {
	    		//ttt.data = array.get("data").; 
	    		//ttt.componentId = array.get("componentId").getAsString();
	    		Field f [] = ttt.data.getClass().getFields();
	    		for (Field af: f){
	    			try {
						af.set(ttt.data, array.get("data").getAsJsonObject().get(af.getName()).getAsJsonPrimitive());
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	    		//ttt.data = new T();
	    	}

	    	if (array.equals(array)) {
		    	
		    }
		    //logging.
	    }
		
	    //getAsJsonArray();
		
		DataRequestT<T> tn = new DataRequestT<T>(tObject.getClass());
		tn.data = tObject;
		tn = doTestDeJSON_(js, tn);
		return tn.data;
	}

	public static Objectify getObjectify() {
		return DBFMetaData.get().getObjectify();// ObjectifyService.begin();
	}

	public static Object prepareJSONReply(List<?> reply) {
		AReply ar = new AReply();

		ar.response.totalRows = reply.size();
		ar.response.endRow = reply.size() - 1;
		ar.response.data = reply.toArray();

		return ar;
	}

	public static <T> Object prepareJSONReply(T reply) {
		// Object ret = null;
		// gson.
		// final Object intRepl = reply;

		AReply ar = new AReply();
		ar.response.data[0] = reply;
		return ar;
	}
	
	public static <T> List<T> listDBObj(Class<T> aClass){
		Objectify ofy = DBF.getObjectify();
		return ofy.query(aClass).list();
	}

	public static <P,C> List<C> listDBObjChilds(Class<P> aParent, Long parID, Class<C> aChild){
		Objectify ofy = DBF.getObjectify();
		
		String fkFieldName = DBFMetaData.get().getFKFieldName(aParent, aChild);
		
		return ofy.query(aChild).filter(fkFieldName, new Key<P>(aParent,parID)).list();
		
		//DBFMetaData.get().get
		//return ofy.query(aChild).filter("courseKey", new Key<Course>(Course.class, getId())).list();
		
		//return ofy.query(Chapter.class).filter("courseKey", new Key<Course>(Course.class, getId())).list();
		//return ofy.query(aClass).list();
	}

	static {
		//ObjectifyService.register(Country.class);
		//ObjectifyService.register(Car.class);
		
		DBFMetaData.get().registerDBClass(Course.class); // ObjectifyService.register(Course.class);
		
		DBFMetaData.get().registerDBClass(Chapter.class); // ObjectifyService.register(Chapter.class);
		DBFMetaData.get().registerParentChildFK(Course.class, Chapter.class, "courseKey");
		
		DBFMetaData.get().registerDBClass(Page.class); // ObjectifyService.register(Page.class);
	}

}
