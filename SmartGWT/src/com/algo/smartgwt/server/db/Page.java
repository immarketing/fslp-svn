package com.algo.smartgwt.server.db;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class Page {
	@Id
	Long id;
	
	String pageName ;
	
	Key<Chapter> chapterKey;
	
	public Key<Chapter> getChapterKey() {
		return chapterKey;
	}

	public void setChapterKey(Key<Chapter> chapterKey) {
		this.chapterKey = chapterKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public Page (){
		this("");
	}
	
	public Page (String pageName){
		this(null,pageName);
	}
	
	public Page (Key<Chapter> chapterKey,String pageName){
		setChapterKey(chapterKey);
		setPageName(pageName);
	}

	public void doUpdate(Objectify ofy, Page newVals){
		setPageName(newVals.getPageName());
		ofy.put(this);
	}

}
