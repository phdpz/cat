<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'showOneOrder.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
  <script>
  function sureAndDel(orderNum)
	{
	    var xmlhttp;
		if (window.XMLHttpRequest)
		  {// code for IE7+, Firefox, Chrome, Opera, Safari
		  xmlhttp=new XMLHttpRequest();
		  }
		else
		  {// code for IE6, IE5
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
			  document.getElementById("btn").innerHTML="";
			  document.getElementById("status").innerHTML="已提货";
			  document.getElementById("result").innerHTML="提交成功！请以此凭证消息跟我们的工作人员领水果吧！欢迎您下次的光临！";
		    }
		  }
		xmlhttp.open("GET","<%=request.getContextPath()%>/admin/sureOrder/"+orderNum,true);
		xmlhttp.send();	
	  }
  
  function checkCode()
  {
	  var code=document.getElementById("code1").value;
	  var urlStr = "<%=request.getContextPath()%>/admin/code/"+code;
		$.ajax({
			method: "GET",
			url: urlStr,
			success:function(data,status,jqXHR){
				var order=data;
				if(order=="")
					document.getElementById("warn").innerHTML="验证码输入有误！";
				else
				{
					document.getElementById("order").style.display="block";
					document.getElementById("code").style.display="none";
					if(order.status=='已提货')
					  document.getElementById("yes").style.display="block";
					else
					 {
					   document.getElementById("yes").style.display="none";
					   document.getElementById("no").style.display="block";
					   document.getElementById("status").innerHTML=order.status;
					   document.getElementById("totalFee").innerHTML=order.totalFee;
					   document.getElementById("btn").innerHTML="<a href='javascript:sureAndDel(&quot;"+order.orderNum+"&quot;);'>确认提货</a>";
					 }
				}
			  }
		}); // end ajax
  }
  </script>
  </head>
  
  <body>
  <div id="order" style="display:none;">
  
    <div id="no" style="display:none;">
		         亲！您的订单信息如下：<br> 
		         订单状态：<span id="status"></span><br>
		         金额： <span id="totalFee"></span><br>
		   <span id="btn"></span><br>
	       <span id="result" style="color:red"></span>
    </div>
    
    <div id="yes" style="display:none;">
	                  亲！小猫并没有查到您未提货的订单信息呦！
    </div>
   </div>
    
    <div id="code">
                  请输入你的提货验证码：<input type="text" name="code1" id="code1"/>
                     <input type="button" value="确定" onClick="checkCode()"/>
                     <span id="warn" style="color:red"></span>
    </div>
    
  </body>
</html>
