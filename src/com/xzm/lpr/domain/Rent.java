package com.xzm.lpr.domain;

public class Rent {
	
	private Integer parkspace_id;
	private String rent_start_date;
	private String rent_stop_date;
	
	public Integer getParkspace_id() {
		return parkspace_id;
	}
	public void setParkspace_id(Integer parkspace_id) {
		this.parkspace_id = parkspace_id;
	}
	public String getRent_start_date() {
		if(rent_start_date==null)
			return rent_start_date;
		return rent_start_date;
	}
	public void setRent_start_date(String rent_start_date) {
		this.rent_start_date = rent_start_date;
	}
	public String getRent_stop_date() {
		if(rent_stop_date==null)
			return rent_stop_date;
		return rent_stop_date;
	}
	public void setRent_stop_date(String rent_stop_date) {
		this.rent_stop_date = rent_stop_date;
	}
	
}
