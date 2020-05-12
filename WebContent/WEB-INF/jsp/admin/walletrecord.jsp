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
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
	</script>
	
	<div class="layui-card">	
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 15px;">
	<legend>搜索</legend>
	</fieldset>  
	<div class="layui-card">
		<div class="layui-card-body">
			<form class="layui-form layui-form-pane" lay-filter="example">
				<div class="layui-inline" style="width: auto;">
						<label class="layui-form-label">关键字</label>
					    <div class="layui-input-block">
					      <input type="text" name="keyword" required  lay-verify="required" placeholder="请输入关键字" autocomplete="off" class="layui-input">
					    </div>
				</div>
				<div class="layui-inline">
					      <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="demo1"><i class="layui-icon layui-icon-search"></i>搜索信息</button>
				</div>
				<div class="layui-inline">
					      <div class="layui-form-mid layui-word-aux">&nbsp;&nbsp;关键字可以是缴费用户、详细操作</div>
				</div>
			</form>
		</div>
	</div>
    <table class="layui-hide" id="walletrecordTable" lay-filter="walletrecordTable"></table>
	<script>
	//创建一个表格
	layui.use(['layer', 'form', 'table', 'element'], function(){
	  var layer = layui.layer
	  ,form = layui.form
	  ,table = layui.table //表格
	  ,element = layui.element; //元素操作
	  
	  form.on('submit(demo1)', function(data){
	    		table.reload('walletrecordTable', {
	    			url:'/LprSever/walletrecord/getWalletRecord'
	      			,where: data.field
				  });
	    	    return false;  //不跳转
	  });
	  
	  table.render({
		    elem: '#walletrecordTable'
		    ,cellMinWidth: 120
		    ,url:'/LprSever/walletrecord/getWalletRecord'
			,toolbar: true
		    ,title: '费用日志表'
		    ,cols: [[
		      {field:'id', title:'标识', fixed: 'left', sort: true}
		      ,{field:'date_pay', title:'支付时间'}
		      ,{field:'name', title:'缴费用户'}
		      ,{field:'operation', title:'详细操作'}
		      ,{field:'cost', title:'费用'}
		      ,{fixed: 'right', align:'center', width:200, toolbar: '#barDemo'}
		    ]]
		  	,limit: 10
		  	,limits: [10,15,30,60,100]
		    ,page: true
		    ,response: {
		      statusCode: 200
		    }
	  });
 
	  table.on('tool(walletrecordTable)', function(obj){
	    var data = obj.data
	    ,layEvent = obj.event
	    ,$ = layui.jquery;
	  	if(layEvent === 'del'){
	  		layer.confirm('真的删除费用记录：'+data.id+'?', function(index){
		  	  	$.ajax({
		  	  		url: '/LprSever/walletrecord/removeWalletRecord?id='+data.id,
		  		    type: 'GET',
		  		    async: false,
		  		    dataType: 'json',
		  		    success: function (data) {
			  		    obj.del(); //删除对应行（tr）的DOM结构
			  		    layer.close(index);
			  	        layer.msg(data.msg, {time:3000});
		  		    },
		  		    error: function () {
		  		    	layer.msg("服务器错误", {time:3000});
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