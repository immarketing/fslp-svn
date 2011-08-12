package com.algo.smartgwt.server.datas.chapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algo.smartgwt.server.db.Course;
import com.algo.smartgwt.server.db.DBF;
import com.google.gson.Gson;
import com.googlecode.objectify.Objectify;

public class ChapterDelete extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9022826918179152597L;
	
	public ChapterDelete() {
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
	
	private Course deleteCourse(Course val){
		Objectify ofy = DBF.getObjectify();
		ofy.delete(val);
		//Course old = ofy.get(Course.class, val.getId());
		//old.doUpdate(ofy, vals[1]);
		return val;
	}
	
	private void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		String s = "";
		
		BufferedReader rdr = req.getReader();
		
		String  s0;
		do {
			s0 = rdr.readLine(); 
			s += (s0==null?"":s0);
		} while ( s0 != null);
		
		resp.setContentType("text/plain");
		List<Course> cntr0 = DBF.deJSONCourse(s);
		Course updated = deleteCourse(cntr0.get(0));
		
		Object rpl = DBF.prepareJSONReply(updated);
		Gson gson = new Gson();
		
		resp.getWriter().println(gson.toJson( rpl ));
		
		
	}
	

}
