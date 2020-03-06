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

import com.xzm.lpr.domain.TraRecord;
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
		/** 查询用户信息     */
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
			jsonobj.put("licenseplate", trarecord.getLicenseplate());
			System.out.println(trarecord.getDate_in());
			jsonobj.put("date_in", trarecord.getDate_in());
			jsonobj.put("date_out", trarecord.getDate_out());
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
		String licenseplate=map.get("licenseplate");
		String date_in=map.get("date_in");
		String date_out=map.get("date_out");
		
		TraRecord traRecord = new TraRecord(id,licenseplate,date_in,date_out);
		
		Integer i = lprService.modifyTraRecord(traRecord);
		System.out.println("i="+i);
		JSONObject jsonmain = new JSONObject();
		if(i!=0) {
			jsonmain.put("code", "200");
			jsonmain.put("msg", "none");
		}
		return jsonmain.toString();
	}
}
