package com.algo.smartgwt.server.datas.common;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algo.smartgwt.server.Utils;
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
		
		List<?> aList = Utils.createEmptyList(getObjClass());

		if (isHasParentObj()) {
			String parentobjIDString = req
					.getParameter(getParentObjParamName());
			try {
				parentobjID = new Long(parentobjIDString);
			} catch (Exception ex) {
			}
			
			if (parentobjID != null ) {
				// родитель - есть. необходимо найти всех "детей" этого объекта.
				// для этого:
				aList = DBF.listDBObjChilds(getParentObjClass(), parentobjID, getObjClass());
			} else {
				// родитель - не определен.
				// не отдаем ничего
			}
		} else {
			// парента нет , значит, надо отдать
			// все?
			aList = DBF.listDBObj(getObjClass());
		}

		if (parentobjID == null) {
		}
		Gson gson = new Gson();
		reply = gson.toJson(DBF.prepareJSONReply(aList));

		getPrintWriter(resp).println(reply);
	}
	
	protected static Logger logger = null;
	
	static {
		logger = Logger.getLogger(new Throwable() .getStackTrace()[0].getClassName());
		//Logger.getLogger( );
	}
}
