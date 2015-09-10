<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    
    <title>My JSP 'perOrder.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
  <script type="text/javascript">
    function show(type)
    {
    	if(type==0){
    		document.getElementById("all").style.display="block";
    		document.getElementById("noPay").style.display="none";
    		document.getElementById("noGet").style.display="none";
    		document.getElementById("yesGet").style.display="none";
    		document.getElementById("cancel").style.display="none";
    	}
    	if(type==1){
    		document.getElementById("all").style.display="none";
    		document.getElementById("noPay").style.display="block";
    		document.getElementById("noGet").style.display="none";
    		document.getElementById("yesGet").style.display="none";
    		document.getElementById("cancel").style.display="none";
    	}
    	if(type==2){
    		document.getElementById("all").style.display="none";
    		document.getElementById("noPay").style.display="none";
    		document.getElementById("noGet").style.display="block";
    		document.getElementById("yesGet").style.display="none";
    		document.getElementById("cancel").style.display="none";
    	}
    	if(type==3){
    		document.getElementById("all").style.display="none";
    		document.getElementById("noPay").style.display="none";
    		document.getElementById("noGet").style.display="none";
    		document.getElementById("yesGet").style.display="block";
    		document.getElementById("cancel").style.display="none";
    	}
    	if(type==4){
    		document.getElementById("all").style.display="none";
    		document.getElementById("noPay").style.display="none";
    		document.getElementById("noGet").style.display="none";
    		document.getElementById("yesGet").style.display="none";
    		document.getElementById("cancel").style.display="block";
    	}
    }
  </script>

  </head>
  
  <body>
     <a href="javascript:show(0);">全部</a>&nbsp;&nbsp;&nbsp;
     <a href="javascript:show(1);">待付款</a>&nbsp;&nbsp;&nbsp;
     <a href="javascript:show(2);">未提货</a>&nbsp;&nbsp;&nbsp;
     <a href="javascript:show(3);">已提货</a>&nbsp;&nbsp;&nbsp;
     <a href="javascript:show(4);">已取消</a><br><br>
     
     <!-- 全部 -->
     <table id="all"style="display:block">
       <c:forEach items="${list}" var="item" varStatus="i">
        <tr>
          <td>订单号</td>
          <td>${item.orderNum}</td>
        </tr>
        <tr>
          <td>状态</td>
          <td>${item.status}</td>
        </tr>
        
        <c:if test="${item.status=='未提货'}">
        <tr>
            <td>验证码</td>
            <td>${item.code}</td>
        </tr>
        </c:if>
         
        <c:if test="${item.status=='待付款'}"> 
        <tr>
            <td><a href="##">去付款</a></td>
        </tr>
        </c:if>
      </c:forEach>  
     </table>
     
     <!-- 待付款 -->
     <table id="noPay" style="display:none">
     <c:forEach items="${list}" var="item" varStatus="i">
       <c:if test="${item.status=='待付款'}"> 
        <tr>
          <td>订单号</td>
          <td>${item.orderNum}</td>
        </tr>
        <tr>
          <td>状态</td>
          <td>${item.status}</td>
        </tr>
         
        <tr>
            <td><a href="##">去付款</a></td>
        </tr>
        </c:if>
       </c:forEach> 
     </table>   
     
     <!-- 未提货-->
     <table id="noGet" style="display:none">
     <c:forEach items="${list}" var="item" varStatus="i">
       <c:if test="${item.status=='未提货'}">
        <tr>
          <td>订单号</td>
          <td>${item.orderNum}</td>
        </tr>
        <tr>
          <td>状态</td>
          <td>${item.status}</td>
        </tr>
        <tr>
            <td>验证码</td>
            <td>${item.code}</td>
        </tr>
      </c:if>
      </c:forEach>  
     </table>         
     
     <!-- 已提货-->
     <table id="yesGet" style="display:none">
     <c:forEach items="${list}" var="item" varStatus="i">
       <c:if test="${item.status=='已提货'}">
        <tr>
          <td>订单号</td>
          <td>${item.orderNum}</td>
        </tr>
        <tr>
          <td>状态</td>
          <td>${item.status}</td>
        </tr>
      </c:if>
     </c:forEach>
     </table>    
     
     <!-- 已取消-->
     <table id="cancel" style="display:none">
     <c:forEach items="${list}" var="item" varStatus="i">
       <c:if test="${item.status=='已取消'}">
        <tr>
          <td>订单号</td>
          <td>${item.orderNum}</td>
        </tr>
        <tr>
          <td>状态</td>
          <td>${item.status}</td>
        </tr>
      </c:if>
     </c:forEach>
     </table>    
     
  </body>
</html>
