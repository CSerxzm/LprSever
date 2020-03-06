package com.xzm.lpr.domain;

import java.io.Serializable;
import java.util.Date;

public class ParkSpace implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String ownner;
	private String hire_start_date;
	private String hire_stop_date;
	private String rentornot;
	private String rent_start_date;
	private String rent_stop_date;	
	
	public ParkSpace() {
		super();
	}
	
	public ParkSpace(Integer id,String name,String ownner,String hire_start_date,String hire_stop_date,String rentornot,String rent_start_date,String rent_stop_date) {
		this.id=id;
		this.name=name;
		this.ownner=ownner;
		this.hire_start_date=hire_start_date;
		this.hire_stop_date=hire_stop_date;
		this.rentornot=rentornot;
		this.rent_start_date=rent_start_date;
		this.rent_stop_date=rent_stop_date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnner() {
		return ownner;
	}

	public void setOwnner(String ownner) {
		this.ownner = ownner;
	}

	public String getHire_start_date() {
		if(hire_start_date==null)
			return hire_start_date;
		return hire_start_date.split("[.]")[0];
	}

	public void setHire_start_date(String hire_start_date) {
		this.hire_start_date = hire_start_date;
	}

	public String getHire_stop_date() {
		if(hire_stop_date==null)
			return hire_stop_date;
		return hire_stop_date.split("[.]")[0];
	}

	public void setHire_stop_date(String hire_stop_date) {
		this.hire_stop_date = hire_stop_date;
	}

	public String getRentornot() {
		return rentornot;
	}

	public void setRentornot(String rentornot) {
		this.rentornot = rentornot;
	}

	public String getRent_start_date() {
		if(rent_start_date==null)
			return rent_start_date;
		return rent_start_date.split("[.]")[0];
	}

	public void setRent_start_date(String rent_start_date) {
		this.rent_start_date = rent_start_date;
	}

	public String getRent_stop_date() {
		if(rent_stop_date==null)
			return rent_stop_date;
		return rent_stop_date.split("[.]")[0];
	}

	public void setRent_stop_date(String rent_stop_date) {
		this.rent_stop_date = rent_stop_date;
	}
	
}
