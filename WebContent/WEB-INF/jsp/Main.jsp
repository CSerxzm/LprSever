<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>车辆管理系统 </title>
  <link rel="stylesheet" href="layui/css/layui.css" media="all">
  <script src="layui/layui.js"></script>
  <link rel="shortcut icon" href="img/favicon.ico" />
  <link rel="bookmark"href="img/favicon.ico" />
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo" style="color:#FFF;"><i class="layui-icon layui-icon-home"></i>&nbsp;车辆管理系统</div>
    <%
    	String pageName = "", parklot="",parkspace="",trarecord="",notice="",user="",upload="";
    	request.setCharacterEncoding("utf-8");
    	if (!request.getParameterMap().isEmpty()){
    		pageName = request.getParameter("page");
    		if(pageName.equals("parklot"))
    			parklot=" layui-this";
    		else if(pageName.equals("parkspace"))
    			parkspace=" layui-this";
    		else if(pageName.equals("trarecord"))
    			trarecord=" layui-this";
    		else if(pageName.equals("notice"))
    			notice=" layui-this";
    		else if(pageName.equals("upload"))
    			upload=" layui-this";
    		else
    			user=" layui-this";
    	}
    	else{
    		pageName = "trarecord";
    		trarecord=" layui-this";
    	}
    %>
    <ul class="layui-nav layui-layout-left">
	      <li class="layui-nav-item<%=parklot%>"><a href="Main?page=parklot">停车场管理</a></li>
	      <li class="layui-nav-item<%=parkspace%>"><a href="Main?page=parkspace">停车位管理</a></li>
	      <li class="layui-nav-item<%=trarecord%>"><a href="Main?page=trarecord">通行日志</a></li>
	      <li class="layui-nav-item<%=notice%>"><a href="Main?page=notice">公告管理</a></li>
	      <li class="layui-nav-item<%=user%>"><a href="Main?page=user">用户管理</a></li>
	      <li class="layui-nav-item<%=upload%>"><a href="Main?page=upload">车牌识别</a></li>
    </ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
			<i class="layui-icon layui-icon-username"></i>&nbsp;${sessionScope.username}
      </li>
      <li class="layui-nav-item">
			<a href="/LprSever/logout">退出<span class="layui-badge-dot"></span></a>
      </li>
    </ul>
  </div>
  
  
  <div class="layui-body" style="left:0px;">
    <!-- 内容主体区域 -->
    <iframe src="./<%=pageName%>" frameborder="0" width="100%" height="100%" id="contentIframe"></iframe>
  </div>
  <script>
	layui.use('element', function(){
	  var element = layui.element;
	  
	});
</script>
  <div class="layui-footer" style="left:0px;text-align:center;">
    ©HFUT - 车辆管理系统 2020
  </div>
</div>
</body>
</html>