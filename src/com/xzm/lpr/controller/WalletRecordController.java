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

import com.xzm.lpr.domain.TraRecord;
import com.xzm.lpr.domain.User;
import com.xzm.lpr.domain.WalletRecord;
import com.xzm.lpr.service.LprService;
import com.xzm.lpr.util.common.LprConstants;
import com.xzm.lpr.util.tag.PageModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class WalletRecordController {
	
	@Autowired
	@Qualifier("lprService")
	private LprService lprService;
	
	@RequestMapping(value="/walletrecord/getWalletRecord")
	@ResponseBody
	 public String getWalletRecord(HttpSession session,Integer page,Integer limit,String keyword) throws UnsupportedEncodingException{

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
		List<WalletRecord> walletrecords = lprService.findWalletRecord(user,pageModel,keyword);
		
		JSONObject jsonmain = new JSONObject();
		jsonmain.put("code", "200");
		jsonmain.put("msg", "none");
		jsonmain.put("count",pageModel.getRecordCount());
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		for (int i = 0; i < walletrecords.size(); i++) {
			WalletRecord walletrecord = (WalletRecord)walletrecords.get(i);
			jsonobj.put("id", walletrecord.getId());
			jsonobj.put("date_pay", walletrecord.getDate_pay());
			jsonobj.put("name", walletrecord.getName());
			jsonobj.put("operation", walletrecord.getOperation());
			jsonobj.put("cost", walletrecord.getCost());
			jsonarray.add(jsonobj);
		}
		
		jsonmain.put("data", jsonarray);
		jsonmain.put("code", 200);
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/walletrecord/charge")
	@ResponseBody
	 public String charge(@RequestParam Map<String,String> map){

		String loginname = (String) map.get("loginname");
		Integer money = Integer.valueOf( map.get("money") );
		
		System.out.println(loginname+"%"+money);
		
		WalletRecord walletrecord = new WalletRecord(loginname,"充值",money);
		Integer result = lprService.addWalletRecord(walletrecord);
		JSONObject jsonmain = new JSONObject();
		if(result!=null) {
			jsonmain.put("code", "200");
		}
		return jsonmain.toString();
	}
	
}
