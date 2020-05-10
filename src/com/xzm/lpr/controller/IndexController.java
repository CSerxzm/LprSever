package com.xzm.lpr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzm.lpr.domain.Notice;
import com.xzm.lpr.domain.ParkLot;
import com.xzm.lpr.service.LprService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class IndexController {
	
	@Autowired
	@Qualifier("lprService")
	private LprService lprService;
		
	@RequestMapping(value="/index/getindex_info")
	@ResponseBody
	public String getIndex_info(){
		
		List<ParkLot> parkLots = lprService.findParkLot();
		
		JSONObject jsonmain = new JSONObject();
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		for (int i = 0; i < parkLots.size(); i++) {
			ParkLot parkLot = (ParkLot)parkLots.get(i);
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
	
	@RequestMapping(value="/index/getindex_notice")
	@ResponseBody
	public String getIndex_notice(){

		List<Notice> notices = lprService.findNotice(null,null);
		
		JSONObject jsonmain = new JSONObject();
		JSONObject jsonobj_notice = new JSONObject();
		JSONArray jsonarray_notice = new JSONArray();
		for (int i = 0; i < notices.size(); i++) {
			Notice notice = (Notice)notices.get(i);
			jsonobj_notice.put("id", notice.getId());
			jsonobj_notice.put("title", notice.getTitle());
			jsonobj_notice.put("content", notice.getContent());
			jsonobj_notice.put("create_date", notice.getCreate_date());
			jsonarray_notice.add(jsonobj_notice);
		}
		jsonmain.put("data", jsonarray_notice);
		return jsonmain.toString();
	}

}
