/**   
* 
<pre>
<b>描述：</b> 
<b>任务编号：</b>
<b>作者：</b> zhangpengfei
<b>创建日期：</b> 2017年5月12日 下午4:39:49
</pre>
*/
$(document).ready(function()
{
	initTable();
});

function initTable()
{
	$("#userTable").datagrid(
	{
	    url : getRootPath() + "/user/query",
	    fit : true,
	    striped : true,
	    idField : 'USERID',
	    singleSelect : true,
	    rownumbers : true,
	    pagination : true,
	    pagePosition : 'bottom',
	    pageSize : 10,
	    pageList : [
	            10, 20, 30, 40
	    ],
	    columns : createColumns(),
	    toolbar : '#tb',
	    // onSelect : selectRow,
	    onRowContextMenu : function(e, index, row)
	    {
		    // if (row != null)
		    // {
		    // e.preventDefault();
		    // $('#roleInfoList').datagrid('selectRow', index);
		    // $('#mm').menu('show',
		    // {
		    // left : e.pageX,
		    // top : e.pageY
		    // });
		    // }
	    }
	});
	var p = $('#userTable').datagrid('getPager');
	$(p).pagination(
	{
	    pageSize : 10,// 每页显示的记录条数，默认为10
	    pageList : [
	            10, 20, 30, 40
	    ],// 可以设置每页记录条数的列表
	    beforePageText : '第',// 页数文本框前显示的汉字
	    afterPageText : '页    共 {pages} 页',
	    displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

function createColumns()
{
	var columns = [
		[
		        {
		            field : 'USERID',
		            hidden : true
		        },
		        {
		            field : 'USERCODE',
		            title : '用户编号',
		            width : '10%',
		            sortable : true
		        },
		        {
		            field : 'USERNAME',
		            title : '用户名称',
		            width : '10%',
		            sortable : true
		        }
		]
	];
	return columns;
}
