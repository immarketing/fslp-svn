package com.algo.smartgwt.server.db;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

import java.util.Arrays;

class Reply {
	public int status = 0;
	@SuppressWarnings({})
	public int startRow = 0;
	@SuppressWarnings({})
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

	public DataRequestT() {

	}

	@SuppressWarnings("unchecked")
	public DataRequestT(Class<? extends Object> aClass) {
		/*
		 * T.class.getClass().newInstance(); data = new T();
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

	public DataRequestCourse() {
		oldValues = null;
		data = null;
	}
}

class DataRequestCourses {
	String dataSource;
	String operationType;
	String componentId;
	String oldValues;
	Course data[];

	public DataRequestCourses() {
		data = new Course[0];
	}
}

class DataRequestPage {
	String dataSource;
	String operationType;
	String componentId;
	Page oldValues;
	Page data;

	public DataRequestPage() {
		oldValues = null;
		data = null;
	}
}

class DataRequestPages {
	String dataSource;
	String operationType;
	String componentId;
	String oldValues;
	Page data[];

	public DataRequestPages() {
		data = new Page[0];
	}
}

class DataRequestChapter {
	String dataSource;
	String operationType;
	String componentId;
	Chapter oldValues;
	Chapter data;

	public DataRequestChapter() {
		oldValues = null;
		data = null;
	}
}

class DataRequestCharters {
	String dataSource;
	String operationType;
	String componentId;
	String oldValues;
	Chapter data[];

	public DataRequestCharters() {
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
	 * @Deprecated static class IdInstanceCreator<T> implements
	 * InstanceCreator<DataRequestT> { public DataRequestT createInstance(Type
	 * type) { return new DataRequestT<T>(); } }
	 */

	public static Course[] deJSONOldNewCourse(String js) {
		Course res[] = new Course[2];
		Gson gson = new Gson();
		DataRequestCourse c = gson.fromJson(js, DataRequestCourse.class);
		if (c.data != null) {
			res[0] = c.oldValues;
			res[1] = c.data;
		}
		return res;
	}

	public static Chapter[] deJSONOldNewChapter(String js) {
		Chapter res[] = new Chapter[2];
		Gson gson = new Gson();
		DataRequestChapter c = gson.fromJson(js, DataRequestChapter.class);
		if (c.data != null) {
			res[0] = c.oldValues;
			res[1] = c.data;
		}
		return res;
	}

	public static Page[] deJSONOldNewPage(String js) {
		Page res[] = new Page[2];
		Gson gson = new Gson();
		DataRequestPage c = gson.fromJson(js, DataRequestPage.class);
		if (c.data != null) {
			res[0] = c.oldValues;
			res[1] = c.data;
		}
		return res;
	}

	public static List<Course> deJSONCourse(String js) {
		List<Course> res = new ArrayList<Course>();
		Gson gson = new Gson();
		DataRequestCourse c = gson.fromJson(js, DataRequestCourse.class);
		if (c.data != null) {
			res.add(c.data);
		}
		return res;
	}

	public static List<Chapter> deJSONChapter(String js) {
		List<Chapter> res = new ArrayList<Chapter>();
		Gson gson = new Gson();
		DataRequestChapter c = gson.fromJson(js, DataRequestChapter.class);
		if (c.data != null) {
			res.add(c.data);
		}
		return res;
	}

	public static List<Page> deJSONPage(String js) {
		List<Page> res = new ArrayList<Page>();
		Gson gson = new Gson();
		DataRequestPage c = gson.fromJson(js, DataRequestPage.class);
		if (c.data != null) {
			res.add(c.data);
		}
		return res;
	}

	private static <T> T doTestDeJSON_(String js, T tObject) {
		Gson gson = new Gson();
		// gson = new GsonBuilder().registerTypeAdapter(DataRequestT.class, new
		// IdInstanceCreator<T>()).create();
		// int DataRequestT;
		Type typeOfT = new TypeToken<T>() {
		}.getType();
		T o = gson.fromJson(js, typeOfT);
		// o.getClass().getName()
		if (o != null) {
			// T ttt = (T)o.getData();// (T)o.data;
			return o; // ttt;// o.data;
		}

		return null;

	}

	private static <T> Field[] getFields(Class<T> aClass) {
		if (aClass == null) {
			return new Field[0];
		}
		Field fos[] = aClass.getDeclaredFields();
		// List<?> fosList = Arrays.asList(fos);
		int ln1 = fos.length;

		Field fosSuper[] = getFields(aClass.getSuperclass());
		// List<?> fosSuperList = Arrays.asList(fosSuper);
		int ln2 = fosSuper.length;

		int ln = ln1 + ln2;

		Field result[] = new Field[ln];

		System.arraycopy(fos, 0, result, 0, ln1);
		System.arraycopy(fosSuper, 0, result, ln1, ln2);

		return result;
	}

	@Deprecated
	private static <T, S> T testDeJSON_JsonObject(JsonElement jsonElement,
			T aObject, S aSubObject) {
		T result = aObject;

		JsonObject array = null; // jsonElement.getAsJsonObject();

		if (jsonElement.isJsonNull()) {
			return null;
		}

		if (jsonElement.isJsonObject()) {
			array = jsonElement.getAsJsonObject();
		}

		Class<?> aClass = aObject.getClass();

		Field flds[] = getFields(aClass);

		Field fos[] = flds;
		for (Field af : fos) {
			String nm = af.getName();

			JsonElement je = null;

			if (array.has(nm)) {
				je = array.get(nm);
			}

			if (je == null) {
				continue;
			}

			if (je.isJsonNull()) {
				continue;
			}

			if (je.isJsonPrimitive()) {
				JsonPrimitive jp = je.getAsJsonPrimitive();
				try {
					if (jp.isString()) {
						af.set(result, jp.getAsString());
					} else if (jp.isNumber()) {
						af.set(result, jp.getAsNumber());
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else if (je.isJsonObject()) {
				try {
					//T obj = (T) aClass.newInstance();
					
					if (aSubObject == null) {
						return null;
					}
					@SuppressWarnings("unchecked")
					S ss = ((Class<S>)aSubObject.getClass()).newInstance();
					
					ss = testDeJSON_JsonObject(je, ss, null);
					
					af.set(result, ss);

					//JsonObject jo = je.getAsJsonObject();

					// jo.
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			//return null;
		}

		return result;
	}

	@Deprecated
	public static <T> T testDeJSON_(String js, T tObject) {
		/*
		 * class DataRequestT<T> { String dataSource; String operationType;
		 * String componentId; String oldValues; T data;
		 * 
		 * T getData() { return data; } }
		 */

		JsonParser parser = new JsonParser();
		DataRequestT<T> ttt = new DataRequestT<T>(tObject.getClass());
		
		if (parser.parse(js).isJsonObject()) {
			JsonObject array = parser.parse(js).getAsJsonObject();


			//Field fos[] = ttt.getClass().getDeclaredFields();

			testDeJSON_JsonObject(array, ttt, tObject);

			// List<T> lt = new ArrayList<T>();
			// tObject.getClass().ne
			// T tar[] = new T[10];

			/*
			for (Field af : fos) {
				String nm = af.getName();

				JsonElement je = null;

				if (array.has(nm)) {
					je = array.get(nm);
				}

				if (je == null) {
					continue;
				}

				if (je.isJsonNull()) {
					continue;
				}

				if (je.isJsonPrimitive()) {
					JsonPrimitive jp = je.getAsJsonPrimitive();
					try {
						if (jp.isString()) {
							af.set(ttt, jp.getAsString());
						} else if (jp.isNumber()) {
							af.set(ttt, jp.getAsNumber());
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else if (je.isJsonObject()) {
					try {
						T obj = (T) tObject.getClass().newInstance();

						JsonObject jo = je.getAsJsonObject();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			*/

			/*
			 * for (JsonObject jso : array.entrySet().toArray(new
			 * JsonObject[0])){
			 * 
			 * }
			 */
			/*

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
				// ttt.data = array.get("data").;
				// ttt.componentId = array.get("componentId").getAsString();
				Field f[] = ttt.data.getClass().getFields();
				for (Field af : f) {
					try {
						af.set(ttt.data, array.get("data").getAsJsonObject()
								.get(af.getName()).getAsJsonPrimitive());
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// ttt.data = new T();
			}

			if (array.equals(array)) {

			}
			*/
			// logging.
		}

		// getAsJsonArray();

		DataRequestT<T> tn = ttt;;//new DataRequestT<T>(tObject.getClass());
		//tn.data = tObject;
		//tn = doTestDeJSON_(js, tn);
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

	public static <T> T storeDBObj(T objToStore) {
		Objectify ofy = DBF.getObjectify();
		ofy.put(objToStore);
		return objToStore;
	}
	
	public static <T> T updateDBObj(Class<T> aClass,T objToStoreOld, T objToStoreNew) {
		try {
			Objectify 	ofy = DBF.getObjectify();
			
			if (objToStoreOld == null) {
				return objToStoreNew;
			}
			
			Field		idField = DBFMetaData.get().getPKField(objToStoreOld.getClass());
			Long		objID = idField.getLong(objToStoreOld);
			
			Key<T>		keyT 	= new Key<T>(aClass, objID.longValue());
			
			T oldDBObj = ofy.get(keyT);
			
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//ofy.get
		//T 
		return null;
		
	}
	
	
	public static <T> List<T> listDBObj(Class<T> aClass) {
		Objectify ofy = DBF.getObjectify();
		return ofy.query(aClass).list();
	}

	public static <P, C> List<C> listDBObjChilds(Class<P> aParent, Long parID,
			Class<C> aChild) {
		Objectify ofy = DBF.getObjectify();

		String fkFieldName = DBFMetaData.get().getFKFieldName(aParent, aChild);

		return ofy.query(aChild)
				.filter(fkFieldName, new Key<P>(aParent, parID)).list();
	}

	static {
		// ObjectifyService.register(Country.class);
		// ObjectifyService.register(Car.class);

		DBFMetaData.get().registerDBClass(Course.class); // ObjectifyService.register(Course.class);

		DBFMetaData.get().registerDBClass(Chapter.class); // ObjectifyService.register(Chapter.class);
		DBFMetaData.get().registerParentChildFK(Course.class, Chapter.class,
				"courseKey");

		DBFMetaData.get().registerDBClass(Page.class); // ObjectifyService.register(Page.class);
	}

}
