package com.algo.smartgwt.server.db;

import java.util.List;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class Course {
	@Id
	Long id;
	
	String courseName ;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Course (){
		this("");
	}
	
	public Course (String courseName){
		this.courseName = courseName;
	}
	
	public void doUpdate(Objectify ofy, Course newVals){
		setCourseName(newVals.getCourseName());
		ofy.put(this);
	}
	
	public List<Chapter> getChapters(){
		Objectify ofy = DBF.getObjectify();
		return ofy.query(Chapter.class).filter("courseKey", new Key<Course>(Course.class, getId())).list();
	}

}
