package com.xzm.lpr.controller;

import javax.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 动态页面跳转控制器
 * */
@Controller
public class FormController{

	@RequestMapping(value="/{formName}")
	 public String loginForm(@PathVariable String formName,HttpServletRequest request){
		return formName;
	}

}

