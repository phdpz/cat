<%@ page language="java" import="java.util.*,java.text.SimpleDateFormat" pageEncoding="utf-8"%>
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
  	function toExcel(obj){
  		var urlStr = "<%=request.getContextPath() %>/order/toExcel";
		//alert("Before Call:"+urlStr);
		$.ajax({
			method: "GET",
			url: urlStr,
			success:function(data,status,jqXHR){
				//alert("Success:"+data);
				var result = data;
				
				window.location.href="${pageContext.request.contextPath}/resources/upload/excel/"+result;
			}
		}); // end ajax
  	}
  	
  	function select(){
  		var school = document.getElementById("school").value;
  		var from = document.getElementById("from").value;
  		var to = document.getElementById("to").value;
  		if(from>to)
  			alert("开始时间必须大于等于结束时间");
  		else	
  		 	window.location.href="${pageContext.request.contextPath}/order/orderStatus?school="+school+"&from="+from+"&to="+to;
  	}
  </script>
  
  <body>
  <% Date d = new Date(); 
  	String date = new SimpleDateFormat("yyyy-MM-dd").format(d);
  	%>
  	<div align="center">欢迎登录,${sessionScope.admin.AUserName}</div>
		<a href="<%=request.getContextPath()%>/fruit/manageFruit">返回首页</a><---------->
		<a href="javascript:toExcel()">生成excel</a>
		<br>
		<select name="school" id="school">
			<option>全部</option>
			<c:forEach items="${stationList}" var="school1">
				<option <c:if test="${school==school1}">selected=selected</c:if>>${school1}</option>
			</c:forEach>
		</select>
		开始时间:<input type="date" name="from" id="from" value="${from}" max="<%=date%>">
		结束时间:<input type="date" name="to" id="to" value="${to}" max="<%=date%>"/>
		<a href="javascript:select()">查询</a>
		<br>
		<h1 align="center">${school}</h1>
		<br>
   	  <table>
   	  <tr>
   	  	  <th>商品名</th>
   	  	  <th>数量</th>
   	  	  <th>重量</th>
   	  	  </tr>
   	  <c:forEach items="${goodsList}" var="goods">
   	  	<tr>
	   	  	<td>${goods.location}${goods.fruitType}</td>
	   	  	<td>${goods.num}</td>
	   	  	<td>${goods.totalWeight}</td>
   	  	</tr>
   	  </c:forEach>
  	  </table>
  	  
  </body>
</html>
