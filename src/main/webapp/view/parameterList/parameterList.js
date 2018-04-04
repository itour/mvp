var bigTypes;
var smallTypes;
var sources;

function updateActions(index){
	$('#para-grid').datagrid('refreshRow',index);
}
function getRowIndex(target){
	var tr = $(target).closest('tr.datagrid-row');
	return parseInt(tr.attr('datagrid-row-index'));
}
function editrow(target){
	$('#para-grid').datagrid('beginEdit', getRowIndex(target));
}
var duArray = new Array();
function deleterow(target){;debugger;
	$.messager.confirm('请确认','确认删除这条数据么?',function(r){
		if (r){
			var tar = getRowIndex(target);
			var rows = $('#para-grid').datagrid('getRows');
			var row = rows[tar];
			var id = row.id;
			$('#para-grid').datagrid('deleteRow', tar);
			$.ajax({
				type : 'POST',
				url : getRootPath() + "/parameter/delete",
				data : {
					'id' : id
				},
				success : function(data) {
					if(data==null){
					$.messager.alert('提示', "数据删除失败！", 'info');
					}
				}
			});
		}
	});
}

var insertPara = false;
var deleteLabel = true;
function saverow(target){debugger;
	var tar = getRowIndex(target);
	$('#para-grid').datagrid('endEdit', tar);
	var rows = $('#para-grid').datagrid('getRows');
	var row = rows[tar];
	if($("#para-grid").datagrid('validateRow', tar) == false){
		$.messager.alert('提示', "数据检验不通过", 'info');
		return;
	}
	var urlPara = getRootPath() + "/parameter/";
	if(insertPara){
		urlPara += "add";
		deleteLabel = false;
	}else{
		urlPara += "update";
	}
	insertPara = false;
	var bigValueTmp = row.big_type_value;
	var bigValue=$.grep(bigTypes,function(n,i){
		return (n.value == bigValueTmp || n.name == bigValueTmp);
		});
	var smallValueTmp = row.small_type_value;
	var smallValue=$.grep(smallTypes,function(n,i){
		return (n.value == smallValueTmp || n.name == smallValueTmp);
		});
	var sourceValueTmp = row.source_value;
	var sourceValue=$.grep(sources,function(n,i){
		return (n.value == sourceValueTmp || n.name == sourceValueTmp);
		});
	$.ajax({
		type : 'POST',
		url : urlPara,
		data : {
			'id': row.id,//不更改
			'name': row.name,//文本
			'unit': row.unit,//文本
			'big_type_value': bigValue[0].value,//下拉框选择
			'small_type_value':smallValue[0].value ,//下拉框选择
			'source_value': sourceValue[0].value,//下拉框选择
			'parameter': row.parameter,//文本
			'creator': row.creator,
			'update_user': row.update_user//当前用户
		},
		success : function(data) {
			if(data==null){
				$.messager.alert('提示', "数据编辑失败！", 'info');
			}else if(data!=null && urlPara == getRootPath() + "/parameter/add"){
				$("#para-grid").datagrid("loadData", data);
			}
		}
	});

}
function cancelrow(target){
	insertPara = false;
	var tar = getRowIndex(target);
	var rows = $('#para-grid').datagrid('getRows');
	var row = rows[tar];
	$('#para-grid').datagrid('cancelEdit', tar);
	$("#para-grid").datagrid('reload');
	if(row.id == null){
		if (deleteLabel) {
			$('#para-grid').datagrid('deleteRow', tar);
		}
	};
}
function insert(){
	$('#para-grid').datagrid('insertRow', {
		index: 0,
		row:{
			creator: '当前用户',
			big_type_value: '1',
			small_type_value: '101',
			source_value: '0'
		}
	});
	insertPara = true;
	deleteLabel = true;
	$('#para-grid').datagrid('selectRow',0);
	$('#para-grid').datagrid('beginEdit',0);
}

(
function() {
	function ParaList() {
	}
	;
	var paraList = new ParaList();
	
	ParaList.prototype.getSources = function() {
		$.ajax({
			type : 'POST',
			async:false,
			url : getRootPath() + "/codeMaster/list",
			data : {
				'codekey' : 'Source'
			},
			success : function(data){
				if(data){
					sources = new Array();
					var index = 0;
					
					var buopt = '<option value=""></option>';
					for(index=0;index<data.total;index++){
						var eachdata = new Object();
						eachdata = data.rows[index];
						sources[index]=eachdata;
						buopt += '<option value="'+ data.rows[index].value +'">' + data.rows[index].name + '</option>';
					}
					$("#sourceValue").html(buopt);
				}
			}
		}
		);
	};
	
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
					var buopt = '<option value="" valueid=""></option>';
					for(index=0;index<data.total;index++){
						var eachdata = new Object();
						eachdata = data.rows[index];
						bigTypes[index]=eachdata;
						buopt += '<option value="'+ data.rows[index].value +'" valueid="'+data.rows[index].value+'">' + data.rows[index].name + '</option>';
					}
					$("#bigTypeValue").html(buopt);
				}
			}
		}
		);
	};
	
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
					duArray = data.rows;
					paraList.changeSelect();
				}
			}
		}
		);
	};
	
	ParaList.prototype.changeSelect = function(){
		var valueOpt = $("#bigTypeValue option:selected").attr("valueid");
		$("#smallTypeValue").empty();
		var buopt = '<option value=""></option>';
		if (valueOpt == ""){
			_.each(duArray, function(value, index){
				if(value != null && value != 'null'){
					buopt += '<option value="'+ value.value +'">' + value.name + '</option>';
				}
			})
		}
		else{
			_.each(duArray, function(value, index){
				if(value != null && value != 'null'){
					if (value.value.substring(0, 1) == valueOpt){
						buopt += '<option value="'+ value.value +'">' + value.name + '</option>';
					}
				}
			})
		}
		$("#smallTypeValue").html(buopt);
	};

	/**
	 * 初始化项目列表
	 */
	ParaList.prototype.initGrid = function() {

		paraList.getPara(true);
	};
	ParaList.prototype.initSelectOpts = function(datas, name, id){
		var bus = _.uniq(_.pluck(datas, name));
		var buopt = '<option value=""></option>';
		_.each(bus, function(value, index){
			if(value != null && value != 'null'){
				buopt += '<option value="'+ value +'">' + value + '</option>';
			}
		})
		$("#" + id).html(buopt);
	};
	
	ParaList.prototype.getPara = function(init, field, order) {
		var urlpara = getRootPath() + "/parameter/list";
		if(field){
			urlpara += "?sort=" + field + "&order=" + order;
		}
		$.ajax({
			type : 'POST',
			url : urlpara,
			async:false,
			data : {
				'name' : $("#measurementParameter").val(),
				'big_type_value' : $("#bigTypeValue").val(),
				'small_type_value' : $("#smallTypeValue").val(),
				'source_value' : $("#sourceValue").val(),
				'parameter' : $("#parameter").val(),
				'unit' : $("#unit").val()
			},
			success : function(data) {
				var opt = $("#para-grid");
				opt.datagrid({
//					url : getRootPath() + "/parameter/list",
					fit : true,
					striped : true,
					idField : 'id',
					singleSelect : true,
					rownumbers : true,
					pagination : true,
					pagePosition : 'bottom',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40 ],
					columns : [ [ {
						field : 'id',
						title : "参数编号",
						width : '8%',
						align : "center",
						hidden:'true'
//						sortable : true
					}, {
						field : 'name',
						title : "参数名称",
						width : '23%',
						align : "center",
						editor:'text',
						sortable : true
					}, {
						field : 'unit',
						title : "单位",
						width : '15%',
						align : "center",
						editor:'text',
						sortable : true
					}, {
						field : 'big_type_value',
						title : "参数大类型",
						width : '10%',
						align : "center",
						formatter:function(value){
							for(var i=0; i<bigTypes.length; i++){
								if (bigTypes[i].value == value) return bigTypes[i].name;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'value',
								textField:'name',
								prompt:'请选择参数大类',
								data:bigTypes,
								required:true,
								editable:false,
								onChange : function(newValue,oldValue){
									var row = getRowIndex(this);
									var value = newValue;
									var valueId = '';
									$.each(bigTypes, function(i, n){
										if (n.value == value || n.name == value){
											valueId = n.value;
											return false;
										}
									});
									var target = $('#para-grid').datagrid('getEditor', {'index':row,'field':'small_type_value'}).target;  
		                            target.combobox('clear'); //清除原来的数据  
		                            var data = [];
		                            for(var i=0; i<smallTypes.length; i++){
			    						if (smallTypes[i].value.substring(0, 1) == valueId)
			    							data.push(smallTypes[i]);
		                        	}
		                            target.combobox("loadData", data);
								}
							}
						},
						sortable : true
					}, {
						field : 'small_type_value',
						title : "参数小类型",
						width : '15%',
						align : "center",
						formatter:function(value){
							for(var i=0; i<smallTypes.length; i++){
								if (smallTypes[i].value == value) return smallTypes[i].name;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'value',
								textField:'name',
								prompt:'请选择参数小类',
								data:smallTypes,
								required:true,
								editable:false
							}
						},
						sortable : true
					}, {
						field : 'source_value',
						title : "数据源",
						width : '15%',
						align : "center",
						formatter:function(value){
							for(var i=0; i<sources.length; i++){
								if (sources[i].value == value) return sources[i].name;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'value',
								prompt:'请选择数据源',
								textField:'name',
								data:sources,
								required:true,
								editable:false
							}
						},
						sortable : true
					}, {
						field : 'parameter',
						title : "对应参数",
						width : '15%',
						align : "center",
						editor:'text',
						sortable : true
					}, {
						field : 'create_date',
						title : "创建时间",
						width : '9%',
						formatter : formatDatebox,
						hidden:'true',
						align : "center",
						sortable : true
					}, {
						field : 'creator',
						title : "创建者",
						width : '6%',
						hidden:'true',
						align : "center",
						sortable : true
					}, {
						field : 'update_date',
						title : "更新日期",
						width : '9%',
						formatter : formatDatebox,
						align : "center",
						hidden:'true',
						sortable : true
					}, {
						field : 'update_user',
						title : "更新者",
						width : '6%',
						hidden:'true',
						align : "center",
						sortable : true
					},{
						field:'action',
						title:'操作',
						width:'7%',
						align:'center',
						formatter:function(value,row,index){
							if (row.editing){
								var s = '<a href="#" onclick="saverow(this)">保存</a> ';
								var c = '<a href="#" onclick="cancelrow(this)">取消</a>';
								return s+c;
							} else {
								var e = '<a href="#" onclick="editrow(this)">编辑</a> ';
								var d = '<a href="#" onclick="deleterow(this)">删除</a>';
								return e+d;
							}
						}
					}
					] ],
					onSortColumn :function(field, order){
						paraList.getPara(false, field, order);
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
				if (data) {
					$("#para-grid").datagrid("loadData", data);
					if(init){
						/*var datas = data.rows;
						
						paraList.initSelectOpts(datas, 'big_type_value', 'bigTypeValue');
						paraList.initSelectOpts(datas, 'small_type_value', 'smallTypeValue');
						paraList.initSelectOpts(datas, 'source_value', 'sourceValue');*/
						
					}
				}

			}
		})
	};
	ParaList.prototype.bindQuery = function() {
		$("#queryPara").click(function() {
			paraList.getPara();
		})
	};
	ParaList.prototype.bindReset = function() {
		$("#resetPara").click(function() {
			$("#measurementParameter").val('');
			$("#bigTypeValue").val('');
			$("#smallTypeValue").val('');
			$("#sourceValue").val('');
			$("#parameter").val('');
			$("#unit").val('');
			$("#para-grid").datagrid('reload');
			paraList.getPara();
			paraList.getSmallTypes();
		})
	};

	$(document).ready(function() {
		paraList.getSources();
		paraList.getBigTypes();
		paraList.getSmallTypes();
		paraList.initGrid();
		paraList.bindQuery();
		paraList.bindReset();
		$("#bigTypeValue").change(function() { paraList.changeSelect(); });
	})
})()

		