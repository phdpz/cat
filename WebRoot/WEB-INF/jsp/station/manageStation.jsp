<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include  file="/WEB-INF/jsp/admin/managerHead.jsp"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<title>猫鲜生后台管理系统</title>

		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/font-awesome-4.3.0/css/font-awesome.min.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/style/admin/index.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/style/admin/siteManagement.css" />

	</head>
	<script type="text/javascript">
		function changeStation(id,area){
	  		var urlStr = "<%=request.getContextPath()%>/station/updateArea/"+id+"/"+area;
			//alert("Before Call:"+urlStr);
			$.ajax({
				method: "GET",
				url: urlStr,
				success:function(data,status,jqXHR){
					//alert("Success:"+data);
					var result = data;
				}
			}); // end ajax
	  	}
		
		function removeArea(obj){
			document.getElementById("tr"+obj).remove();
			
			var urlStr = "<%=request.getContextPath()%>/station/deleteArea/"+obj;
			//alert("Before Call:"+urlStr);
			$.ajax({
				method: "GET",
				url: urlStr,
				success:function(data,status,jqXHR){
					//alert("Success:"+data);
					var result = data;
				}
			}); // end ajax
		}
		
		function updateAdmin(){	
			var urlStr = "<%=request.getContextPath()%>/admin/updateAdmin/"+$("#userName").val()+"/"+
			$("#password").val()+"/"+$("#name").val()+"/"+$("#phone").val()+"/"+$("#email").val()+"/${school}";
		
			//alert("Before Call:"+urlStr);
			$.ajax({
				method: "GET",
				url: urlStr,
				success:function(data,status,jqXHR){
					//alert("Success:"+data);
					var result = data;
				}
			}); // end ajax
		}
		
		function addArea(){
			
			
			 var urlStr = "<%=request.getContextPath()%>/station/addArea/${school}";
			//alert("Before Call:"+urlStr);
			$.ajax({
				method: "GET",
				url: urlStr,
				success:function(data,status,jqXHR){
					//alert("Success:"+data);
					var result = data;
					
					var tb=document.getElementById("t_body");
					var aTr=document.getElementById("appendTable");
					var last=aTr.rowIndex;
					/* var tr = document.createElement('tr');
					var td1 = document.createElement('td');
					var td2 = document.createElement('td');
					var td3 = document.createElement('td'); */
					
					/* td1.innerHTML = "";
					td2.innerHTML = ""+(parseInt(last)-1)+"、<input type='text' onchange='changeStation(1,this.value)'/>";
					td3.innerHTML = "<button type='button' onclick='removeArea(${station.SId})'>删除</button>";
					tr.appendChild(td1);
					tr.appendChild(td2);
					tr.appendChild(td3); */
					var newTr = tb.insertRow(last);
					newTr.setAttribute("id","tr"+result);
					
					var newTd3 = newTr.insertCell();
					newTd3.innerHTML="<button type='button' onclick='removeArea("+result+")'>删除</button>";
					var newTd2 = newTr.insertCell();
					newTd2.innerHTML=""+(parseInt(last)-1)+"、<input type='text' onchange='changeStation("+result+",this.value)'/>";
					var newTd1 = newTr.insertCell();
					newTd1.innerHTML="";
				}
			}); // end ajax 
		}
	</script>
	<body>
			<!-- content -->
			<div class="content">

				<h2 class="title">站点管理</h2>

				<div class="container">

						<form class="wrap" action="" method="">
							<table>
								<tbody id="t_body">
									<tr>
										<td>学校：</td>
										<td name="spec">
											<select id="select" value="">
												<c:forEach items="${schoolList}" var="school">
													<option>${school}</option>
												</c:forEach>
												<!-- <optgroup label="h">
													<option value="华南农业大学">华南农业大学</option>
													<option value="华南理工大学">华南理工大学</option>
												</optgroup>
												<optgroup label="z">
													<option value="中山大学">华南农业大学</option>
												</optgroup> -->
											</select>
											<span>共${schoolList.size()}个站点</span>
										</td>
										<td><button class="fa fa-plus" type="button"  id="addSite" > 新增站点</button></td>
									</tr>
									<tr>
										<td>学校：</td>
										<td name="spec"><input type="text" value="华南农业大学" required="required" /></td>
										<td><button type="button">删除</button></td>
									</tr>
									<c:forEach items="${stationList}" var="station" varStatus="i">
									<tr id="tr${station.SId}">
										<td><c:if test="${i.index==0}">提货点：</c:if></td>
										<td>${i.index+1}、<input type="text" value="${station.SArea}" onchange="changeStation(${station.SId},this.value)"/></td>
										<td><button type="button" onclick="removeArea(${station.SId})">删除</button></td>
									</tr>
									</c:forEach>
									<tr id="appendTable"></tr>
									<tr id="addSite">
										<td></td>
										<td name="spec"><button type="button" id="add" onclick="addArea()">新增</button></td>
									</tr>
									<tr>
										<td>管理员：</td>
										<td name="spec"><input type="text" value="张山" /></td>
										<td><button type="button" id="editManager">修改资料</button></td>
									</tr>
								</tbody>
							</table>
						
						<form action="">
						<!-- 修改管理员资料 -->
						<div id="managerBox">
							<table>
								<tr>
									<td>用户名：</td>
									<td><input type="text" id="userName" value="${admin.AUserName}" /></td>
								</tr>
								<tr>
									<td>密码：</td>
									<td><input type="text" id="password" value="${admin.APassword }" /></td>
								</tr>
								<tr>
									<td>管理员姓名：</td>
									<td><input type="text" id="name" value="${admin.AStuName }" /></td>
								</tr>
								<tr>
									<td>电话：</td>
									<td><input type="text" id="phone" value="${admin.AStuPhone }" /></td>
								</tr>
								<tr>
									<td>邮箱：</td>
									<td><input type="text" id="email" value="${admin.AStuMail }" /></td>
								</tr>
								<tr>
									<td></td>
									<td><button type="button" id="sureNanagerEdit" onclick="updateAdmin()">确定修改</button></td>
								</tr>
							</table>
						</div>	
						<!-- 修改管理员资料 -->
					
						
						<button type="submit" class="confirm">保存修改</button>

						</form>

				</div><!-- end container -->
				
			</div><!-- end content -->
			
		</div><!-- body -->

		<script src="<%=request.getContextPath()%>/resources/script/admin/jquery-2.1.4.min.js"></script>
		<script src="<%=request.getContextPath()%>/resources/script/admin/index.js"></script>
		<script>
		$(window).load( function () {

			// 修改管理员用户名和密码
			var $editManager = $('#editManager');
			var $managerBox = $('#managerBox');
			var $sureNanagerEdit = $('#sureNanagerEdit');
			$editManager.click( function() {
				$managerBox.show();
			});

			$sureNanagerEdit.click( function() {
				$managerBox.hide();
			})

			// 新增站点
			var $addSite = $('#addSite');
			$addSite.click( function() {

			});

		});
		
		</script>

	</body>
</html>