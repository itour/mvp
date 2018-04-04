function saveUseInfo(parma) {
	var userName = $("#userName").val().trim();
	var psd = $("#password").val().trim();
	var repsd = $("#repassword").val().trim();

	if (userName == "") {
		document.getElementById("userNameText").innerHTML = "用户名不能为空";
		return false;
	} else {
		document.getElementById("userNameText").innerHTML = "";
	}

	if (psd == "") {
		document.getElementById("passwordText").innerHTML = "密码不能为空";
		return false;
	} else if (psd.length < 6 || psd.length > 15) {
		document.getElementById("passwordText").innerHTML = "密码不能小于6位或不能大于15位";
		return false;
	} else if (isNaN(psd)) {
		document.getElementById("passwordText").innerHTML = "密码必须是数字";
		return false;
	} else {
		document.getElementById("passwordText").innerHTML = "";
	}

	if (psd != repsd) {
		document.getElementById("passwordText").innerHTML = "密码不一致，请重新输入";
		$("#password").select();
		$("#repassword").value = "";
		return false;
	} else {
		document.getElementById("passwordText").innerHTML = "";
	}

	if (parma != "") {
		$.ajax({
			type : 'POST',
			url : getRootPath() + "/user/updateUser",
			data : {
				'username' : $("#userName").val(),
				'password' : $("#password").val()
			},
			success : function(data) {
				if (data.message == "更新失败") {
					$.messager.alert('提示', data.message, 'info');
				} else {
					$.messager.alert('提示', data.message, 'info');
					window.parent.$('#tbs').tabs('close', '用户基本信息');
					if (data != null) {
						$("#user-grid").datagrid("loadData", data);
					}
				}
			}
		});
	} else {
		$.ajax({
			type : 'POST',
			url : getRootPath() + "/user/insertInfo",
			data : {
				'userName' : $("#userName").val(),
				'password' : $("#password").val(),
				'repassword' : $("#repassword").val()
			},
			success : function(data) {
				if (data.message == "保存失败" || data.message == "用户名已存在") {
					$.messager.alert('提示', data.message, 'info');
				} else {
					$.messager.alert('提示', data.message, 'info');
					window.parent.$('#tbs').tabs('close', '用户基本信息');
					if (data != null) {
						$("#user-grid").datagrid("loadData", data);
					}
				} 
			}
		});
	}
}

function changeUserName() {
	if ($("#userName").val() != " ") {
		$("#userNameText").html("");
	}
}

function changePassword() {
	if ($("#password").val() != " ") {
		$("#passwordText").html("");
	}
}

function changeRePassword() {
	if ($("#repassword").val() != " ") {
		$("#repasswordText").html("");
	}
}