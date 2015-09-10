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
  	<a href="<%=request.getContextPath()%>/fruit/manageFruit">水果</a>
 	 <form action="<%=request.getContextPath()%>/admin/addAdmin" method="post">
   	  	用户名<input type="text" name="AUserName" oninput="checkU(this.value)"/><div id="tip"></div><br>
   	  	密码<input type="text" name="APassword"/><br>
   	  	学校<input type="text" name="ASchool"/><br>
   	  	区域<input type="text" name="AArea"/><br>
   	  	名字<input type="text" name="AStuName"/><br>
   	  	电话<input type="text" name="AStuPhone"/><br>
   	  	email<input type="text" name="AStuMail"/><br>
   	  	
   	  	<input type="submit" value="新增用户"/>
   	  </form>
   	  <table>
   	  <tr><th>用户名</th>
   	  	  <th>密码</th>
   	  	  <th>学校</th>
   	  	  <th>区域</th>
   	  	  <th>名字</th>
   	  	  <th>电话</th>
   	  	  <th>email</th>
   	  	  <th>操作</th>
   	  	  </tr>
   	  <c:forEach items="${list}" var="admin">
   	  	<tr><td>${admin.AUserName }</td>
	   	  	<td>${admin.APassword}</td>
	   	  	<td>${admin.ASchool}</td>
	   	  	<td>${admin.AArea}</td>
	   	  	<td>${admin.AStuName}</td>
	   	  	<td>${admin.AStuPhone}</td>
	   	  	<td>${admin.AStuMail}</td>
	   	  	<td><a href="<%=request.getContextPath() %>/admin/delete/${admin.AUserName}">删除</a></td>
   	  	</tr>
   	  </c:forEach>
  	  </table>
  	  <c:if test="${page.currentPageNo!=1}">
  	   <a href="<%=request.getContextPath() %>/admin/manageAdmin?currentPageNo=${page.currentPageNo-1}">上一页</a>
  	  </c:if>
  	  <c:if test="${page.currentPageNo!=page.totalPages}">
  	  <a href="<%=request.getContextPath() %>/admin/manageAdmin?currentPageNo=${page.currentPageNo+1}">下一页</a>
  	  </c:if>
  	  <span>共${page.totalPages}页</span>
  </body>
</html>
