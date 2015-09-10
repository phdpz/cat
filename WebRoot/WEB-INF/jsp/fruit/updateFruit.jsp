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
 		<form action="<%=request.getContextPath()%>/fruit/update" method="post" enctype="multipart/form-data">
 			<input type="hidden" name="FId" value="${fruit.FId }"/>
 			水果名<input type="text" name="FGodsName" value="${fruit.FGodsName}"/><br>
	   	  	单位<input type="text" name="FUnit" value="${fruit.FUnit}"/><br>
	   	  	重量<input type="text" name="FWeight" value="${fruit.FWeight}"/><br>
	   	  	品种<input type="text" name="FFruitType" value="${fruit.FFruitType}"/><br>
	   	  	实价<input type="text" name="FRealFee" value="${fruit.FRealFee}"/><br>
	   	  	市场价<input type="text" name="FMarketFee" value="${fruit.FMarketFee}"/><br>
	   	  	简介<input type="text" name="FDetail" value="${fruit.FDetail}"/><br>
	   	  	状态<input type="text" name="FStatus" value="${fruit.FStatus}"/><br>
	   	  	图片<input type="file" name="file"/><br>
	   	  	<input type="hidden" name="FImg" value="${fruit.FImg}"/>
	   	  	<img src="${pageContext.request.contextPath}/resources/upload/${fruit.FImg}" width="100px" height="100px"/>
	   	  	<input type="submit" value="添加"/>		
 		</form>
  </body>
</html>
