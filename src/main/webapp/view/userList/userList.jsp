<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="panel-fit">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>用户信息列表</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/bootstrap/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/common.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/easyui/themes/mrtro/linkbutton.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/easyui/themes/icon.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/jquery/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/underscore/underscore.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/comm/comm.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/view/userList/userList.js"></script>
<script type="text/javascript">
	function changeGridHeight() {
		var ifm = document.getElementById("gridContent");
		ifm.style.height = (document.documentElement.clientHeight - 170) + 'px';
	}
</script>
<style>
.trans {
	display: none;
	width: 54px;
	height: 37px;
	opacity: 0;
	position: relative;
	margin-top: -34px;
	cursor: pointer;
}
</style>
</head>
<body onload="changeGridHeight();">
	<div class="mainContent">
		<div class="easyui-panel" title="查询条件" data-options="footer:'#ft'">
			<table width="100%" class="searchContent">
				<tr>
					<td width="50px" align="left"
						style="padding-left: 430px; padding-bottom: 5px;">
						用户名:&nbsp;<input name="user"
						style="width: 160px; height: 26px;" id="userName" /></td>
				</tr>
			</table>
		    <div id="ft" class='footContent'>
				<button type="button" class="btn btn-primary" id="queryUser">查询</button>
			</div>
		</div>

		<div class="easyui-panel" title="用户信息列表">
			<div class="controlContent">
			 <button id="addUserInfo" type="button" class="btn btn-default">添加</button>
			</div>
		</div>

		<div id="gridContent">
			<div id="user-grid"></div>
		</div>
	</div>
</body>
</html>