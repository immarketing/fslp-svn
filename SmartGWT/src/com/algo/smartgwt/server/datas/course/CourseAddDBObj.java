package com.algo.smartgwt.server.datas.course;

import com.algo.smartgwt.server.datas.common.DBObjAddServlet;
import com.algo.smartgwt.server.db.Course;

public class CourseAddDBObj extends DBObjAddServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2650387238127184017L;
	
	public CourseAddDBObj(){
		initialize(Course.class);
	}

}
