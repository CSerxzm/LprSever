package com.xzm.lpr.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzm.lpr.domain.TraRecord;
import com.xzm.lpr.service.LprService;
import com.xzm.lpr.util.tag.PageModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class TraRecordController {
	
	@Autowired
	@Qualifier("lprService")
	private LprService lprService;
	
	@RequestMapping(value="/trarecord/getTraRecord",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	 public String getTraRecord(Integer page,Integer limit){
		
		System.out.println(page+"/"+limit);
		PageModel pageModel = new PageModel();
		if(page != null){
			pageModel.setPageIndex(page);
		}
		if(page != null){
			pageModel.setPageSize(limit);
		}

		List<TraRecord> trarecords = lprService.findTraRecord(pageModel);
		
		JSONObject jsonmain = new JSONObject();
		jsonmain.put("code", "200");
		jsonmain.put("msg", "none");
		jsonmain.put("count",pageModel.getRecordCount());
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		for (int i = 0; i < trarecords.size(); i++) {
			TraRecord trarecord = (TraRecord)trarecords.get(i);
			jsonobj.put("id", trarecord.getId());
			jsonobj.put("space_id", trarecord.getSpace_id());
			jsonobj.put("licenseplate", trarecord.getLicenseplate());
			System.out.println(trarecord.getDate_in());
			jsonobj.put("date_in", trarecord.getDate_in());
			jsonobj.put("date_out", trarecord.getDate_out());
			jsonobj.put("cost", trarecord.getCost());
			jsonarray.add(jsonobj);
		}
		
		jsonmain.put("data", jsonarray);		
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/trarecord/removeTraRecord",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String removeTraRecord(int id){
		
		System.out.println("id="+id);
		
		Integer i = lprService.removeTraRecordById(id);
		System.out.println("i="+i);
		JSONObject jsonmain = new JSONObject();
		if(i!=0) {
			jsonmain.put("code", "200");
			jsonmain.put("msg", "none");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/trarecord/updateTraRecord",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String updateTraRecord(@RequestParam Map<String,String> map){
		
		Integer id=Integer.valueOf(map.get("id"));
		Integer space_id =Integer.valueOf(map.get("space_id"));
		String licenseplate=map.get("licenseplate");
		String date_in=map.get("date_in");
		String date_out=map.get("date_out");
		Integer cost=Integer.valueOf(map.get("cost"));
		
		TraRecord traRecord = new TraRecord(id,space_id,licenseplate,date_in,date_out,cost);
		
		Integer i = lprService.updateTraRecord(traRecord);
		System.out.println("i="+i);
		JSONObject jsonmain = new JSONObject();
		if(i!=0) {
			jsonmain.put("code", "200");
			jsonmain.put("msg", "none");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/trarecord/addTraRecord",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String addTraRecord(@RequestParam Map<String,String> map){
		
		Integer space_id =Integer.valueOf(map.get("space_id"));
		String licenseplate=map.get("licenseplate");
		String date_in=map.get("date_in");
		String date_out=map.get("date_out");
		String cost_str = map.get("cost");
		Integer cost=null;
		if(!cost_str.equals("")){
			cost=Integer.valueOf(cost_str);			
		}

		TraRecord traRecord = new TraRecord(space_id,licenseplate,date_in,date_out,cost);
		
		Integer i = lprService.addTraRecord(traRecord);
		System.out.println("i="+i);
		JSONObject jsonmain = new JSONObject();
		if(i!=0) {
			jsonmain.put("code", "200");
			jsonmain.put("msg", "none");
		}
		return jsonmain.toString();
	}
	
}
