package com.algo.smartgwt.server.datas.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algo.smartgwt.server.Utils;

public class DBObjAddServlet extends DBObjServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -960382914926005772L;

	protected void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		super.doHandleRequest(req, resp);
		
		String content = Utils.getRequestContent(req);
	}
}
