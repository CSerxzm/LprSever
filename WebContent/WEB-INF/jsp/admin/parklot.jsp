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
	<legend>停车场——详细信息</legend>
	</fieldset> 
	<div class="layui-card" style="max-width:1000px;margin:0 auto;"> 
		<div class="layui-card-body">
			<form class="layui-form layui-form-pane" lay-filter="parklotForm" id="parklotForm">
                    <div class="layui-form-item">
                    	<div class="layui-inline">
	                        <label class="layui-form-label">停车场标识</label>
	                    	<div class="layui-input-inline">
	                            <input type="text" name="id" autocomplete="off" class="layui-input" readonly></div>
	                        
	                        <label class="layui-form-label">停车场名字</label>
                        	<div class="layui-input-inline">
                            	<input type="text" name="name" autocomplete="off" class="layui-input"></div>
                    	</div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">停车场地址</label>
                        <div class="layui-input-block">
                            <input type="text" name="address" autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">联系电话</label>
                        <div class="layui-input-block">
                            <input type="number" name="telephone" autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">活动车位</label>
                        <div class="layui-input-inline">
                            <input type="number" name="activitynum" autocomplete="off" class="layui-input"></div>
                        <div class="layui-input-inline">
                        <input type="text" name="activitynum_leave"  class="layui-input" readonly></div>                    
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">固定车位</label>
                        <div class="layui-input-inline">
                            <input type="number" name="fixationnum" autocomplete="off" class="layui-input"></div>
                        <div class="layui-input-inline">    
						<input type="text" name="fixationnum_leave"  class="layui-input" readonly></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">单位收费</label>
                        <div class="layui-input-inline">
                            <input type="number" name="activitycost_per" placeholder="$" autocomplete="off" class="layui-input"></div>
                   		 <div class="layui-form-mid">元</div>
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
		  url: '/LprSever/parklot/getParkLot',
		  type: 'GET',
		  async: false,
		  dataType: 'json',
		  success: function (data) {
			  var datalist=data.data[0];
			  form.val('parklotForm', {
				"id":datalist.id
				,"name": datalist.name
				,"address": datalist.address
				,"telephone": datalist.telephone
				,"activitynum": datalist.activitynum
				,"fixationnum": datalist.fixationnum
				,"activitynum_leave":"剩余停车位"+datalist.activitynum_leave+"个"
				,"fixationnum_leave":"剩余停车位"+datalist.fixationnum_leave+"个"
				,"activitycost_per":datalist.activitycost_per
			});
		  },
		  error: function () {
			  
		  }
		});
	  
		form.on('submit(demo1)', function(data){
			$.ajax({
				url: '/LprSever/parklot/updateParkLot',
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