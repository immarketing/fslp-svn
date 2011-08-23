package com.algo.smartgwt.server.datas.page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algo.smartgwt.LnrGlobals;
import com.algo.smartgwt.server.db.Chapter;
import com.algo.smartgwt.server.db.DBF;
import com.algo.smartgwt.server.db.Page;
import com.google.gson.Gson;
import com.googlecode.objectify.Objectify;

public class PageAdd extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8802132814365086602L;

	public PageAdd() {
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
	
	private Page storePage(Long chapterID, Page cntr){
		Objectify ofy = DBF.getObjectify();
		
		Chapter crs = ofy.get(Chapter.class, chapterID);
		

		Page c = cntr;
		crs.addPage(c);
		assert c.getId() != null;  
		//log.log(Level.WARNING, "porsche.getId() == " + c.getId());
		return c;
	}
	
	
	private void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		String s = "";
		
		s = com.algo.smartgwt.server.Utils.getRequestContent(req);
		
		//String chapterID = req.getParameter(LnrGlobals.CURRENT_COURCE_ID_PARAM_NAME); 
		String chapterID = req.getParameter(LnrGlobals.CURRENT_CHAPTER_ID_PARAM_NAME);
		
		List<Page> cntr0 = DBF.deJSONPage(s);

		resp.setContentType("application/json");
		
		resp.setCharacterEncoding("utf-8");
		
		Page c = storePage(new Long(chapterID), cntr0.get(0));
		
		Gson gson = new Gson();
		List<Page> c0 = new ArrayList<Page>();
		c0.add(c);
		Object rpl = DBF.prepareJSONReply(c0);
		//log.log(Level.WARNING, gson.toJson( rpl ));
		resp.getWriter().println(gson.toJson( rpl ));
		
	}
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(PageAdd.class.getName()); 
	
}
