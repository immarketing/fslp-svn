package com.algo.smartgwt.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Utils {
	
	public static <T> List<T> createEmptyList(Class<T> aClass){
		return new ArrayList<T>();
	}
	
	public static String getRequestContent (HttpServletRequest req) throws UnsupportedEncodingException, IOException{
		return getRequestContent (req, "UTF8");
	}
	public static String getRequestContent (HttpServletRequest req, String encoding) throws UnsupportedEncodingException, IOException{
		InputStreamReader isr = new  InputStreamReader(req.getInputStream(),encoding);
		BufferedReader rdr = new BufferedReader(isr);
		int lng = req.getContentLength();
		char chars[]=new char[lng];
		int readed=rdr.read(chars);
		return new String(chars,0,readed);
	}

}
