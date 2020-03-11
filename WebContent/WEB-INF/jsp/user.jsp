<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="layui/css/layui.css" media="all">
<script src="layui/layui.js"></script>
</head>
<body>
<div  style="max-width:1350px;margin:0 auto;">

	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>修改</a>
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
	</script>
	<script type="text/html" id="barHeadDemo">
		<a class="layui-btn layui-btn-primary layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-add-1"></i>添加用户</a>
	</script>
	
	<div class="layui-card">
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

	layui.use(['layer', 'form', 'table','laydate','element'], function(){
	  var layer = layui.layer
	  ,form = layui.form
	  ,table = layui.table
	  ,element = layui.element;
	  
	  form.on('submit(demo1)', function(data){
	    		table.reload('userTable', {
	    			url:'/LprSever/user/getUser'
	      			,where: data.field
				  });
	    	    return false; 
	  });
	  
	  table.render({
		    elem: '#userTable'
		    ,cellMinWidth: 80
		    ,url:'/LprSever/user/getUser'
			,toolbar: '#barHeadDemo'
		    ,title: '用户表'
		    ,cols: [[
		      {field:'loginname', title:'登录名', fixed: 'left', sort: true}
		      ,{field:'password', title:'密码'}
		      ,{field:'parkspace_id', title:'停车位'}
		      ,{field:'licenseplate', title:'车辆牌照'}
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
	  
	  //头工具栏事件
	  table.on('toolbar(userTable)', function(obj){
		    var data = obj.data
		    ,layEvent = obj.event
		    ,$ = layui.jquery;
		    
		    switch(layEvent){
		      case 'add':
		    	  
					$("#addUserForm")[0].reset();
					form.render(null, 'addUserForm');
			    	layer.open({
			    		  title: '添加用户'
				    	  ,btn: ['添加','取消']
					      ,success: function (layero, index) {
				                layero.addClass('layui-form');
				                layero.find('.layui-layer-btn0').attr('lay-filter', 'formContent').attr('lay-submit', '');
				                form.render(); 
				          }
				    	  ,yes: function(index, layero){
				    		  //监听提交按钮
				    		  form.on('submit(formContent)', function (data) {
				    			  
					    		  var formObject = {};
					    		  var formArray =$('#layui-layer'+index).find('form').serializeArray();
					    		  $.each(formArray,function(i,item){
					    			  formObject[item.name] = item.value;
					    			  });

					    		  $.ajax({
						              url: '/LprSever/user/addUser',
						              type: 'POST',
						              data: formObject,
						              async: false,
						              dataType: 'json',
						              success: function (data) {
					                      layer.msg("添加成功", {time:3000});
						    			  table.reload('userTable', {
						    			  });
								    	  layer.closeAll();
						              },
						              error: function () {
					                      layer.msg("添加失败", {time:3000});
						              }
						          });
			                  });
				    	  }
			    		  ,type: 1
			    		  ,area: ['550px', '450px']
			    		  ,content: $('#noDisplayFormAdd')  
			    		}); 	    	
		      break;
		    };
	  });	  
	  
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
			
			//表单初始赋值
			form.val('addUserForm', {
			  "loginname": data.loginname
			  ,"password": data.password
			  ,"parkspace_id": data.parkspace_id
			  ,"licenseplate": data.licenseplate
			  ,"telephone": data.telephone
			  ,"createdate": data.createdate
			  ,"authority": data.authority
			});
			
	    	layer.open({
	    		  title: '编辑：'+ data.loginname
		    	  ,btn: ['更改','取消']
			      ,success: function (layero, index) {
		                layero.addClass('layui-form');
		                layero.find('.layui-layer-btn0').attr('lay-filter', 'formContent').attr('lay-submit', '');
		                form.render(); 
		          }
		    	  ,yes: function(index, layero){
		    		  //监听提交按钮
		    		  form.on('submit(formContent)', function (data) {
		    			  
			    		  var formObject = {};
			    		  var formArray =$('#layui-layer'+index).find('form').serializeArray();
			    		  $.each(formArray,function(i,item){
			    			  formObject[item.name] = item.value;
			    			  });

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
	    		  ,type: 1
	    		  ,area: ['550px', '450px']
	    		  ,content: $('#noDisplayFormAdd')  
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
                            <input type="text" name="loginname" lay-verify="required|username" placeholder="请输入登录名" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon layui-icon-password">&nbsp;</i>密&nbsp;&nbsp;码</label>
                        <div class="layui-input-block">
                            <input type="password" name="password" lay-verify="required|password" placeholder="请输入密码" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">电话号码</label>
                        <div class="layui-input-block">
                            <input type="text" name="telephone"  required placeholder="请输入电话号码"  class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">注册时间</label>
                        <div class="layui-input-block">
                            <input type="text" name="createdate" id="createdate" placeholder="请输入注册时间" class="layui-input"></div>
                    </div>
					<div class="layui-form-item"  pane="">
						<label class="layui-form-label">权限</label>
					    <div class="layui-input-block">
					      <input type="radio" name="authority" value="系统管理员" title="系统管理员" checked>
	     			 	  <input type="radio" name="authority" value="停车场管理员" title="停车场管理员">
	     			 	  <input type="radio" name="authority" value="用户" title="用户">
					    </div>
					</div>
					<div class="layui-form-item">
                        <label class="layui-form-label">停车位</label>
                        <div class="layui-input-block">
                            <input type="text" name="parkspace_id" placeholder="请输入停车位" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">车辆牌照</label>
                        <div class="layui-input-block">
                            <input type="text" name="licenseplate" placeholder="请输入车牌照" class="layui-input"></div>
                    </div>
                </form>
            </div>
        </div>
	</div>
	
</body>
</html>