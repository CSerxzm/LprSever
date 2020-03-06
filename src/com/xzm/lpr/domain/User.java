package com.xzm.lpr.domain;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Description: 
 * <br>网站：<a href="http://www.fkit.org">疯狂Java</a> 
 * @author 肖文吉	36750064@qq.com   
 * @version V1.0   
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String loginname;	// 登录名
	private String password;	// 密码
	private String username;
	private String telephone;
	private String createDate;	// 建档日期
	
	private String authority;	

	

	// 无参数构造器
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(String loginname,String password,String username,String telephone) {
		this.loginname=loginname;
		this.password=password;
		this.username=username;
		this.telephone=telephone;
	}
	
	public User(String loginname,String password,String username,String telephone,String createDate,String authority) {
		this.loginname=loginname;
		this.password=password;
		this.username=username;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
