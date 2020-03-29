package com.xzm.lpr.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzm.lpr.domain.ParkLot;
import com.xzm.lpr.service.LprService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class ParkLotController {
	
	@Autowired
	@Qualifier("lprService")
	private LprService lprService;
			
	@RequestMapping(value="/parklot/getParkLot")
	@ResponseBody
	 public String getParkLot(){
		
		List<ParkLot> parkLots = lprService.findParkLot();
		
		JSONObject jsonmain = new JSONObject();
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
			jsonobj.put("monthcost", parkLot.getMonthcost());
			jsonobj.put("quartercost", parkLot.getQuartercost());
			jsonobj.put("halfyearcost", parkLot.getHalfyearcost());
			jsonobj.put("yearcost", parkLot.getYearcost());
			jsonarray.add(jsonobj);
		}
		
		jsonmain.put("data", jsonarray);		
		return jsonmain.toString();
		
	}
	
	@RequestMapping(value="/parklot/updateParkLot")
	@ResponseBody
	public String updateParkLot(@RequestParam Map<String,String> map){
		
		Integer id=Integer.valueOf(map.get("id"));
		String name=map.get("name");
		String address=map.get("address");
		String telephone=map.get("telephone");
		Integer activity_spaces_num=Integer.valueOf(map.get("activitynum"));
		Integer fixation_spaces_num=Integer.valueOf(map.get("fixationnum"));
		Integer activitycost_per=Integer.valueOf(map.get("activitycost_per"));
		Integer monthcost=Integer.valueOf(map.get("monthcost"));
		Integer quartercost=Integer.valueOf(map.get("quartercost"));
		Integer halfyearcost=Integer.valueOf(map.get("halfyearcost"));
		Integer yearcost=Integer.valueOf(map.get("yearcost"));
		
		ParkLot parkLot = new ParkLot(id,name,address,telephone,activity_spaces_num,fixation_spaces_num
				,activitycost_per,monthcost,quartercost,halfyearcost,yearcost);
		
		Integer i=lprService.updateParkLot(parkLot);
		JSONObject jsonmain = new JSONObject();
		if(i != null){
			jsonmain.put("msg", "更新成功");
		}else{
			jsonmain.put("msg", "更新失败");
		}
		return jsonmain.toString();
	}
	
}
