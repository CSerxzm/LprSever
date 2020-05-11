<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="layui/css/layui.css">
<link rel="stylesheet" href="css/index.css">
<script src="layui/layui.js"></script>
<script src="js/jquery.min.js"></script>
<script src="js/ledstyle.js"></script>
<link rel="shortcut icon" href="img/favicon.ico" />
<link rel="bookmark"href="img/favicon.ico" />
<title>停车场</title>
</head>

<body>
<div style="width:100%;margin:0 auto;">
	<div class="header" style="width:1335px;height:60px;line-height:60px;padding:5px;background-color: #F2F2F2;margin:0 auto;text-align:center;">
		<span style="float:left;height:64px;line-height:64px;"><img src="img/home.jpg" style="vertical-align:middle;position:relative;"/></span>
		<font style="font-family:楷体;font-size:16px;"><span style="float:left;">&nbsp;停车</span></font>
		<font style="font-family:楷体;font-size:40px;"><div id="name" style="display:inline;margin:0 auto;">停车场</div></font>
		<span style="float:right;">
			<button class="layui-btn layui-btn-sm layui-bg-black" data-method="login">登录</button>
			<button class="layui-btn layui-btn-sm layui-bg-black" data-method="regist">注册</button>
		</span>
	</div>

	<div style="margin:0 auto;">
		<div style="height:300px;padding:5px;margin:0 auto;">
			<div style="float:left;width:60%;height:300px;margin:5px;border:1px solid #000;">
				<div class="layui-carousel" id="test1" style="background:#F2F2F2;">
					<div carousel-item>
						<div><img src="img/1.jpg"></div>
						<div><img src="img/2.jpg"></div>
						<div><img src="img/3.jpg"></div>
					</div>
				</div>
			</div>
			<div style="float:right;width:38%;height:300px;margin:5px;border:1px solid #000;">
				<div style="height:40px;background:#F2F2F2;font-weight:bold;text-align:center"><h1>公告</h1></div>
				<div id ="notice" style="height:250px;overflow-y:scroll;"></div>
			</div>
		</div>
		
		<div style="height:260px;padding:5px;margin:0 auto;">
			<div style="float:left;width:53%;height:100%;margin:5px;border:1px solid #000;background:#F2F2F2;">
				<div style="padding:5px;">
					<div class="item"><span>剩余车位:&nbsp;&nbsp;&nbsp;活动:</span><span class="activitynum_leave"></span><sapn>/</span><span class="activitynum"></span><sapn>&nbsp;&nbsp;个</span>
					<span>&nbsp;&nbsp;&nbsp;固定:</span><span class="fixationnum_leave"></span><sapn>/</span><span class="fixationnum"></span><sapn>&nbsp;&nbsp;个</span></div>
					<div class="item"><span>今日价格:&nbsp;&nbsp;&nbsp;</span><span class="activitycost_per"></span><span>&nbsp;&nbsp;元/单位小时。</span></div>
					<div class="item"><span>租赁价格:&nbsp;&nbsp;&nbsp;按月:</span><span class="monthcost"></span><sapn>元&nbsp;&nbsp;季度:</span>
					<span class="quartercost"></span><sapn>元&nbsp;&nbsp;年度:</span><span class="yearcost"></span><sapn>元</span></div>
					<div class="item"><span>停车场地址:&nbsp;&nbsp;&nbsp;</span><span id="address"></span></div>
					<div class="item"><span>联系电话:&nbsp;&nbsp;&nbsp;</span><span id="telephone"></span></div>
				</div>
			</div>
			<div style="float:right;width:45%;height:260px;margin:5px;border:1px solid #000;">
				<div id ="map" style="height:100%;"></div>
			</div>
		</div>
		
	</div>
	
	
</div>

<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.8&key=3d098e8598f64ea72645e21ed4c65598"></script> 

<script>

	//加载高德地图
	window.onload = function(){
		var map = new  AMap.Map('map',{
			resizeEnable: true, 
			zoom: 10
		});
	}

	layui.use(['layer','form','carousel','element'], function(){
	  var form = layui.form;
	  var element = layui.element;
	  var layer = layui.layer;
	  var $ = layui.jquery;
	  var carousel = layui.carousel;
	  
	  //轮播
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
			    		    	  else if(data.msg==='OK_root'){
			    			          layer.msg("登录成功", {time:3000});
			    		    		  setTimeout("window.location.href='/LprSever/root/Main';", 1000);
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
	    		  ,area: ['400px', '230px']
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
		    			    	  }
		    			      },
		    			      error: function () {
		    			          layer.msg("服务器错误", {time:3000});
		    			      }
		    			  });
			    			
	                  });
		    	  }
	    		  ,type: 1
	    		  ,area: ['400px', '350px']
	    		  ,content: $('#noDisplayFormregist')  
	    		});
		}
	  }
	  
	  $('.layui-btn').on('click', function(){
		    var othis = $(this), method = othis.data('method');
		    active[method] ? active[method].call(this, othis) : '';
		  });
	  
	  $.ajax({
		  url: '/LprSever/index/getindex_info',
		  type: 'GET',
		  async: false,
		  dataType: 'json',
		  success: function (data) {
			  var datalist=data.data[0];
			  $("#name").text(datalist.name);
			  $("#address").text(datalist.address);
			  $("#telephone").text(datalist.telephone);		  
			  let led0 = new LedStyle(20,30,3,'.fixationnum_leave','red','0');
			  led0.setValues(datalist.fixationnum_leave);			  
			  let led1 = new LedStyle(20,30,3,'.activitynum_leave','red','0');
			  led1.setValues(datalist.activitynum_leave);
			  let led2 = new LedStyle(20,30,3,'.activitynum','red','0');
			  led2.setValues(datalist.activitynum);
			  let led3 = new LedStyle(20,30,3,'.fixationnum','red','0');
			  led3.setValues(datalist.fixationnum);
			  let led4 = new LedStyle(20,30,3,'.activitycost_per','red','0');
			  led4.setValues(datalist.activitycost_per);
			  let led5 = new LedStyle(20,30,3,'.monthcost','red','0');
			  led5.setValues(datalist.monthcost);
			  let led6 = new LedStyle(20,30,3,'.quartercost','red','0');
			  led6.setValues(datalist.quartercost);
			  let led7 = new LedStyle(20,30,3,'.yearcost','red','0');
			  led7.setValues(datalist.yearcost);
			  
		  }
		});
	  
	  
	  $.ajax({
		  url: '/LprSever/index/getindex_notice',
		  type: 'GET',
		  async: false,
		  dataType: 'json',
		  success: function (data) {
			  var noticelist=data.data;
			  for(var i = 0; i < noticelist.length; i++)
			  {
				  $("#notice").append('<div class="layui-card"><a href="/LprSever/notice/getNoticeShow?id='+noticelist[i].id+'"><h3>&nbsp;&nbsp;'+noticelist[i].create_date+'</h3>'
				  +'<div class="layui-card-body">'+noticelist[i].title+'</div></a></div>');
			  }
			  
		  }
		});
	  
	  
	  //表单验证
	  form.verify({
		  loginname: function(value){ 
		    if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
		      return '用户名不能有特殊字符';
		    }
		    if(/(^\_)|(\__)|(\_+$)/.test(value)){
		      return '用户名首尾不能出现下划线\'_\'';
		    }
		    if(/^\d+\d+\d$/.test(value)){
		      return '用户名不能全为数字';
		    }
		  }
		  ,password: [
			  /^[\S]{6,12}$/
		    ,'密码必须6到12位，且不能出现空格'
		  ]
		  ,telephone: [
			  /^1\d{10}$/
			    ,'请输入正确的手机号码'
			  ] 
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
                            <input type="text" name="loginname" lay-verify="required" placeholder="请输入用户名" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon layui-icon-password">&nbsp;</i>密&nbsp;&nbsp;码</label>
                        <div class="layui-input-block">
                            <input type="password" name="password" lay-verify="required" placeholder="请输入密码" class="layui-input"></div>
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
                            <input type="text" name="loginname" lay-verify="required|loginname" placeholder="请输入登录名" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon layui-icon-password">&nbsp;</i>密&nbsp;&nbsp;码</label>
                        <div class="layui-input-block">
                            <input type="password" name="password" lay-verify="required|password" placeholder="请输入密码" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon layui-icon-password">&nbsp;</i>密&nbsp;&nbsp;码</label>
                        <div class="layui-input-block">
                            <input type="password" name="password" lay-verify="required|password" placeholder="请再次输入密码" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon layui-icon-cellphone">&nbsp;</i>电话号码</label>
                        <div class="layui-input-block">
                            <input type="text" name="telephone" lay-verify="required|telephone" placeholder="请输入手机号码" class="layui-input"></div>
                    </div>
                </form>
            </div>
        </div>
	</div>
</body>
</html>