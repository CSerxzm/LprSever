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
		<a class="layui-btn layui-btn-primary layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-add-1"></i>添加通行记录</a>
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
			,toolbar: '#barHeadDemo'
		    ,title: '通行日志表'
		    ,cols: [[
		      {field:'id', title:'标识', fixed: 'left', sort: true}
		      ,{field:'space_id', title:'停车位'}
		      ,{field:'licenseplate', title:'牌照'}
		      ,{field:'date_in', title:'驶入时间'}
		      ,{field:'date_out', title:'驶出时间'}
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

	  table.on('toolbar(trarecordTable)', function(obj){
		    var data = obj.data
		    ,layEvent = obj.event
		    ,$ = layui.jquery;
		    
		    switch(layEvent){
		      case 'add':
					$("#trarecordForm")[0].reset();
					form.render(null, 'trarecordForm');
			    	layer.open({
			    		  title: '添加通行记录'
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
						              url: '/LprSever/trarecord/addTraRecord',
						              type: 'POST',
						              data: formObject,
						              async: false,
						              dataType: 'json',
						              success: function (data) {
					                      layer.msg("添加成功", {time:3000});
						    			  table.reload('trarecordTable', {
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

	  table.on('tool(trarecordTable)', function(obj){
	    var data = obj.data
	    ,layEvent = obj.event
	    ,$ = layui.jquery;
	  	if(layEvent === 'del'){
	  		layer.confirm('真的删除记录：'+data.id+'?', function(index){
		  	  	$.ajax({
		  	  		url: '/LprSever/trarecord/removeTraRecord?id='+data.id,
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
			$("#trarecordForm")[0].reset();
			form.render(null, 'trarecordForm');
			
			form.val('trarecordForm', {
				"id":data.id
				,"space_id": data.space_id
				,"licenseplate": data.licenseplate
				,"date_in": data.date_in
				,"date_out": data.date_out
				,"cost": data.cost
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
				              url: '/LprSever/trarecord/updateTraRecord',
				              type: 'POST',
				              data: formObject,
				              async: false,
				              dataType: 'json',
				              success: function (data) {
			                      layer.msg("修改成功", {time:3000});
				    			  table.reload('trarecordTable', {
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
	    		  ,area: ['400px', '385px']
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
                <form class="layui-form layui-form-pane" lay-filter="trarecordForm" id="trarecordForm">
                    <input type="hidden" name="id" class="layui-input">
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon">&nbsp;</i>停车位</label>
                        <div class="layui-input-block">
                            <input type="text" name="space_id" placeholder="请输入space_id" autocomplete="off" class="layui-input"></div>
                    </div>                    
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon">&nbsp;</i>牌照</label>
                        <div class="layui-input-block">
                            <input type="text" name="licenseplate" placeholder="请输入牌照" autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon">&nbsp;</i>驶入时间</label>
                        <div class="layui-input-block">
                            <input type="text" name="date_in" placeholder="请输入驶入时间" autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon">&nbsp;</i>驶出时间</label>
                        <div class="layui-input-block">
                            <input type="text" name="date_out" placeholder="请输入驶出时间" autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><i class="layui-icon">&nbsp;</i>费用</label>
                        <div class="layui-input-block">
                            <input type="text" name="cost" placeholder="请输入费用" autocomplete="off" class="layui-input"></div>
                    </div>                    
                </form>
            </div>
        </div>
	</div>
</body>
</html>