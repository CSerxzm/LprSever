package com.xzm.lpr.domain;

import java.io.Serializable;

public class ParkLot implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String address;
	private String telephone;
	private Integer activitynum;
	private Integer fixationnum;
	private Integer activitynum_leave;
	private Integer fixationnum_leave;
	private Integer activitycost_per;
	private Integer monthcost;
	private Integer quartercost;
	private Integer halfyearcost;
	private Integer yearcost;

	public ParkLot() {
		super();
	}
	
	public ParkLot(Integer id,String name,String address,String telephone,Integer activitynum,Integer fixationnum
			,Integer activitycost_per,Integer monthcost,Integer quartercost,Integer halfyearcost,Integer yearcost) {
		this.id=id;
		this.name=name;
		this.address=address;
		this.telephone=telephone;
		this.activitynum=activitynum;
		this.fixationnum=fixationnum;
		this.activitycost_per=activitycost_per;
		this.monthcost=monthcost;
		this.quartercost=quartercost;
		this.halfyearcost=halfyearcost;
		this.yearcost=yearcost;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getActivitynum() {
		return activitynum;
	}

	public void setActivitynum(Integer activitynum) {
		this.activitynum = activitynum;
	}

	public Integer getFixationnum() {
		return fixationnum;
	}

	public void setFixationnum(Integer fixationnum) {
		this.fixationnum = fixationnum;
	}

	public Integer getActivitynum_leave() {
		return activitynum_leave;
	}

	public void setActivitynum_leave(Integer activitynum_leave) {
		this.activitynum_leave = activitynum_leave;
	}

	public Integer getFixationnum_leave() {
		return fixationnum_leave;
	}

	public void setFixationnum_leave(Integer fixationnum_leave) {
		this.fixationnum_leave = fixationnum_leave;
	}
	
	public Integer getActivitycost_per() {
		return activitycost_per;
	}

	public void setActivitycost_per(Integer activitycost_per) {
		this.activitycost_per = activitycost_per;
	}

	public Integer getMonthcost() {
		return monthcost;
	}

	public void setMonthcost(Integer monthcost) {
		this.monthcost = monthcost;
	}

	public Integer getQuartercost() {
		return quartercost;
	}

	public void setQuartercost(Integer quartercost) {
		this.quartercost = quartercost;
	}

	public Integer getHalfyearcost() {
		return halfyearcost;
	}

	public void setHalfyearcost(Integer halfyearcost) {
		this.halfyearcost = halfyearcost;
	}

	public Integer getYearcost() {
		return yearcost;
	}

	public void setYearcost(Integer yearcost) {
		this.yearcost = yearcost;
	}
	
}
