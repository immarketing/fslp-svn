package com.algo.smartgwt.server.datas.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algo.smartgwt.server.Utils;
import com.algo.smartgwt.server.db.DBF;
import com.algo.smartgwt.server.db.Page;
import com.google.gson.Gson;
import com.googlecode.objectify.Objectify;

public class PageUpdate extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7757800677932365561L;

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
	
	private Page updatePage(Page vals[]){
		Objectify ofy = DBF.getObjectify();
		Page old = ofy.get(Page.class, vals[0].getId());
		old.doUpdate(ofy, vals[1]);
		return old;
	}
	
	private void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		String s = "";
		
		s = Utils.getRequestContent(req);
		
		//resp.setContentType("text/plain");
		resp.setContentType("application/json");		
		resp.setCharacterEncoding("utf-8");
		
		Page cntr0[] = DBF.deJSONOldNewPage(s);
		Page updated = updatePage(cntr0);
		
		Object rpl = DBF.prepareJSONReply(updated);
		
		Gson gson = new Gson();
		
		resp.getWriter().println(gson.toJson( rpl ));
		
		
	}
	
	
}
