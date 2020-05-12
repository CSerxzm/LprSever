	//加载高德地图
	window.onload = function(){
		var map = new  AMap.Map('map',{
			resizeEnable: true,
			center: [117.20418,31.77143],
			zoom: 15
		});
		// 创建一个 Marker 实例：（标记点）        
		var marker = new AMap.Marker({
			position: new AMap.LngLat(117.20418,31.77143),
			title: "位置信息"
		});
		// 将创建的点标记添加到已有的地图实例：       
		map.add(marker);
	}

	layui.use(['layer','form','carousel','element'], function(){
	  var form = layui.form;
	  var element = layui.element;
	  var layer = layui.layer;
	  var $ = layui.jquery;
	  var carousel = layui.carousel;
	  var verifyCode;
	  
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
		            	verifyCode = new GVerify("v_container");
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
	    		  ,area: ['400px', '350px']
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
				  $("#notice").append('<div class="layui-card"><a id='+noticelist[i].id+' href="#"><h3>&nbsp;&nbsp;'+noticelist[i].create_date+'</h3>'
				  +'<div class="layui-card-body">'+noticelist[i].title+'</div></a></div>');
			  }
			  
		  }
		});
	  
	  $('a').click(function(id){
		  $.ajax({
			  url: '/LprSever/index/getindex_noticeshow?id='+$(this).attr("id"),
			  type: 'GET',
			  async: false,
			  dataType: 'json',
			  success: function (data) {
			      layer.open({
			          type: 1
			          ,title: '公告'
			          ,offset: 'auto'
			          ,id:data.title //防止重复弹出
			          ,content:shownotice(data)
			          ,btn: '我知道了'
			          ,btnAlign: 'c' //按钮居中
			          ,area: ['500px', '300px']
			          ,yes: function(){
			            layer.closeAll();
			          }
			        });
			 }
		  });	 
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
		  ,repassword: function(value) {
			  if (value === "")
				  return "请输入二次密码！";
			  var pwd = $('#password').val();
			  console.log(pwd);
			  console.log(value);
			  if (pwd !== value)
				  return "二次输入的密码不一致！";
		  }
		  ,telephone: [
			  /^1\d{10}$/
			    ,'请输入正确的手机号码'
			  ] 
		  ,code:function(value) {
			  var res = verifyCode.validate(value);
	            if(!res){
	                return "图片验证码错误！";
	            }
		  }
		});		
	 });
	
	//对公告进行格式控制
	function shownotice(data){
		return  '<div style="padding:10px;margin:0 auto;"><div style="text-align:center;"><font style="font-size:20px;">'+data.title
		+'</font></div><div style="text-align:center;">发布者：'+data.name_publish+'&nbsp;&nbsp;发布时间：'+data.create_date+'</div></hr><div>'
		+data.content+'</div></div>'
	}

