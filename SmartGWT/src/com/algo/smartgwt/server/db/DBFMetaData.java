package com.algo.smartgwt.server.db;

import java.util.Hashtable;
import java.util.Map;

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

	private static class DefaultForeignKey extends ForeignKey{
		private DefaultForeignKey (Class<?> parentClass, String parentPKFieldName,
				Class<?> childClass, String childFKFieldName) {
			super(parentClass, parentPKFieldName, childClass, childFKFieldName);
		}
		
		protected String calcFKHash() {
			return calcFKHash(parentClass, childClass);
		}
	}
	
	private static DBFMetaData singleton = new DBFMetaData();

	public static DBFMetaData get() {
		return singleton;
	}

	Map<String, ForeignKey> registeredFKs = new Hashtable<String, DBFMetaData.ForeignKey>();

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

	public void registerDBClass(Class<?> aClass) {
		ObjectifyService.register(aClass);
	}

	public void registerFK(Class<?> parentClass, Class<?> childClass,
			String childFKFieldName) {
		registerFK(parentClass, "", childClass, childFKFieldName);
	}
	
	private void registerFK (ForeignKey fk) {
		registeredFKs.put(fk.getFkHash(), fk);
	}

	public void registerDefaultFK(Class<?> parentClass, Class<?> childClass,
			String childFKFieldName) {
		ForeignKey fk = new DefaultForeignKey(childClass, childFKFieldName,
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