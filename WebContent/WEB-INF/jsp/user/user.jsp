<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="/LprSever/layui/css/layui.css" media="all">
<script src="/LprSever/layui/layui.js"></script>
</head>
<body>
<div  style="max-width:1350x;margin:0 auto;">
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
	<legend>个人信息</legend>
	</fieldset> 
	<div class="layui-card" style="max-width:800px;margin:0 auto;"> 
		<div class="layui-card-body">
			<form class="layui-form layui-form-pane" lay-filter="userForm" id="userForm">
                    <div class="layui-form-item">
	                    <label class="layui-form-label">登录名</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="loginname" class="layui-input" readonly></div>
	                    <div class="layui-form-mid layui-word-aux">登录名不可修改</div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">注册时间</label>
                        <div class="layui-input-inline">
                            <input type="text" name="createdate" class="layui-input" readonly></div>
                        <div class="layui-form-mid layui-word-aux">注册时间不可修改</div>                            
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">停车位id</label>
                        <div class="layui-input-inline">
                            <input type="text" name="parkspace_id" class="layui-input" readonly></div>
                        <div class="layui-form-mid layui-word-aux">停车位id不可修改</div>
                    </div> 
                    <div class="layui-form-item">
                        <label class="layui-form-label">权限</label>
                        <div class="layui-input-inline">
                            <input type="text" name="authority" class="layui-input" readonly></div>
                        <div class="layui-form-mid layui-word-aux">权限不可修改</div>                           
                    </div>                                       
                    <div class="layui-form-item">
	                    <label class="layui-form-label">登陆密码</label>
                        <div class="layui-input-inline">
                            <input type="password" name="password" class="layui-input"></div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">车牌号码</label>
                        <div class="layui-input-inline">
                            <input type="text" name="licenseplate" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">电话号码</label>
                        <div class="layui-input-inline">
                            <input type="text" name="telephone" class="layui-input"></div>               
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">余额</label>
                        <div class="layui-input-inline">
                            <input type="text" name="wallet" class="layui-input" readonly></div>
                        <div class="layui-input-inline">
                            <button class="layui-btn" lay-submit lay-filter="charge">充值</button></div>
                    </div> 
                    <div class="layui-form-item">
	                    <div class="layui-inline">
						      <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="submit">确认修改</button>
						</div>
                    </div>
             </form>
		</div>
	</div>
</div>
<script>
	layui.use(['layer','form','element'], function(){
	  var form = layui.form;
	  var element = layui.element;
	  var layer = layui.layer;
	  var $ = layui.jquery;
	  
	  var loginname;
	  function reload(){
		  $.ajax({
			  url: '/LprSever/user/getUserOne',
			  type: 'GET',
			  async: false,
			  dataType: 'json',
			  success: function (data) {
				  var datalist=data.data[0];
				  loginname=datalist.loginname;
				  form.val('userForm', {
					"loginname":datalist.loginname
					,"password": datalist.password
					,"parkspace_id":datalist.parkspace_id
					,"licenseplate":datalist.licenseplate
					,"telephone":datalist.telephone
					,"createdate": datalist.createdate
					,"authority":datalist.authority
					,"wallet":datalist.wallet
				});
			  },
			  error: function () {  
			  }
			}); 
	  }
	  reload();
		form.on('submit(submit)', function(data){
			$.ajax({
				url: '/LprSever/user/updateUser',
			    type: 'POST',
			    data:data.field,
			    async: false,
			    dataType: 'json',
			    success: function (data) {
				         layer.msg(data.msg, {time:3000});
			     },
			     error: function () {
			         layer.msg("服务器错误", {time:3000});
			     }
			 });
		  return false;
		});
		
		/* 充值金额 */
		form.on('submit(charge)', function(data){
			$("#chargeForm")[0].reset();
			form.render(null, 'chargeForm');
			
			form.val('chargeForm', {
				"loginname":loginname
			});
			
	    	layer.open({
	    		  title: '充值金额'
		    	  ,btn: ['充值','取消']
			      ,success: function (layero, index) {
		                layero.addClass('layui-form');
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
			    		  console.log(formObject);
			    		  $.ajax({
				              url: '/LprSever/walletrecord/charge',
				              type: 'POST',
				              data: formObject,
				              async: false,
				              dataType: 'json',
				              success: function (data) {
			                      layer.msg(data.msg, {time:3000});
			                      reload();
						    	  layer.closeAll();
				              },
				              error: function () {
			                      layer.msg("服务器错误", {time:3000});
				              }
				          });
	                  });
		    	  }
	    		  ,type: 1
	    		  ,area: ['400px', '180px']
	    		  ,content: $('#noDisplayFormAdd')  
	    		}); 
		  return false;
		});
		
	 });
</script>

	<!-- 以下是隐藏的表单容器 -->
	<div id="noDisplayFormAdd" style="display:none;">
		 <div class="layui-card">
            <div class="layui-card-body">
                <form class="layui-form layui-form-pane" lay-filter="chargeForm" id="chargeForm">
                    <input type="hidden" name="loginname" class="layui-input">
                    <div class="layui-form-item">
                        <label class="layui-form-label">充值金额</label>
                        <div class="layui-input-block">
                            <input type="number" name="money" placeholder="请输入充值金额"  class="layui-input"></div>
                    </div>              
                </form>
            </div>
        </div>
	</div>

</body>
</html>