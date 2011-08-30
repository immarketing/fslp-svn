package com.algo.smartgwt.server.datas.course;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algo.smartgwt.server.db.Country;
import com.algo.smartgwt.server.db.Course;
import com.algo.smartgwt.server.db.DBF;
import com.google.gson.Gson;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

@Deprecated
public class CourseFetch extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8547756851725976301L;
	
	public  CourseFetch (){
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doGet(req, resp);
		doHandleRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doPost(req, resp);
		doHandleRequest(req, resp);
	}
	
	private Logger log = Logger.getLogger(Add.class.getName()); 

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
	
	private void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		
		log.log(Level.WARNING, req.toString());
		
		String courses = getCourses(req,resp); 
		//resp.getWriter().println(gson.toJson(new AReply () ));
		//resp.getWriter().println(gson.toJson(DBF.prepareJSONReply(new Country("North America","United States","US")) ));
		resp.getWriter().println(courses);
				
	}
}
