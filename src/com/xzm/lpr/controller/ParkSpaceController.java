package com.xzm.lpr.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzm.lpr.domain.ParkSpace;
import com.xzm.lpr.service.LprService;
import com.xzm.lpr.util.tag.PageModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
			jsonobj.put("idle", parkSpace.getIdle());
			jsonobj.put("hire_start_date", parkSpace.getHire_start_date());
			jsonobj.put("hire_stop_date", parkSpace.getHire_stop_date());
			jsonobj.put("rentornot", parkSpace.getRentornot());
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
		String idle=map.get("idle");
		String hire_start_date=map.get("hire_start_date");
		String hire_stop_date=map.get("hire_stop_date");
		String rentornot=map.get("rentornot");
		
		ParkSpace parkSpace = new ParkSpace(id,name,idle,hire_start_date,hire_stop_date,rentornot);
		
		int i=lprService.updateParkSpace(parkSpace);
		JSONObject jsonmain = new JSONObject();
		if(i != 0){
			jsonmain.put("msg", "OK");
		}else{
			jsonmain.put("msg", "ERROR");
		}
		return jsonmain.toString();
	}
	
}
