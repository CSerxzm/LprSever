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

    <script type="text/html" id="barDemo">
     <a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
     <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
	</script>
	<script type="text/html" id="barHeadDemo">
     <a class="layui-btn layui-btn-primary layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-add-1"></i>添加公告</a>
	</script>
	
<div  style="max-width:1350px;margin:0 auto;">
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
	<legend>搜索</legend>
	</fieldset>  
	<div class="layui-card">
		<div class="layui-card-body">
			<form class="layui-form layui-form-pane layui-inline" lay-filter="example">
				<div class="layui-inline" style="width: auto;">
						<label class="layui-form-label">关键字</label>
					    <div class="layui-input-block">
					      <input type="text" name="dormitory" required  lay-verify="required" placeholder="请输入任意关键字" autocomplete="off" class="layui-input">
					    </div>
				</div>
				<div class="layui-inline">
					<button class="layui-btn layui-btn-fluid" lay-submit lay-filter="demo1"><i class="layui-icon layui-icon-search"></i>搜索信息</button>
				</div>		
			</form>			
		</div>
	</div>
	<div class="layui-card">
    <table class="layui-hide" id="newsTable" lay-filter="newsTable"></table>
	<script>
	//创建一个表格
	layui.use(['layer', 'form', 'table', 'element'], function(){
	  var layer = layui.layer
	  ,form = layui.form
	  ,table = layui.table //表格
	  ,element = layui.element; //元素操作

	  
	  form.on('submit(demo1)', function(data){
	    		table.reload('newsTable', {
	    			url:'/LprSever/notice/getNotice'
	      			,where: data.field
				  });
	    	    return false;  //不跳转
	  });
	  
	  table.render({
		    elem: '#newsTable'
		    ,cellMinWidth: 120
		    ,url:'/LprSever/notice/getNotice'
		    ,toolbar: '#barHeadDemo'
		    ,title: '公告表'
		    ,cols: [[
		      {field:'id', title:'标识', fixed: 'left', sort: true}
		      ,{field:'title', title:'标题'}
		      ,{field:'content', title:'内容'}
		      ,{field:'create_date', title:'发表时间'}
		      ,{field:'name_publish', title:'发表者'}
		      ,{fixed: 'right', align:'center', width:200, toolbar: '#barDemo'}
		    ]]
			,even:true
		  	,limit: 10
		  	,limits: [10,15,30,60,100]
		    ,page: true
		    ,response: {
		      statusCode: 200
		    }
	  });
	  	  
	  //头工具栏事件
	  table.on('toolbar(newsTable)', function(obj){
	    var checkStatus = table.checkStatus(obj.config.id);
	    switch(obj.event){
	      case 'add':
	    	  window.location.href="noticeadd";
	      break;
	    };
	  }); 
	  
	  //监听行工具事件
	  table.on('tool(newsTable)', function(obj){
	    var data = obj.data
	    ,layEvent = obj.event
	    ,$ = layui.jquery;
	  	if(layEvent === 'del'){
	  		layer.confirm('真的删除记录：'+data.id+'?', function(index){
		  	  	$.ajax({
		  	  		url: '/LprSever/notice/removeNotice?id='+data.id,
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
	  		
	  	}
	  }); 
	  
	});
	</script>
	</div>
</div>
</body>
</html>