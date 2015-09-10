<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'home.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery-1.11.3.min.js"></script>
  </head>
  <script type="text/javascript">
  	function checkU(obj){
  		
  		var urlStr = "<%=request.getContextPath()%>/admin/check/"+obj;
		//alert("Before Call:"+urlStr);
		$.ajax({
			method: "GET",
			url: urlStr,
			success:function(data,status,jqXHR){
				//alert("Success:"+data);
				var result = data;
			
				$("#tip").html(result);
			}
		}); // end ajax
  	}
  	
  </script>
  
  <body>
  	<div align="center">欢迎登录,${sessionScope.admin.AUserName}</div>
		<a href="<%=request.getContextPath()%>/fruit/manageFruit">返回首页</a>
   	  <table>
   	  <tr><th>订单号</th>
   	  	  <th>金额</th>
   	  	  <th>下单时间</th>
   	  	  <th>状态</th>
   	  	  <th>验证码</th>
   	  	  <th>用户名</th>
   	  	  <th>学校</th>
   	  	  <th>区域</th>
   	  	  <th>电话</th>
   	  	  </tr>
   	  <c:forEach items="${orderList}" var="order">
   	  	<tr><td>${order.orderNum }</td>
	   	  	<td>${order.totalFee}</td>
	   	  	<td>${order.orderTime}</td>
	   	  	<td>${order.status}</td>
	   	  	<td>${order.code}</td>
	   	  	<td>${order.clientName}</td>
	   	  	<td>${order.school}</td>
	   	  	<td>${order.phone}</td>
   	  	</tr>
   	  </c:forEach>
  	  </table>
  	  
  </body>
</html>
