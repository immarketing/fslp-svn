package com.algo.smartgwt.server.datas.chapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jcp.xml.dsig.internal.dom.Utils;

import com.algo.smartgwt.LnrGlobals;
import com.algo.smartgwt.server.db.Chapter;
import com.algo.smartgwt.server.db.Course;
import com.algo.smartgwt.server.db.DBF;
import com.google.gson.Gson;
import com.googlecode.objectify.Objectify;

public class ChapterAdd extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -577956555519422681L;

	public ChapterAdd() {
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
	
	private Chapter storeChapter(Long courseID, Chapter cntr){
		Objectify ofy = DBF.getObjectify();
		
		Course crs = ofy.get(Course.class, courseID);
		

		Chapter c = cntr;
		crs.addChapter(c);
		//ofy.put(c);
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
		
		s = com.algo.smartgwt.server.Utils.getRequestContent(req);
		/*
		BufferedReader rdr = null;//req.getReader();
		//PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF8"), true);
		InputStreamReader isr = new  InputStreamReader(req.getInputStream(),"UTF8");
		rdr = new BufferedReader(isr);
		//isr.re
		String  s0;
		do {
			s0 = rdr.readLine(); 
			s += (s0==null?"":s0);
		} while ( s0 != null);
		*/
		
		String courseID = req.getParameter(LnrGlobals.CURRENT_COURCE_ID_PARAM_NAME); 
		
		List<Chapter> cntr0 = DBF.deJSONChapter(s);

		//resp.setContentType("text/plain");
		resp.setContentType("application/json");
		
		resp.setCharacterEncoding("utf-8");
		
		Chapter c = storeChapter(new Long(courseID), cntr0.get(0));
		
		/*
		
		Course c = storeCourse(cntr0.get(0));
		
		
		*/		
		Gson gson = new Gson();
		List<Chapter> c0 = new ArrayList<Chapter>();
		c0.add(c);
		Object rpl = DBF.prepareJSONReply(c0);
		log.log(Level.WARNING, gson.toJson( rpl ));
		resp.getWriter().println(gson.toJson( rpl ));
		
	}
	
	private static Logger log = Logger.getLogger(ChapterAdd.class.getName()); 
	
}
