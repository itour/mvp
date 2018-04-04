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

function saverow(target){debugger;
	var tar = getRowIndex(target);
	var rows = $('#para-grid').datagrid('getRows');
	$('#para-grid').datagrid('endEdit', tar);
	var row = rows[tar];
	var urlPara = getRootPath() + "/projParaValue/update";
	$.ajax({
		type : 'POST',
		url : urlPara,
		data : {
			'id': row.id,
			'no': row.no,
			'yearMonth' : new Date(row.yearMonth),
			'value': row.value
		},
		dataType: 'json',
		success : function(data) {
			if(data==null){
			$.messager.alert('提示', "数据编辑失败！", 'info');
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
		$('#para-grid').datagrid('deleteRow', tar);
	};
}

(
function() {
	function ParaList() {
	}
	;
	var paraList = new ParaList();
	/**
	 * 初始化项目列表
	 */
	ParaList.prototype.initGrid = function() {
		paraList.getPara(true);
	};
	ParaList.prototype.getPara = function(init, field, order) {
		var urlpara = getRootPath() + "/projParaValue/list";
		if(field){
			urlpara += "?sort=" + field + "&order=" + order;
		}
		$.ajax({
			type : 'POST',
			url : urlpara,
			data : {
				'no': $("#hidNo").val(),
				'yearMonth' : new Date($("#selectYear").val(),$("#selectMonth").val() - 1,1),
				'name' : $("#measurementParameter").val().trim(),
				'big_type_value' : $("#bigTypeValue").val(),
				'small_type_value' : $("#smallTypeValue").val(),
				'source' : $("#sourceValue").val(),
				'unit' : $("#unit").val().trim(),
				'isDisplay' : $("#selectIsDisplay").val()
			},
			success : function(data) {
				var opt = $("#para-grid");
				opt.datagrid({
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
						field : 'id',
						title : "id",
						width : '8%',
						align : "center",
						hidden: true  
					}, 
					{
						field : 'no',
						title : "no",
						width : '8%',
						align : "center",
						hidden: true
					}, 
					{
						field : 'yearMonth',
						title : "yearMonth",
						width : '8%',
						align : "center",
						formatter : formatDatebox,
						hidden: true
					}, 
					{
						field : 'big_type_value',
						title : "参数大类型",
						width : '11%',
						align : "center",
						sortable : true
					}, {
						field : 'small_type_value',
						title : "参数小类型",
						width : '11%',
						align : "center",
						sortable : true
					},
					{
						field : 'name',
						title : "参数名称",
						width : '16%',
						align : "center",
						sortable : true
					}, {
						field : 'unit',
						title : "单位",
						width : '11%',
						align : "center",
						sortable : true
					}, 
					{
						field : 'source',
						title : "数据源",
						width : '11%',
						align : "center",
						sortable : true
					},
					{
						field : 'isDisplay',
						title : "是否显示",
						width : '10%',
						align : "center",
						sortable : true,
						formatter:function(value,row,index){
							var text = '';
							if (value == 1){
								text = '是';
							}
							else{
								text = '否';
							}
							return text;
						}
					}, {
						field : 'value',
						title : "值",
						width : '20%',
						align : "center",
						editor:{type:'numberbox',options:{precision:2}},
						sortable : true
					},
					{
						field:'action',
						title:'操作',
						width:'10%',
						align:'center',
						formatter:function(value,row,index){
							if (row.editing){
								var s = '<a href="#" onclick="saverow(this)">保存</a> ';
								var c = '<a href="#" onclick="cancelrow(this)">取消</a>';
								return s+c;
							} else {
								var e = '<a href="#" onclick="editrow(this)">编辑</a> ';
								/*var d = '<a href="#" onclick="deleterow(this)">删除</a>';
								return e+d;*/
								return e;
							}
						}
					}
					] ]
					,
					
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
			$("#selectYear").val('');
			$("#selectMonth").val('');
			$("#bigTypeValue").val('');
			$("#smallTypeValue").val('');
			$("#measurementParameter").val('');
			$("#unit").val('');
			$("#sourceValue").val('');
			$("#selectIsDisplay").val('');
			$("#para-grid").datagrid('reload');
			paraList.getPara(true);
			paraList.getPara();
		});
	};
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
						buopt += '<option value="'+ data.rows[index].name +'">' + data.rows[index].name + '</option>';
					}
					$("#sourceValue").html(buopt);
				}
			}
		}
		);
	};
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
					var buopt = '<option value="" valueid=""></option>';
					for(index=0;index<data.total;index++){
						var eachdata = new Object();
						eachdata = data.rows[index];
						bigTypes[index]=eachdata;
						buopt += '<option value="'+ data.rows[index].name +'" valueid="'+data.rows[index].value+'">' + data.rows[index].name + '</option>';
					}
					$("#bigTypeValue").html(buopt);
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
					buopt += '<option value="'+ value.name +'">' + value.name + '</option>';
				}
			})
		}
		else{
			_.each(duArray, function(value, index){
				if(value != null && value != 'null'){
					if (value.value.substring(0, 1) == valueOpt){
						buopt += '<option value="'+ value.name +'">' + value.name + '</option>';
					}
				}
			})
		}
		$("#smallTypeValue").html(buopt);
	};
	ParaList.prototype.getYearAndMonthSelect = function(){
		var yearOption = '';
		var monthOption = '';
		var d = new Date();
		var currentYear = d.getFullYear();
		var currentMonth = d.getMonth() + 1;
		for (var i = -2; i <= 2; i++){
			var tmpYear = currentYear + i;
			
			if (tmpYear == currentYear){
				yearOption += '<option value="'+ tmpYear +'" selected = "selected" >' + tmpYear + '年</option>';
			}
			else{
				yearOption += '<option value="'+ tmpYear +'">' + tmpYear + '年</option>';
			}
		}
		for (var i = 1; i <= 12; i++){			
			if (i == currentMonth){
				monthOption += '<option value="'+ i +'" selected = "selected" >' + i + '月</option>';
			}
			else{
				monthOption += '<option value="'+ i +'">' + i + '月</option>';
			}
		}
		$("#selectYear").html(yearOption);
		$("#selectMonth").html(monthOption);
	};
	ParaList.prototype.getDisplay = function(){
		var option = '<option value=""></option><option value="1">是</option><option value="0">否</option>';
		$("#selectIsDisplay").html(option);
	};
	
	ParaList.prototype.scanFile = function(){
		$("#importInput").change(function(){
			$("#filePathInfo").val($(this).val());
		})
	};
	String.prototype.endWith=function(str){    
		  var reg=new RegExp(str+"$");    
		  return reg.test(this);       
	};
	
	ParaList.prototype.submitImport = function(){
		$("#submitImport").click(function(){
			var filePath = $("#filePathInfo").val();
			if(filePath == ''){
				$.messager.alert('提示', "请先选择要导入的文件!", 'info');
			}else if(!filePath.endWith('.xlsx')){
				alert('提示', "文件格式需为xlsx", 'info');
			}else{
				var option = {
					url: getRootPath() + "/projParaValue/import",
					type: 'POST',
					dataType: 'json',
					data:{
						projNo: $("#hidNo").val()
					},
					success: function(data){
						if(data.STATUS == 'FAILED'){
							$.messager.alert('提示', data.MESSAGE, 'info');
						}else{
							$.messager.alert('提示', "导入成功!", 'info');
							paraList.getPara();
						}
					}
				};
				$('#uploadDialog').modal('hide');
				$("#importForm").ajaxSubmit(option);
			}
		})
	};
	
	$(document).ready(function() {
		paraList.getYearAndMonthSelect();
		paraList.getSources();
		paraList.getDisplay();
		paraList.initGrid();
		paraList.bindQuery();
		paraList.bindReset();
		paraList.getBigTypes();
		paraList.getSmallTypes();
		$("#bigTypeValue").change(function() { paraList.changeSelect(); });
		paraList.scanFile();
		paraList.submitImport();
		
		$("#templateDownload").click(function(){
			var month = $("#selectMonth").val();
			if(month.length == 1){
				month = '0' + month;
			}
			var downloadUrl = getRootPath() + '/projParaValue/paramTemplate?no='+$("#hidNo").val()
			+'&date='+$("#selectYear").val()+'-'+month;
			$("#templateDownload").attr('href', downloadUrl);
		})
	})
})()

		