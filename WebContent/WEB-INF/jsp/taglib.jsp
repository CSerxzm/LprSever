<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 设置一个项目路径的变量  -->
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!-- 配置分页标签   -->
<%@ taglib prefix="fkjava" uri="/pager-tags" %>
<script>
 //获取当前时间 格式yyyy-MM-dd HH:mm:ss
 function getNowFormatDate() {
	    var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var seconds = date.getSeconds();
	    if (seconds >= 0 && seconds <= 9) {
	    	seconds = "0" + seconds;
	    }
	    var minutes = date.getMinutes();
	    if (minutes >= 0 && minutes <= 9) {
	    	minutes = "0" + minutes;
	    }
	    var hours = date.getHours();
	    if (hours >= 0 && hours <= 9) {
	    	hours = "0" + hours;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	            + " " + hours + seperator2 + minutes
	            + seperator2 + seconds;
	    return currentdate;
	}
 </script>

