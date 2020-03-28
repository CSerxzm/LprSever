package com.xzm.lpr.domain;

import java.io.Serializable;

public class WalletRecord  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String date_pay;
	private String name;
	private String operation;
	private Integer cost;
	
	public WalletRecord(){
		super();
	}
	
	public WalletRecord(String name,String operation,Integer cost){
		this.name=name;
		this.operation=operation;
		this.cost=cost;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDate_pay() {
		if(date_pay==null)
			return date_pay;
		return this.date_pay.split("[.]")[0];
	}
	public void setDate_pay(String date_pay) {
		this.date_pay = date_pay;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}

}
