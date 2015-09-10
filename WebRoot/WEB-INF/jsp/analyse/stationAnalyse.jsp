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
	function checkDate()
	  {
	  	var date1=document.getElementById("beginDate").value;
	  	var date2=document.getElementById("endDate").value;
	  	var flag=true;
	  	if(date1==""||date2=="")
	 		{
	 		    document.getElementById("warn").innerHTML="任一时间不能为空！";
	 		    flag=false;
	 		}
	  	else
	 		{
	  		var str1=date1.split("-");
	  		var integer1=parseInt(str1[0]+""+str1[1]+""+str1[2]);
	  		var str2=date2.split("-");
	  		var integer2=parseInt(str2[0]+""+str2[1]+""+str2[2]);
	  		if(integer1>integer2)
	  		  {
	  			document.getElementById("warn").innerHTML="请选择有效的时间组合，开始日期必须小于等于结束日期！";
	  			flag=false;
	  		  }
	 		}
	  	if(flag)
	  		document.getElementById("warn").innerHTML="";
	  }
	  
	  function test()
	  {
		  var flag=document.getElementById("warn").innerHTML;
		  if(flag!="")
			 document.getElementById("warn").innerHTML="输入不规范，请检查后再提交";
		  else
			 search();
	  }
	  
	  function search()
	  {
		 var beginDate=document.getElementById("beginDate").value;
		 var endDate=document.getElementById("endDate").value;
		 var school=document.getElementById("school").value;
		 
		 var urlStr = "<%=request.getContextPath()%>/analyse/stationAnalyseAjax/"+school+"/"+beginDate+"/"+endDate;
			$.ajax({
				method: "GET",
				url: urlStr,
				success:function(data,status,jqXHR){
					document.getElementById("station").innerHTML="";
					var THEAD = document.createElement('thead');
					var TR = document.createElement('tr');
					TR.setAttribute("name", "head")
					var th1 = document.createElement('th');
					th1.innerHTML="站点";
					var th2 = document.createElement('th');
					th2.innerHTML="下单量"+"<span class='fa fa-caret-up caret-up' onclick='javascript:$.sortTable.sort(\"station\",1,0)'></span>"+
					"<span class='fa fa-caret-down caret-down' onclick='javascript:$.sortTable.sort(&quot;station&quot;,1,1)'></span>";
					var th3 = document.createElement('th');
					th3.innerHTML="下单量比例"+"<span class='fa fa-caret-up caret-up' onclick='javascript:$.sortTable.sort(\"station\",1,0)'></span>"+
					"<span class='fa fa-caret-down caret-down' onclick='javascript:$.sortTable.sort(&quot;station&quot;,1,1)'></span>";
					var th4 = document.createElement('th');
					th4.innerHTML="营业额"+"<span class='fa fa-caret-up caret-up' onclick='javascript:$.sortTable.sort(\"station\",3,0)'></span>"+
					"<span class='fa fa-caret-down caret-down' onclick='javascript:$.sortTable.sort(&quot;station&quot;,3,1)'></span>";
					var th5 = document.createElement('th');
					th5.innerHTML="营业额比例"+"<span class='fa fa-caret-up caret-up' onclick='javascript:$.sortTable.sort(\"station\",3,0)'></span>"+
					"<span class='fa fa-caret-down caret-down' onclick='javascript:$.sortTable.sort(&quot;station&quot;,3,1)'></span>";
					TR.appendChild(th1);
					TR.appendChild(th2);
					TR.appendChild(th3);
					TR.appendChild(th4);
					TR.appendChild(th5);
					THEAD.appendChild(TR)
					
					var tb=document.getElementById("station");
					tb.appendChild(THEAD);
					
					var tbody=document.createElement('tbody');
					for(var i=0;i<data.area.length;i++){
						TR = document.createElement('tr');
						TR.setAttribute('align',"right");
						th1 = document.createElement('td');
						th1.innerHTML=data.area[i];
						th2 = document.createElement('td');
						th2.innerHTML=data.num[i];
						th3 = document.createElement('td');
						th3.innerHTML=data.radio[i];
						th4 = document.createElement('td');
						th4.innerHTML=data.money[i];
						th5 = document.createElement('td');
						th5.innerHTML=data.radioMoney[i];
						TR.appendChild(th1);
						TR.appendChild(th2);
						TR.appendChild(th3);
						TR.appendChild(th4);
						TR.appendChild(th5);
						tbody.appendChild(TR);
					}
					tb.appendChild(tbody);
					document.getElementById("img").src="<%=request.getContextPath()%>/DisplayChart?filename="+data.fileName;
					document.getElementById("img1").src="<%=request.getContextPath()%>/DisplayChart?filename="+data.fileName1;
				}
			}); // end ajax
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
						<li><a href="<%=request.getContextPath() %>/analyse/analyseBussiness">业绩分析</a></li>
						<li><a href="<%=request.getContextPath()%>/analyse/schoolAnalyse/2015-08-01/<%=date%>" >学校分析</a></li>
						<li><a href="<%=request.getContextPath()%>/analyse/stationAnalyse/华南农业大学/2015-08-01/<%=date%>" class="active">站点分析</a></li>
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
							<span class="setSpace">学校:</span>
								<select class="input school" id="school">
								</select>
							<span class="setSpace">开始时间:</span>
								<input type="date" class="input cls3" name="beginDate" id="beginDate" value="2015-08-01" min="2015-08-01" max="<%=date%>" onInput="checkDate()"/>
							<span class="setSpace">结束时间:</span>
								<input type="date" class="input cls3" name="endDate" id="endDate" value="<%=date%>" max="<%=date%>" onInput="checkDate()"/>
							<button class="btn fr" onClick="test()">查询</button>
							<span id="warn" style="color:red"></span><br><br>
							
						    <h3>统计图</h3>

						    <div class="cartogram">
							    <!--销售量统计图 -->
							    <div class="cartogram_1">
									<img alt="" id="img" src="<%=path%>/DisplayChart?filename=${fileName}">
   
							    </div>

							    <!-- 营业额统计图 -->
							    <div class="cartogram_2">
									 <img alt="" id="img1" src="<%=path%>/DisplayChart?filename=${fileName1}">
							    </div>
							</div>

						    <h3>统计表</h3>

						    <table class="table" id="station">
								<thead>
									<tr name="head">
										<th>站点</th>
										<th>下单量
											<span class="fa fa-caret-up caret-up" onclick="javascript:$.sortTable.sort('station',1,0)"></span>
											<span class="fa fa-caret-down caret-down" onclick="javascript:$.sortTable.sort('station',1,1)"></span>
										</th>
										<th>下单量比例
											<span class="fa fa-caret-up caret-up" onclick="javascript:$.sortTable.sort('station',1,0)"></span>
											<span class="fa fa-caret-down caret-down" onclick="javascript:$.sortTable.sort('station',1,1)"></span>
										</th>
										<th>营业额
											<span class="fa fa-caret-up caret-up" onclick="javascript:$.sortTable.sort('station',3,0)"></span>
											<span class="fa fa-caret-down caret-down" onclick="javascript:$.sortTable.sort('station',3,1)"></span>
										</th>
										<th>营业额比例
											<span class="fa fa-caret-up caret-up" onclick="javascript:$.sortTable.sort('station',3,0)"></span>
											<span class="fa fa-caret-down caret-down" onclick="javascript:$.sortTable.sort('station',3,1)"></span>
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${map.area}" var="item" varStatus="i">
								       <c:forEach items="${map.num}" var="item1" varStatus="j" begin="${i.index}" end="${i.index}">
								        <c:forEach items="${map.money}" var="item2" varStatus="k" begin="${i.index}" end="${i.index}">
								        <tr align="center">
								          <td>${item}</td>
								          <td>${item1}</td>
								          <td>
								            <c:if test="${all!=0}">
								             <fmt:formatNumber type="PERCENT" value="${item1/all}" maxFractionDigits="2"/>
								            </c:if>
								            <c:if test="${all==0}">
								             0%
								            </c:if>
								          </td>
								          <td>${item2}</td>
								          <td>
								            <c:if test="${allMoney!=0}">
								             <fmt:formatNumber type="PERCENT" value="${item2/allMoney}" maxFractionDigits="2"/>
								            </c:if>
								            <c:if test="${allMoney==0}">
								             0%
								            </c:if>
								          </td>
								        </tr>
								        </c:forEach>
								       </c:forEach>
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
		
		
		jQuery(function($){
			var urlStr = "<%=request.getContextPath()%>/analyse/getSchools";
			$.ajax({
				method: "GET",
				url: urlStr,
				success:function(data,status,jqXHR){
					 var a=document.getElementById("school");
					 var school=data;
					 var option;
				     for(var i=0;i<school.length;i++){
				    	 option = document.createElement('option');
				    	 option.innerHTML=school[i];
						 a.appendChild(option);
				     }
				}
			}); // end ajax 
		});
		
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


<%-- <%@ page language="java" import="java.util.*,java.text.*,org.jfree.chart.servlet.ServletUtilities" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'sexAnalyse.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	
  <script>
  function checkDate()
  {
  	var date1=document.getElementById("beginDate").value;
  	var date2=document.getElementById("endDate").value;
  	var flag=true;
  	if(date1==""||date2=="")
 		{
 		    document.getElementById("warn").innerHTML="任一时间不能为空！";
 		    flag=false;
 		}
  	else
 		{
  		var str1=date1.split("-");
  		var integer1=parseInt(str1[0]+""+str1[1]+""+str1[2]);
  		var str2=date2.split("-");
  		var integer2=parseInt(str2[0]+""+str2[1]+""+str2[2]);
  		if(integer1>integer2)
  		  {
  			document.getElementById("warn").innerHTML="请选择有效的时间组合，开始日期必须小于等于结束日期！";
  			flag=false;
  		  }
 		}
  	if(flag)
  		document.getElementById("warn").innerHTML="";
  }
  
  function test()
  {
	  var flag=document.getElementById("warn").innerHTML;
	  if(flag!="")
		 document.getElementById("warn").innerHTML="输入不规范，请检查后再提交";
	  else
		 search();
  }
  
  function search()
  {
	 var beginDate=document.getElementById("beginDate").value;
	 var endDate=document.getElementById("endDate").value;
	 var school=document.getElementById("school").value;
	 
	 var urlStr = "<%=request.getContextPath()%>/analyse/stationAnalyseAjax/"+school+"/"+beginDate+"/"+endDate;
		$.ajax({
			method: "GET",
			url: urlStr,
			success:function(data,status,jqXHR){
				document.getElementById("station").innerHTML="";
				var TR = document.createElement('tr');
				var th1 = document.createElement('th');
				th1.innerHTML="站点";
				var th2 = document.createElement('th');
				th2.innerHTML="下单量";
				var th3 = document.createElement('th');
				th3.innerHTML="下单量比例";
				var th4 = document.createElement('th');
				th4.innerHTML="营业额";
				var th5 = document.createElement('th');
				th5.innerHTML="营业额比例";
				TR.appendChild(th1);
				TR.appendChild(th2);
				TR.appendChild(th3);
				TR.appendChild(th4);
				TR.appendChild(th5);
				var tb=document.getElementById("station");
				tb.appendChild(TR);
				
				for(var i=0;i<data.area.length;i++){
					TR = document.createElement('tr');
					TR.setAttribute('align',"center");
					th1 = document.createElement('td');
					th1.innerHTML=data.area[i];
					th2 = document.createElement('td');
					th2.innerHTML=data.num[i];
					th3 = document.createElement('td');
					th3.innerHTML=data.radio[i];
					th4 = document.createElement('td');
					th4.innerHTML=data.money[i];
					th5 = document.createElement('td');
					th5.innerHTML=data.radioMoney[i];
					TR.appendChild(th1);
					TR.appendChild(th2);
					TR.appendChild(th3);
					TR.appendChild(th4);
					TR.appendChild(th5);
					tb.appendChild(TR);
				}
				document.getElementById("img").src="<%=request.getContextPath()%>/DisplayChart?filename="+data.fileName;
				document.getElementById("img1").src="<%=request.getContextPath()%>/DisplayChart?filename="+data.fileName1;
			}
		}); // end ajax
  }
  
  jQuery(function($){
		var urlStr = "<%=request.getContextPath()%>/analyse/getSchools";
		$.ajax({
			method: "GET",
			url: urlStr,
			success:function(data,status,jqXHR){
				 var a=document.getElementById("school");
				 var school=data;
				 var option;
			     for(var i=0;i<school.length;i++){
			    	 option = document.createElement('option');
			    	 option.innerHTML=school[i];
					 a.appendChild(option);
			     }
			}
		}); // end ajax 
	});
  
  </script>

  </head>
  
  <body>
  <%
    Date date= new Date(new Date().getTime()-24*60*60*1000);
    String date1=new SimpleDateFormat("yyyy-MM-dd").format(date);
  %>
  
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
    
    <br><br><br>
   <font style="font-size:20px;font-weight:bold">站点统计</font> 
   
   <br><br>
         学校：<select id="school"> 
       </select>
       
        开始时间：<input type="date" name="beginDate" id="beginDate" value="2015-08-01" min="2015-08-01" max="<%=date1%>" onInput="checkDate()"/>
        结束时间：<input type="date" name="endDate" id="endDate" value="<%=date1%>" max="<%=date1%>" onInput="checkDate()"/> 
        
   <input type="button" value="提交" onClick="test()"/>
   <span id="warn" style="color:red"></span><br><br><br><br>
   
    <img alt="" id="img" src="<%=path%>/DisplayChart?filename=${fileName}">
    <img alt="" id="img1" src="<%=path%>/DisplayChart?filename=${fileName1}">
   
   <br>
   <table border=1 width="800px" id="station">
      <tr>
         <th>站点</th>
         <th>下单量</th>
         <th>下单量比例</th>
         <th>营业额</th>
         <th>营业额比例</th>
      </tr>
       <c:forEach items="${map.area}" var="item" varStatus="i">
       <c:forEach items="${map.num}" var="item1" varStatus="j" begin="${i.index}" end="${i.index}">
        <c:forEach items="${map.money}" var="item2" varStatus="k" begin="${i.index}" end="${i.index}">
        <tr align="center">
          <td>${item}</td>
          <td>${item1}</td>
          <td>
            <c:if test="${all!=0}">
             <fmt:formatNumber type="PERCENT" value="${item1/all}" maxFractionDigits="2"/>
            </c:if>
            <c:if test="${all==0}">
             0%
            </c:if>
          </td>
          <td>${item2}</td>
          <td>
            <c:if test="${allMoney!=0}">
             <fmt:formatNumber type="PERCENT" value="${item2/allMoney}" maxFractionDigits="2"/>
            </c:if>
            <c:if test="${allMoney==0}">
             0%
            </c:if>
          </td>
        </tr>
        </c:forEach>
       </c:forEach>
      </c:forEach> 
   </table>
   
  </body>
</html>
 --%>