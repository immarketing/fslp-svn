package com.algo.smartgwt.server.db;

import javax.persistence.Id;

@Deprecated
public class Country {
	@Id
	Long id;
	
	String continent = "";
	String countryName = "";
	String countryCode = "";
	
	public Country() {
	}

	public Country(String continent, String countryName,String countryCode ) {
		this();
		this.continent = continent;
		this.countryName = countryName;
		this.countryCode = countryCode;
	}

	public Long getId() {
		return this.id;
	}
	
}
