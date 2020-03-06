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
public class ParkLotController {
	
	@Autowired
	@Qualifier("lprService")
	private LprService lprService;
			
	@RequestMapping(value="/parklot/getParkLot",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	 public String getParkLot(){
		
		/** 查询用户信息     */
		List<ParkLot> parkLots = lprService.findParkLot();
		
		JSONObject jsonmain = new JSONObject();
		jsonmain.put("code", "200");
		jsonmain.put("msg", "none");
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		for (int i = 0; i < parkLots.size(); i++) {
			ParkLot parkLot = (ParkLot)parkLots.get(i);
			jsonobj.put("id", parkLot.getId());
			jsonobj.put("name", parkLot.getName());
			jsonobj.put("address", parkLot.getAddress());
			jsonobj.put("telephone", parkLot.getTelephone());
			jsonobj.put("activitynum", parkLot.getActivitynum());
			jsonobj.put("fixationnum", parkLot.getFixationnum());
			jsonobj.put("activitynum_leave", parkLot.getActivitynum_leave());
			jsonobj.put("fixationnum_leave", parkLot.getFixationnum_leave());
			jsonobj.put("activitycost_per", parkLot.getActivitycost_per());
			jsonarray.add(jsonobj);
		}
		
		jsonmain.put("data", jsonarray);		
		return jsonmain.toString();
		
	}
	
	@RequestMapping(value="/parklot/updateParkLot",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String updateParkLot(@RequestParam Map<String,String> map){
		
		Integer id=Integer.valueOf(map.get("id"));
		String name=map.get("name");
		String address=map.get("address");
		String telephone=map.get("telephone");
		Integer activity_spaces_num=Integer.valueOf(map.get("activitynum"));
		Integer fixation_spaces_num=Integer.valueOf(map.get("fixationnum"));
		
		ParkLot parkLot = new ParkLot(id,name,address,telephone,activity_spaces_num,fixation_spaces_num);
		
		int i=lprService.modifyParkLot(parkLot);
		JSONObject jsonmain = new JSONObject();
		if(i != 0){
			jsonmain.put("msg", "OK");
		}else{
			jsonmain.put("msg", "ERROR");
		}
		return jsonmain.toString();
	}
	
}
