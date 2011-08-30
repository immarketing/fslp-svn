package com.algo.smartgwt.server.datas.common;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DBObjServlet extends HttpServlet {
	private String parentObjParamName = "";
	private boolean hasParentObj = false;
	
	protected void initialize(Class<?> objClass) {
		initialize(objClass, false, null, null);
	}
	
	protected void initialize(Class<?> objClass, Class<?> parentObjClass, String parentObjParamName) {
		initialize(objClass, true, parentObjClass, parentObjParamName);
	}
	
	protected void initialize(Class<?> objClass, boolean hasParentObj, Class<?> parentObjClass, String parentObjParamName) {
		setHasParentObj(hasParentObj);
		setObjClass(objClass);
		setParentObjClass(parentObjClass);
		setParentObjParamName(parentObjParamName);
	}

	public boolean isHasParentObj() {
		return hasParentObj;
	}

	public void setHasParentObj(boolean hasParentObj) {
		this.hasParentObj = hasParentObj;
	}

	public String getParentObjParamName() {
		return parentObjParamName;
	}

	public void setParentObjParamName(String parentObjParamName) {
		this.parentObjParamName = parentObjParamName;
	}

	private Class<?> objClass = null;

	public Class<?> getObjClass() {
		return objClass;
	}

	public void setObjClass(Class<?> objClass) {
		this.objClass = objClass;
	}

	private Class<?> parentObjClass = null;
	public Class<?> getParentObjClass() {
		return parentObjClass;
	}

	public void setParentObjClass(Class<?> parentObjClass) {
		this.parentObjClass = parentObjClass;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4108138912790436984L;

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
	
	protected void onHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) {		
	}

	protected PrintWriter getPrintWriter (HttpServletResponse resp) throws UnsupportedEncodingException, IOException {
		return getPrintWriter(resp, "UTF8");
		
	}
	
	protected PrintWriter getPrintWriter (HttpServletResponse resp, String charEnc) throws UnsupportedEncodingException, IOException {
		// charEnc "UTF8"
		PrintWriter result = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), charEnc), true);
		return result;
		//out.println(ret);
		
	}
	

	protected void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		onHandleRequest(req, resp);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");

	}

}
