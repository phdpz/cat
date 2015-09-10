<%@ page language="java" import="java.util.*,java.text.SimpleDateFormat" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include  file="/WEB-INF/jsp/admin/managerHead.jsp"%>

<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<title>猫鲜生后台管理系统</title>

		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/font-awesome-4.3.0/css/font-awesome.min.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/style/admin/index.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/style/admin/reportStatistics.css" />

	</head>
	<script type="text/javascript">
		function select(){
	  		var region = document.getElementById("region").value;
	  		var from = document.getElementById("from").value;
	  		var to = document.getElementById("to").value;
	  		if(from>to)
	  			alert("开始时间必须大于等于结束时间");
	  		else	
	  		 	window.location.href="${pageContext.request.contextPath}/analyse/analyseBussiness?region="+region+"&from="+from+"&to="+to;
	  	}
	</script>
	<% 
		Date d = new Date(); 
  		String date = new SimpleDateFormat("yyyy-MM-dd").format(d);
  	%>
	<body>
		<!-- content -->
			<div class="content">

				<h2 class="title">报表统计</h2>

				<div class="container">

					<ul class="report_nav" id="report_nav">
						<li><a href="<%=request.getContextPath() %>/analyse/analyseBussiness" class="active">业绩分析</a></li>
						<li><a href="<%=request.getContextPath()%>/analyse/schoolAnalyse/2015-08-01/<%=date%>" >学校分析</a></li>
						<li><a href="<%=request.getContextPath()%>/analyse/stationAnalyse/华南农业大学/2015-08-01/<%=date%>" >站点分析</a></li>
						<li><a href="<%=request.getContextPath()%>/analyse/sexAnalyse/全部/全部/2015-08-01/<%=date%>" >性别分析</a></li>
						<li><a href="<%=request.getContextPath() %>/analyse/analyse" >品种分析</a></li>
						<span id="line"></span>
					</ul>

					<div class="panel">

						<div class="panel_header">
							<span class="fa fa-building-o"></span>
							<span>学校分析</span>
						</div>

						<div class="panel_body">
							<span class="setSpace">区间大小:</span>
							<input type="text" name="region" id="region" value="${region}"/>
							<span class="setSpace">开始时间:</span>
								<input type="date" class="input cls3" name="from" id="from" value="${from}" max="<%=date%>" />
							<span class="setSpace">结束时间:</span>
								<input type="date" class="input cls3" name="to" id="to" value="${to}" max="<%=date%>" />
							<button class="btn fr" onclick="select()">查询</button>

						    <h3>统计图</h3>

						    <div class="cartogram">
							    <!--销售量统计图 -->
									<img src="${charturl}"/>
							</div>

						    <h3>统计表</h3>

						    <table class="table" id="tableSort">
								<thead>
									<tr name="head">
										<th>区间</th>
										<th>营业额
											<span class="fa fa-caret-up caret-up" onclick="javascript:$.sortTable.sort('tableSort',1,0)"></span>
											<span class="fa fa-caret-down caret-down" onclick="javascript:$.sortTable.sort('tableSort',1,1)"></span>
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${map}" var="bussiness">
							  			<tr>
							  				<td>${bussiness.key}</td>
							  				<td>${bussiness.value}</td>
							  			</tr>
							  		</c:forEach>
								</tbody>
							</table>
					    </div>
					</div><!-- table -->

				</div><!-- end container -->

			</div><!-- end content -->

		</div><!-- body -->

		<script src="<%=request.getContextPath() %>/resources/script/jquery-2.1.4.min.js"></script>
		<script src="<%=request.getContextPath() %>/resources/script/admin/index.js"></script>
		<script>
		(function($){  
		    //插件  
		    $.extend($,{  
		        //命名空间  
		        sortTable:{  
		            sort:function(tableId,Idx,desc){  
		                var table = document.getElementById(tableId);  
		                var tbody = table.tBodies[0];  
		                var tr = tbody.rows;   
		                
		          
		                var trValue = new Array();  
		                for (var i=0; i<tr.length; i++ ) {  
		                    trValue[i] = tr[i];  //将表格中各行的信息存储在新建的数组中  
		                }  
		          
		               // if (tbody.sortCol == Idx) {  
		                  //  trValue.reverse(); //如果该列已经进行排序过了，则直接对其反序排列  
		               // } else {  
		                    //trValue.sort(compareTrs(Idx));  //进行排序  
		                    trValue.sort(function(tr1, tr2){  
		                    	var value1 = parseInt(tr1.cells[Idx].innerHTML);  
		                        var value2 = parseInt(tr2.cells[Idx].innerHTML);  
		                        if(desc==0)
		                        	return value1-value2;
		                        else
		                        	return value2-value1;
		                    });  
		               // }  
		          
		                var fragment = document.createDocumentFragment();  //新建一个代码片段，用于保存排序后的结果  
		                for (var i=0; i<trValue.length; i++ ) {  
		                    fragment.appendChild(trValue[i]);  
		                }  
		          
		                tbody.appendChild(fragment); //将排序的结果替换掉之前的值  
		                tbody.sortCol = Idx;  
		            }  
		        }  
		    });         
		})(jQuery);  
		
		
		$(window).load( function() {
			
			var $report_nav_a = $('#report_nav li a');
			var $line = $('#line');
			$report_nav_a.each( function(index,value) {

				$(this).click( function() {
					for(var j=0;j<$report_nav_a.length;j++) {
						$($report_nav_a[j]).removeClass('active');
					}

					$(this).addClass('active');
					$line.animate({'left': index * (100+5) + 'px' },'fast');

				});
			});

		});
			
		</script>

	</body>
</html>


<%-- <%@ page language="java" import="java.util.*,java.text.SimpleDateFormat" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
				var option = document.createElement("option");
				option.innerText = "全部";
				select.appendChild(option);
				
				for(var i=0;i<result.length-1;i++){
					var option = document.createElement("option"); 
					option.innerText = result[i];
					select.appendChild(option);
				}
				
			}
		}); // end ajax
  	}  
	
	function select(){
  		var region = document.getElementById("region").value;
  		var from = document.getElementById("from").value;
  		var to = document.getElementById("to").value;
  		if(from>to)
  			alert("开始时间必须大于等于结束时间");
  		else	
  		 	window.location.href="${pageContext.request.contextPath}/analyse/analyseBussiness?region="+region+"&from="+from+"&to="+to;
  	}
	
	(function($){  
	    //插件  
	    $.extend($,{  
	        //命名空间  
	        sortTable:{  
	            sort:function(tableId,Idx,desc){  
	                var table = document.getElementById(tableId);  
	                var tbody = table.tBodies[0];  
	                var tr = tbody.rows;   
	                
	          
	                var trValue = new Array();  
	                for (var i=0; i<tr.length; i++ ) {  
	                    trValue[i] = tr[i];  //将表格中各行的信息存储在新建的数组中  
	                }  
	          
	               // if (tbody.sortCol == Idx) {  
	                  //  trValue.reverse(); //如果该列已经进行排序过了，则直接对其反序排列  
	               // } else {  
	                    //trValue.sort(compareTrs(Idx));  //进行排序  
	                    trValue.sort(function(tr1, tr2){  
	                    	var value1 = parseInt(tr1.cells[Idx].innerHTML);  
	                        var value2 = parseInt(tr2.cells[Idx].innerHTML);  
	                        if(desc==0)
	                        	return value1-value2;
	                        else
	                        	return value2-value1;
	                    });  
	               // }  
	          
	                var fragment = document.createDocumentFragment();  //新建一个代码片段，用于保存排序后的结果  
	                for (var i=0; i<trValue.length; i++ ) {  
	                    fragment.appendChild(trValue[i]);  
	                }  
	          
	                tbody.appendChild(fragment); //将排序的结果替换掉之前的值  
	                tbody.sortCol = Idx;  
	            }  
	        }  
	    });         
	})(jQuery);  

  </script>
   <% Date d = new Date(); 
  	String date = new SimpleDateFormat("yyyy-MM-dd").format(d);
  	Date d1 = new Date();
  	String date1 = new SimpleDateFormat("yyyy-MM-dd").format(d);
  	%>
  <body>
  <ul style="margin-left:-40px">
      <li style="float:left"><a href="<%=request.getContextPath() %>/analyse/analyseBussiness">业绩分析</a></li>
      <li style="float:left">&nbsp;&nbsp;&nbsp;</li>
      <li style="float:left"><a href="<%=request.getContextPath()%>/analyse/sexAnalyse/全部/全部/2015-08-01/<%=date1%>">性别分析</a></li>
      <li style="float:left">&nbsp;&nbsp;&nbsp;</li>
      <li style="float:left"><a href="<%=request.getContextPath()%>/analyse/schoolAnalyse/2015-08-01/<%=date1%>">学校分析</a></li>
      <li style="float:left">&nbsp;&nbsp;&nbsp;</li>
      <li style="float:left"><a href="<%=request.getContextPath()%>/analyse/stationAnalyse/华南农业大学/2015-08-01/<%=date1%>">站点分析</a></li>
      <li style="float:left">&nbsp;&nbsp;&nbsp;</li>
      <li style="float:left"><a href="<%=request.getContextPath() %>/analyse/analyse">品种分析</a></li>
    </ul>
  <br><br>
  
  	<div align="center">欢迎登录,${sessionScope.admin.AUserName}</div>
	<a href="<%=request.getContextPath()%>/fruit/manageFruit">返回</a>
	
		区间大小:<input type="text" name="region" id="region" value="${region}"/>
		开始时间:<input type="date" name="from" id="from" value="${from}" max="<%=date%>">
		结束时间:<input type="date" name="to" id="to" value="${to}" max="<%=date%>"/>			
		<a href="javascript:select()">查询</a>
	
	<img src="${charturl}"/>
  	<table border="1px" id="tableSort">
  		<thead>
  		<tr>
  			<th>日期</th>
  			<th>营业额<a href="javascript:$.sortTable.sort('tableSort',1,0)">▲</a><a href="javascript:$.sortTable.sort('tableSort',1,1)">▼</a></th>
  		</tr>
  		</thead>
  		<c:forEach items="${map}" var="bussiness">
  			<tr>
  				<td>${bussiness.key}</td>
  				<td>${bussiness.value}</td>
  			</tr>
  		</c:forEach>
  	</table> 
  
  </body>
</html>
 --%>