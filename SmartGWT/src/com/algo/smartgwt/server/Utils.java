package com.algo.smartgwt.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class Utils {

	public static class DataJSON<T> {
		public String dataSource;
		public String operationType;
		public String componentId;
		public T data;
		public T oldValues;

		public DataJSON() {
		}

	}

	public static <T> List<T> createEmptyList(Class<T> aClass) {
		return new ArrayList<T>();
	}

	public static String getRequestContent(HttpServletRequest req)
			throws UnsupportedEncodingException, IOException {
		return getRequestContent(req, "UTF8");
	}

	public static String getRequestContent(HttpServletRequest req,
			String encoding) throws UnsupportedEncodingException, IOException {
		InputStreamReader isr = new InputStreamReader(req.getInputStream(),
				encoding);
		BufferedReader rdr = new BufferedReader(isr);
		int lng = req.getContentLength();
		char chars[] = new char[lng];
		int readed = rdr.read(chars);
		return new String(chars, 0, readed);
	}

	public static <T> DataJSON<T> deJsonObject(String jsonContent, T aObject) {
		JsonParser parser = new JsonParser();
		DataJSON<T> result = new DataJSON<T>();

		JsonElement je = parser.parse(jsonContent);

		if (je.isJsonObject()) {
			result = deJSONJsonObject(je, result, aObject);
			return result;

		} else {
			return result;
		}
	}
	
	public static <T> T deJsonAddObject(String jsonContent, T aObject) {
		JsonParser parser = new JsonParser();
		DataJSON<T> result = new DataJSON<T>();

		JsonElement je = parser.parse(jsonContent);

		if (je.isJsonObject()) {
			result = deJSONJsonObject(je, result, aObject);
			return result.data;

		} else {
			return aObject;
		}
	}

	public static <T> Field[] getAllFields(Class<T> aClass) {
		if (aClass == null) {
			return new Field[0];
		}
		Field fos[] = aClass.getDeclaredFields();
		// List<?> fosList = Arrays.asList(fos);
		int ln1 = fos.length;

		Field fosSuper[] = getAllFields(aClass.getSuperclass());
		// List<?> fosSuperList = Arrays.asList(fosSuper);
		int ln2 = fosSuper.length;

		int ln = ln1 + ln2;

		Field result[] = new Field[ln];

		System.arraycopy(fos, 0, result, 0, ln1);
		System.arraycopy(fosSuper, 0, result, ln1, ln2);

		return result;
	}

	private static <T, S> T deJSONJsonObject(JsonElement jsonElement,
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

		Field flds[] = getAllFields(aClass);

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
					// T obj = (T) aClass.newInstance();

					if (aSubObject == null) {
						return null;
					}
					@SuppressWarnings("unchecked")
					S ss = ((Class<S>) aSubObject.getClass()).newInstance();

					ss = deJSONJsonObject(je, ss, null);

					af.set(result, ss);

					// JsonObject jo = je.getAsJsonObject();

					// jo.
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			// return null;
		}

		return result;
	}

}
