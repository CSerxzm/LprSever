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
    <table class="layui-hide" id="trarecordTable" lay-filter="trarecordTable"></table>
	<script>
	//创建一个表格
	layui.use(['layer', 'form', 'table', 'element'], function(){
	  var layer = layui.layer
	  ,form = layui.form
	  ,table = layui.table //表格
	  ,element = layui.element; //元素操作
	  
	  form.on('submit(demo1)', function(data){
	    		table.reload('trarecordTable', {
	    			url:'/LprSever/trarecord/getTraRecord'
	      			,where: data.field
				  });
	    	    return false;  //不跳转
	  });
	  
	  table.render({
		    elem: '#trarecordTable'
		    ,cellMinWidth: 120
		    ,url:'/LprSever/trarecord/getTraRecord'
		    ,title: '通行日志表'
		    ,toolbar: true
		    ,cols: [[
		      {field:'id', title:'标识', fixed: 'left', sort: true}
		      ,{field:'space_id', title:'停车位'}
		      ,{field:'licenseplate', title:'牌照'}
		      ,{field:'date_in', title:'驶入时间'}
		      ,{field:'date_out', title:'驶出时间'}
		      ,{field:'cost', title:'费用'}
		    ]]
		  	,limit: 10
		  	,limits: [10,15,30,60,100]
		    ,page: true
		    ,response: {
		      statusCode: 200
		    }
	  });
	  
	});
	</script>
	</div>
</div>
</body>
</html>