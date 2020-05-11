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
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping(value="/notice/getNotice")
	@ResponseBody
	 public String getNotice(Integer page,Integer limit,String keyword) throws UnsupportedEncodingException{
		
		System.out.println(page+"/"+limit);
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

		List<Notice> notices = lprService.findNotice(pageModel,keyword);
		
		JSONObject jsonmain = new JSONObject();
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
		jsonmain.put("code", 200);
		return jsonmain.toString();
	
	}
	
	@RequestMapping(value="/notice/removeNotice")
	@ResponseBody
	 public String removeNotice(String id){
		Integer i = lprService.removeNoticeById(Integer.parseInt(id));
		JSONObject jsonmain = new JSONObject();
		if(i != null){
			jsonmain.put("msg", "删除成功");
		}else{
			jsonmain.put("msg", "删除失败");
		}
		return jsonmain.toString();
	}
	
	@RequestMapping(value="/notice/addNotice")
	@ResponseBody
	 public String addNotice(HttpSession session,@RequestParam Map<String,String> map){
		String title=map.get("title");
		String content=map.get("content");
		User user=(User)session.getAttribute(LprConstants.USER_SESSION);
		String name_publish=user.getLoginname();
		Integer i = lprService.addNotice(new Notice(title,content,name_publish));
		
		JSONObject jsonmain = new JSONObject();
		if(i != null){
			jsonmain.put("msg", "添加成功");
		}else{
			jsonmain.put("msg", "添加失败");
		}
		return jsonmain.toString();		
	}
	
	@RequestMapping(value="/notice/updateNotice")
	@ResponseBody
	 public String updateNotice(HttpSession session,@RequestParam Map<String,String> map){
		String title=map.get("title");
		String content=map.get("content");
		Integer id=Integer.valueOf(map.get("id"));
		User user=(User)session.getAttribute(LprConstants.USER_SESSION);
		String name_publish=user.getLoginname();
		
		Integer i = lprService.updateNotice(new Notice(id,title,content,name_publish));

		JSONObject jsonmain = new JSONObject();
		if(i != null){
			jsonmain.put("msg", "更新成功");
		}else{
			jsonmain.put("msg", "更新失败");
		}
		return jsonmain.toString();	
	}
	
	@RequestMapping(value="/notice/toupdateNotice")	
	 public ModelAndView toUpdateNotice(ModelAndView mv,Integer id){
		Notice notice = lprService.findNoticeById(id);
		mv.addObject("notice",notice);
		mv.setViewName("admin/noticeupdate");
		return mv;
	}
	
	@RequestMapping(value="/notice/getNoticeShow")	
	 public ModelAndView getNoticeShow(ModelAndView mv,Integer id){
		Notice notice = lprService.findNoticeById(id);
		mv.addObject("notice",notice);
		mv.setViewName("admin/noticeshow");
		return mv;
	}
	
	@RequestMapping(value="/notice/getNoticeShowIndex")
	@ResponseBody
	 public String getNoticeShowIndex(Integer id){
		
		Notice notice = lprService.findNoticeById(id);
		JSONObject jsonmain = new JSONObject();
		
		if(notice != null){
			jsonmain.put("title", notice.getTitle());
			jsonmain.put("content", notice.getContent());
			jsonmain.put("create_date", notice.getCreate_date());
			jsonmain.put("name_publish", notice.getName_publish());	
			jsonmain.put("code", "200");
		}else{
			jsonmain.put("code", "-1");
		}
		return jsonmain.toString();		
	}
}
