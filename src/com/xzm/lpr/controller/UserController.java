package com.xzm.lpr.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
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
			if(user.getAuthority().equals("用户"))
				jsonmain.put("msg", "OK_user");
			else
				jsonmain.put("msg", "OK_admin");
		}else{
			jsonmain.put("msg", "登陆失败");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/user/getUser",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	 public String getUser(Integer page,Integer limit){
		
		PageModel pageModel = new PageModel();
		if(page != null){
			pageModel.setPageIndex(page);
		}
		if(page != null){
			pageModel.setPageSize(limit);
		}
		
		List<User> users = lprService.findUser(null,pageModel);
		
		JSONObject jsonmain = new JSONObject();
		jsonmain.put("count",pageModel.getRecordCount());
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		for (int i = 0; i < users.size(); i++) {
			User user = (User)users.get(i);
			jsonobj.put("loginname", user.getLoginname());
			jsonobj.put("password", user.getPassword());
			jsonobj.put("parkspace_id", user.getParkspace_id());
			jsonobj.put("licenseplate", user.getLicenseplate());
			jsonobj.put("telephone", user.getTelephone());
			jsonobj.put("createdate", user.getCreateDate());
			jsonobj.put("authority", user.getAuthority());
			jsonobj.put("wallet", user.getWallet());
			jsonarray.add(jsonobj);
		}
		
		jsonmain.put("data", jsonarray);
		jsonmain.put("code", 200);
		return jsonmain.toString();
		
	}
	
	@RequestMapping(value="/user/getUserOne",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String getUserOne(HttpSession session){
		
		User user = (User) session.getAttribute(LprConstants.USER_SESSION);
		
		List<User> users = lprService.findUser(user,null);
		
		JSONObject jsonmain = new JSONObject();
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		for (int i = 0; i < users.size(); i++) {
			User user_temp = (User)users.get(i);
			jsonobj.put("loginname", user_temp.getLoginname());
			jsonobj.put("password", user_temp.getPassword());
			jsonobj.put("parkspace_id", user_temp.getParkspace_id());
			jsonobj.put("licenseplate", user_temp.getLicenseplate());
			jsonobj.put("telephone", user_temp.getTelephone());
			jsonobj.put("createdate", user_temp.getCreateDate());
			jsonobj.put("authority", user_temp.getAuthority());
			jsonobj.put("wallet", user_temp.getWallet());
			jsonarray.add(jsonobj);
		}
		
		jsonmain.put("data", jsonarray);		
		return jsonmain.toString();
		
	}
	
	@RequestMapping(value="/user/removeUser")
	@ResponseBody
	public String removeUser(String loginname){
		
		Integer i = lprService.removeUserByLogin(loginname);
		JSONObject jsonmain = new JSONObject();
		if( i!=null ) {
			jsonmain.put("msg", "删除成功");
		}else{
			jsonmain.put("msg", "删除失败");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/user/registerUser",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String registerUser(@RequestParam Map<String,String> map){
		String loginname=map.get("loginname");
		String password=map.get("password");
		String telephone=map.get("telephone");
		System.out.println("loginname"+loginname);
		
		User user = new User(loginname,password,telephone);
		
		Integer i=lprService.addUser(user);
		JSONObject jsonmain = new JSONObject();
		if(i != null){
			jsonmain.put("msg", "OK");
		}else{
			jsonmain.put("msg", "Dupe");//用户名重复
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/user/updateUser",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String updateUser(HttpSession session,@RequestParam Map<String,String> map){
		
		Integer parkspace_id=null;
		String loginname=map.get("loginname");
		String password=map.get("password");
		if(!map.get("parkspace_id").equals("")) {
			parkspace_id=Integer.valueOf(map.get("parkspace_id"));
		}
		String licenseplate=map.get("licenseplate");
		String telephone=map.get("telephone");
		String createdate=map.get("createdate");
		String authority=map.get("authority");
		
		User user = new User(loginname,password,parkspace_id,licenseplate,telephone,createdate,authority);		
		if(authority.equals("用户")) {
			session.setAttribute(LprConstants.USER_SESSION,user);
		}
		
		Integer i=lprService.updateUser(user);
		JSONObject jsonmain = new JSONObject();
		if(i != null){
			jsonmain.put("msg", "更新成功");
		}else{
			jsonmain.put("msg", "更新失败");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/user/addUser",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String addUser(@RequestParam Map<String,String> map){
		
		Integer parkspace_id=null;
		String loginname=map.get("loginname");
		String password=map.get("password");
		if(!map.get("parkspace_id").equals("")) {
			parkspace_id=Integer.valueOf(map.get("parkspace_id"));
		}
		String licenseplate=map.get("licenseplate");
		String telephone=map.get("telephone");
		String createdate=map.get("createdate");
		String authority=map.get("authority");
		
		User user = new User(loginname,password,parkspace_id,licenseplate,telephone,createdate,authority);
		
		Integer i=lprService.addUser(user);
		JSONObject jsonmain = new JSONObject();
		if(i != null){
			jsonmain.put("msg", "添加成功");
		}else{
			jsonmain.put("msg", "添加失败");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/logout")
	 public ModelAndView logout(
			 ModelAndView mv,
			 HttpSession session) {
		
		session.invalidate();
		mv.setViewName("redirect:/index");
		return mv;
	}
	
}
