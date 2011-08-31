package com.algo.smartgwt.server.datas.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algo.smartgwt.server.Utils;
import com.algo.smartgwt.server.db.DBF;
import com.google.gson.Gson;

public class DBObjUpdateServlet extends DBObjServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3572766564544759312L;
	
	
	protected <T, ST> void readAndSaveObject(HttpServletRequest req,
			HttpServletResponse resp, String content, Class<T> aClass,
			Class<ST> aParentClass) {
		try {
			T readObject = aClass.newInstance();
			Utils.DataJSON<T> readObjects = Utils.deJsonObject(content, readObject); 
			//readObject = Utils.deJsonAddObject(content, readObject);
			readObject = DBF.storeDBObj(readObject);

			Gson gson = new Gson();
			String reply = gson.toJson(DBF.prepareJSONReply(readObject));

			getPrintWriter(resp).println(reply);

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	protected void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		super.doHandleRequest(req, resp);

		String content = Utils.getRequestContent(req);

		readAndSaveObject(req, resp, content, getObjClass(),
				getParentObjClass());
	}

}
