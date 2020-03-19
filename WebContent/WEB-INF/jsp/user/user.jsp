<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="/LprSever/layui/css/layui.css" media="all">
<script src="/LprSever/layui/layui.js"></script>
</head>
<body>
<div  style="max-width:1350x;margin:0 auto;">
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
	<legend>个人——详细信息</legend>
	</fieldset> 
	<div class="layui-card" style="max-width:500px;margin:0 auto;"> 
		<div class="layui-card-body">
			<form class="layui-form layui-form-pane" lay-filter="userForm" id="userForm">
                    <div class="layui-form-item">
	                    <label class="layui-form-label">登录名</label>
	                    <div class="layui-input-block">
	                        <input type="text" name="loginname" class="layui-input" readonly></div>
                    </div>
                    <div class="layui-form-item">
	                    <label class="layui-form-label">登陆密码</label>
                        <div class="layui-input-block">
                            <input type="password" name="password" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">停车位id</label>
                        <div class="layui-input-block">
                            <input type="text" name="parkspace_id" class="layui-input" readonly></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">车牌号码</label>
                        <div class="layui-input-block">
                            <input type="text" name="licenseplate" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">电话号码</label>
                        <div class="layui-input-block">
                            <input type="text" name="telephone" class="layui-input"></div>               
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">注册时间</label>
                        <div class="layui-input-block">
                            <input type="text" name="createdate" class="layui-input" readonly></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">权限</label>
                        <div class="layui-input-block">
                            <input type="text" name="authority" class="layui-input" readonly></div>
                    </div>                                   
                    <div class="layui-form-item">
	                    <div class="layui-inline">
						      <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="demo1">确认修改</button>
						</div>
                    </div>
             </form>
		</div>
	</div>
</div>
<script>
	layui.use(['layer','form','element'], function(){
	  var form = layui.form;
	  var element = layui.element;
	  var layer = layui.layer;
	  var $ = layui.jquery;
	  
	  $.ajax({
		  url: '/LprSever/user/getUserOne',
		  type: 'GET',
		  async: false,
		  dataType: 'json',
		  success: function (data) {
			  var datalist=data.data[0];
			  form.val('userForm', {
				"loginname":datalist.loginname
				,"password": datalist.name
				,"parkspace_id":datalist.parkspace_id
				,"licenseplate":datalist.licenseplate
				,"telephone":datalist.telephone
				,"createdate": datalist.createdate
				,"authority":datalist.authority
			});
		  },
		  error: function () {  
		  }
		});
	  
		form.on('submit(demo1)', function(data){
			$.ajax({
				url: '/LprSever/user/updateUser',
			    type: 'POST',
			    data:data.field,
			    async: false,
			    dataType: 'json',
			    success: function (data) {
			    	 if(data.msg==='OK'){
				         layer.msg("修改成功", {time:3000});
			    	 }else{
			    		 layer.msg("修改错误", {time:3000});
			    	 }
			     },
			     error: function () {
			         layer.msg("服务器错误", {time:3000});
			     }
			 });
		  return false;
		});
		
	 });
</script>
</body>
</html>