<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="layui/css/layui.css" media="all">
<script src="layui/layui.js"></script>
</head>
<body>
<div  style="max-width:1350px;margin:0 auto;">
	<div class="layui-card">
		<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
			<legend>车牌识别</legend>
		</fieldset>
		<div class="layui-container" style="width:100%">
			  <div class="layui-row layui-col-space10">
			    <div class="layui-col-lg7">
			    	<div class="layui-card-body">
			    		<div class="layui-upload  layui-bg-gray">
						  <button class="layui-btn" id="file" type="button">上传图片</button>
						  <div class="layui-upload-list">
						    <img class="layui-upload-img" id="demo1">
						  </div>
						</div>
			    	</div>
			    </div>
			    <div class="layui-col-lg5">
			      	<div class="layui-card-body">
			    		<div class="layui-bg-gray">
						    <p id="demoText"></p>
						</div>
			    	</div>
			    </div>
			  </div>
		</div> 
	</div>
</div>
<script>
layui.use('upload', function(){
  var $ = layui.jquery
  ,upload = layui.upload;
  
  //普通图片上传
  var uploadInst = upload.render({
     elem: '#file'
    ,url: '/LprSever/upload'
    ,before: function(obj){
      //预读本地文件示例，不支持ie8
      obj.preview(function(index, file, result){
        $('#demo1').attr('src', result); //图片链接（base64）
      });
    }
    ,done: function(data){
      if(data.code > 0){
    	var demoText = $('#demoText');
    	demoText.html(data.number);
        return layer.msg('上传成功');
      }
      //上传成功
    }
    ,error: function(){
      var demoText = $('#demoText');
      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
      demoText.find('.demo-reload').on('click', function(){
        uploadInst.upload();
      });
    }
  });
 });
</script>
</body>
</html>