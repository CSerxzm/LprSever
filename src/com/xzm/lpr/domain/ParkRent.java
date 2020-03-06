package com.xzm.lpr.domain;

public class ParkRent {
	
	private Integer id;
	private Integer space_id;
	private String licenseplate;
	private Integer cost;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSpace_id() {
		return space_id;
	}
	public void setSpace_id(Integer space_id) {
		this.space_id = space_id;
	}
	public String getLicenseplate() {
		return licenseplate;
	}
	public void setLicenseplate(String licenseplate) {
		this.licenseplate = licenseplate;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	
	

}
