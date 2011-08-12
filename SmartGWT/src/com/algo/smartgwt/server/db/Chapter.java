package com.algo.smartgwt.server.db;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class Chapter {
	@Id
	Long id;
	
	String chapterName ;
	
	Key<Course> courseKey;
	
	public Key<Course> getCourseKey() {
		return courseKey;
	}

	public void setCourseKey(Key<Course> courseKey) {
		this.courseKey = courseKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String courseName) {
		this.chapterName = courseName;
	}

	public Chapter (){
		this("");
	}
	
	public Chapter (String chapterName){
		this(null,chapterName);
	}
	
	public Chapter (Key<Course> courseKey,String chapterName){
		setCourseKey(courseKey);
		setChapterName(chapterName);
	}

	public void doUpdate(Objectify ofy, Chapter newVals){
		setChapterName(newVals.getChapterName());
		ofy.put(this);
	}

}
