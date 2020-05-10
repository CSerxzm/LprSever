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
import com.xzm.lpr.domain.TraRecord;
import com.xzm.lpr.domain.User;
import com.xzm.lpr.service.LprService;
import com.xzm.lpr.util.common.LprConstants;
import com.xzm.lpr.util.common.LprFunc;
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
	 public String getTraRecord(HttpSession session,Integer page,Integer limit,String keyword) throws UnsupportedEncodingException{

		User user=(User) session.getAttribute(LprConstants.USER_SESSION);
		
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
		
		List<TraRecord> trarecords = lprService.findTraRecord(user,pageModel,keyword);
		
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
	
	//车辆通行，添加通行记录
	@RequestMapping(value="/trarecord/drive")
	@ResponseBody
	public String driveIn(@RequestParam Map<String,String> map){
		
		String licenseplate=map.get("licenseplate");
		String date_in=map.get("date_in");
		String date_out=map.get("date_out");
		
		ParkSpace parkSpace;
		
		JSONObject jsonmain = new JSONObject();
		
		//驶入还是驶出
		//返回驶出时间为空的记录
		TraRecord traRecord = lprService.getTraRecordDate_out(licenseplate);
		if(traRecord==null) {
			//驶入
			//该车牌是否绑定用户
			Integer parkspace_id = lprService.getParkSpace_idByLicenseplate(licenseplate);
			if(parkspace_id == null) {
				//分配停车位
				parkSpace = lprService.getParkSpaceOne();
				parkspace_id = parkSpace.getId();
				parkSpace = new ParkSpace(parkspace_id,null,"是",null,null,null,null);
				Integer j=lprService.updateParkSpace(parkSpace);
			}
			
			TraRecord traRecord_in = new TraRecord(parkspace_id,licenseplate,date_in);
			Integer i = lprService.addTraRecord(traRecord_in);
			
			jsonmain.put("parkspace_id", parkspace_id);

			if( i!=null ) {
				jsonmain.put("msg", "分配成功");
			}else {
				jsonmain.put("msg", "分配失败");
			}
			
		}else {
			//驶出
			date_in = traRecord.getDate_in();
			int time = LprFunc.CalTime(date_in,date_out);
			Integer cost = lprService.getParkLotActivitycost_per()*time;
			traRecord.setDate_out(date_out);
			traRecord.setCost(cost);//99为临时数值
			Integer i = lprService.updateTraRecord(traRecord);
			parkSpace = new ParkSpace(traRecord.getSpace_id(),null,"否",null,null,null,null);
			Integer j=lprService.updateParkSpace(parkSpace);
			if( i!=null ) {
				jsonmain.put("parkspace_id", traRecord.getSpace_id());
				jsonmain.put("licenseplate", traRecord.getLicenseplate());
				jsonmain.put("date_in", traRecord.getDate_in());
				jsonmain.put("date_out", traRecord.getDate_out());
				jsonmain.put("time", time);
				jsonmain.put("cost", cost);
				jsonmain.put("msg", "更新成功");
			}else {
				jsonmain.put("msg", "更新失败");
			}
		}
		return jsonmain.toString();
	}
	
}
