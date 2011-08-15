package com.algo.smartgwt.client;

import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;

public class Context {
	private static Context context = new Context();
	// public static Canvas coursesCanvas;
	public static Context get() {
		return context;
	}

	public static Canvas getCourseCanvas() {
		return LGFactory.getCourseCanvas();
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

	public Canvas getChaptersCanvas() {
		return chaptersCanvas;
	}
	public RestDataSource getCourcesDataSource() {
		return courcesDataSource;
	}

	public ListGrid getCourcesListGrid() {
		return courcesListGrid;
	}

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

}
