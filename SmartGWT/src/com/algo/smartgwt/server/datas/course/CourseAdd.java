package com.algo.smartgwt.server.datas.course;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
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

public class CourseAdd extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -577956555519422681L;

	public CourseAdd() {
		// TODO Auto-generated constructor stub
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
	
	private Course storeCourse(Course cntr){
		Objectify ofy = DBF.getObjectify();

		Course c = cntr;
		ofy.put(c);
		assert c.getId() != null;  
		//log.log(Level.WARNING, "porsche.getId() == " + c.getId());
		return c;
	}
	
	
	private void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		//Logger log = Logger.getLogger(Add.class.getName());
		//log.log(Level.WARNING, req.toString());
		
		//Enumeration en = req.getAttributeNames();
		String s = "";
		
		BufferedReader rdr = req.getReader();
		
		String  s0;
		do {
			s0 = rdr.readLine(); 
			s += (s0==null?"":s0);
		} while ( s0 != null);
		
		List<Course> cntr0 = DBF.deJSONCourse(s);
		
		Course c0_ = DBF.testDeJSON_(s, new Course());

		resp.setContentType("text/plain");
		
		Course c = storeCourse(cntr0.get(0));
		
		Gson gson = new Gson();
		
		List<Course> c0 = new ArrayList<Course>();
		c0.add(c);
		Object rpl = DBF.prepareJSONReply(c0);
		log.log(Level.WARNING, gson.toJson( rpl ));
		resp.getWriter().println(gson.toJson( rpl ));		
		
	}
	
	private static Logger log = Logger.getLogger(CourseAdd.class.getName()); 
	
}
