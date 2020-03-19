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
	{{# if(d.idle=="是"){ }}
		<a class="layui-btn layui-btn-xs" lay-event="rent"><i class="layui-icon layui-icon-cart"></i>租赁</a>
	{{# } if(d.idle=="否"){ }}
		<a class="layui-btn layui-btn-xs layui-btn-disabled" lay-event="rent">已被租赁</a>
	{{# } }}
	</script>
	
	<div class="layui-card">
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
	<legend>停车位管理界面</legend>
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
	    			url:'/LprSever/parkspace/getParkSpace?operate=manage'
	      			,where: data.field
				  });
	    	    return false;  //不跳转
	  });
	  
	  table.render({
		    elem: '#parkspaceTable'
		    ,cellMaxWidth: 80
		    ,url:'/LprSever/parkspace/getParkSpace?operate=manage'
		    ,toolbar: '#barHeadDemo'
		    ,title: '车位表'
		    ,cols: [[
		      {field:'id', title:'标识', fixed: 'left',sort: true}
		      ,{field:'name', title:'车位名称'}
		      ,{field:'idle', title:'空闲',sort: true}
		      ,{field:'state', title:'车位状态'}
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
	    
		var activitycost_per;
		$.ajax({
			url: '/LprSever/index/getindex',
			type: 'GET',
			async: false,
			dataType: 'json',
			success: function (data) {
				var datalist=data.data[0];
				activitycost_per=datalist.activitycost_per;
			}
		});
	    
	  	if(layEvent === 'rent'){  		
			$("#parkspaceForm")[0].reset();
			form.render(null, 'parkspaceForm');
			form.val('parkspaceForm', {
				"id":data.id
			  ,"name": data.name
			  ,
			});
			
			console.log("activitycost_per="+activitycost_per);
			
	    	layer.open({
	    		  title: '租赁停车位：'+ data.id
		    	  ,btn: ['租赁','取消']
			      ,success: function (layero, index) {
			    	    //添加form标识
		                layero.addClass('layui-form');
		                //将按钮重置为可提交按钮以触发表单验证
		                layero.find('.layui-layer-btn0').attr('lay-filter', 'formContent').attr('lay-submit', '');
						form.render();
						
		    			//监听下拉框
		    			$("#rentcycle").change(function(){ 
		    				alert($(this).children('option:selected').val()); 
		    				var p_value=$(this).children('option:selected').val(); 
		    				var paynumber = p_value*activitycost_per;
		    				console.log("paynumber="+paynumber);
		    			});

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
			                      layer.msg("租赁成功", {time:3000});
				    			  table.reload('parkspaceTable', {
				    			  });
						    	  layer.closeAll();
				              },
				              error: function () {
			                      layer.msg("租赁失败", {time:3000});
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
                    <input type="hidden" name="id" class="layui-input">
                    <div class="layui-form-item">
                        <label class="layui-form-label">车位名称</label>
                        <div class="layui-input-block">
                            <input type="text" name="name" placeholder="请输入车位名字" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">租赁开始</label>
                        <div class="layui-input-block">
                            <input type="text" name="hire_start_date"class="layui-input"></div>
                    </div>                    
					<div class="layui-form-item">
						<label class="layui-form-label">租赁周期</label>
					    <div class="layui-input-block">
					      <select name="rentcycle" id="rentcycle">
					        <option value="1" selected>1个月</option>
					        <option value="3">3个月</option>
					        <option value="6">6个月</option>
					        <option value="12">1年</option>
					      </select>
					    </div>
					</div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">支付金额</label>
                        <div class="layui-input-block">
                            <input type="name" name="paynumber" class="layui-input"></div>
                    </div>                  
                </form>
            </div>
        </div>
	</div>
</body>
</html>