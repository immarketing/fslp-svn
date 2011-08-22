package com.algo.smartgwt.client;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.Layout;

public class Context {
	private static Context context = new Context();
	
	final public static String CURRENT_CHAPTER_ID_PARAM_NAME =  "currentchapterid";
	
	final public static String CURRENT_COURCE_ID_PARAM_NAME =  "currentcourseid";
	
	

	// public static Canvas coursesCanvas;
	public static Context get() {
		return context;
	}

	private Map<String,String> chapterRequestParams = new HashMap<String,String> ();

	private Canvas chaptersCanvas;

	private RestDataSource chaptersDataSource;

	private ListGrid chaptersListGrid;
	

	private RestDataSource courcesDataSource;

	private ListGrid courcesListGrid;

	private Canvas coursesCanvas;

	private String currentCourseID = "";
	
	private String currentChapterID = "";

	private Canvas pageCanvas;

	private ListGrid pagesListGrid;

	public ListGrid getPagesListGrid() {
		return pagesListGrid;
	}

	private RestDataSource pagesDataSource;

	public RestDataSource getPagesDataSource() {
		return pagesDataSource;
	}

	public void setPagesDataSource(RestDataSource pagesDataSource) {
		this.pagesDataSource = pagesDataSource;
	}

	public void setPagesListGrid(ListGrid pageListGrid) {
		this.pagesListGrid = pageListGrid;
	}

	private Map<String,String> pageRequestParams = new HashMap<String,String> ();
	private Context (){
		chapterRequestParams.put(CURRENT_COURCE_ID_PARAM_NAME, currentCourseID);
	}
	
	public Map<String, String> getChapterRequestParams() {
		return chapterRequestParams;
	}
	public  Canvas getChaptersCanvas() {
		synchronized (Context.get()){
			Context cntx = Context.get();
			if (cntx.chaptersCanvas == null ) {
				LGFactory.createChapterCanvas();
			}
			return cntx.chaptersCanvas;
		}
	}

	public RestDataSource getChaptersDataSource() {
		//chaptersDataSource.removeData(null, null, null);
		return chaptersDataSource;
	}

	public ListGrid getChaptersListGrid() {
		return chaptersListGrid;
	}

	public RestDataSource getCourcesDataSource() {
		return courcesDataSource;
	}

	public ListGrid getCourcesListGrid() {
		return courcesListGrid;
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

	@Deprecated
	public Canvas getCoursesCanvas() {
		return coursesCanvas;
	}

	public String getCurrentCourseID() {
		return currentCourseID;
	}
	
	public  Canvas getPageCanvas() {
		synchronized (Context.get()){
			Context cntx = Context.get();
			if (cntx.pageCanvas == null ) {
				LGFactory.createPageCanvas();
			}
			return cntx.pageCanvas;
		}
	}
	public Map<String, String> getPageRequestParams() {
		return pageRequestParams;
	}

	public void setChaptersCanvas(Canvas chaptersCanvas) {
		this.chaptersCanvas = chaptersCanvas;
	}

	public void setChaptersDataSource(RestDataSource chaptersDataSource) {
		this.chaptersDataSource = chaptersDataSource;
	}
	public void setChaptersListGrid(ListGrid chaptersListGrid) {
		this.chaptersListGrid = chaptersListGrid;
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

	public void setCurrentCourseID(String currentCourseID) {
		this.currentCourseID = currentCourseID;
		chapterRequestParams.put(CURRENT_COURCE_ID_PARAM_NAME, currentCourseID);
	}
	public void setCurrentChapterID(String currentChapterID) {
		this.currentChapterID = currentChapterID;
		pageRequestParams.put(CURRENT_CHAPTER_ID_PARAM_NAME, currentChapterID);
	}
	public void setPageCanvas(Layout pageCanvas) {
		this.pageCanvas = pageCanvas;
		
	}

}
