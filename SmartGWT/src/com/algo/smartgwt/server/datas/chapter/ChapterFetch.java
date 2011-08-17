package com.algo.smartgwt.server.datas.chapter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;
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
	
	private String recode (String s) {
		// Create the encoder and decoder for ISO-8859-1
		Charset charset1251 = Charset.forName("CP1251");
		Charset charset = Charset.forName("UTF-8");
		CharsetDecoder decoder = charset1251.newDecoder();
		CharsetEncoder encoder = charset.newEncoder();

		try {
		    // Convert a string to ISO-LATIN-1 bytes in a ByteBuffer
		    // The new ByteBuffer is ready to be read.
		    ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(s));
		    //bbuf.f
		    //String ss = bbuf.toString(); 
		    //return bbuf.toString();

		    // Convert ISO-LATIN-1 bytes in a ByteBuffer to a character ByteBuffer and then to a string.
		    // The new ByteBuffer is ready to be read.
		    CharBuffer cbuf = decoder.decode(bbuf);
		    String s0 = cbuf.toString();//new String(cbuf.array(),"CP1251");
		    s0 = new String(s.getBytes("UTF-8"), "Cp1251");
		    return s0;
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e0){
			e0.printStackTrace();
		}
		return null;
	}
	
	class Test {
		String ttt;
	}
	
	private void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.setContentType("application/json");
		resp.setContentType("text/javascript; charset=utf-8");
		resp.setContentType("text/html; charset=utf-8");
		
		resp.setCharacterEncoding("utf-8");
		
		//log.log(Level.WARNING, resp.toString());
		
		String courseID = req.getParameter(LnrGlobals.CURRENT_COURCE_ID_PARAM_NAME); 
		//log.log(Level.WARNING, courseID + " == " + courseID);
		
		PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF8"), true);
		/*
		Test t = new Test();
		t.ttt = "проба";
		Gson gson = new Gson();
		String ret = gson.toJson(t);
		out.println(ret);
		if (true) {
			return;
		}
		*/
		
		try {
			Course crs = DBF.getObjectify().get(Course.class,new Long(courseID));
			List<Chapter> chptrs = crs.getChapters();
			Gson gson = new Gson();
			String ret = gson.toJson(DBF.prepareJSONReply(chptrs));
			//log.log(Level.WARNING, ""+crs);
			//resp.getWriter().println(recode(ret));
			out.println(ret);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			List<Chapter> chptrs = new ArrayList<Chapter>();
			Gson gson = new Gson();
			String ret = gson.toJson(DBF.prepareJSONReply(chptrs));
			resp.getWriter().println(ret);
		}
		
		
		//resp.getWriter().println(gson.toJson(new AReply () ));
		//resp.getWriter().println(gson.toJson(DBF.prepareJSONReply(new Country("North America","United States","US")) ));
		
		//resp.getWriter().println(courses);
				
	}
}
