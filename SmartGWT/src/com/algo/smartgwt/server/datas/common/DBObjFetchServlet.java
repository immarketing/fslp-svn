package com.algo.smartgwt.server.datas.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algo.smartgwt.server.db.DBF;
import com.google.gson.Gson;

public class DBObjFetchServlet extends DBObjServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7398883167506295216L;

	protected void doHandleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		super.doHandleRequest(req, resp);

		Long parentobjID = null;
		String reply = "";

		if (isHasParentObj()) {
			String parentobjIDString = req
					.getParameter(getParentObjParamName());
			try {
				parentobjID = new Long(parentobjIDString);
			} catch (Exception ex) {
			}
			
			if (parentobjID != null ) {
				
			}
		}

		if (parentobjID == null) {
			// парент объект == нуллу, значит, этого объекта нет и надо отдать
			// все?
			List<?> aList = DBF.listDBObj(getObjClass());
			Gson gson = new Gson();
			reply = gson.toJson(DBF.prepareJSONReply(aList));

			// getObjClass().
		}

		getPrintWriter(resp).println(reply);
	}

}
