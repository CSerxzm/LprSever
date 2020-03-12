<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="../layui/css/layui.css" media="all">
<link href="../umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script src="../layui/layui.js"></script>
<script type="text/javascript" src="../umeditor/jquery-1.10.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="../umeditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
<div  style="max-width:1350px;margin:0 auto;">
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
	<legend>新闻添加</legend>
	</fieldset> 
	<form class="layui-form" id="updatenotice" enctype="multipart/form-data" style="width:80%;margin:10px auto;">
		<input type="hidden" name="id" class="layui-input" value="${notice.id}">
		<div class="layui-form-item">
			<label class="layui-form-label">标题</label>
			<div class="layui-input-block">
			<input type="text" name="title" required value="${notice.title}" class="layui-input" style="width:80%">
			</div>
		</div>
		<script id="container" name="content" type="text/plain">${notice.content}</script>
		<script type="text/javascript">
    	   	var editor = UM.getEditor('container');
		</script>
        <div class="layui-form-item"  style="margin:10px 10px;">
        	<div class="layui-input-block">
        	<button class="layui-btn layui-btn-normal" lay-submit lay-filter="updatenotice">发布</button>
        </div>
	</form>
</div>

<script>
	layui.use(['layer','form','element'], function(){
		var form = layui.form;
		var element = layui.element;
		var layer = layui.layer;
		var $ = layui.jquery;
		form.on('submit(updatenotice)', function(data){
			  var formObject = {};
			  var formArray =$('#updatenotice').serializeArray();
			  $.each(formArray,function(i,item){
				  formObject[item.name] = item.value;
				  });
			  
			  $.ajax({
			      url: '/LprSever/notice/updateNotice',
			      type: 'POST',
			      data:formObject,
			      async: false,
			      dataType: 'json',
			      success: function (data) {
			    	  if(data.msg==='OK'){
				          layer.msg("发表成功", {time:3000});
				          window.location.href="../notice";
			    	  }else{
			    		  layer.msg("发表失败", {time:3000});
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