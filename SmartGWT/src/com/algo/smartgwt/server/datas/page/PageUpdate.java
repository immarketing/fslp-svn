package com.algo.smartgwt.server.datas.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algo.smartgwt.server.Utils;
import com.algo.smartgwt.server.db.Chapter;
import com.algo.smartgwt.server.db.DBF;
import com.google.gson.Gson;
import com.googlecode.objectify.Objectify;

public class PageUpdate extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424585213995453837L;

	/**
	 * 
	 */

	public PageUpdate() {
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
	
	private Chapter updateChapter(Chapter vals[]){
		Objectify ofy = DBF.getObjectify();
		Chapter old = ofy.get(Chapter.class, vals[0].getId());
		old.doUpdate(ofy, vals[1]);
		return old;
	}
	
	private void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		String s = "";
		
		s = Utils.getRequestContent(req);
		
		/*
		BufferedReader rdr = req.getReader();
		
		String  s0;
		do {
			s0 = rdr.readLine(); 
			s += (s0==null?"":s0);
		} while ( s0 != null);
		*/
		
		resp.setContentType("text/plain");
		resp.setContentType("application/json");		
		resp.setCharacterEncoding("utf-8");
		
		Chapter cntr0[] = DBF.deJSONOldNewChapter(s);
		Chapter updated = updateChapter(cntr0);
		
		Object rpl = DBF.prepareJSONReply(updated);
		
		Gson gson = new Gson();
		
		resp.getWriter().println(gson.toJson( rpl ));
		
		
	}
	
	
}
