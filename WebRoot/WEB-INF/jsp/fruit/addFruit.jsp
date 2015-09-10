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
    
    <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/resources/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/resources/ueditor/ueditor.all.js"> </script>
    <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/resources/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/resources/ueditor/ueditor.parse.min.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
  </head>
  <script type="text/javascript">
  	function findLocation(obj){
  		var urlStr = "<%=request.getContextPath()%>/fruit/findLocation/"+obj;
		//alert("Before Call:"+urlStr);
		$.ajax({
			method: "GET",
			url: urlStr,
			success:function(data,status,jqXHR){
				//alert("Success:"+data);
				var result = eval("(data)");
				var select = document.getElementById("location");
				select.innerHTML="";
				
				for(var i=0;i<result.length;i++){
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
 		<form action="<%=request.getContextPath()%>/fruit/addFruit" method="post" enctype="multipart/form-data">
 			水果名<input type="text" name="FGodsName"/><br>
	   	  	单位<input type="text" name="FUnit"/><br>
	   	  	重量<input type="text" name="FWeight"/><br>
	   	  	品种<select onChange="findLocation(this.value)">
	   	  	   	<c:forEach items="${fruitTypeList}" var="fruitType">
	   	  	   		<option>${fruitType}</option>
	   	  	   	</c:forEach>
	   	  	   </select>
	   	  	<input type="text" name="FFruitType"/><br>
	   	  	品种修饰<select id="location">
	   	  			
	   	  		  </select>
	   	  	<input type="text" name="FFLocation"/><br>
	   	  	实价<input type="text" name="FRealFee"/><br>
	   	  	市场价<input type="text" name="FMarketFee"/><br>
	   	  	简介<input type="text" name="FDetail"/><br>
	   	  	状态<input type="text" name="FStatus"/><br>
	   	  	图片<input type="file" name="file"/><br>
	   	  	水果介绍：<textarea id="container" name="FIntroduce"></textarea><br>
	   	  	<script type="text/javascript">
      			var editor = new UE.ui.Editor({initialFrameHeight:280,initialFrameWidth:900});
      			editor.render("container");
            </script>
	   	  	<input type="submit" value="添加"/>		
 		</form>
  </body>
</html>
