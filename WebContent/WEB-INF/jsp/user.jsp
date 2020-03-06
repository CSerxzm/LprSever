<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="layui/css/layui.css" media="all">
 <script src="layui/layui.js"></script>
</head>
<%@include file="./taglib.jsp" %>
<body>
<div  style="max-width:1350px;margin:0 auto;">
	<div class="layui-card">
	<script type="text/html" id="barDemo">
	 <a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>修改</a>
     <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
	</script>
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
	<legend>搜索</legend>
	</fieldset>  
	<div class="layui-card">
		<div class="layui-card-body">
			<form class="layui-form layui-form-pane" lay-filter="example">
				<div class="layui-inline" style="width: auto;">
						<label class="layui-form-label">关键字</label>
					    <div class="layui-input-block">
					      <input type="text" name="dormitory" required  lay-verify="required" placeholder="请输入关键字" autocomplete="off" class="layui-input">
					    </div>
				</div>
				<div class="layui-inline">
					      <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="demo1"><i class="layui-icon layui-icon-search"></i>搜索信息</button>
				</div>
				<div class="layui-inline">
					      <div class="layui-form-mid layui-word-aux">&nbsp;&nbsp;关键字可以是任何关键字</div>
				</div>
			</form>
		</div>
	</div>
    <table class="layui-hide" id="userTable" lay-filter="userTable"></table>
	<script>
	//创建一个表格
	layui.use(['layer', 'form', 'table','laydate','element'], function(){
	  var layer = layui.layer
	  ,form = layui.form
	  ,table = layui.table //表格
	  ,laydate = layui.laydate
	  ,element = layui.element; //元素操作
	  
	  form.on('submit(demo1)', function(data){
	    		table.reload('userTable', {
	    			url:'/LprSever/user/getUser'
	      			,where: data.field
				  });
	    	    return false;  //不跳转
	  });
	  
	  table.render({
		    elem: '#userTable'
		    ,cellMinWidth: 80
		    ,url:'/LprSever/user/getUser'
		    ,toolbar: "true"
		    ,title: '用户表'
		    ,cols: [[
		      {field:'loginname', title:'登录名', fixed: 'left', sort: true}
		      ,{field:'password', title:'密码'}
		      ,{field:'username', title:'用户名'}
		      ,{field:'telephone', title:'电话号码'}
		      ,{field:'createdate', title:'注册时间'}
		      ,{field:'authority', title:'权限'}
		      ,{fixed: 'right', align:'center', width:200, toolbar: '#barDemo'}
		    ]]
		  	,limit: 10
		  	,limits: [10,15,30,60,100]
		    ,page: true
		    ,response: {
		      statusCode: 200
		    }
	  });
	  
	  //监听行工具事件
	  table.on('tool(userTable)', function(obj){
	    var data = obj.data
	    ,layEvent = obj.event
	    ,$ = layui.jquery;
	  	if(layEvent === 'del'){
	  		layer.confirm('真的删除用户：'+data.loginname+'?', function(index){
		  	  	$.ajax({
		  	  		url: '/LprSever/user/removeUser?loginname='+data.loginname,
		  		    type: 'GET',
		  		    async: false,
		  		    dataType: 'json',
		  		    success: function (data) {
			  		    obj.del(); //删除对应行（tr）的DOM结构
			  		    layer.close(index);
			  	        layer.msg("删除成功", {time:3000});
		  		    },
		  		    error: function () {
		  		    	layer.msg("删除失败", {time:3000});
		  		    }
		  		 });
	  	     });
	  	}
	  	else if(layEvent === 'edit'){
	  		
			$("#addUserForm")[0].reset();
			form.render(null, 'addUserForm');
			
			//执行一个laydate实例
			laydate.render({
				elem: '#createdate' //指定元素
				,type: 'datetime'
			});
			  
			//表单初始赋值
			form.val('addUserForm', {
			  "loginname": data.loginname
			  ,"password": data.password
			  ,"username": data.username
			  ,"telephone": data.telephone
			  ,"createdate": data.createdate
			  ,"authority": data.authority
			});
			
	    	layer.open({
	    		  title: '编辑：'+ data.loginname
		    	  ,btn: ['更改','取消']
			      ,success: function (layero, index) {
			    	    //添加form标识
		                layero.addClass('layui-form');
		                //将按钮重置为可提交按钮以触发表单验证
		                layero.find('.layui-layer-btn0').attr('lay-filter', 'formContent').attr('lay-submit', '');
		                form.render(); 
		          }
		    	  ,yes: function(index, layero){
		    		  //监听提交按钮
		    		  form.on('submit(formContent)', function (data) {
			    		  //转为json字符串
			    		  var formObject = {};
			    		  var formArray =$('#layui-layer'+index).find('form').serializeArray();
			    		  $.each(formArray,function(i,item){
			    			  formObject[item.name] = item.value;
			    			  });
			    		  console.log(formObject);
			    		  //请求服务器
			    		  $.ajax({
				              url: '/LprSever/user/updateUser',
				              type: 'POST',
				              data: formObject,
				              async: false,
				              dataType: 'json',
				              success: function (data) {
			                      layer.msg("修改成功", {time:3000});
				    			  table.reload('userTable', {
				    			  });
						    	  layer.closeAll();
				              },
				              error: function () {
			                      layer.msg("修改失败", {time:3000});
				              }
				          });
	                  });
		    	  }
		    	  ,btn2: function(index, layero){
		    		
		    	  }
	    		  ,type: 1
	    		  ,area: ['400px', '455px']
	    		  ,content: $('#noDisplayFormAdd')
	    		  ,end: function(index, layero){ 
	    				// 清空表单
	    				$("#addStuForm")[0].reset();
	    				form.render(null, 'addStuForm');
	    			}   
	    		}); 	    	
	  	}
	  });
	});
	</script>
	</div>
</div>
	<!-- 以下是隐藏的表单容器 -->
	<div id="noDisplayFormAdd" style="display:none;">
		 <div class="layui-card">
            <div class="layui-card-body">
                <form class="layui-form layui-form-pane" lay-filter="addUserForm" id="addUserForm">
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon layui-icon-username">&nbsp;</i>登录名</label>
                        <div class="layui-input-block">
                            <input type="text" name="loginname" required lay-verify="required|username" placeholder="请输入登录名" autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon layui-icon-password">&nbsp;</i>密&nbsp;&nbsp;码</label>
                        <div class="layui-input-block">
                            <input type="password" name="password" required lay-verify="required|password" placeholder="请输入密码" autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon">&nbsp;</i>名字</label>
                        <div class="layui-input-block">
                            <input type="text" name="username" placeholder="请输入名字" autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon">&nbsp;</i>电话号码</label>
                        <div class="layui-input-block">
                            <input type="text" name="telephone" placeholder="请输入电话号码" autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon">&nbsp;</i>注册时间</label>
                        <div class="layui-input-block">
                            <input type="text" name="createdate" id="createdate" placeholder="请输入注册时间" autocomplete="off" class="layui-input"></div>
                    </div>
					<div class="layui-form-item"  pane="">
						<label class="layui-form-label">权限</label>
					    <div class="layui-input-block">
					      <input type="radio" name="authority" value="管理员" title="管理员" checked>
	     			 	  <input type="radio" name="authority" value="用户" title="用户">
					    </div>
					</div>
                </form>
            </div>
        </div>
	</div>
</body>
</html>