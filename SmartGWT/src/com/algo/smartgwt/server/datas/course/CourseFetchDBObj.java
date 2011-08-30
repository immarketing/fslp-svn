package com.algo.smartgwt.server.datas.course;

import com.algo.smartgwt.server.datas.common.DBObjFetchServlet;
import com.algo.smartgwt.server.db.Course;

public class CourseFetchDBObj extends DBObjFetchServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4298429814863215038L;
	
	public CourseFetchDBObj (){
		initialize(Course.class);
	}

}
