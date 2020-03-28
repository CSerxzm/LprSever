<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>车辆管理系统 </title>
  <link rel="stylesheet" href="/LprSever/layui/css/layui.css" media="all">
  <script src="/LprSever/layui/layui.js"></script>
  <link rel="shortcut icon" href="/LprSever/img/stu.ico" />
  <link rel="bookmark"href="/LprSever/img/stu.ico" />
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo" style="color:#FFF;"><i class="layui-icon layui-icon-home"></i>&nbsp;车辆管理系统</div>
    <%
    	String pageName = "",parkspace_order="",trarecord="",user="",walletrecord="",parkspace_manage="";
    	request.setCharacterEncoding("utf-8");
    	if (!request.getParameterMap().isEmpty()){
    		pageName = request.getParameter("page");
			if(pageName.equals("parkspace_order"))
    			parkspace_order=" layui-this";
    		else if(pageName.equals("trarecord"))
    			trarecord=" layui-this";
    		else if(pageName.equals("walletrecord"))
    			walletrecord=" layui-this";
    		else if(pageName.equals("parkspace_manage"))
    			parkspace_manage=" layui-this";
    		else
    			user=" layui-this";
    	}
    	else{
    		pageName = "user";
    		user=" layui-this";
    	}
    %>
    <ul class="layui-nav layui-layout-left">
    	  <li class="layui-nav-item<%=user%>"><a href="Main?page=user">个人信息</a></li>
    	  <li class="layui-nav-item<%=trarecord%>"><a href="Main?page=trarecord">通行日志</a></li>
	      <li class="layui-nav-item<%=parkspace_order%>"><a href="Main?page=parkspace_order">停车位预约</a></li>
	      <li class="layui-nav-item<%=parkspace_manage%>"><a href="Main?page=parkspace_manage">停车位管理</a></li>
	      <li class="layui-nav-item<%=walletrecord%>"><a href="Main?page=walletrecord">费用记录</a></li>
    </ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
			<i class="layui-icon layui-icon-username"></i>&nbsp;${user_session.loginname}&nbsp;&nbsp;
      </li>
      <li class="layui-nav-item">
			<a href="/LprSever/logout">退出</a>
      </li>
    </ul>
  </div>

  <div class="layui-body" style="left:0px;overflow: hidden;">
    <iframe src="./<%=pageName%>" style="overflow:hidden;width:100%;height:100%;"></iframe>
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