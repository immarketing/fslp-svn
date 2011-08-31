package com.algo.smartgwt.server.db;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Map;

import com.algo.smartgwt.server.Utils;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class DBFMetaData {
	private static class ForeignKey {
		protected static String calcFKHash(Class<?> parentClass,
				Class<?> childClass) {
			return calcFKHash(parentClass, "", childClass, "");
		}

		protected static String calcFKHash(Class<?> parentClass,
				String parentPKFieldName, Class<?> childClass,
				String childFKFieldName) {
			return parentClass.getName() + "|" + childClass.getName() + "|"
					+ childFKFieldName;
		}

		Class<?> childClass;
		String childFKFieldName;
		Class<?> parentClass;
		String parentPKFieldName;
		String fkHash;

		public String getFkHash() {
			return fkHash;
		}

		public void setFkHash(String fkHash) {
			this.fkHash = fkHash;
		}

		protected ForeignKey(Class<?> parentClass, String parentPKFieldName,
				Class<?> childClass, String childFKFieldName) {
			this.parentClass = parentClass;
			this.parentPKFieldName = parentPKFieldName;
			this.childClass = childClass;
			this.childFKFieldName = childFKFieldName;
			setFkHash (calcFKHash());
		}

		protected String calcFKHash() {
			return calcFKHash(parentClass, parentPKFieldName, childClass,
					childFKFieldName);
			// parentClass.getName()+"|"+childClass.getName()+"|"+childFKFieldName;
		}
	}

	private static class ParentChildForeignKey extends ForeignKey{
		private ParentChildForeignKey (Class<?> parentClass, String parentPKFieldName,
				Class<?> childClass, String childFKFieldName) {
			super(parentClass, parentPKFieldName, childClass, childFKFieldName);
		}
		private ParentChildForeignKey (Class<?> parentClass, Class<?> childClass, String childFKFieldName) {
			this (childClass, "", childClass, childFKFieldName);
		}
		
		protected String calcFKHash() {
			return calcFKHash(parentClass, childClass);
		}
	}
	
	private static DBFMetaData singleton = new DBFMetaData();

	public static DBFMetaData get() {
		return singleton;
	}

	private Map<String, ForeignKey> registeredFKs = new Hashtable<String, DBFMetaData.ForeignKey>();

	public String getFKFieldName(Class<?> parentClass, Class<?> childClass) {
		String hsh = ForeignKey.calcFKHash(parentClass, childClass);
		ForeignKey fk = registeredFKs.get(hsh);
		if (fk != null) {
			return fk.childFKFieldName;
		} else {
			return null;
		}
	}

	public Objectify getObjectify() {
		return ObjectifyService.begin();
	}
	
	private static class DBClass  {
		Class<?> 	aClass;
		Field 		fields[];
		
		Field 		idField;
		
		DBClass (Class<?> 	aClass){
			this.aClass = aClass;
			fields = Utils.getAllFields(this.aClass);
			
			for (Field af : fields) {
				if (af.getAnnotation(javax.persistence.Id.class) != null){
					idField = af;
					break;					
				}
			}
		}
	}
	
	private Map<String, DBClass > registeredDBObjects = new Hashtable<String, DBClass>();
	

	public <T> Field[] getFields(Class<T> aClass) {
		Field 		result[]	= null;
		DBClass 	dbc			= registeredDBObjects.get(aClass.getName());
		
		if (dbc != null) {
			result = dbc.fields;
		}
		
		return result;
		
	}
	public <T> Field getPKField(Class<T> aClass) {
		Field 		result	= null;
		DBClass 	dbc		= registeredDBObjects.get(aClass.getName());
		
		if (dbc != null) {
			result = dbc.idField;
		}
		
		return result;
	}
	public <T> void registerDBClass(Class<T> aClass) {
		ObjectifyService.register(aClass);
		registeredDBObjects.put(aClass.getName(), new DBClass(aClass));
		//aClass.getField("").get
	}

	public void registerFK(Class<?> parentClass, Class<?> childClass,
			String childFKFieldName) {
		registerFK(parentClass, "", childClass, childFKFieldName);
	}
	
	private void registerFK (ForeignKey fk) {
		registeredFKs.put(fk.getFkHash(), fk);
	}

	public void registerParentChildFK(Class<?> parentClass, Class<?> childClass,
			String childFKFieldName) {
		ForeignKey fk = new ParentChildForeignKey(childClass, childFKFieldName,
				childClass, childFKFieldName);
		registerFK (fk);
	}

	public void registerFK(Class<?> parentClass, String parentPKFieldName,
			Class<?> childClass, String childFKFieldName) {
		ForeignKey fk = new ForeignKey(childClass, childFKFieldName,
				childClass, childFKFieldName);
		registerFK (fk);
		//registeredFKs.put(fk.getFKHash(), fk);
	}
}
