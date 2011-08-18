package com.algo.smartgwt.server.datas.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algo.smartgwt.server.Utils;
import com.algo.smartgwt.server.db.DBF;
import com.algo.smartgwt.server.db.Page;
import com.google.gson.Gson;
import com.googlecode.objectify.Objectify;

public class PageDelete extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5062654880875805089L;

	public PageDelete() {
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
	
	private Page deletePage(Page val){
		Objectify ofy = DBF.getObjectify();
		ofy.delete(val);
		return val;
	}
	
	private void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		String s = "";
		
		s = Utils.getRequestContent(req);
		
		List<Page> cntr0 = DBF.deJSONPage(s);
		Page updated = deletePage(cntr0.get(0));
		
		Object rpl = DBF.prepareJSONReply(updated);
		Gson gson = new Gson();
		
		resp.getWriter().println(gson.toJson( rpl ));
		
		
	}
	

}
