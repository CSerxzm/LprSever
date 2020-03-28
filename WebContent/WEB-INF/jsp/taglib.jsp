<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
 function addMonth(nowdate,monthnum){
	 
	     var seperator1 = "-";
	     var seperator2 = ":";
	     
		 var sDate = new Date(nowdate);
		 var num = parseInt(monthnum);
		 
		 var sYear = sDate.getFullYear();
	     var sMonth = sDate.getMonth() + 1;
	     var sDay = sDate.getDate();
	     var sHour = sDate.getHours();
	     var sMinute = sDate.getMinutes();
	     var sSecond = sDate.getSeconds();
	 
	     var eYear = sYear;
	     var eMonth = sMonth + num;
	     var eDay = sDay;
	     while (eMonth > 12) {
	    	 eYear++;
	    	 eMonth -= 12;
	     }
	     var eDate = new Date(eYear, eMonth - 1, eDay);
	      
	     while (eDate.getMonth() != eMonth - 1){
	    	 eDay--;
	    	 eDate = new Date(eYear, eMonth - 1, eDay);
	     }
	     var month=eDate.getMonth()+1;
		 if (month >= 1 && month <= 9) {
			 month = "0" + month;
		 }
		 var futuredate = eDate.getFullYear() + seperator1 + month + seperator1 + eDate.getDate()
		 	+ " " + sHour + seperator2 + sMinute
            + seperator2 + sSecond;
	     return futuredate;
 }
 </script>
