package com.algo.smartgwt.server.db;

import javax.persistence.Id;
import javax.persistence.Transient;

public class Car {
	@Id
	Long id;
	String vin;
	private int color;
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	String stringColor;
	@Transient
	String doNotPersist;

	private Car() {
	}

	public Car(String vin, String stringColor) {
		this();
		this.vin = vin;
		this.stringColor = stringColor;

	}

	public Car(String vin, int color) {
		this();
		this.vin = vin;
		this.setColor(color);
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param color the color to set
	 */
	public void setColorString(String stringColor) {
		this.stringColor = stringColor;
	}

	/**
	 * @return the color
	 */
	public String getColorString() {
		return stringColor;
	}
}
