package com.xzm.lpr.domain;

import java.io.Serializable;
import java.sql.Date;

public class TraRecord implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String licenseplate;
	private String date_in;
	private String date_out;	
	
	public TraRecord() {
		super();
	}
	
	public TraRecord(Integer id,String licenseplate,String date_in,String date_out) {
		this.id = id;
		this.licenseplate = licenseplate;
		this.date_in = date_in;
		this.date_out = date_out;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLicenseplate() {
		return licenseplate;
	}

	public void setLicenseplate(String licenseplate) {
		this.licenseplate = licenseplate;
	}

	public String getDate_in() {
		return date_in.split("[.]")[0];
	}

	public void setDate_in(String date_in) {
		this.date_in = date_in;
	}

	public String getDate_out() {
		return date_out.split("[.]")[0];
	}

	public void setDate_out(String date_out) {
		this.date_out = date_out;
	}

}
