<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="layui/css/layui.css">
<link rel="stylesheet" href="css/index.css">
<script src="layui/layui.js"></script>
<link rel="shortcut icon" href="img/favicon.ico" />
<link rel="bookmark"href="img/favicon.ico" />
<title>停车场</title>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
	<div class="layui-header">
		<div class="layui-logo" style="color:#FFF;"><i class="layui-icon layui-icon-home"></i>&nbsp;停车场</div>
		<ul class="layui-nav layui-layout-right">
	      <li class="layui-nav-item" style="padding-right:5px">
				<button class="layui-btn layui-btn-sm" data-method="login">登录</button>
	      </li>
	      <li class="layui-nav-item">
				<button class="layui-btn layui-btn-sm" data-method="regist">注册</button>
	      </li>
	    </ul>
	</div>

	<div class="layui-body" style="left:0px;bottom:0px;">
		<div style="padding: 20px; background-color: #F2F2F2">
		  <div class="layui-row layui-col-space15">
		    <div class="layui-col-md6">
		      <div class="layui-card" style="height:563px;border:solid 2px #009688;background:#f2f2f2;">
				<div class="layui-carousel" id="test1">
				  <div carousel-item>
				    <div><img src="img/bg.jpg"></div>
				    <div><img src="img/1.jpg"></div>
				    <div><img src="img/2.jpg"></div>
				    <div><img src="img/3.jpg"></div>
				  </div>
				</div>
				<div>		      
			        <div class="item"><label id="name"></label>停车场竭诚为您服务！</div>
			        <div class="item">地址：<label id="address"></label></div>
			        <div class="item">电话：<label id="telephone"></label></div>
			        <div class="item">剩余活动停车位：<label id="activitynum_leave"></label>个&nbsp;/&nbsp;<label id="activitynum"></label></div>
			        <div class="item">剩余灵活停车位：<label id="fixationnum_leave"></label>个&nbsp;/&nbsp;<label id="fixationnum"></label></div>
			        <div class="item">今日价格:&nbsp;<label id="activitycost_per"></label>&nbsp;元/单位小时。</div>
		        </div>    
		      </div>
		    </div>
		    <div class="layui-col-md6">
		    	<div  class="layui-card" style="height:563px;border:solid 2px #009688;background:#f2f2f2;">
		    		<div style="height:40px;background:#009681;font-weight:bold;text-align:center"><h1>公告&nbsp;&nbsp;</h1></div>
					<div id ="notice" style="height:500px;overflow-y:scroll;"></div>
		    	</div>
		  	</div>
		</div> 
	</div>
	
	</div>

</div>

<script>
	layui.use(['layer','form','carousel','element'], function(){
	  var form = layui.form;
	  var element = layui.element;
	  var layer = layui.layer;
	  var $ = layui.jquery;
	  var carousel = layui.carousel;
	  
	  carousel.render({
		    elem: '#test1'
		    ,width: '100%' //设置容器宽度
		    ,arrow: 'always' //始终显示箭头
		  });
	  
	  //触发事件
	  var active = {
		login: function(){    	
			$("#loginForm")[0].reset();
			form.render(null, 'loginForm');
	    	layer.open({
	    		  title: '登陆系统'
		    	  ,btn: ['登陆','取消']
			      ,success: function (layero, index) {
		                layero.addClass('layui-form');
		                layero.find('.layui-layer-btn0').attr('lay-filter', 'formContent').attr('lay-submit', '');
		                form.render(); 
		          }
		    	  ,yes: function(index, layero){
		    		  //监听提交按钮
		    		  form.on('submit(formContent)', function (data) {
		    			  //请求服务器
			    		  $.ajax({
			    		      url: '/LprSever/login',
			    		      type: 'POST',
			    		      data:data.field,
			    		      async: false,
			    		      dataType: 'json',
			    		      success: function (data) {
			    		    	  if(data.msg==='OK_user'){
			    			          layer.msg("登录成功", {time:3000});
			    		    		  setTimeout("window.location.href='/LprSever/user/Main';", 1000);
			    		    	  }else if(data.msg==='OK_admin'){
			    			          layer.msg("登录成功", {time:3000});
			    		    		  setTimeout("window.location.href='/LprSever/admin/Main';", 1000);
			    		    	  }
			    		    	  else{
			    		    		  layer.msg("用户名或密码错误", {time:3000});
			    		    	  }
			    		    	  
			    		      },
			    		      error: function () {
			    		          layer.msg("服务器错误", {time:3000});
			    		      }
			    		  });
			    			
	                  });
		    	  }
	    		  ,type: 1
	    		  ,area: ['350px', '230px']
	    		  ,content: $('#noDisplayFormlogin')  
	    		}); 	
	    }
		,regist:function(){
			$("#registForm")[0].reset();
			form.render(null, 'registForm');
	    	layer.open({
	    		  title: '注册用户'
		    	  ,btn: ['注册','取消']
			      ,success: function (layero, index) {
		                layero.addClass('layui-form');
		                layero.find('.layui-layer-btn0').attr('lay-filter', 'formContent').attr('lay-submit', '');
		                form.render(); 
		          }
		    	  ,yes: function(index, layero){
		    		  form.on('submit(formContent)', function (data) {
		    			  $.ajax({
		    			      url: '/LprSever/user/registerUser',
		    			      type: 'POST',
		    			      data: data.field,
		    			      async: false,
		    			      dataType: 'json',
		    			      success: function (data) {
		    			    	  if(data.msg==='OK'){
		    				          layer.msg("注册成功", {time:3000});
		    			    		  setTimeout("window.location.href='/LprSever/index';", 1000);
		    			    	  }else if(data.msg==='Dupe'){
		    			    		  layer.msg("用户名已存在", {time:3000});
		    			    	  }else{
		    			    		  layer.msg("服务器错误", {time:3000});
		    			    	  }
		    			      },
		    			      error: function () {
		    			          layer.msg("服务器错误", {time:3000});
		    			      }
		    			  });
			    			
	                  });
		    	  }
	    		  ,type: 1
	    		  ,area: ['350px', '280px']
	    		  ,content: $('#noDisplayFormregist')  
	    		});
		}
	  }
	  
	  $('.layui-btn').on('click', function(){
		    var othis = $(this), method = othis.data('method');
		    active[method] ? active[method].call(this, othis) : '';
		  });
	  
	  $.ajax({
		  url: '/LprSever/index/getindex',
		  type: 'GET',
		  async: false,
		  dataType: 'json',
		  success: function (data) {
			  var datalist=data.data[0];
			  $("#name").text(datalist.name);
			  $("#address").text(datalist.address);
			  $("#telephone").text(datalist.telephone);
			  $("#activitynum_leave").text(datalist.activitynum_leave);
			  $("#fixationnum_leave").text(datalist.fixationnum_leave);
			  $("#activitynum").text(datalist.activitynum);
			  $("#fixationnum").text(datalist.fixationnum);
			  $("#activitycost_per").text(datalist.activitycost_per);
			  console.log(datalist.activitynum+"wq"+datalist.fixationnum);
			  
			  var noticelist=data.data[1];
			  console.log(noticelist.length);
			  for(var i = 0; i < noticelist.length; i++)
			  {
				  $("#notice").append('<div class="layui-card"><a href="http://www.baidu.com"><h3>&nbsp;&nbsp;'+noticelist[i].create_date+'</h3>'
				  +'<div class="layui-card-body">'+noticelist[i].title+'</div></a></div>');
			  }
			  
		  }
		});
	  		
	 });
</script>

	<!-- 以下是隐藏的表单容器 -->
	<div id="noDisplayFormlogin" style="display:none;">
		<div class="layui-card">
            <div class="layui-card-body">
                <form class="layui-form layui-form-pane" lay-filter="loginForm" id="loginForm">
                      <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon layui-icon-username">&nbsp;</i>用户名</label>
                        <div class="layui-input-block">
                            <input type="text" name="loginname" required lay-verify="required" placeholder="请输入用户名" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon layui-icon-password">&nbsp;</i>密&nbsp;&nbsp;码</label>
                        <div class="layui-input-block">
                            <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" class="layui-input"></div>
                    </div>
                </form>
            </div>
        </div>
	</div>
	<div id="noDisplayFormregist" style="display:none;">
		<div class="layui-card">
            <div class="layui-card-body">
                <form class="layui-form layui-form-pane" lay-filter="registForm" id="registForm">
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon layui-icon-username">&nbsp;</i>登录名</label>
                        <div class="layui-input-block">
                            <input type="text" name="loginname" required lay-verify="required|loginname" placeholder="请输入登录名" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon layui-icon-password">&nbsp;</i>密&nbsp;&nbsp;码</label>
                        <div class="layui-input-block">
                            <input type="password" name="password" required lay-verify="required|password" placeholder="请输入密码" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">电话号码</label>
                        <div class="layui-input-block">
                            <input type="text" name="telephone" required placeholder="请输入电话号码" class="layui-input"></div>
                    </div>
                </form>
            </div>
        </div>
	</div>
	
</body>
</html>