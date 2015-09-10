<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include  file="/WEB-INF/jsp/admin/managerHead.jsp"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<title>猫鲜生后台管理系统</title>

		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/font-awesome-4.3.0/css/font-awesome.min.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/style/admin/index.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/style/admin/goodsManagement.css" />
		<script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/resources/ueditor/ueditor.config.js"></script>
	    <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/resources/ueditor/ueditor.all.js"> </script>
	    <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/resources/ueditor/lang/zh-cn/zh-cn.js"></script>
	    <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/resources/ueditor/ueditor.parse.min.js"></script>
	</head>
	<script type="text/javascript">
		function showDel(obj){
			document.getElementById("toDelete").value=obj;
		}
		
		function deleteFruit(){
			window.location.href="<%=request.getContextPath() %>/fruit/delete/"+document.getElementById("toDelete").value;
		}
	</script>
	<body>
			<!-- content -->
			<div class="content">

				<h2 class="title">仓库管理</h2>

				<div class="container">

					<div class="goods_filter">
						<span class="fa fa-filter"></span>
						<span>水果：
							<select class="input">
								<option></option>
							</select>
						</span>

						<p class="add_fruit fr" id="add_fruit">
							<span class="fa fa-plus-square"></span>
							<span> 添加水果</span>
						</p>

					</div>

					<table class="table goods_table">
						<thead>
							<tr name="head">
								<th>水果名</th>
								<th>品种</th>
								<th>分类</th>
								<th>单位</th>
								<th>重量(kg)</th>
								<th>价格</th>
								<th>市场价</th>
								<th>标题</th>
								<th>描述</th>
								<th>图片</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${fruitList}" var="fruit">
								<tr>
									<td>${fruit.FGodsName }</td>
									<td>${fruit.FFruitType}</td>
									<td><c:choose><c:when test="${furit.FNative==1}">国产</c:when><c:otherwise>进口</c:otherwise></c:choose></td>
									<td>${fruit.FUnit}</td>
									<td>${fruit.FWeight}</td>
									<td>${fruit.FRealFee}</td>
									<td>${fruit.FMarketFee}</td>
									<td>${fruit.FGodsName}</td>
									<td>${fruit.FDetail}</td>
									<td>
										<a href="##" class="good_img"><img src="${pageContext.request.contextPath}/resources/upload/${fruit.FImg}" alt="" /></a>
									</td>
									<td></td>
									<td>
										<a href="javascript:showDel(${fruit.FId})" class="fa fa-trash-o dele"></a>
										<a href="##" class="fa fa-edit dialog_edit"></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<div class="page_nav">
						<a href="##" class="page_nav_active">首页</a>
						<a href="##">2</a>
						<a href="##">3</a>
						<span class="dotted">...</span>
						<a href="##">11</a>
						<a href="##">12</a>
						<a href="##">尾页</a>
						<div class="set_page">转到 <input type="number" value="${page.currentPageNo}" min="1" max="12" id="page" step="1" oninput="window.location.href='<%=request.getContextPath() %>/fruit/manageFruit?currentPageNo='+this.value"/> 页</div>
						<button class="btn">确定</button>
					</div>
					
					<form action="<%=request.getContextPath()%>/fruit/addFruit" method="post" enctype="multipart/form-data">
					<!-- goods dialog -->
					<div id="goods_dialog">
						
						<div id="goods_dialog_header">
							<span class="goods_title">添 加 水 果</span>
							<span class="fa fa-close fr close" id="goods_dialog_header_close"></span>
						</div>

						<div id="goods_dialog_body">
							<table class="goods_edit_table">
								<tbody>
									<tr>
										<td>水果名：</td>
										<td><input type="text" name="FGodsName"/></td>
									</tr>
									<tr>
										<td>品种：</td>
										<td><input type="text" name="FFruitType"/></td>
									</tr>
									<tr>
										<td>分类：</td>
										<td>
											<select name="FNative">
												<option value="1">国产</option>
												<option value="0">进口</option>
											</select>
										</td>
									</tr>
									<tr>
										<td>单位：</td>
										<td><input type="text" name="FUnit" required/></td>
									</tr>
									<tr>
										<td>重量(kg)：</td>
										<td><input type="text" name="FWeight" required/></td>
									</tr>
									<tr>
										<td>价格：</td>
										<td><input type="text" name="FRealFee" required/></td>
									</tr>
									<tr>
										<td>市场价：</td>
										<td><input type="text" name="FMarketFee" required/></td>
									</tr>
									<tr>
										<td>描述：</td>
										<td><input type="text" name="FDetail" required/></td>
									</tr>
									<tr>
										<td>图片：</td>
										<td><input type="file" name="file" id="img_file" /></td>
									</tr>
									<tr id="img_area">
										<td></td>
										<td><img src="" alt="" /></td>
									</tr>
									<tr>
										<td>每天可售量：</td>
										<td><input type="text" required/></td>
									</tr>
									<tr>
										<td>水果介绍：</td>
										<td><textarea id="container" name="FIntroduce"></textarea><br>
								   	  	</td><script type="text/javascript">
							      			var editor = new UE.ui.Editor({initialFrameHeight:280,initialFrameWidth:400});
							      			editor.render("container");
							            </script>
									</tr>
								</tbody>
							</table>

						</div><!-- end goods_dialog_body -->

						<div id="goods_dialog_footer">
							<button type="button" class="fr btn2" id="goods_dialog_footer_close">取消</button>
							<button type="submit" class="fr btn2" id="goods_dialog_footer_sure">确定</button>
						</div>

					</div><!-- end goods dialog -->
					</form>
					
					<!-- confirm dialog -->
					<div class="confirm_dialog">
						<div class="confirm_dialog_header">
							<span class="fa fa-close" id="dialog_close"></span>
						</div>

						<div class="confirm_dialog_body">
								确定删除么？
						</div>

						<div class="confirm_dialog_footer">
							<input type="hidden" id="toDelete"/>
							<button type="button" class="btn fr" id="dialog_cancel">取消</button>
							<button type="button" class="btn fr" id="dialog_sure" onclick="deleteFruit()">确定</button>
						</div>

					</div><!-- end confirm dialog -->

					<!-- image dialog -->
					<div id="img_dialog">
						<span class="fa fa-close fr"></span>
						<img src="../images/1.jpg" alt="" />
					</div><!-- end image dialog -->


				</div><!-- end container -->

			</div><!-- end content -->

		</div><!-- body -->

		<script src="<%=request.getContextPath()%>/resources/script/admin/jquery-2.1.4.min.js"></script>
		<script src="<%=request.getContextPath()%>/resources/script/admin/jquery-ui.min.js" type="text/javascript"></script>
		<script src="<%=request.getContextPath()%>/resources/script/admin/index.js"></script>
		<script src="<%=request.getContextPath()%>/resources/script/admin/goodsManagement.js"></script>

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
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
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
  	
	function toShopcart(obj){
  		
  		var urlStr = "<%=request.getContextPath()%>/shopcart/putToCart/"+obj+"/1";
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
	
	function shaixuan(){
		var fruitType = document.getElementById("fruitType").value;
		var native = document.getElementById("native").value;
		window.location.href="<%=request.getContextPath()%>/fruit/manageFruit?fruitType="+fruitType+"&native="+native;
	}
  </script>
 
  <body>
  	<div align="center">欢迎登录,${sessionScope.admin.AUserName}</div>
	 	 <a href="<%=request.getContextPath() %>/fruit/manageFruit?add">添加水果</a><---------->
	 	 <a href="<%=request.getContextPath() %>/order/orderStatus">统计数量</a><------------>
	 	 <a href="<%=request.getContextPath() %>/station/manageStation">站点管理</a><-------->
	 	 <a href="<%=request.getContextPath() %>/order/manageOrder">订单管理</a><----------->
	 	 <a href="<%=request.getContextPath() %>/analyse/analyse">销售分析</a><----------->
	 	 <a href="<%=request.getContextPath() %>/analyse/analyseBussiness">营业额</a>
	 	 <br><br>
	 	 筛选:<select id="fruitType">
	 	 	<option>全部</option>
	 	 	<c:forEach items="${fruitTypeList}" var="fruitType">
	 	 		<option <c:if test="${fruitType==fruitType1}">selected=selected</c:if>>${fruitType}</option>
	 	 	</c:forEach>
	 	 </select>
	 	 <select id="native">
	 	 	<option>全部</option>
	 	 	<option value="1" <c:if test="${native1=='1'}">selected=selected</c:if>>本地</option>
	 	 	<option value="0" <c:if test="${native1=='0'}">selected=selected</c:if>>进口</option>
	 	 </select>
	 	 <a href="javascript:shaixuan()">查询</a>
   	  <table>
   	  <tr><th>水果名</th>
   	  	  <th>单位</th>
   	  	  <th>重量</th>
   	  	  <th>品种</th>
   	  	  <th>实价</th>
   	  	  <th>市场价</th>
   	  	  <th>简介</th>
   	  	  <th>图片</th>
   	  	  <th>状态</th>
   	  	  </tr>
   	  <c:forEach items="${fruitList}" var="fruit">
   	  	<tr><td>${fruit.FGodsName }</td>
	   	  	<td>${fruit.FUnit}</td>
	   	  	<td>${fruit.FWeight}</td>
	   	  	<td>${fruit.FFruitType}</td>
	   	  	<td>${fruit.FRealFee}</td>
	   	  	<td>${fruit.FMarketFee}</td>
	   	  	<td>${fruit.FDetail}</td>
	   	  	<td><a href="${pageContext.request.contextPath}/fruit/showFruit/${fruit.FId}">
	   	  		<img src="${pageContext.request.contextPath}/resources/upload/${fruit.FImg}" width="100px" height="100px"/>
	   	  		</a></td>
	   	  	<td>${fruit.FStatus}</td>
	   	  	<td><a href="<%=request.getContextPath() %>/fruit/update/${fruit.FId}">修改</a>||<a href="<%=request.getContextPath() %>/fruit/delete/${fruit.FId}">删除</a></td>
   	  		<td><a href="javascript:toShopcart(${fruit.FId})">加入购物车</a>
   	  		</td>
   	  	</tr>
   	  </c:forEach>
  	  </table>
  	  |<a href="<%=request.getContextPath()%>/shopcart/toShopcart">购物车：<span id="cartNum">${cartNum}</span></a>|
  	  <c:if test="${page.currentPageNo!=1}">
  	   <a href="<%=request.getContextPath() %>/fruit/manageFruit?currentPageNo=${page.currentPageNo-1}">上一页</a>
  	  </c:if>
  	  <c:if test="${page.currentPageNo!=page.totalPages}">
  	  <a href="<%=request.getContextPath() %>/fruit/manageFruit?currentPageNo=${page.currentPageNo+1}">下一页</a>
  	  </c:if>
  	  <span>共${page.totalPages}页</span>
  </body>
</html>
 --%>