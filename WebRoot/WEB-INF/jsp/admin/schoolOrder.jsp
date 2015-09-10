<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'schoolOrder.jsp' starting page</title>
    
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
		 document.getElementById("warn").innerHTML="输入不规范，请检查后再点击查询";
	  else
	  {	 
		  search();
		  search1();
	  }
  }
  
  function toDateString(obj){
	  var result="";
	  result+=obj.getFullYear();
	  
	  if((obj.getMonth()+1)>=10)
	  	result+="-"+(obj.getMonth()+1);
	  else
		result+="-0"+(obj.getMonth()+1);
	 
	  if(obj.getDate()>=10)
	  	result+="-"+obj.getDate();
	  else
		result+="-0"+obj.getDate();  

	  if(obj.getHours()>=10)
	  	result+=" "+obj.getHours();
	  else
		result+=" 0"+obj.getHours();
	  
	  if(obj.getMinutes()>=10)
	  	result+=":"+obj.getMinutes();
	  else
		result+=":0"+obj.getMinutes();
	  
	  if(obj.getSeconds()>=10)
	 	 result+=":"+obj.getSeconds();
	  else
		 result+=":0"+obj.getSeconds();
  	return result;
  }
  
  function search()
  {
	 var orderType=document.getElementById("orderType").value;
	 var beginDate=document.getElementById("beginDate").value;
	 var endDate=document.getElementById("endDate").value;
	 var area=document.getElementById("area").value;
	 
	 var urlStr = "<%=request.getContextPath()%>/admin/searchOrder/${school}/"+area+"/"+orderType+"/"+beginDate+"/"+endDate;
		$.ajax({
			method: "GET",
			url: urlStr,
			success:function(data,status,jqXHR){
			  document.getElementById("orderList").innerHTML="";
			  var TR = document.createElement('tr');
			  var th1 = document.createElement('th');
			  th1.innerHTML="验证码";
			  var th2 = document.createElement('th');
			  th2.innerHTML="下单日期";
			  var th3 = document.createElement('th');
			  th3.innerHTML="地区";
			  var th4 = document.createElement('th');
			  th4.innerHTML="状态";
			  var th5 = document.createElement('th');
			  th5.innerHTML="操作";
			  TR.appendChild(th1);
			  TR.appendChild(th2);
			  TR.appendChild(th3);
			  TR.appendChild(th4);
			  TR.appendChild(th5);
			  var tb=document.getElementById("orderList");
			  tb.appendChild(TR);
			  
			  var list = data.list;	
			  document.getElementById("json").value=data.json;
			  var order;
			  for(var i = 0;i<list.length;i++){
				order = list[i];
			    var tr = document.createElement('tr');
			    tr.setAttribute('id',order.orderNum+"1");
			    var td1 = document.createElement('td');
				td1.innerHTML=order.code;
				var td2 = document.createElement('td');
				var d=new Date(order.orderTime);
				td2.innerHTML=toDateString(d);
				var td3 = document.createElement('td');
				td3.innerHTML=order.area;
				var td4 = document.createElement('td');
				td4.setAttribute('id',order.orderNum);
				td4.innerHTML=order.status;
				if(order.status=='未提货'){
					var td5 = document.createElement('td');
					td5.setAttribute('id',order.orderNum+"2");
					td5.innerHTML="<a href='javascript:sureAndDel(&quot;"+order.orderNum+"&quot;);'>确认提货</a>";
				}
			    tr.appendChild(td1);
				tr.appendChild(td2);
				tr.appendChild(td3);
				tr.appendChild(td4);
				if(order.status=='未提货'){
					tr.appendChild(td5);
				}
				tb.appendChild(tr);
				document.getElementById("result").innerHTML="";
			  }
			  if(list.length==0)
				  document.getElementById("result").innerHTML="该时间段内没有符合条件的订单！";
			}
		}); // end ajax
  }
  
  function search1()
  {
	 var beginDate=document.getElementById("beginDate").value;
	 var endDate=document.getElementById("endDate").value;
	 var area=document.getElementById("area").value;
	 
	 var urlStr = "<%=request.getContextPath()%>/admin/searchOrder1/${school}/"+beginDate+"/"+endDate;
		$.ajax({
			method: "GET",
			url: urlStr,
			success:function(data,status,jqXHR){
			  document.getElementById("goodsList").innerHTML="";
			  var TR = document.createElement('tr');
			  var th1 = document.createElement('th');
			  th1.innerHTML="商品名";
			  var th2 = document.createElement('th');
			  th2.innerHTML="数量";
			  var th3 = document.createElement('th');
			  th3.innerHTML="重量";
			  
			  TR.appendChild(th1);
			  TR.appendChild(th2);
			  TR.appendChild(th3);
			  
			  var tb=document.getElementById("goodsList");
			  tb.appendChild(TR);
			  
			  var result = data;
			  
			  var order;
			  
			  for(var i = 0;i<result.length;i++){
				goods = result[i];
			    var tr = document.createElement('tr');
			  	
			    var td1 = document.createElement('td');
				td1.innerHTML=goods.location+goods.fruitType;
				var td2 = document.createElement('td');
				td2.innerHTML=goods.num;
				var td3 = document.createElement('td');
				td3.innerHTML=goods.totalWeight;
				
				tr.appendChild(td1);
				tr.appendChild(td2);
				tr.appendChild(td3);
				tb.appendChild(tr);
			  }
		
			}
		}); // end ajax
  }
  
  function sureAndDel(orderNum)
	{
	    var xmlhttp;
		if (window.XMLHttpRequest)
		  {// code for IE7+, Firefox, Chrome, Opera, Safari
		  xmlhttp=new XMLHttpRequest();
		  }
		else
		  {// code for IE6, IE5
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    { 
			  var size=document.getElementById("size").innerHTML;
			  document.getElementById("size").innerHTML=parseInt(size)-1;
			  document.getElementById(orderNum).innerHTML="已提货";
			  var obj = document.getElementById(orderNum+"2");
			  var tr = obj.parentNode;
			  tr.removeChild(obj);
			 
			  var num=document.getElementById("current").value;
			 	if(num!="")
			  document.getElementById(num+"1").style.color="black";
			  document.getElementById("current").value=orderNum;
			  document.getElementById(orderNum+"1").style.color="red";
		    }
		  }
		xmlhttp.open("GET","<%=request.getContextPath()%>/admin/sureOrder/"+orderNum,true);
		xmlhttp.send();	
	}
  
  function checkCode(value)
  {
	  var json =document.getElementById("json").value;
	  var allOrder = eval('(' + json + ')'); 
	  var order;
	  for(var i = 0;i<allOrder.length;i++){
			order = allOrder[i];	
			if(order.code==value)
				{
				 var num=document.getElementById("current").value;
			     if(num!="")
				 	document.getElementById(num+"1").style.color="black";
				  document.getElementById("current").value=order.orderNum;
				  document.getElementById(order.orderNum+"1").style.color="red";
				  var forword=window.location.href;
				  var str=forword.split("#");
				  window.location.href=str[0]+"#"+order.orderNum;
				  break;
				}
		}
  }
  
  jQuery(function($){
		var urlStr = "<%=request.getContextPath()%>/admin/getAreas/${school}";
 		$.ajax({
			method: "GET",
			url: urlStr,
			success:function(data,status,jqXHR){
				 var a=document.getElementById("area");
				 var area=data.areaList;
				 var option;
			     for(var i=0;i<area.length;i++){
			    	 option = document.createElement('option');
			    	 option.innerHTML=area[i];
					 a.appendChild(option);
			     }
			     document.getElementById("size").innerHTML=data.size;
			}
		}); // end ajax 
	});
  
  function print()
  {
	  var flag=document.getElementById("warn").innerHTML;
	  if(flag!="")
		 document.getElementById("warn").innerHTML="输入不规范，请检查后再点击导出excel";
	  else
	  {
		 var orderType=document.getElementById("orderType").value;
		 var beginDate=document.getElementById("beginDate").value;
		 var endDate=document.getElementById("endDate").value;
		 var area=document.getElementById("area").value;
		 
		 if(orderType==0)
			 var str="确定要打印"+area+"站点的，自"+beginDate+"至"+endDate+"的未提货订单？";
		 if(orderType==1)
			 var str="确定要打印"+area+"站点的，自"+beginDate+"至"+endDate+"的全部订单？";
		 if(orderType==2)
			 var str="确定要打印"+area+"站点的，自"+beginDate+"至"+endDate+"的已提货订单？";
		 
		 if(!confirm(str)){
			 window.event.returnValue=false;
		 }
		 else
		 {
			 var urlStr = "<%=request.getContextPath()%>/admin/print/${school}/"+area+"/"+orderType+"/"+beginDate+"/"+endDate;
			 $.ajax({
				method: "GET",
				url: urlStr,
				success:function(data,status,jqXHR){
					window.location.href="<%=request.getContextPath()%>/resources/upload/excel/"+data;
				}
			 }); 
		 } 
	  }
  }
  </script>
  </head>
  
  <body>
  <input type="hidden" value="" id="current"/>
  <input type="hidden" value="${json}" id="json"/>
  <%
    Date date= new Date(new Date().getTime()-24*60*60*1000);
    String date1=new SimpleDateFormat("yyyy-MM-dd").format(date);
  %>
  <a href="javascript:print();">导出excel表格</a><br/><br/>
  
      还有<span id="size"></span>个未提货的订单<br><br><br>
  
      请输入验证码<input id="code" name="code" type="text" onInput="checkCode(this.value)"/>
  
  <br>
        站点：
	   <select name="area" id="area">
	      <option>全部</option>
	   </select>
	   
        类型：
	   <select name="orderType" id="orderType">
	     <option value="0">未提货</option>
	     <option value="1">全部</option>
	     <option value="2">已提货</option>
	   </select><br>
	   
      下单日期：
	    <input type="date" name="beginDate" id="beginDate" value="<%=date1%>" max="<%=date1%>" onInput="checkDate()"/>
	     ---
	    <input type="date" name="endDate" id="endDate" value="<%=date1%>" max="<%=date1%>" onInput="checkDate()"/>	    
	    <input type="button" value="查询" onClick="test()"/>
	    <span id="warn" style="color:red"></span><br>
	    
    <table id="orderList" border=1>
        <tr>
          <th>验证码</th>
          <th>下单日期</th>
          <th>地区</th>
          <th>状态</th>
          <th>操作</th>
        </tr>
	    <c:forEach items="${list}" var="item" varStatus="i">
        <tr id="${item.orderNum}1">
          <td>${item.code}</td>
          <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.orderTime}"/></td>
          <td>${item.area}</td>
          <td id="${item.orderNum}">${item.status}</td>
          <c:if test="${item.status=='未提货'}">
            <td id="${item.orderNum}2"><a href="javascript:sureAndDel('${item.orderNum}');">确认提货</a></td>
          </c:if>
        </tr>
      </c:forEach>   
	</table>
	
	
	<span id="result" style="color:red"></span>
	
	<br><br><br><br>
	<table id="goodsList" border=1>
		<tr>
			<th>商品名</th>
			<th>数量</th>
			<th>重量</th>
		</tr>
		  <c:forEach items="${list1}" var="goods">
	   	  	<tr>
		   	  	<td>${goods.location}${goods.fruitType}</td>
		   	  	<td>${goods.num}</td>
		   	  	<td>${goods.totalWeight}</td>
	   	  	</tr>
	   	  </c:forEach>
	</table>
  </body>
</html>
