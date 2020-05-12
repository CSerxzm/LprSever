<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="/LprSever/layui/css/layui.css" media="all">
<script src="/LprSever/layui/layui.js"></script>
</head>
<body>
<div  style="max-width:1350px;margin:0 auto;">
	<div class="layui-card">
		<fieldset class="layui-elem-field layui-field-title" style="margin-top:15px;">
			<legend>车牌识别</legend>
		</fieldset>
		<div class="layui-container" style="width:100%">
			  <div class="layui-row layui-col-space10">
			    <div class="layui-col-lg6">
			    	<div class="layui-card-body">
			    		<div class="layui-upload">
						  <button class="layui-btn" id="file" type="button">拍照</button>
						  <div class="layui-upload-list layui-bg-gray" style="height:420px;width:100%;">
						    <img class="layui-upload-img" id="picture_pre" style="height:100%;width:100%;">
						  </div>
						</div>
			    	</div>
			    </div>
			    <div class="layui-col-lg6">
			      	<div class="layui-card-body">
						<div style="padding:10px;">
							<label style="font-size:20px">详情面板</label>						
						</div>
		                <form class="layui-form layui-form-pane" lay-filter="trarecordForm" id="trarecordForm">
		                    <div class="layui-form-item">
		                        <label class="layui-form-label">牌照</label>
		                        <div class="layui-input-inline">
		                            <input type="text" name="licenseplate" id="licenseplate" required placeholder="车牌号码" class="layui-input"></div>
		                    		<div class="layui-form-mid">若识别车牌号码错误，可手动修改。</div>
		                    </div>
		                    <div class="layui-form-item">
		                        <label class="layui-form-label">停车位</label>
		                        <div class="layui-input-inline">
		                            <input type="text" name="parkspace_id" id="parkspace_id" placeholder="分配的停车位标识" class="layui-input" readonly></div>
		                    </div>
		                    <div class="layui-form-item">
		                    	<label class="layui-form-label">停车时间</label>
								<div class="layui-input-inline" style="width: 240px;">
								    <input type="text" name="date_in" id="date_in" autocomplete="off" class="layui-input">
								</div>
								<div class="layui-form-mid">-</div>
								<div class="layui-input-inline" style="width: 240px;">
								    <input type="text" name="date_out" id="date_out" autocomplete="off" class="layui-input">
								</div>
		                    </div>
		                    <div class="layui-form-item">
		                        <label class="layui-form-label">停车时间</label>
		                        <div class="layui-input-inline">
		                            <input type="text" name="time" id="time" placeholder="0" class="layui-input" readonly></div>
		                        <div class="layui-form-mid">小时</div>
		                    </div>
		                    <div class="layui-form-item">
		                        <label class="layui-form-label">费用</label>
		                        <div class="layui-input-inline">
		                            <input type="text" name="cost" id="cost" placeholder="0" autocomplete="off" class="layui-input" readonly></div>
		                    		<div class="layui-form-mid">元</div>
		                    </div>
		                    <div class="layui-form-item">
		                         <div class="layui-inline">
								      <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="gothrough">通行</button>
								</div>								
		                    </div>	                                       
		                </form>	                	                		                
			    	</div>
			    </div>
			  </div>
		</div> 
	</div>
</div>
<script>
layui.use(['upload','form','element'], function(){
	var $ = layui.jquery
	,upload = layui.upload;
	var form = layui.form;
	var element = layui.element;

	var uploadInst = upload.render({
		elem: '#file'
	    ,url: '/LprSever/uploadtoweb'
	    ,before: function(obj){
	
	      obj.preview(function(index, file, result){
	        $('#picture_pre').attr('src', result);
	      });
	    }
	    ,done: function(data){
	      if(data.code > 0){
	    	var demoText = $('#demoText');
	    	$("#licenseplate").val(data.number);
	    	$("#date_in").val(getNowFormatDate());
	    	$("#date_out").val(getNowFormatDate());
	        return layer.msg('图片采集成功');
	      }
	    }
	  });
 
	//监控通行
	form.on('submit(gothrough)', function(data){
		$.ajax({
			url: '/LprSever/trarecord/drive',
		    type: 'POST',
		    data:data.field,
		    async: false,
		    dataType: 'json',
		    success: function (data) {
		    	$("#parkspace_id").val(data.parkspace_id);
		    	$("#date_in").val(data.date_in);
		    	$("#date_out").val(data.date_out);
		    	$("#time").val(data.time);
		    	$("#cost").val(data.cost);
			    layer.msg(data.msg, {time:3000});
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