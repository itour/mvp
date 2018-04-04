var userName;
function loadUserUpdatePage(val){
	window.parent.initUpdataUserInfo(val);
};

function updateActions(index) {
	$('#user-grid').datagrid('refreshRow', index);
}

function getDSCRowIndex(target) {
	var tr = $(target).closest('tr.datagrid-row');
	return parseInt(tr.attr('datagrid-row-index'));
}
function deletedscrow(target) {
	$.messager.confirm('Confirm', '确定删除?', function(r) {
		if (r) {
			var tar = getDSCRowIndex(target);
			var rows = $('#user-grid').datagrid('getRows');
			var row = rows[tar];
			$.ajax({
				type : 'POST',
				url : getRootPath() + '/user/delete',
				data : {
					'USERID' : row.userid
				},
				success : function(data) {
					if (1 != data) {
						$.messager.alert('提示', "数据删除失败：" + data, 'info');
					}
				}
			});
			$('#user-grid').datagrid('deleteRow', getDSCRowIndex(target));
		}
	});
}
(function() {
	function UserList() {
	}
	;

	var userList = new UserList();

	UserList.prototype.initGrid = function() {
		userList.getUser(true);
	};

	UserList.prototype.getUser = function(init, field, order) {
		var userUrl = getRootPath() + "/user/query";
		if (field) {
			userUrl += "?sort=" + field + "&order=" + order;
		}
		$.ajax({
					type : 'POST',
					url : userUrl,
					async : false,
					data : {
						'username' : $("#userName").val()
					},
					success : function(data) {
						var opt = $("#user-grid");
						opt.datagrid(
							{
								fit : true,
								striped : true,
								idField : 'id',
								singleSelect : true,
								rownumbers : true,
								pagination : true,
								pagePosition : 'bottom',
								pageSize : 20,
								pageList : [ 10, 20, 30, 40 ],
								columns : [ [
										{
											field : 'username',
											title : "用户名",
											width : '25%',
											align : "center",
											formatter: function(val, row){
												return '<a onclick="loadUserUpdatePage(\''+ row.username + '\')">'+ val +'</a>';
											},
											sortable : true
										},
										{
											field : 'creater',
											title : "创建人",
											width : '25%',
											align : "center",
											sortable : true
										},
										{
											field : 'creatertime',
											title : "创建时间",
											formatter : formatDatebox,
											width : '25%',
											align : "center",
											sortable : true
										},
										{
											field : 'action',
											title : '操作',
											width : '25%',
											align : 'center',
											formatter : function(
													value, row, index) {
												var d = '<a href="#" onclick="deletedscrow(this)">删除</a>';
												return d;
											}
										} ] ],
								onSortColumn : function(field,
										order) {
									paraList.getPara(false, field,
											order);
								},
								onBeforeEdit : function(index, row) {
									row.editing = true;
									updateActions(index);
								},
								onAfterEdit : function(index, row) {
									row.editing = false;
									updateActions(index);
								},
								onCancelEdit : function(index, row) {
									row.editing = false;
									updateActions(index);
								}
							}).datagrid('clientPaging');

				var p = opt.datagrid('getPager');
				$(p).pagination(
					{
						pageSize : 20,
						pageList : [ 10, 20, 30, 40 ],
						beforePageText : '第',
						afterPageText : '页    共 {pages} 页',
						displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
					});
				if (data) {
					$("#user-grid").datagrid("loadData", data);
				}
			}
		});
	};

	UserList.prototype.bindQuery = function() {
		$("#queryUser").click(function() {
			userList.getUser();
		});
	};

	UserList.prototype.bindAdd = function() {
		$("#addUserInfo").click(function() {
			window.parent.initAddUserInfo();
		});
	};

	$(document).ready(function() {
		userList.initGrid();
		userList.bindQuery();
		userList.bindAdd();
	});
})();