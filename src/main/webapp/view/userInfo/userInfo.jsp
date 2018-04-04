<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息列表</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/bootstrap/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/easyui/themes/default/easyui.css">
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
	src="<%=request.getContextPath()%>/view/userInfo/userInfo.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/view/userInfo/saveUserInfo.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/common.css">
<script type="text/javascript">
	var username = '${user.USERNAME}';
	var password = '${user.PASSWORD}';
	function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d);
	}
	function myparser(s) {
		if (!s)
			return new Date();
		var ss = (s.split('-'));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		var d = parseInt(ss[2], 10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
			return new Date(y, m - 1, d);
		} else {
			return new Date();
		}
	}

	$(document).ready(function() {
		debugger;
		if (username != "") {
			$("#userName").attr("readOnly", true);
			$("#userName").val(username);
			/* $("#userName").style.backgroundColor="gray"; */
			$("#password").val(password);
			$("#repassword").val(password);
		} else {
			$("#userName").attr("readOnly", false);
		}
	})
</script>
</head>
<body>
	<div class='mainContent'>
		<input id="parma" name="parma" value="${parma}" style="display: none">
		<div id="userInfo" class="easyui-panel" title="用户基本信息">
			<table width="100%" class="searchTable" id="tableContent">
				<tr>
					<td width="10%" align="right">用户名：</td>
					<td width="15%" align="left"><input id="userName"
						labelPosition="left" onkeyup="changeUserName()"
						value='${user.USERNAME}' /><br /> <span id="userNameText"
						style="color: red"></span></td>
					<td width="10%" align="right">密码：</td>
					<td width="15%" align="left"><input type="password"
						id="password" name="password" labelPosition="left"
						value='${user.PASSWORD}' class="inputtxt"
						onkeyup="changePassword()" /><br /> <span id="passwordText"
						style="color: red"></span></td>
					<td width="10%" align="right">确认密码：</td>
					<td width="15%" align="left"><input type="password"
						name="repassword" id="repassword" labelPosition="left"
						value='${user.PASSWORD}' class="inputtxt"
						onkeyup="changeRePassword()"><br /> <span
						id="repasswordText" style="color: red"></span></td>
				</tr>
				<!-- <tr>
				    <td width="10%" align="right">用户ID：</td>
					<td width="15%" align="left"><input id="userId"
						labelPosition="left" onkeyup="changeUserId()" /><br /> 
				    <td width="10%" align="right">创建人：</td>
					<td width="15%" align="left"><input id="creater" 
                        labelPosition="left"/><br />
					<td width="10%" align="right">创建时间：</td>
					<td width="15%" align="left"><input id="createDate" labelPosition="top"
						data-options="formatter:myformatter,parser:myparser"
						style="width: 80%;"><br />
				</tr> -->
			</table>
			<div style="margin-bottom: 10px">
				<button type="button" style="margin-left: 600px"
					class="btn btn-default" onclick="saveUseInfo('${user.USERNAME}')">保存</button>
			</div>
		</div>
	</div>
</body>
</html>