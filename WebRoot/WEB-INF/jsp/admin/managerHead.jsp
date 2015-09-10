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
    
    <title>猫鲜生后台管理系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	  </head>
  
  <body>
  	<!-- header -->
		<div class="header">

			<!-- header_bar -->
			<ul class="header_bar">

				<li class="nav_btn" id="nav_btn">
					<button class="fa fa-navicon toggle_btn" id="toggle_btn"></button>
				</li>

				<li class="logo">
					<img src="<%=request.getContextPath()%>/resources/images/logo.png" alert="logo" />
					<img src="<%=request.getContextPath()%>/resources/images/logo2.png" alert="logo" />
				</li>

				<li class="admin">
					<button class="btn2"><span class="fa fa-user"></span> 注销</button>
				</li>

			</ul><!-- end header_bar -->


		</div><!-- end header -->

		<!-- body -->
		<div class="main_body">

			<!-- nav_bar -->
			<div class="nav_bar" id="nav_bar">
    
				<ul class="nav">

					<li class="title">商品管理</li>
					<li class="list">
						<a href="<%=request.getContextPath()%>/analyse/analyseBussiness">
							<span class="fa fa-line-chart"></span>
							<span>报表统计</span>
						</a>
					</li>
					<li class="list">
						<a href="<%=request.getContextPath()%>/fruit/manageFruit" <c:if test="${active=='fruitActive'}">class="active"</c:if>>
							<span class="fa fa-list-alt"></span>
							<span>仓库管理</span>
						</a>
					</li>
					<li class="list">
						<a href="##">
							<span class="fa fa-cart-plus"></span>
							<span>订单管理</span>
						</a>
					</li>
					<li class="title">商店管理</li>
					<li class="list">
						<a href="<%=request.getContextPath()%>/station/manageStation">
							<span class="fa fa-building-o"></span>
							<span>站点管理</span>
						</a>
					</li>
					<li class="title">基本信息</li>
					<li class="list">
						<a href="##">
							<span class="fa fa-street-view"></span>
							<span>基本信息</span>
						</a>
					</li>
					<!-- 小屏幕下的注销菜单 -->
					<li class="title loginout">注销</li>
					<li class="list loginout">
						<a href="##">
							<span class="fa fa-truck"></span>
							<span>注销</span>
						</a>
					</li>

				</ul>

			</div><!-- end nav_bar -->
  </body>
</html>
