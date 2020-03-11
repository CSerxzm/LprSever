package com.xzm.lpr.domain;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String loginname;
	private String password;
	private Integer parkspace_id;
	private String licenseplate;
	private String telephone;
	private String createDate;
	
	private String authority;	

	public User() {
		super();
	}
	
	public User(String loginname,String password,String telephone) {
		this.loginname=loginname;
		this.password=password;
		this.telephone=telephone;
	}
	
	public User(String loginname,String password,Integer parkspace_id,String licenseplate,String telephone,String createDate,String authority) {
		this.loginname=loginname;
		this.password=password;
		this.parkspace_id=parkspace_id;
		this.licenseplate=licenseplate;
		this.telephone=telephone;
		this.createDate=createDate;
		this.authority=authority;
	}

	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCreateDate() {
		if(createDate==null)
			return createDate;
		return createDate.split("[.]")[0];
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Integer getParkspace_id() {
		return parkspace_id;
	}

	public void setParkspace_id(Integer parkspace_id) {
		this.parkspace_id = parkspace_id;
	}

	public String getLicenseplate() {
		return licenseplate;
	}

	public void setLicenseplate(String licenseplate) {
		this.licenseplate = licenseplate;
	}
}
