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

import com.xzm.lpr.domain.ParkLot;
import com.xzm.lpr.domain.ParkSpace;
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
public class ParkSpaceController {
	
	@Autowired
	@Qualifier("lprService")
	private LprService lprService;
			
	@RequestMapping(value="/parkspace/getParkSpace",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	 public String getParkLot(Integer page,Integer limit){
		
		System.out.println(page+"/"+limit);
		PageModel pageModel = new PageModel();
		if(page != null){
			pageModel.setPageIndex(page);
		}
		if(page != null){
			pageModel.setPageSize(limit);
		}

		List<ParkSpace> parkSpaces = lprService.findParkSpace(pageModel);
		
		JSONObject jsonmain = new JSONObject();
		jsonmain.put("code", "200");
		jsonmain.put("msg", "none");
		jsonmain.put("count",pageModel.getRecordCount());
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		for (int i = 0; i < parkSpaces.size(); i++) {
			ParkSpace parkSpace = (ParkSpace)parkSpaces.get(i);
			jsonobj.put("id", parkSpace.getId());
			jsonobj.put("name", parkSpace.getName());
			jsonobj.put("ownner", parkSpace.getOwnner());
			jsonobj.put("hire_start_date", parkSpace.getHire_start_date());
			jsonobj.put("hire_stop_date", parkSpace.getHire_stop_date());
			jsonobj.put("rentornot", parkSpace.getRentornot());
			jsonobj.put("rent_start_date", parkSpace.getRent_start_date());
			jsonobj.put("rent_stop_date", parkSpace.getRent_stop_date());
			jsonarray.add(jsonobj);
		}
		
		jsonmain.put("data", jsonarray);		
		return jsonmain.toString();
		
	}
	
	@RequestMapping(value="/parkspace/removeParkSpace")
	@ResponseBody
	public String removeParkSpace(int id){
		
		System.out.println("id="+id);
		
		Integer i=lprService.removeParkSpaceById(id);

		JSONObject jsonmain = new JSONObject();
		if(i!=null) {
			jsonmain.put("code", "200");
			jsonmain.put("msg", "none");			
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/parkspace/updateParkSpace",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String updateParkLot(@RequestParam Map<String,String> map){
		
		Integer id=Integer.valueOf(map.get("id"));
		String name=map.get("name");
		String address=map.get("address");
		String ownner=map.get("ownner");
		String hire_start_date=map.get("hire_start_date");
		String hire_stop_date=map.get("hire_stop_date");
		String rentornot=map.get("rentornot");
		String rent_start_date=map.get("rent_start_date");
		String rent_stop_date=map.get("rent_stop_date");
		
		ParkSpace parkSpace = new ParkSpace(id,name,ownner,hire_start_date,hire_stop_date,rentornot,rent_start_date,rent_stop_date);
		
		int i=lprService.modifyParkSpace(parkSpace);
		JSONObject jsonmain = new JSONObject();
		if(i != 0){
			jsonmain.put("msg", "OK");
		}else{
			jsonmain.put("msg", "ERROR");
		}
		return jsonmain.toString();
	}
	
}
