package com.xzm.lpr.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzm.lpr.domain.ParkSpace;
import com.xzm.lpr.domain.User;
import com.xzm.lpr.service.LprService;
import com.xzm.lpr.util.common.LprConstants;
import com.xzm.lpr.util.tag.PageModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class ParkSpaceController {
	
	@Autowired
	@Qualifier("lprService")
	private LprService lprService;
			
	@RequestMapping(value="/parkspace/getParkSpace")
	@ResponseBody
	 public String getParkSpace(HttpSession session,String operate,Integer page,Integer limit,String keyword) throws UnsupportedEncodingException{
		
		PageModel pageModel = new PageModel();
		if(page != null){
			pageModel.setPageIndex(page);
		}
		if(page != null){
			pageModel.setPageSize(limit);
		}
		if(keyword!=null) {
			keyword = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");	
		}
		System.out.println("keyword="+keyword);
		User user=(User) session.getAttribute(LprConstants.USER_SESSION);
		
		List<ParkSpace> parkSpaces = lprService.findParkSpace(user,operate,pageModel,keyword);
		
		JSONObject jsonmain = new JSONObject();
		jsonmain.put("count",pageModel.getRecordCount());
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		for (int i = 0; i < parkSpaces.size(); i++) {
			ParkSpace parkSpace = (ParkSpace)parkSpaces.get(i);
			jsonobj.put("id", parkSpace.getId());
			jsonobj.put("name", parkSpace.getName());
			jsonobj.put("state", parkSpace.getState());
			jsonobj.put("idle", parkSpace.getIdle());
			jsonobj.put("hire_start_date", parkSpace.getHire_start_date());
			jsonobj.put("hire_stop_date", parkSpace.getHire_stop_date());
			jsonobj.put("rentornot", parkSpace.getRentornot());
			jsonarray.add(jsonobj);
		}
		
		jsonmain.put("data", jsonarray);
		jsonmain.put("code", 200);
		return jsonmain.toString();
		
	}
	
	@RequestMapping(value="/parkspace/removeParkSpace")
	@ResponseBody
	public String removeParkSpace(int id){
		
		Integer i=lprService.removeParkSpaceById(id);

		JSONObject jsonmain = new JSONObject();
		if( i!=null ) {
			jsonmain.put("msg", "删除成功");			
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/parkspace/updateParkSpace")
	@ResponseBody
	public String updateParkSpace(@RequestParam Map<String,String> map){
		
		Integer id=Integer.valueOf(map.get("id"));
		String name=map.get("name");
		String state=map.get("state");
		String idle=map.get("idle");
		String hire_start_date=map.get("hire_start_date");
		String hire_stop_date=map.get("hire_stop_date");
		String rentornot=map.get("rentornot");
		
		ParkSpace parkSpace = new ParkSpace(id,name,state,idle,hire_start_date,hire_stop_date,rentornot);
		
		Integer i=lprService.updateParkSpace(parkSpace);
		JSONObject jsonmain = new JSONObject();
		if(i != null){
			jsonmain.put("msg", "更新成功");
		}else{
			jsonmain.put("msg", "更新失败");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/parkspace/addParkSpace")
	@ResponseBody
	public String addParkSpace(@RequestParam Map<String,String> map){
		
		String name=map.get("name");
		String idle=map.get("idle");
		String state=map.get("state");
		String hire_start_date=map.get("hire_start_date");
		String hire_stop_date=map.get("hire_stop_date");
		String rentornot=map.get("rentornot");
		
		ParkSpace parkSpace = new ParkSpace(name,state,idle,hire_start_date,hire_stop_date,rentornot);
		
		Integer i=lprService.addParkSpace(parkSpace);
		JSONObject jsonmain = new JSONObject();
		if(i != null){
			jsonmain.put("msg", "添加成功");
		}else{
			jsonmain.put("msg", "添加失败");
		}
		return jsonmain.toString();
	}
	
	/*
	 用户预约
	 */
	@RequestMapping(value="/parkspace/orderParkSpace")
	@ResponseBody
	public String orderParkSpace(@RequestParam Map<String,String> map){
		
		Integer id=Integer.valueOf(map.get("id"));
		
		ParkSpace parkSpace = new ParkSpace(id,null,"是",null,null,null,null);
		
		Integer i=lprService.updateParkSpace(parkSpace);
		JSONObject jsonmain = new JSONObject();
		if(i != null){
			jsonmain.put("msg", "预约成功");
		}else{
			jsonmain.put("msg", "预约失败");
		}
		return jsonmain.toString();
	}
	
	/*
	 用户租赁
	 */
	@RequestMapping(value="/parkspace/rentParkSpace")
	@ResponseBody
	public String rentParkLot(HttpSession session,@RequestParam Map<String,String> map){
		
		User user=(User) session.getAttribute(LprConstants.USER_SESSION);
		
		Integer id=Integer.valueOf(map.get("id"));
		String name = (String) map.get("name");
		String hire_start_date = (String) map.get("hire_start_date");
		String hire_stop_date = (String) map.get("hire_stop_date");
		Integer paynumber=Integer.valueOf(map.get("paynumber"));
		
		ParkSpace parkSpace = new ParkSpace(id,name,"否",hire_start_date,hire_stop_date);
		
		user.setParkspace_id(id);
		user.setWallet(paynumber);
		
		Integer i=lprService.rentParkSpace(parkSpace,user);
		JSONObject jsonmain = new JSONObject();
		if(i != null && i==2 ){
			jsonmain.put("msg", "你已经租赁停车位");
		}else if(i != null && i==1 ) {
			jsonmain.put("msg", "租赁成功");
		}else{
			jsonmain.put("msg", "租赁失败");
		}
		return jsonmain.toString();
	}
}
