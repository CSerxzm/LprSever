<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="/LprSever/layui/css/layui.css" media="all">
<link href="/LprSever/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script src="/LprSever/layui/layui.js"></script>
<script type="text/javascript" src="/LprSever/umeditor/jquery-1.10.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/LprSever/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/LprSever/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="/LprSever/umeditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
<div  style="max-width:1350px;margin:0 auto;"> 
	<div class="layui-form" enctype="multipart/form-data" style="width:80%;margin:10px auto;">
		<div style="font-weight:bold;font-size:20px;margin:30px auto;">${notice.title}</div>
		<div style="font-size:16px;margin:10px auto;"><span>发布者：${notice.name_publish}</span>&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;<span>发布时间：${notice.create_date}</span>
		</div>
		<div  name="content" type="text/plain">${notice.content}</div>
	</div>
</div>
</body>
</html>