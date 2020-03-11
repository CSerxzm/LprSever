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

import com.xzm.lpr.domain.Notice;
import com.xzm.lpr.domain.User;
import com.xzm.lpr.service.LprService;
import com.xzm.lpr.util.common.LprConstants;
import com.xzm.lpr.util.tag.PageModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



@Controller
public class NoticeController {

	@Autowired
	@Qualifier("lprService")
	private LprService lprService;
	
	@RequestMapping(value="/notice/getNotice",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	 public String getNotice(Integer page,Integer limit){
		
		System.out.println(page+"/"+limit);
		PageModel pageModel = new PageModel();
		if(page != null){
			pageModel.setPageIndex(page);
		}
		if(page != null){
			pageModel.setPageSize(limit);
		}

		List<Notice> notices = lprService.findNotice(pageModel);
		
		JSONObject jsonmain = new JSONObject();
		jsonmain.put("code", "200");
		jsonmain.put("msg", "none");
		jsonmain.put("count",pageModel.getRecordCount());
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		for (int i = 0; i < notices.size(); i++) {
			Notice notice = (Notice)notices.get(i);
			jsonobj.put("id", notice.getId());
			jsonobj.put("title", notice.getTitle());
			jsonobj.put("content", notice.getContent());
			jsonobj.put("create_date", notice.getCreate_date());
			jsonobj.put("name_publish", notice.getName_publish());			
			jsonarray.add(jsonobj);
		}
		
		jsonmain.put("data", jsonarray);		
		return jsonmain.toString();
	
	}
	
	@RequestMapping(value="/notice/removeNotice",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	 public String removeNotice(String id){
		Integer i = lprService.removeNoticeById(Integer.parseInt(id));
		JSONObject jsonmain = new JSONObject();
		if(i != 0){
			jsonmain.put("msg", "OK");
		}else{
			jsonmain.put("msg", "ERROR");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/notice/addNotice",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	 public String addNotice(HttpSession session,@RequestParam Map<String,String> map){
		String title=map.get("title");
		String content=map.get("content");
		User user=(User)session.getAttribute(LprConstants.USER_SESSION);
		String name_publish=user.getLoginname();
		Integer i = lprService.addNotice(new Notice(title,content,name_publish));
		System.out.println("i="+i);
		JSONObject jsonmain = new JSONObject();
		if(i != 0){
			jsonmain.put("msg", "OK");
		}else{
			jsonmain.put("msg", "ERROR");
		}
		return jsonmain.toString();		
	}
	
	@RequestMapping(value="/notice/updateNotice",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	 public String updateNotice(HttpSession session,@RequestParam Map<String,String> map){
		String title=map.get("title");
		String content=map.get("content");
		User user=(User)session.getAttribute(LprConstants.USER_SESSION);
		String name_publish=user.getLoginname();
		
		Integer i = lprService.addNotice(new Notice(title,content,name_publish));
		
		JSONObject jsonmain = new JSONObject();
		if(i != 0){
			jsonmain.put("msg", "OK");
		}else{
			jsonmain.put("msg", "ERROR");
		}
		return jsonmain.toString();	
	}
	
}
