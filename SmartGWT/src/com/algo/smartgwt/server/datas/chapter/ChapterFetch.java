package com.algo.smartgwt.server.datas.chapter;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algo.smartgwt.LnrGlobals;
import com.algo.smartgwt.server.db.Chapter;
import com.algo.smartgwt.server.db.Course;
import com.algo.smartgwt.server.db.DBF;
import com.google.gson.Gson;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

public class ChapterFetch extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8547756851725976301L;
	
	public  ChapterFetch (){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doHandleRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doHandleRequest(req, resp);
	}
	
	private Logger log = Logger.getLogger(ChapterFetch.class.getName()); 

	/*
	private List<Course> getCoursesList(HttpServletRequest req,
			HttpServletResponse resp){
		Objectify ofy = DBF.getObjectify();
		//Car car = ofy.query(Car.class).filter("vin", "123456789").get();
		// The Query itself is Iterable
		Query<Course> q = ofy.query(Course.class).filter("id >=", 1);
		List<Course> lc = q.list();
		return lc;
	}

	private String getCourses (HttpServletRequest req,
			HttpServletResponse resp) {
		Gson gson = new Gson();
		List<Course> lc = getCoursesList(req,resp);
		String ret = gson.toJson(DBF.prepareJSONReply(lc ) ); 
		return ret;
	}
	*/
	
	private void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		
		log.log(Level.WARNING, req.toString());
		
		String courseID = req.getParameter(LnrGlobals.CURRENT_COURCE_ID_PARAM_NAME); 
		log.log(Level.WARNING, courseID + " == " + courseID);
		
		try {
			Course crs = DBF.getObjectify().get(Course.class,new Long(courseID));
			List<Chapter> chptrs = crs.getChapters();
			log.log(Level.WARNING, ""+crs);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		//resp.getWriter().println(gson.toJson(new AReply () ));
		//resp.getWriter().println(gson.toJson(DBF.prepareJSONReply(new Country("North America","United States","US")) ));
		
		//resp.getWriter().println(courses);
				
	}
}
