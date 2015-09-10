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
		<title>购物车</title>

		<!-- jquery mobile样式 -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/style/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css?00009" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/style/jquery.mobile-1.4.5/jquery.mobile.swatch.f.css?00009" />
		<!-- 字体文件 -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/style/font-awesome.min.css?00009" />
		<!-- 本页面的样式 -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/style/cart.css?00009" />

		<script src="<%=request.getContextPath()%>/resources/script/jquery-2.1.4.min.js?00009"></script>
		<script src="<%=request.getContextPath()%>/resources/vendor/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js?00009"></script>

	</head>
	
	<script type="text/javascript">
	  	function deleteCart(){
	  		var obj = document.getElementById("toDelete").value;
	  		
	  		var urlStr = "<%=request.getContextPath()%>/shopcart/deleteCart/"+obj+"/1";
			//alert("Before Call:"+urlStr);
			$.ajax({
				method: "GET",
				url: urlStr,
				success:function(data,status,jqXHR){
					//alert("Success:"+data);
					var result = data;
					if(result.indexOf("delete")>=0){
						var s = result.split(":");
						
						$("#shopcart"+s[1]).html("");
						$("#price").html(s[2]);
					}else{
						var s = result.split(":");
						$("#cartNum"+s[0]).html(s[1]);
						$("#button"+s[0]).html(s[1]);
						$("#price").html(s[2]);
					}
					
					var clear= result.split("^-^");
					if(clear[1]=="null"){
						document.getElementById("cc").innerHTML="";
						location.reload();
						
					}
				}
			}); // end ajax
	  	}
	  	
	function changeCart(obj1,obj2){
	  		
	  		var urlStr = "<%=request.getContextPath()%>/shopcart/changeCart/"+obj1+"/1"+"/"+obj2;
			//alert("Before Call:"+urlStr);
			$.ajax({
				method: "GET",
				url: urlStr,
				success:function(data,status,jqXHR){
					//alert("Success:"+data);
					var result = data;
					//alert(result);
					if(result.indexOf("doNothing")<0){
						var s = result.split(":");
						var sFee = $("#singelFee"+s[0]).attr("value");;
						$("#cartNum"+s[0]).html(s[1]);
						$("#button"+s[0]).html(s[1]);
						$("#sum"+s[0]).html(parseInt(s[1])*parseFloat(sFee));
						$("#price").html(s[2]);
					}	
				}
			}); // end ajax
	  	}
	
		function settle(){
			/* var allCheckBox = document.getElementsByName("checkCart");
			for(var i=0;i<allCheckBox.length;i++){
				
				if(allCheckBox[i].checked){
					alert(allCheckBox[i].id);
				}
			} */
			window.location.href="<%=request.getContextPath()%>/order/toAddOrder";
		}
		
		function toDelete(obj){
			
			document.getElementById("toDelete").value=obj;
		}
  </script>
	
	<body>
		<c:if test="${cartNum!=0}">
		<!-- page2 -->
		<div id="cc">
		<div data-role="page" id="page2" data-theme="f">

			<!-- header -->
			<div data-role="header" data-theme="f" id="content1">
				<a href="#" data-role="button" data-rel="back"><span class="fa fa-chevron-left"></span></a>
				<h1>购物车</h1>
				<!-- <span class="fa fa-circle-thin allselect" id="allselect"> 全选</span> -->
				
			</div><!-- end header -->

			<!-- content -->
			<div data-role="content">

				<from>
					<c:forEach items="${shopList}" var="shopcart">
   	 				<c:forEach items="${fruitList}" var="fruit">
   	 				<c:if test="${shopcart.fruitId==fruit.FId}">
					<!-- list -->
					<div id="shopcart${shopcart.SId}">
					<div class="list">
	                	<div class="col1">
	                		<%-- <input type="checkbox" name="checkCart" id="${shopcart.SId}" data-role="none"/>
	                		<span class="fa fa-circle-thin select"></span> --%>
	                	</div>
	                    <div class="col2">
	                    	<a href="##"><img src="<%=request.getContextPath() %>/upload/${fruit.FImg}" alt="猫先生o2o" /></a>
	                    </div>
	                    <div class="col3">
	                    	<p>${fruit.FGodsName}</p>
	                    	<p>单价:￥${fruit.FRealFee} /${fruit.FUnit}个</p>
	                    	<div>
	                    		<input type="hidden" id="singelFee${shopcart.SId }" value="${fruit.FRealFee}"/>
	                    		数量：<span id="cartNum${shopcart.SId}">${shopcart.SNum}</span>
								<span>总计:<span id="sum${shopcart.SId }">${shopcart.SNum*fruit.FRealFee }</span></span>
							</div>
						</div>

	                    <p class="edit">修改数量</p>
	                    	<div data-role="controlgroup" data-type="horizontal" data-mini="true" class="btnGruop">
							  <a href="javascript:changeCart(${shopcart.SId},'-')" data-role="button">-</a>
							  <a id="button${shopcart.SId}" href="#anylink" data-role="button">${shopcart.SNum }</a>
							  <a href="javascript:changeCart(${shopcart.SId},'+')" data-role="button">+</a>
							</div>
	                    <a href="javascript:toDelete(${shopcart.SId})" class="fa fa-trash-o cart"></a>

					</div><!-- end list -->
					</div>
					</c:if>
					</c:forEach>
					</c:forEach>

				</from>

			</div><!-- end content -->

			<div data-role="footer" data-position="fixed" data-fullscreen="true" data-tap-toggle="false">
				
				<!-- account -->
				<div class="account">

					&nbsp总计:<span id="price">${totalPrice}</span>
					<button class="ui-btn-right btn" data-inline="true" data-mini="true" onclick="settle()">去结算</button>

				</div><!-- end account -->

			</div><!-- end footer -->

			<!-- dialog -->
			<div class="dialog2" id="dialog2">
				<span class="fa fa-exclamation-triangle"></span>
				<span>确定要删除吗</span>
				<div class="btnAre">
					<input id="toDelete" type="hidden" value=""/>
					<button data-inline="true" data-mini="true" id="sure" onclick="deleteCart()">确定</button>
					<button data-inline="true" data-mini="true" id="del">取消</button>
				</div>
			</div><!-- end dialog -->

		</div><!--end page1-->
		</div>
		</c:if>
		
		<!-- 没有货物的情况 -->
		<c:if test="${cartNum==0}">
			<div data-role="page" id="page1">

			<!-- header -->
			<div data-role="header" data-theme="f" data-position="fixed" id="content1">
				<a href="#" data-role="button" data-rel="back"><span class="fa fa-chevron-left"></span></a>
				<h1>购物车</h1>
			</div><!-- end header -->

			<!-- content -->
			<div data-role="content">

				<div class="tipDiv">
					<span class="fa fa-frown-o"></span>
					<p>sorry！您的购物车还是空的哦~</p>
					<a href="<%=request.getContextPath()%>/fruit/toIndex" data-role="button" data-inline="true" class="go" 
						data-ajax="false">去逛逛</a>
				</div>

			</div><!-- end content -->

			<!-- footer -->
			<div data-role="footer" data-theme="f" data-position="fixed" data-fullscreen="true">

				<!-- navbar -->
				<div data-role="navbar">
					<ul>

						<li>
							<a href="<%=request.getContextPath()%>/fruit/toIndex" data-ajax="false">
								<span class="guideIcon"></span>
								<span class="fa fa-home footIcon"></span>
							    <span>首页</span>
							</a></li>

						<li>
							<a href="<%=request.getContextPath()%>/shopcart/toShopcart" data-ajax="false">
								<span class="fa fa-caret-down guideIcon"></span>
								<span class="fa fa-shopping-cart footIcon">
								</span>
								<span>购物车</span>
								
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

		</div><!--end page1-->
		</c:if>
		<div id="dd">
		<div data-role="page" id="page1">

			<!-- header -->
			<div data-role="header" data-theme="f" data-position="fixed" id="content1">
				<a href="#" data-role="button" data-rel="back"><span class="fa fa-chevron-left"></span></a>
				<h1>购物车</h1>
			</div><!-- end header -->

			<!-- content -->
			<div data-role="content">

				<div class="tipDiv">
					<span class="fa fa-frown-o"></span>
					<p>sorry！您的购物车还是空的哦~</p>
					<a href="<%=request.getContextPath()%>/fruit/toIndex" data-role="button" data-inline="true" class="go" 
						data-ajax="false">去逛逛</a>
				</div>

			</div><!-- end content -->

			<!-- footer -->
			<div data-role="footer" data-theme="f" data-position="fixed" data-fullscreen="true">

				<!-- navbar -->
				<div data-role="navbar">
					<ul>

						<li>
							<a href="<%=request.getContextPath()%>/fruit/toIndex" data-ajax="false">
								<span class="guideIcon"></span>
								<span class="fa fa-home footIcon"></span>
							    <span>首页</span>
							</a></li>

						<li>
							<a href="<%=request.getContextPath()%>/shopcart/toShopcart" data-ajax="false">
								<span class="fa fa-caret-down guideIcon"></span>
								<span class="fa fa-shopping-cart footIcon">
								</span>
								<span>购物车</span>
								
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

		</div><!--end page1-->
		</div>
		<script src="<%=request.getContextPath()%>/resources/script/cart.js?00009"></script>
		
		
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
  	function deleteCart(obj){
  		
  		var urlStr = "<%=request.getContextPath()%>/shopcart/deleteCart/"+obj+"/1";
		//alert("Before Call:"+urlStr);
		$.ajax({
			method: "GET",
			url: urlStr,
			success:function(data,status,jqXHR){
				//alert("Success:"+data);
				var result = data;
				
				if(result.indexOf("delete")>=0){
					var s = result.split(":");
					
					$("#shopcart"+s[1]).html("");
					$("#price").html(s[2]);
				}else{
					var s = result.split(":");
					$("#cartNum"+s[0]).html(s[1]);
					$("#price").html(s[2]);
				}
				
			}
		}); // end ajax
  	}
  	
function changeCart(obj1,obj2){
  		
  		var urlStr = "<%=request.getContextPath()%>/shopcart/changeCart/"+obj1+"/1"+"/"+obj2;
		//alert("Before Call:"+urlStr);
		$.ajax({
			method: "GET",
			url: urlStr,
			success:function(data,status,jqXHR){
				//alert("Success:"+data);
				var result = data;
				//alert(result);
				if(result.indexOf("doNothing")<0){
					var s = result.split(":");
					$("#cartNum"+s[0]).html(s[1]);
					$("#price").html(s[2]);
				}	
			}
		}); // end ajax
  	}
  </script>
  
  <body>
  	<div align="center">欢迎登录,${sessionScope.admin.AUserName}</div>
  	<br><h3>总价：￥</h3><h3 id="price">${totalPrice}</h3>
   	 <c:forEach items="${shopList}" var="shopcart">
   	 	<c:forEach items="${fruitList}" var="fruit">
   	 		<c:if test="${shopcart.fruitId==fruit.FId}">
   	 			<div id="shopcart${shopcart.SId}">
	   	 			${fruit.FGodsName}<br>
	   	 			<img src="<%=request.getContextPath() %>/resources/upload/${fruit.FImg}" width="80px" height="80px"/>
	   	 			<br>${fruit.FRealFee}
	   	 			<br>数量:<div id="cartNum${shopcart.SId}">${shopcart.SNum}</div>
	   	 			<a href="javascript:changeCart(${shopcart.SId},'+')">+</a>
	   	 			<a href="javascript:changeCart(${shopcart.SId},'-')">-</a>
	   	 			<a href="javascript:deleteCart(${shopcart.SId})">删除</a>
	   	 			<br>--------------------------------------------------------------------<br>
   	 			</div>
   	 		</c:if>
   	 	</c:forEach>
   	 </c:forEach>
   	 <c:if test="${shopList.size()!=0}"><a href="<%=request.getContextPath()%>/order/toAddOrder">结算</a></c:if>
  </body>
</html>
 --%>