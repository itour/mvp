var projectNo;
(function() {

	function ParaList() {

	}
	;

	var paraList = new ParaList();
	var cmForSV;
	ParaList.prototype.getCmForSV = function() {
		$.ajax({
			type : 'POST',
			async:false,
			url : getRootPath() + "/codeMaster/list",
			data : {
				'codekey' : 'Source'
			},
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
	var CmIsDisplay = [
	                   {value:'1',name:'是'},
	                   {value:'0',name:'否'}
	                   ];
	var bigTypes;
	ParaList.prototype.getBigTypes = function() {
		$.ajax({
			type : 'POST',
			async:false,
			url : getRootPath() + "/codeMaster/list",
			data : {
				'codekey' : 'BigType'
			},
			success : function(data){
				if(data){
					bigTypes = new Array();
					var index = 0;
					for(index=0;index<data.total;index++){
						var eachdata = new Object();
						eachdata = data.rows[index];
						bigTypes[index]=eachdata;
					}		
				}
			}
		}
		);
	};
	var smallTypes;
	ParaList.prototype.getSmallTypes = function() {
		$.ajax({
			type : 'POST',
			async:false,
			url : getRootPath() + "/codeMaster/list",
			data : {
				'codekey' : 'SmallType'
			},
			success : function(data){
				if(data){
					smallTypes = new Array();
					var index = 0;
					for(index=0;index<data.total;index++){
						var eachdata = new Object();
						eachdata = data.rows[index];
						smallTypes[index]=eachdata;
					}		
				}
			}
		}
		);
	};
	/**
	 * 初始化项目列表
	 */
	var i=0;
	// 加分页的
	ParaList.prototype.initGrid = function() {
		var opt = $("#po-grid");
		opt.datagrid({
			fit : true,
			striped : true,
			singleSelect : true,
			pagination : true,
			pagePosition : 'bottom',
			selectOnCheck:false,
			rownumbers : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40 ],
			columns :[[             
			    {
        		field : 'id',
        		title : "id",
        		width : '5%',
        		align : "center",
        		sortable : true,
        		hidden:true  
			},        
			 {
        		field : 'big_type_value',
        		title : "参数大类型",
        		width : '16%',
        		align : "center",
        		formatter:function(value){
					for(var i=0; i<bigTypes.length; i++){
						if (bigTypes[i].value == value) return bigTypes[i].name;
					}
					return value;
				},
        		sortable : true
        	}, {
        		field : 'small_type_value',
        		title : "参数小类型",
        		width : '16%',
        		align : "center",
        		formatter:function(value){
					for(var i=0; i<smallTypes.length; i++){
						if (smallTypes[i].value == value) return smallTypes[i].name;
					}
					return value;
				},
        		sortable : true
        	}, {
        		field : 'name',
        		title : "名称",
        		width : '10%',
        		align : "center",
        		sortable : true
        	}, {
        		field : 'unit',
        		title : "单位",
        		width : '10%',
        		align : "center",
        		sortable : true
        	}, {
        		field : 'source_value',
        		title : "数据源",
        		width : '10%',
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
        					editable:false
        				}
        			}  
        		
        	}, {
        		field : 'parameter',
        		title : "对应参数",
        		width : '16%',
        		align : "center",
        		editor:'text',
        		sortable : true
        	}, {
        		field : 'isDisplay',
        		title : "是否显示",
        		width : '10%',
        		align : "center",
        		formatter:function(value){
					for(var i=0; i<CmIsDisplay.length; i++){
						if (CmIsDisplay[i].value == value) return CmIsDisplay[i].name;
					}
					return value;
				},
				editor:{
					type:'combobox',
					options:{
						valueField:'value',
						textField:'name',
						data:CmIsDisplay,
						required:true,
						editable:false
					}
				},	
        		sortable : true
        	},{
        		field:'action',
        		title:'操作',
        		width:'10%',
        		align:'center',
        		formatter:function(value,row,index){
        			if (row.editing){
        				var s = '<a href="#" onclick="saveparamrow(this)">保存</a> ';
        				var c = '<a href="#" onclick="cancelparamrow(this)">取消</a>';
        				return s+c;
        			} else {
        				var e = '<a href="#" onclick="editparamrow(this)">编辑</a> ';
        				return e;
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
	ParaList.prototype.initParamInfo = function(init, field, order) {
		var url = getRootPath() + "/paraInfo/query";
		if(field){
			url += "?sort="+ field + "&order=" + order;
		}
		$.ajax({
			type : 'POST',
			url : url,
			data : {
				'projNo' :$("#noHid").val()
			},
			success : function(data) {
				if (data) {	
				$("#po-grid").datagrid("loadData", data);
				}
				
		}
	});
	}
	
	$(document).ready(function() {
		paraList.getCmForSV();
		paraList.getBigTypes();
		paraList.getSmallTypes();
		projectNo = $("#noHid").val();
		paraList.initGrid();
	});

})();

function updateActions(index){
	$('#po-grid').datagrid('refreshRow',index);
}
function getRowParamIndex(target){
			var tr = $(target).closest('tr.datagrid-row');
			return parseInt(tr.attr('datagrid-row-index'));
		}

function editparamrow(target){
	$('#po-grid').datagrid('beginEdit', getRowParamIndex(target));
}
function cancelparamrow(target){
	$('#po-grid').datagrid('cancelEdit', getRowParamIndex(target));
}

// 保存数据
	function saveparamrow(target){;debugger;
		var tar = getRowParamIndex(target);
		var rows = $('#po-grid').datagrid('getRows');
		var row = rows[tar];
		var ed = $('#po-grid').datagrid('getEditor', {index:tar,field:'isDisplay'});
		var edsource = $('#po-grid').datagrid('getEditor', {index:tar,field:'source_value'});
		var isDisplay_value = $(ed.target).combobox('getValue'); 
		var edsource_value = $(edsource.target).combobox('getValue'); 
		if ((isDisplay_value == 1 && edsource_value == '')
			|| (isDisplay_value == 1 && edsource_value == undefined)) {
		 $.messager.alert('提示', '数据源不能为空', 'info');
		 return;
	    }	
		$('#po-grid').datagrid('endEdit', tar);
		$.ajax({
			type : 'POST',
			async:false,
			url : getRootPath() + "/paraInfo/updateParam",
			data : {
				'parameterId':row.id,
				'sourceValue':edsource_value,
				'parameter':row.parameter,
				'no' :$("#noHid").val(),
				'isDisplay':isDisplay_value
			},		
			success : function(data) {
				if(data=="保存失败"){
					$.messager.alert('提示', data, 'info');
				}	
			}
		});
	}
	


