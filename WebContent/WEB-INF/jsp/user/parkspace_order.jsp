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
	{{# if(d.state=="否" ){ }}
		<a class="layui-btn layui-btn-xs" lay-event="order"><i class="layui-icon layui-icon-add-1"></i>预约</a>
	{{# } if(d.state=="是" ){ }}
		<a class="layui-btn layui-btn-xs layui-btn-disabled" lay-event="order"><i class="layui-icon layui-icon-add-1"></i>已被占用</a>
	{{# } }}
	</script>
	
	<div class="layui-card">
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
	<legend>停车位界面</legend>
	</fieldset>
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
	    			url:'/LprSever/parkspace/getParkSpace?operate=order'
	      			,where: data.field
				  });
	    	    return false;  //不跳转
	  });
	  
	  table.render({
		    elem: '#parkspaceTable'
		    ,cellMinWidth: 80
		    ,url:'/LprSever/parkspace/getParkSpace?operate=order'
		    ,toolbar: '#barHeadDemo'
		    ,title: '车位表'
		    ,cols: [[
		      {field:'id', title:'标识', fixed: 'left', sort: true}
		      ,{field:'name', title:'车位名称'}
		      ,{field:'idle', title:'是否被租赁'}
		      ,{field:'rentornot', title:'用户是否外租'}
		      ,{field:'state', title:'车位状态',sort: true}
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
	  table.on('tool(parkspaceTable)', function(obj){
	    var data = obj.data
	    ,layEvent = obj.event
	    ,$ = layui.jquery;
	    
	  	if(layEvent === 'order'){
	  		layer.confirm('真的预定停车位：'+data.id+'?停车费用从此刻开始算起。', function(index){
		  	  	$.ajax({
		  	  		url: '/LprSever/parkspace/orderParkSpace?id='+data.id,
		  		    type: 'GET',
		  		    async: false,
		  		    dataType: 'json',
		  		    success: function (data) {
			  	        layer.msg("预定成功", {time:3000});
		    			table.reload('parkspaceTable', {
		    			});
		  		    },
		  		    error: function () {
		  		    	layer.msg("预定失败", {time:3000});
		  		    }
		  		 });
	  	     });
	  	}
	  	
	  });
	});
	</script>
	</div>
</div>
</body>
</html>