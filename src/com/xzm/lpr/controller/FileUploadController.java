package com.xzm.lpr.controller;

import java.io.File;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xzm.lpr.aip.LicensePlate;

import net.sf.json.JSONObject;

@Controller
public class FileUploadController{
	
	 @PostMapping(value="/upload",produces={"text/html;charset=UTF-8"})
	 @ResponseBody
	 public String upload(HttpServletRequest request,
			MultipartFile file) throws Exception{
		 
		 JSONObject jsonmain = new JSONObject();
		if(!file.isEmpty()){
			String path = request.getServletContext().getRealPath("/images");
			String filename = file.getOriginalFilename();

		    File filepath = new File(path,filename);
	        if (!filepath.getParentFile().exists()) { 
	        	filepath.getParentFile().mkdirs();
	        }
			file.transferTo(new File(path+File.separator+ filename));
			
			String number = LicensePlate.getlicensePlate(path+File.separator+ filename);
			
			System.out.println("上传文件路径：" + (path+File.separator+ filename));
			System.out.println(number);
			jsonmain.put("code", "200");
			jsonmain.put("msg", "none");
			jsonmain.put("number", number);
		}
		
		return jsonmain.toString();
		 
	 }
	 
	 @GetMapping(value="/download",produces={"text/html;charset=UTF-8"})
	 public ResponseEntity<byte[]> download(HttpServletRequest request,
			 @RequestParam("filename") String filename,
			 @RequestHeader("User-Agent") String userAgent
			 )throws Exception{
		// 下载文件路径
		String path = request.getServletContext().getRealPath(
                "/images");
		// 构建File
		File file = new File(path+File.separator+ filename);
		// ok表示Http协议中的状态 200
        BodyBuilder builder = ResponseEntity.ok();
        // 内容长度
        builder.contentLength(file.length());
        // application/octet-stream ： 二进制流数据（最常见的文件下载）。
        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        // 使用URLDecoder.decode对文件名进行解码
        filename = URLEncoder.encode(filename, "UTF-8");
        // 设置实际的响应文件名，告诉浏览器文件要用于【下载】、【保存】attachment 以附件形式
        // 不同的浏览器，处理方式不同，要根据浏览器版本进行区别判断
        if (userAgent.indexOf("MSIE") > 0) {
                // 如果是IE，只需要用UTF-8字符集进行URL编码即可
                builder.header("Content-Disposition", "attachment; filename=" + filename);
        } else {
                // 而FireFox、Chrome等浏览器，则需要说明编码的字符集
                // 注意filename后面有个*号，在UTF-8后面有两个单引号！
                builder.header("Content-Disposition", "attachment; filename*=UTF-8''" + filename);
        }
        return builder.body(FileUtils.readFileToByteArray(file));
	 }

}

