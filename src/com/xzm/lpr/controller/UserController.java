package com.xzm.lpr.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xzm.lpr.domain.User;
import com.xzm.lpr.service.LprService;
import com.xzm.lpr.util.common.LprConstants;
import com.xzm.lpr.util.tag.PageModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 处理用户请求控制器
 * */
@Controller
public class UserController {
	
	@Autowired
	@Qualifier("lprService")
	private LprService lprService;
		
	@RequestMapping(value="/login")
	@ResponseBody
	public String login(HttpSession session,@RequestParam Map<String,String> map){
		String loginname=map.get("loginname");
		String password=map.get("password");
		User user = lprService.login(loginname, password);
		JSONObject jsonmain = new JSONObject();
		if(user != null){
			session.setAttribute(LprConstants.USER_SESSION,user);
			jsonmain.put("msg", "OK");
		}else{
			jsonmain.put("msg", "ERROR");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/user/getUser",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	 public String getUser(Integer page,Integer limit){
		
		System.out.println(page+"/"+limit);
		PageModel pageModel = new PageModel();
		if(page != null){
			pageModel.setPageIndex(page);
		}
		if(page != null){
			pageModel.setPageSize(limit);
		}
		/** 查询用户信息     */
		List<User> users = lprService.findUser(pageModel);
		
		JSONObject jsonmain = new JSONObject();
		jsonmain.put("code", "200");
		jsonmain.put("msg", "none");
		jsonmain.put("count",pageModel.getRecordCount());
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		for (int i = 0; i < users.size(); i++) {
			User user = (User)users.get(i);
			jsonobj.put("loginname", user.getLoginname());
			jsonobj.put("password", user.getPassword());
			jsonobj.put("username", user.getUsername());
			jsonobj.put("telephone", user.getTelephone());
			jsonobj.put("createdate", user.getCreateDate());
			jsonobj.put("authority", user.getAuthority());
			System.out.println(user.getCreateDate());
			jsonarray.add(jsonobj);
		}
		
		jsonmain.put("data", jsonarray);		
		return jsonmain.toString();
		
	}
	
	@RequestMapping(value="/user/removeUser")
	@ResponseBody
	public String removeUser(String loginname){
		
		System.out.println(loginname);
		
		lprService.removeUserByLogin(loginname);
		JSONObject jsonmain = new JSONObject();
		jsonmain.put("code", "200");
		jsonmain.put("msg", "none");
		
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/user/registerUser",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String registerUser(@RequestParam Map<String,String> map){
		String loginname=map.get("loginname");
		String password=map.get("password");
		String username=map.get("username");
		String telephone=map.get("telephone");
		User user = new User(loginname,password,username,telephone);
		int i=lprService.addUser(user);
		JSONObject jsonmain = new JSONObject();
		if(i != 0){
			jsonmain.put("msg", "OK");
		}else{
			jsonmain.put("msg", "ERROR");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/user/updateUser",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String updateUser(@RequestParam Map<String,String> map){
		
		User user=null;	
		String loginname=map.get("loginname");
		String password=map.get("password");
		String username=map.get("username");
		String telephone=map.get("telephone");
		String createdate=map.get("createdate");
		String authority=map.get("authority");
		
		user = new User(loginname,password,username,telephone,createdate,authority);
		
		int i=lprService.modifyUser(user);
		JSONObject jsonmain = new JSONObject();
		if(i != 0){
			jsonmain.put("msg", "OK");
		}else{
			jsonmain.put("msg", "ERROR");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/logout")
	 public ModelAndView logout(
			 ModelAndView mv,
			 HttpSession session) {
		// 注销session
		session.invalidate();
		// 跳转到登录页面
		mv.setViewName("redirect:/loginform");
		return mv;
	}
	
}
