package com.algo.smartgwt.server.datas.course;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

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
		// super.doPost(req, resp);
		doHandleRequest(req, resp);
	}

	class ReplyRow {
		String continent = "North America";
		String countryName = "United States";
		String countryCode = "US";

	}

	class Reply {
		int status = 0;
		int startRow = 0;
		int endRow = 1;
		int totalRows = 1;
		Object data[] = { new Object() {
			Object record = new ReplyRow();
		} 
		};
	};

	private void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		// Logger logger = Logger.getLogger("NameOfYourLogger");
		Logger log = Logger.getLogger(Add.class.getName());
		log.log(Level.WARNING, req.toString());

		resp.setContentType("text/plain");
		Gson gson = new Gson();

		resp.getWriter().println(gson.toJson(new Reply()));

		if (true) {
			return;
		}
		;

		resp.getWriter()
				.println(
						"<response>"
								+ "<status>0</status>"
								+ "<startRow>0</startRow>"
								+ "<endRow>7</endRow>"
								+ "<totalRows>7</totalRows>"
								+ "<data>"
								+ "<record>"
								+ "    <continent>North America</continent>"
								+ "    <countryName>United States</countryName>"
								+ "    <countryCode>US</countryCode>"
								+ "    <area>9631420</area>"
								+ "    <population>298444215</population>"
								+ "    <gdp>12360000</gdp>"
								+ "    <independence>1776-07-04</independence>"
								+ "    <government>federal republic</government>"
								+ "    <government_desc>2</government_desc>"
								+ "    <capital>Washington, DC</capital>"
								+ "    <member_g8>true</member_g8>"
								+ "    <article>http://en.wikipedia.org/wiki/United_states</article>"
								+ "</record>"
								+ "<record>"
								+ "    <continent>Europe</continent>"
								+ "    <countryName>Germany</countryName>"
								+ "    <countryCode>GM</countryCode>"
								+ "    <area>357021</area>"
								+ "    <population>82422299</population>"
								+ "    <gdp>2504000</gdp>"
								+ "    <independence>1871-01-18</independence>"
								+ "    <government>federal republic</government>"
								+ "    <government_desc>2</government_desc>"
								+ "    <capital>Berlin</capital>"
								+ "    <member_g8>true</member_g8>"
								+ "    <article>http://en.wikipedia.org/wiki/Germany</article>"
								+ "</record>"
								+ "<record>"
								+ "    <continent>Europe</continent>"
								+ "    <countryName>United Kingdom</countryName>"
								+ "    <countryCode>UK</countryCode>"
								+ "    <area>244820</area>"
								+ "    <population>60609153</population>"
								+ "    <gdp>1830000</gdp>"
								+ "    <independence>1801-01-01</independence>"
								+ "    <government>constitutional monarchy</government>"
								+ "    <government_desc>1</government_desc>"
								+ "    <capital>London</capital>"
								+ "    <member_g8>true</member_g8>"
								+ "    <article>http://en.wikipedia.org/wiki/United_kingdom</article>"
								+ "</record>"
								+ "<record>"
								+ "    <continent>Europe</continent>"
								+ "    <countryName>France</countryName>"
								+ "    <countryCode>FR</countryCode>"
								+ "    <area>547030</area>"
								+ "    <population>60876136</population>"
								+ "    <gdp>1816000</gdp>"
								+ "    <government>republic</government>"
								+ "    <government_desc>5</government_desc>"
								+ "    <capital>Paris</capital>"
								+ "    <member_g8>true</member_g8>"
								+ "    <article>http://en.wikipedia.org/wiki/France</article>"
								+ "</record>"
								+ "<record>"
								+ "    <continent>South America</continent>"
								+ "    <countryName>Brazil</countryName>"
								+ "    <countryCode>BR</countryCode>"
								+ "    <area>8511965</area>"
								+ "    <population>188078227</population>"
								+ "    <gdp>1556000</gdp>"
								+ "    <independence>1822-09-07</independence>"
								+ "    <government>federative republic</government>"
								+ "    <government_desc>3</government_desc>"
								+ "    <capital>Brasilia</capital>"
								+ "    <member_g8>false</member_g8>"
								+ "    <article>http://en.wikipedia.org/wiki/Brazil</article>"
								+ "</record>"
								+ "<record>"
								+ "    <continent>North America</continent>"
								+ "    <countryName>Canada</countryName>"
								+ "    <countryCode>CA</countryCode>"
								+ "    <area>9984670</area>"
								+ "    <population>33098932</population>"
								+ "    <gdp>1114000</gdp>"
								+ "    <independence>1867-07-01</independence>"
								+ "    <government>constitutional monarchy with parliamentary democracy and federation</government>"
								+ "    <government_desc>1</government_desc>"
								+ "    <capital>Ottawa</capital>"
								+ "    <member_g8>true</member_g8>"
								+ "    <article>http://en.wikipedia.org/wiki/Canada</article>"
								+ "</record>"
								+ "<record>"
								+ "    <continent>North America</continent>"
								+ "    <countryName>Mexico</countryName>"
								+ "    <countryCode>MX</countryCode>"
								+ "    <area>1972550</area>"
								+ "    <population>107449525</population>"
								+ "    <gdp>1067000</gdp>"
								+ "    <independence>1810-09-16</independence>"
								+ "    <government>federal republic</government>"
								+ "    <government_desc>2</government_desc>"
								+ "    <capital>Mexico (Distrito Federal)</capital>"
								+ "    <member_g8>false</member_g8>"
								+ "    <article>http://en.wikipedia.org/wiki/Mexico</article>"
								+ "</record>" +

								"</data>" + "</response>" + "");

	}
}
