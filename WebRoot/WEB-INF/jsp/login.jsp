<%@ page language="java" import="java.util.*,java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
	<head>

		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />

		<title>猫鲜生o2o后台管理系统</title>
        
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/font-awesome-4.3.0/css/font-awesome.min.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/style/admin/_login.css" />
		<style>
			
		</style>	
	</head>

	<body>
		<% 
		   Date d = new Date(); 
  		   String date = new SimpleDateFormat("yyyy-MM-dd").format(d);
  		   String week = new SimpleDateFormat("EEEE").format(d);
  		 %>
		<h1 class="title">猫鲜生o2o管理系统</h1>

		<div class="date">
			<p>Hello :)</p>
			<p><%=week%></p>
			<p><%=date%></p>
		</div>

		<div class="content">

			<form  method="post" action="<%=request.getContextPath()%>/admin/login">

				 <div class="picture">
                     <img src="<%=request.getContextPath()%>/resources/images/logo.png" alt="" />
                 </div>

                 <div class="userBox">
                 	<input type="text" class="user" placeholder="username" name="userName"/>
                 	<span class="fa fa-user user-icon"></span>
                 	<span class="fa fa-close user-close-icon" id="user_close"></span>
                 </div>
                 <div class="tip">
                 	<p><span class="fa fa-exclamation-triangle">用户名输入错误</span></p>
                 </div>

                 <div class="passwordBox">
                 	<input type="password" class="password" placeholder="password" name="password"/>
                 	<span class="fa fa-key password-icon"></span>
                 	<span class="fa fa-close password-close-icon close" id="password_close"></span>
                 </div>
                 <div class="tip">
                 	<p><span class="fa fa-exclamation-triangle">密码输入错误</span></p>
                 </div>

                 <div class="loginBox" style="margin-left:24px">
          			<button type="submit">登录</button>
                 </div>

			</form>

		</div>

		<script src="<%=request.getContextPath()%>/resources/script/admin/login.js"></script>

	</body>
</html>



<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <form method="post" action="<%=request.getContextPath()%>/admin/login">
                用户名：<input name="userName" type="text"/>
                密码：<input name="password" type="password"/>
    <input type="submit" value="提交"/>
    </form>
  </body>
</html>
 --%>