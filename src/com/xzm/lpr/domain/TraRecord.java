package com.xzm.lpr.domain;

import java.io.Serializable;

public class TraRecord implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer space_id;
	private String licenseplate;
	private String date_in;
	private String date_out;
	private Integer cost;
	
	public TraRecord() {
		super();
	}
	
	public TraRecord(Integer id,Integer space_id,String licenseplate,String date_in,String date_out,Integer cost) {
		this.id = id;
		this.space_id=space_id;
		this.licenseplate = licenseplate;
		this.date_in = date_in;
		this.date_out = date_out;
		this.cost=cost;
	}
	
	public TraRecord(Integer space_id,String licenseplate,String date_in,String date_out,Integer cost) {
		this.space_id=space_id;
		this.licenseplate = licenseplate;
		this.date_in = date_in;
		this.date_out = date_out;
		this.cost=cost;
	}

	public Integer getId() {
		return id;
	}

	public Integer getSpace_id() {
		return space_id;
	}

	public void setSpace_id(Integer space_id) {
		this.space_id = space_id;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
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
		if(date_in==null)
			return date_in;
		return date_in.split("[.]")[0];
	}

	public void setDate_in(String date_in) {
		this.date_in = date_in;
	}

	public String getDate_out() {
		if(date_out==null)
			return date_out;
		return date_out.split("[.]")[0];
	}

	public void setDate_out(String date_out) {
		this.date_out = date_out;
	}

}
