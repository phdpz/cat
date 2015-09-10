<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>

	<head>

		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<title>商品详情</title>

		<!-- jquery mobile样式 -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/style/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/style/jquery.mobile-1.4.5/jquery.mobile.swatch.f.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/style/font-awesome.min.css" />
		<!-- 本页面的样式 -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/style/detail.css" />


		<script src="<%=request.getContextPath()%>/resources/script/jquery-2.1.4.min.js"></script>
		<script src="<%=request.getContextPath()%>/resources/vendor/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>

	</head>
	
	<script type="text/javascript">
	  	function toShopcart(obj){
	  		var urlStr = "<%=request.getContextPath()%>/shopcart/putToCart/"+obj+"/"+document.getElementById("num").innerHTML;
			//alert("Before Call:"+urlStr);
			$.ajax({
				method: "GET",
				url: urlStr,
				success:function(data,status,jqXHR){
					//alert("Success:"+data);
					var result = data;
					$("#cartNum").html("<span id='badge'>"+result+"</span>");
				}
			}); // end ajax
	  	}
	  	
	  	function changeNumber(obj){
	  		var number = parseInt(document.getElementById("num").innerHTML);
	  		if(obj=="+")
	  			number++;
	  		else{
	  			if(number>1)
	  				number--;
	  		}
	  		document.getElementById("num").innerHTML = number;
	  	}
	</script>
	
	<body>

		<!-- page -->
		<div data-role="page">

			<!-- header -->
			<div data-role="header" data-theme="f">
			  <a href="#" data-role="button" data-rel="back"><span class="fa fa-chevron-left"></span></a>
			  <h1>水果详情</h1>
			</div><!-- end header -->

			<!-- content -->
			<div data-role="content">

				<!-- img -->
				<div class="imgArea">
					<img src="<%=request.getContextPath()%>/resources/upload/${fruit.FImg}" alt="" />
				</div><!--end img -->

				<div class="ui-grid-a center">

					<div class="ui-block-a">
						<p>${fruit.FGodsName}</p>
						<p>规格：${fruit.FUnit}/约${fruit.FWeight}g</p>
						<p>￥ ${fruit.FRealFee} </p>
					</div>

					<div class="ui-block-b">
						<div data-role="controlgroup" data-mini="true" data-type="horizontal">
							<a href="javascript:changeNumber('-')" data-role="button">-</a>
							<a id="num" href="##" data-role="button">1</a>
							<a href="javascript:changeNumber('+')" data-role="button">+</a>
						</div>

						<a href="javascript:toShopcart(${fruit.FId})" data-role="button" data-inline="true" class="buy" data-mini="true">加入购物车</a>
	
					</div>

				</div>

				<div id="detial">
					<p class="title">图文详情</p>
               		${fruit.FIntroduce}
				</div>

			</div><!-- end content -->

			<!-- footer -->
			<div data-role="footer" data-theme="f" data-position="fixed" data-fullscreen="true">

				<!-- navbar -->
				<div data-role="navbar">
					<ul>

						<li>
							<a href="../index.html" data-ajax="false">
								<span class="guideIcon"></span>
								<span class="fa fa-home footIcon"></span>
							    <span>首页</span>
							</a></li>

						<li>
							<a href="cartEmpty.html" data-ajax="false">
								<span class="guideIcon"></span>
								<span class="fa fa-shopping-cart footIcon">
								</span>
								<span>购物车</span>
								<div id="cartNum"><c:if test="${cartNum!=0}"><span id="badge">${cartNum}</span></c:if></div>
							</a></li>

						<li>
							<a href="personalCenter.html" data-ajax="false">
								<span class="guideIcon"></span>
								<span class="fa fa-smile-o footIcon"></span>
								<span>个人中心</span>
							</a></li>

					</ul>
				</div><!-- end navbar -->

			</div><!--end footer-->

		</div><!--end page-->

	</body>

</html>

<%-- <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
  	function toShopcart(obj){
  		var urlStr = "<%=request.getContextPath()%>/shopcart/putToCart/"+obj+"/"+document.getElementById("num").value;
		//alert("Before Call:"+urlStr);
		$.ajax({
			method: "GET",
			url: urlStr,
			success:function(data,status,jqXHR){
				//alert("Success:"+data);
				var result = data;
				$("#cartNum").html(result);
			}
		}); // end ajax
  	}
  </script>
  
  <body>
  	<div align="center">欢迎登录,${sessionScope.admin.AUserName}</div>
   	  <h1 align="center">${fruit.FGodsName}</h1>
   	  <img src="<%=request.getContextPath()%>/resources/upload/${fruit.FImg}" width="300px" height="300px"/>
   	  ${fruit.FDetail}<br>
   	  <input type="number" id="num" value="1"/><a href="javascript:toShopcart(${fruit.FId})">加入购物车</a>
   	  |<a href="<%=request.getContextPath()%>/shopcart/toShopcart">购物车：<span id="cartNum">${cartNum}</span></a>|
   	  
  </body>
</html>
 --%>