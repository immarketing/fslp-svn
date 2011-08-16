package com.algo.smartgwt.client;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;

public class Context {
	private static Context context = new Context();
	
	private Context (){
		chapterRequestParams.put(CURRENT_COURCE_ID_PARAM_NAME, currentCourseID);
	}
	
	// public static Canvas coursesCanvas;
	public static Context get() {
		return context;
	}
	
	

	public Canvas getCourseCanvas() {
		synchronized (Context.get()){
			Context cntx = Context.get();
			if (cntx.coursesCanvas == null ) {
				LGFactory.createCourseCanvas();
			}
			return cntx.coursesCanvas;
		}
	}

	private RestDataSource chaptersDataSource;

	public RestDataSource getChaptersDataSource() {
		//chaptersDataSource.removeData(null, null, null);
		return chaptersDataSource;
	}

	public void setChaptersDataSource(RestDataSource chaptersDataSource) {
		this.chaptersDataSource = chaptersDataSource;
	}

	public ListGrid getChaptersListGrid() {
		return chaptersListGrid;
	}

	public void setChaptersListGrid(ListGrid chaptersListGrid) {
		this.chaptersListGrid = chaptersListGrid;
	}

	private ListGrid chaptersListGrid;

	private Canvas chaptersCanvas;

	private RestDataSource courcesDataSource;

	private ListGrid courcesListGrid;

	private Canvas coursesCanvas;

	public  Canvas getChaptersCanvas() {
		
		synchronized (Context.get()){
			Context cntx = Context.get();
			if (cntx.chaptersCanvas == null ) {
				LGFactory.createChapterCanvas();
			}
			return cntx.chaptersCanvas;
		}
	}
	
	public RestDataSource getCourcesDataSource() {
		return courcesDataSource;
	}

	public ListGrid getCourcesListGrid() {
		return courcesListGrid;
	}

	@Deprecated
	public Canvas getCoursesCanvas() {
		return coursesCanvas;
	}

	public void setChaptersCanvas(Canvas chaptersCanvas) {
		this.chaptersCanvas = chaptersCanvas;
	}

	public void setCourcesDataSource(RestDataSource courcesDataSource) {
		this.courcesDataSource = courcesDataSource;
	}

	public void setCourcesListGrid(ListGrid courcesListGrid) {
		this.courcesListGrid = courcesListGrid;
	}

	public void setCoursesCanvas(Canvas coursesCanvas) {
		this.coursesCanvas = coursesCanvas;
	}
	
	private String currentCourseID = "";
	public String getCurrentCourseID() {
		return currentCourseID;
	}

	public void setCurrentCourseID(String currentCourseID) {
		this.currentCourseID = currentCourseID;
		chapterRequestParams.put(CURRENT_COURCE_ID_PARAM_NAME, currentCourseID);
		
        //DSRequest dsr = new DSRequest();
        //dsr.setParams(chapterRequestParams);
        //Context.get().getChaptersDataSource().setRequestProperties(dsr);
		
	}

	private Map<String,String> chapterRequestParams = new HashMap<String,String> ();
	public Map<String, String> getChapterRequestParams() {
		return chapterRequestParams;
	}

	final private static String CURRENT_COURCE_ID_PARAM_NAME =  "currentcourseid";  
	

}
