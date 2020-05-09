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

import com.xzm.lpr.domain.ParkSpace;
import com.xzm.lpr.domain.TraRecord;
import com.xzm.lpr.domain.User;
import com.xzm.lpr.service.LprService;
import com.xzm.lpr.util.common.LprConstants;
import com.xzm.lpr.util.tag.PageModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class TraRecordController {
	
	@Autowired
	@Qualifier("lprService")
	private LprService lprService;
	
	@RequestMapping(value="/trarecord/getTraRecord")
	@ResponseBody
	 public String getTraRecord(HttpSession session,Integer page,Integer limit){

		User user=(User) session.getAttribute(LprConstants.USER_SESSION);
		
		PageModel pageModel = new PageModel();
		if(page != null){
			pageModel.setPageIndex(page);
		}
		if(page != null){
			pageModel.setPageSize(limit);
		}

		List<TraRecord> trarecords = lprService.findTraRecord(user,pageModel);
		
		JSONObject jsonmain = new JSONObject();
		jsonmain.put("count",pageModel.getRecordCount());
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		for (int i = 0; i < trarecords.size(); i++) {
			TraRecord trarecord = (TraRecord)trarecords.get(i);
			jsonobj.put("id", trarecord.getId());
			jsonobj.put("space_id", trarecord.getSpace_id());
			jsonobj.put("licenseplate", trarecord.getLicenseplate());
			jsonobj.put("date_in", trarecord.getDate_in());
			jsonobj.put("date_out", trarecord.getDate_out());
			jsonobj.put("cost", trarecord.getCost());
			jsonarray.add(jsonobj);
		}
		
		jsonmain.put("data", jsonarray);
		jsonmain.put("code", 200);
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/trarecord/removeTraRecord")
	@ResponseBody
	public String removeTraRecord(int id){
				
		Integer i = lprService.removeTraRecordById(id);
		JSONObject jsonmain = new JSONObject();
		if( i!=null ) {
			jsonmain.put("msg", "删除成功");
		}else {
			jsonmain.put("msg", "删除失败");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/trarecord/updateTraRecord")
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
		JSONObject jsonmain = new JSONObject();
		if(i!=null) {
			jsonmain.put("msg", "更新成功");
		}else {
			jsonmain.put("msg", "更新失败");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/trarecord/addTraRecord")
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
		
		JSONObject jsonmain = new JSONObject();
		if( i!=null ) {
			jsonmain.put("msg", "添加成功");
		}else {
			jsonmain.put("msg", "添加失败");
		}
		return jsonmain.toString();
	}
	
	//车辆驶入，添加通行记录
	@RequestMapping(value="/trarecord/drivein")
	@ResponseBody
	public String driveIn(@RequestParam Map<String,String> map){
		
		String licenseplate=map.get("licenseplate");
		String date_in=map.get("date_in");
		
		System.out.println("licenseplate="+licenseplate);
		
		JSONObject jsonmain = new JSONObject();
		
		//该车牌是否绑定用户
		Integer parkspace_id = lprService.getParkSpace_idByLicenseplate(licenseplate);
		
		if(parkspace_id == null) {
			//找停车位
			ParkSpace parkSpace = lprService.getParkSpaceOne();
			parkspace_id = parkSpace.getId();
		}
		
		jsonmain.put("parkspace_id", parkspace_id);
		TraRecord traRecord = new TraRecord(parkspace_id,licenseplate,date_in);
		Integer i = lprService.addTraRecord(traRecord);
		
		if( i!=null ) {
			jsonmain.put("msg", "分配成功");
		}else {
			jsonmain.put("msg", "分配失败");
		}
		return jsonmain.toString();
	}
	
	/*
	 * 车辆驶出，修改通行记录
	 */
	@RequestMapping(value="/trarecord/driveout")
	@ResponseBody
	public String driveOut(@RequestParam Map<String,String> map){
		
		String licenseplate=map.get("licenseplate");
		String date_out=map.get("date_out");
		
		//查找记录
		TraRecord traRecord = lprService.getTraRecordDate_out(licenseplate);
		
		Integer cost = 99;
		traRecord.setDate_out(date_out);
		traRecord.setCost(cost);//99为临时数值
		
		Integer i = lprService.updateTraRecord(traRecord);
		
		JSONObject jsonmain = new JSONObject();
		
		if( i!=null ) {
			jsonmain.put("space_id", traRecord.getSpace_id());
			jsonmain.put("licenseplate", traRecord.getLicenseplate());
			jsonmain.put("date_in", traRecord.getDate_in());
			jsonmain.put("msg", "更新成功");
			jsonmain.put("cost", cost);
		}else {
			jsonmain.put("msg", "更新失败");
		}
		return jsonmain.toString();
	}
	
}
