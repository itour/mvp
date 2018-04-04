var projectNo;
(function() {

	function ParaList() {

	}
	;

	var paraList = new ParaList();
	
	var cmForSV ;
	ParaList.prototype.getcmForSV = function() {
		$.ajax({
			type : 'GET',
			async:false,
			url : getRootPath() + "/paraInfo/queryprojSV",
			success : function(data){
				if(data){
					cmForSV = new Array();
					var index = 0;
					for(index=0;index<data.total;index++){
						var eachdata = new Object();
						eachdata = data.rows[index];
						cmForSV[index]=eachdata;
					}		
				}
			}
		}
		);
	};
	
	/**
	 * 初始化项目列表
	 */
	
	// 加分页的
	ParaList.prototype.initGrid = function() {
		var opt = $("#po-grid");
		opt.datagrid({
			fit : true,
			striped : true,
			singleSelect : true,
			rownumbers : false,
			pagination : true,
			pagePosition : 'bottom',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40 ],
			columns :[[ {
        		field : 'id',
        		title : "id",
        		width : '7%',
        		align : "center",
        		editor:'text',
        		sortable : true,
        		hidden:true
			},        
			 {
        		field : 'big_type_value',
        		title : "大类型",
        		width : '7%',
        		align : "center",
        		editor:'text',
        		sortable : true
        	}, {
        		field : 'small_type_value',
        		title : "小类型",
        		width : '12%',
        		align : "center",
        		editor:'text',
        		sortable : true
        	}, {
        		field : 'name',
        		title : "名称",
        		width : '7%',
        		align : "center",
        		editor:'text',
        		sortable : true
        	}, {
        		field : 'unit',
        		title : "单位",
        		width : '11%',
        		align : "center",
        		editor:'text',
        		sortable : true
        	}, {
        		field : 'source_value',
        		title : "数据源",
        		width : '11%',
        		align : "center",
        		sortable : true,
        		formatter:function(value){
        			for(var i=0; i<cmForSV.length; i++){
        				if (cmForSV[i].value == value) return cmForSV[i].name;
        			}
        			return value;
            	  },
            	  editor:{
        				type:'combobox',
        				options:{
        					valueField:'value',
        					textField:'name',
        					data:cmForSV,
        					required:true,
        					editable:false
        				}
        			}  
        		
        	}, {
        		field : 'parameter',
        		title : "对应参数",
        		width : '12%',
        		align : "center",
        		editor:'text',
        		sortable : true
        	}, {
        		field : 'isDisplay',
        		title : "是否显示",
        		width : '6%',
        		align : "center",
        		editor:'text',
        		sortable : true
        	},{
        		field:'action',
        		title:'Action',
        		width:'9%',
        		align:'center',
        		formatter:function(value,row,index){
        			if (row.editing){
        				var s = '<a href="#" onclick="saverow(this)">Save</a> ';
        				var c = '<a href="#" onclick="cancelrow(this)">Cancel</a>';
        				return s+c;
        			} else {
        				var e = '<a href="#" onclick="editrow(this)">Edit</a> ';
        				var d = '<a href="#" onclick="deleterow(this)">Delete</a>';
        				return e+d;
        			}
        		}
        	}
        ]], 
			                           
			onSortColumn: function(field, order){
				paraList.initParamInfo(false, field, order);
			},
		  onBeforeEdit:function(index,row){
			row.editing = true;
			updateActions(index);
		},
		  onAfterEdit:function(index,row){
			row.editing = false;
			updateActions(index);
		  },
	   onCancelEdit:function(index,row){
				row.editing = false;
				updateActions(index);
		 }
		}).datagrid('clientPaging');
	
	var p = opt.datagrid('getPager');
	$(p).pagination({
		pageSize : 20,// 每页显示的记录条数，默认为10
		pageList : [ 10, 20, 30, 40 ],// 可以设置每页记录条数的列表
		beforePageText : '第',// 页数文本框前显示的汉字
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	paraList.initParamInfo(true);
	}
	
	
	
  // 查询信息的请求
	ParaList.prototype.initParamInfo = function() {
		var url = getRootPath() + "/paraInfo/query";
		$.ajax({
			type : 'POST',
			url : url,
			data : {
				'projNo' :$("#projectNo").val()
			},
			success : function(data) {
				if (data) {	
				$("#po-grid").datagrid("loadData", data);
				}
				
		}
	});
	}
	
	$(document).ready(function() {
		projectNo = $("#noHid").val();
		paraList.getcmForSV();
		paraList.initGrid();
	});

})();

function updateActions(index){
	$('#po-grid').datagrid('refreshRow',index);
}
function getRowIndex(target){
			var tr = $(target).closest('tr.datagrid-row');
			return parseInt(tr.attr('datagrid-row-index'));
		}

function editrow(target){
	$('#po-grid').datagrid('beginEdit', getRowIndex(target));
}
function cancelrow(target){
	$('#po-grid').datagrid('cancelEdit', getRowIndex(target));
}

// 保存数据
	function saverow(target){
		var tar = getRowIndex(target);
		var rows = $('#po-grid').datagrid('getRows');
		var row = rows[tar];
		$('#po-grid').datagrid('endEdit', tar);
		$.ajax({
			type : 'POST',
			url : getRootPath() + "/paraInfo/addParam",
			data : {
				'id':row.id,
				'projNo' :$("#projectNo").val(),
				'big_type_value' : row.big_type_value,
				'small_type_value': row.small_type_value,
				'name': row.name,
				'unit': row.unit,
				'source_value':row.source_value,
				'parameter':row.parameter,
				'isDisplay':row.isDisplay		
			},
			
			success : function(data) {
				$.messager.alert('提示', data, 'info');
			}
		});
	}
	
	function deleterow(target){
		$.messager.confirm('请确认','确认删除这条数据么?',function(r){
			if (r){
				var tar = getRowIndex(target);
				var rows = $('#po-grid').datagrid('getRows');
				var row = rows[tar];
				var id = row.id;
				$('#po-grid').datagrid('deleteRow', tar);
				$.ajax({
					type : 'POST',
					url : getRootPath() + "/paraInfo/delParam",
					data : {
						'projNo' : $("#projectNo").val(),
						'id' : row.id
					},
					success : function(data) {
						$.messager.alert('提示', data, 'info');
					}
				});
			}
		});
	}

