<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="/LprSever/layui/css/layui.css" media="all">
 <script src="/LprSever/layui/layui.js"></script>
</head>
<body>
<div  style="max-width:1350px;margin:0 auto;">

	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>修改</a>
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
	</script>
	<script type="text/html" id="barHeadDemo">
		<a class="layui-btn layui-btn-primary layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-add-1"></i>添加停车位</a>
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
    <table class="layui-hide" id="parkspaceTable" lay-filter="parkspaceTable"></table>
	<script>
	//创建一个表格
	layui.use(['layer', 'form', 'table', 'element'], function(){
	  var layer = layui.layer
	  ,form = layui.form
	  ,table = layui.table //表格
	  ,element = layui.element; //元素操作
	  
	  form.on('submit(demo1)', function(data){
	    		table.reload('parkspaceTable', {
	    			url:'/LprSever/parkspace/getParkSpace'
	      			,where: data.field
				  });
	    	    return false;  //不跳转
	  });
	  
	  table.render({
		    elem: '#parkspaceTable'
		    ,cellMinWidth: 80
		    ,url:'/LprSever/parkspace/getParkSpace'
		    ,toolbar: '#barHeadDemo'
		    ,title: '车位表'
		    ,cols: [[
		      {field:'id', title:'标识', fixed: 'left', sort: true}
		      ,{field:'name', title:'车位名称'}
		      ,{field:'state', title:'状态'}
		      ,{field:'idle', title:'空闲'}
		      ,{field:'hire_start_date', title:'租赁开始时间'}
		      ,{field:'hire_stop_date', title:'租赁结束时间'}
		      ,{field:'rentornot', title:'外租',sort: true}
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
	  table.on('toolbar(parkspaceTable)', function(obj){
		    var data = obj.data
		    ,layEvent = obj.event
		    ,$ = layui.jquery;
		    
		    switch(layEvent){
		      case 'add':
					$("#parkspaceForm")[0].reset();
					form.render(null, 'parkspaceForm');
			    	layer.open({
			    		  title: '添加停车位'
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
						              url: '/LprSever/parkspace/addParkSpace',
						              type: 'POST',
						              data: formObject,
						              async: false,
						              dataType: 'json',
						              success: function (data) {
					                      layer.msg("添加成功", {time:3000});
						    			  table.reload('parkspaceTable', {
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
	  
	  //监听行工具事件
	  table.on('tool(parkspaceTable)', function(obj){
	    var data = obj.data
	    ,layEvent = obj.event
	    ,$ = layui.jquery;
	    
	  	if(layEvent === 'del'){
	  		layer.confirm('真的删除停车位：'+data.id+'?', function(index){
		  	  	$.ajax({
		  	  		url: '/LprSever/parkspace/removeParkSpace?id='+data.id,
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
			$("#parkspaceForm")[0].reset();
			form.render(null, 'parkspaceForm');
			form.val('parkspaceForm', {
				"id":data.id
			  ,"name": data.name
			  ,"state":data.state
			  ,"idle": data.idle
			  ,"hire_start_date": data.hire_start_date
			  ,"hire_stop_date": data.hire_stop_date
			  ,"rentornot": data.rentornot			  
			});
			
	    	layer.open({
	    		  title: '编辑停车位：'+ data.id
		    	  ,btn: ['更改','取消']
			      ,success: function (layero, index) {
			    	    //添加form标识
		                layero.addClass('layui-form');
		                //将按钮重置为可提交按钮以触发表单验证
		                layero.find('.layui-layer-btn0').attr('lay-filter', 'formContent').attr('lay-submit', '');
		                form.render(); 
		          }
		    	  ,yes: function(index, layero){
		    		  form.on('submit(formContent)', function (data) {
			    		  var formObject = {};
			    		  var formArray =$('#layui-layer'+index).find('form').serializeArray();
			    		  $.each(formArray,function(i,item){
			    			  formObject[item.name] = item.value;
			    			  });
			    		  $.ajax({
				              url: '/LprSever/parkspace/updateParkSpace',
				              type: 'POST',
				              data: formObject,
				              async: false,
				              dataType: 'json',
				              success: function (data) {
			                      layer.msg("修改成功", {time:3000});
				    			  table.reload('parkspaceTable', {
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
	    		  ,area: ['400px', '350px']
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
                <form class="layui-form layui-form-pane" lay-filter="parkspaceForm" id="parkspaceForm">
                    <input type="hidden" name="id" placeholder="请输入车位名字" autocomplete="off" class="layui-input">
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon">&nbsp;</i>车位名称</label>
                        <div class="layui-input-block">
                            <input type="text" name="name" placeholder="请输入车位名字" autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                    	<label class="layui-form-label">当前是否有车</label>
                    	<div class="layui-input-block">
	                    	<input name="state" title="是" type="radio" value="是">
	                    	<input name="state" title="否" type="radio" value="否">
                    	</div>
                    </div>
                    <div class="layui-form-item">
                    	<label class="layui-form-label">是否空闲</label>
                    	<div class="layui-input-block">
	                    	<input name="idle" title="是" type="radio" value="是">
	                    	<input name="idle" title="否" type="radio" value="否">
                    	</div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon">&nbsp;</i>租赁开始</label>
                        <div class="layui-input-block">
                            <input type="text" name="hire_start_date" placeholder="请输入租赁开始时间" autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon">&nbsp;</i>租赁结束</label>
                        <div class="layui-input-block">
                            <input type="text" name="hire_stop_date" placeholder="请输入租赁结束时间" autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                    	<label class="layui-form-label">是否外借</label>
                    	<div class="layui-input-block">
	                    	<input name="rentornot" title="是" type="radio" value="是">
	                    	<input name="rentornot" title="否" type="radio" value="否">
                    	</div>
                    </div>                   
                </form>
            </div>
        </div>
	</div>
</body>
</html>