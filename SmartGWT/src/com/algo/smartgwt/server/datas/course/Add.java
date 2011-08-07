package com.algo.smartgwt.server.datas.course;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Add extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7919090660058543558L;

	public Add() {
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
		//super.doPost(req, resp);
		doHandleRequest(req, resp);
	}
	
	private void doHandleRequest(HttpServletRequest req, HttpServletResponse resp){
		//Logger logger = Logger.getLogger("NameOfYourLogger");
		Logger log = Logger.getLogger(Add.class.getName());
		log.log(Level.WARNING, req.toString());
		
	}
	
	
	
	

}
