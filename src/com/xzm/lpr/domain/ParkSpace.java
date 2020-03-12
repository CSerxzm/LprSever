package com.xzm.lpr.domain;

import java.io.Serializable;

public class ParkSpace implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String idle;
	private String hire_start_date;
	private String hire_stop_date;
	private String rentornot;	
	
	public ParkSpace() {
		super();
	}
	
	public ParkSpace(Integer id,String name,String idle,String hire_start_date,String hire_stop_date,String rentornot) {
		this.id=id;
		this.name=name;
		this.idle=idle;
		this.hire_start_date=hire_start_date;
		this.hire_stop_date=hire_stop_date;
		this.rentornot=rentornot;
	}
	
	public ParkSpace(String name,String idle,String hire_start_date,String hire_stop_date,String rentornot) {
		this.name=name;
		this.idle=idle;
		this.hire_start_date=hire_start_date;
		this.hire_stop_date=hire_stop_date;
		this.rentornot=rentornot;
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

	public String getIdle() {
		return idle;
	}

	public void setIdle(String idle) {
		this.idle = idle;
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
	
}
