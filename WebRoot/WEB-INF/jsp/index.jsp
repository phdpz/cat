<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>

	<head>

		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />

		<title>猫鲜生o2o</title>

		<!--jquery mobile样式文件-->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/style/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css?1231" />
		<!-- 调色板 包括公共样式 -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/style/jquery.mobile-1.4.5/jquery.mobile.swatch.f.css?1231" />
		<!-- 字体图标 -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/style/font-awesome.min.css?1231" />
		<!--自定义样式-->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/style/index.css?1231" />
		
		<!--jquery mobile js文件-->
		<script src="<%=request.getContextPath()%>/resources/script/jquery-2.1.4.min.js?1231"></script>
		<script src="<%=request.getContextPath()%>/resources/vendor/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js?1231"></script>
		
	</head>
	
	<script type="text/javascript">
		function toShopcart(obj){	
	  		var urlStr = "<%=request.getContextPath()%>/shopcart/putToCart/"+obj+"/1";
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
		
		function EnterPress(e){ //传入 event 
		    var e = e || window.event; 
		    if(e.keyCode == 13){ 
		    	window.location.href="<%=request.getContextPath()%>/fruit/toIndex";
		    } 
		}
		
		function more(){	
	  		var urlStr = "<%=request.getContextPath()%>/fruit/pageConsult?currentPageNo="+document.getElementById("pageNo").value+"&fruitType=${fruitType}&native=${native1}";
			//alert("Before Call:"+urlStr);
			$.ajax({
				method: "GET",
				url: urlStr,
				success:function(data,status,jqXHR){
					//alert("Success:"+data);
					var result =data;
					var html="";
					for(var i=0;i<result.length-1;i++){
						var fruit = result[i];
						if(i%2==0)
							html+="<div class='box'>";
						html+="<div>";
						html+="<a href='${pageContext.request.contextPath}/fruit/showFruit/"+fruit.fid+"' data-ajax='false'>";
						html+="<img src='${pageContext.request.contextPath}/resources/upload/"+fruit.fimg+"' alt='猫先生o2o' class='fruitImg'>";
						html+="</a><p class='desc'>"+fruit.fgodsName+"</p><p class='spec'>"+fruit.fdetail+"</p>";
						html+="<span class='money'>￥"+fruit.frealFee+"</span><span class='market'><s>市场价：￥"+fruit.fmarketFee+"</s></span>";
						html+="<a href='javascript:toShopcart("+fruit.fid+")'><span class='cart fa fa-cart-plus'></span></a>";
						html+="</div>";
						if(i%2!=0 || i==result.length-2)
							html+="</div>";
					}
					document.getElementById("pageNo").value=result[result.length-1].fid;
					document.getElementById("more"+result[result.length-1].fid).innerHTML=html;
					if(result[result.length-1].fstatus=="last")
						document.getElementById("hh").innerHTML="";
				}
			}); // end ajax
	  	}
	</script>
	
	<body>
		<input type="hidden" id="pageNo" value="2"/>
		<!--page1-->
		<div data-role="page" id="page1" data-theme="f">

			<!-- header -->
			<div data-role="header" data-position="fixed" data-tap-toggle="false">
					<div id="search">
						<input id="goods" placeholder="搜索商品" data-mini="true" onkeypress="EnterPress(event)"/>
						<span class="fa fa-search" id="search_icon"></span>
						<span class="fa fa-close" id="close_icon"></span>
					</div>
					<span class="ui-btn-right fa fa-filter" id="filter_btn"></span>

			</div><!-- end header -->

			<!-- screen -->
			<div class="screen" id="screen"></div>

			<!-- filter -->
			<div class="filter" id="filter">

				<table>
					<tr><td title="td1" onclick="window.location.href='<%=request.getContextPath()%>/fruit/toIndex?native=1'" class="">国产</td></tr>
					<tr><td title="td1" onclick="window.location.href='<%=request.getContextPath()%>/fruit/toIndex?native=0'">进口</td></tr>
					<%-- <tr><td title="td1" onclick="window.location.href='<%=request.getContextPath()%>/fruit/toIndex?fruitType=精选果篮'">精选果篮</td></tr> --%>
				</table>
				
			</div><!-- end filter -->

			<!-- content -->
			<div data-role="content" id="index_content">

				<!-- carousel -->
				<div id='mySwipe' class='swipe'>

					<div class='swipe-wrap' id="swipe-wrap">
						<div><img src="<%=request.getContextPath()%>/resources/images/1.jpg" alt="" /></div>
						<div><img src="<%=request.getContextPath()%>/resources/images/2.jpg" alt="" /></div>
					</div>

					<div class="control">
					</div>

					<!-- logo -->
					<div class="logoArea" id="logoArea">
                         <img src="<%=request.getContextPath()%>/resources/images/logo.png" alt="" />
	                     <h2>猫先生o2o</h2>
					</div><!-- logo -->

				</div><!--end carousel -->
					
				<!-- 滑动侧边栏 -->
				<div id="slide">

					<!-- 每日精选 -->
					<div class="imgShow">
						<img src="<%=request.getContextPath()%>/resources/images/title.png" alt="每日精选"/>
					</div>
					
					<c:forEach items="${fruitList}" var="fruit" varStatus="i">
					<!-- box -->
					<c:if test="${i.index%2==0}">
						<div class="box">
					</c:if>
						<div>
							<a href="${pageContext.request.contextPath}/fruit/showFruit/${fruit.FId}"  data-ajax="false">
								<img src="${pageContext.request.contextPath}/resources/upload/${fruit.FImg}" alt="猫先生o2o" class="fruitImg"></a>
							<p class="desc">${fruit.FGodsName }</p>
							<p class="spec">${fruit.FDetail}</p>
							<span class="money">￥${fruit.FRealFee}</span>
							<span class="market"><s>市场价：￥${fruit.FMarketFee}</s></span>
							<a href="javascript:toShopcart(${fruit.FId})"><span class="cart fa fa-cart-plus"></span></a>
						</div>

						
					<c:if test="${i.index%2!=0 || i.index==fruitList.size()-1}">
					</div><!-- end box -->
					</c:if>
					</c:forEach>

					<div id="more3"></div>
					<div id="more4"></div>
					<div id="more5"></div>
					<div id="more6"></div>
					<div id="more7"></div>
					<c:if test="${!last}"><a href="javascript:more()"><h2 id="hh" align="center">加载更多</h2></a></c:if>
					<a id="view"></a>
					
					<!-- 精选果篮 -->
					<div class="imgShow">
						<img src="<%=request.getContextPath()%>/resources/images/title2.png" alt="每日精选"/>
					</div>
					
						<c:forEach items="${choiceList}" var="fruit" varStatus="i">
						<!-- box -->
						<c:if test="${i.index%2==0}">
							<div class="box">
						</c:if>
							<div>
								<a href="${pageContext.request.contextPath}/fruit/showFruit/${fruit.FId}"  data-ajax="false">
									<img src="${pageContext.request.contextPath}/resources/upload/${fruit.FImg}" alt="猫先生o2o" class="fruitImg"></a>
								<p class="desc">${fruit.FGodsName }</p>
								<p class="spec">${fruit.FDetail}</p>
								<span class="money">￥${fruit.FRealFee}</span>
								<span class="market"><s>市场价：￥${fruit.FMarketFee}</s></span>
								<a href="javascript:toShopcart(${fruit.FId})"><span class="cart fa fa-cart-plus"></span></a>
							</div>
	
							
						<c:if test="${i.index%2!=0 || i.index==choiceList.size()-1}">
						</div><!-- end box -->
						</c:if>
						</c:forEach>

				</div><!-- end 滑动侧边栏 -->

			</div><!-- end content -->

			<!-- footer -->
			<div data-role="footer" data-position="fixed" data-fullscreen="true" data-tap-toggle="false">

				<!-- navbar -->
				<div data-role="navbar">
					<ul>

						<li>
							<a href="<%=request.getContextPath()%>/fruit/toIndex" data-ajax="false">
								<span class="fa fa-caret-down guideIcon"></span>
								<span class="fa fa-home footIcon"></span>
							    <span>首页</span>
							</a></li>

						<li>
							<a href="<%=request.getContextPath()%>/shopcart/toShopcart" data-ajax="false">
								<span class="guideIcon"></span>
								<span class="fa fa-shopping-cart footIcon">
								</span>
								<span>购物车</span>
								<div id="cartNum"><c:if test="${cartNum!=0}"><span id="badge">${cartNum}</span></c:if></div>
							</a></li>

						<li>
							<a href="<%=request.getContextPath()%>/client/toPerCenter/1" data-ajax="false">
								<span class="guideIcon"></span>
								<span class="fa fa-smile-o footIcon"></span>
								<span>个人中心</span>
							</a></li>

					</ul>
				</div><!-- end navbar -->

			</div><!-- end footer -->

			<!-- dialog -->
			<div class="dialog1" id="dialog1">
                <span class="fa fa-check-square-o"></span>
                <span>成功加入购物车</span>
			</div><!-- end dialog -->

			<!-- retutn top -->
			<a class="fa fa-chevron-up"  id="returnTop" href="javascript:goTopFun();" >
			</a><!-- end retutn top -->
			
		</div><!--end page1-->

		<!--自定义js文件-->
		<script src="<%=request.getContextPath()%>/resources/script/index.js?1231"></script>
		<!--swipe-master插件-->
		<script src="<%=request.getContextPath()%>/resources/vendor/swipe-master/swipe.js?1231"></script>
		<script src="<%=request.getContextPath()%>/resources/script/goTop.js"></script>

	</body>

</html>