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
	function queryArea(obj){
  		var urlStr = "<%=request.getContextPath()%>/station/queryArea/"+obj;
		//alert("Before Call:"+urlStr);
		$.ajax({
			method: "GET",
			url: urlStr,
			success:function(data,status,jqXHR){
				//alert("Success:"+data);
				var result = data.split(":");
				var select = document.getElementById("area");
				select.innerHTML="";
				
				for(var i=0;i<result.length-1;i++){
					var option = document.createElement("option"); 
					option.innerText = result[i];
					select.appendChild(option);
				}
				
			}
		}); // end ajax
  	}  

  </script>
  
  <body>
  	<div align="center">欢迎登录,${sessionScope.admin.AUserName}</div>
	
	<form action="<%=request.getContextPath() %>/order/addOrder1" method="get">
		<table>
			<tr>
				<th>姓名</th>
				<th>电话</th>
				<th>学校</th>
				<th>站点</th>
				<th></th>
			</tr>
			<c:forEach items="${addrList}" var="addr">
				<tr>
					<td>${addr.name }</td>
					<td>${addr.phone }</td>
					<td>${addr.school }</td>
					<td>${addr.area }</td>
					<td><input type="radio" name="CId" value="${addr.name}&&${addr.phone }&&${addr.school}&&${addr.area}"/></td>
				</tr>			
			</c:forEach>
		</table>
		<input type="submit" value="下单"/>
	</form>
	
	<form action="<%=request.getContextPath() %>/order/addOrder" method="post">
  	  收获人姓名<input type="text" name="clientName"/>
		电话<input type="text" name="phone"/>
		学校：<select name="school" onChange="queryArea(this.value)">
				<option>--请选择--</option>
				<c:forEach items="${schoolList}" var="school">
					<option>${school}</option>
				</c:forEach>
			</select>
		区域:<select name="area" id="area">		
			</select>		
		<input type="submit" value="下单"/>
	</form>	
  </body>
</html>
