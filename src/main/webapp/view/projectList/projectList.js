var projectNo;
function loadMaintainPage(val){
	window.parent.initMaintainList(val);
};
function loadParameterEditorPage(val){
	window.parent.parameterEditorPage(val);
};
var duArray = new Array();

(function() {

	function PoList() {

	};

	var poList = new PoList();

	poList.columns = [ [ {
		field : 'name',
		title : "项目名称",
		width : '10%',
		align : "center",
		sortable : true,
		formatter: function(val, row){
			return '<a onclick="loadMaintainPage(\''+row.no+'\')">'+val+'</a>';
		}
	}, {
		field : 'no',
		title : "项目编号",
		width : '10%',
		align : "center",
		sortable : true
	}, {
		field : 'bu',
		title : "事业部",
		width : '8%',
		align : "center",
		sortable : true
	}, {
		field : 'du',
		title : "交付部",
		width : '8%',
		align : "center",
		sortable : true
	}, {
		field : 'pdu',
		title : "产品",
		width : '8%',
		align : "center",
		sortable : true
	}, {
		field : 'area',
		title : "地域",
		width : '6%',
		align : "center",
		sortable : true
	}, {
		field : 'pm',
		title : "项目经理",
		width : '4%',
		align : "center",
		sortable : true
	}, {
		field : 'projectType',
		title : "项目类型",
		width : '10%',
		align : "center",
		sortable : true
	}, {
		field : 'startDate',
		title : "项目开始日期",
		width : '10%',
		formatter : formatDatebox,
		align : "center",
		sortable : true
	}, {
		field : 'endDate',
		title : "项目结束日期",
		width : '8%',
		formatter : formatDatebox,
		align : "center",
		sortable : true
	}, {
		field : 'importDate',
		title : "导入日期",
		width : '8%',
		formatter : formatDatebox,
		align : "center",
		sortable : true
	}, {
		field : 'status',
		title : "状态",
		width : '5%',
		align : "center",
		sortable : true
	}, {
		field : 'editor',
		title : "操作",
		width : '7%',
		align : "center",
		sortable : false,
		formatter: function(val, row){
			return '<a href="#" onclick="loadParameterEditorPage(\''+row.no+'\')">指标编辑</a>';
		}
	}
	] ];

	/**
	 * 初始化项目列表
	 */
	PoList.prototype.initGrid = function() {
		poList.getProj(true);
	};
	
	PoList.prototype.initSelectOpts = function(datas, name, id){
		var bus = _.uniq(_.pluck(datas, name));
		var buopt = '<option value=""></option>';
		_.each(bus, function(value, index){
			if(value != '' &&value != null && value != 'null'){
				buopt += '<option value="'+ value +'">' + value + '</option>';
			}
		})
		$("#" + id).html(buopt);
	};
	

	PoList.prototype.getProj = function(init, field, order) {
		if (init){
			$.ajax({
				type : 'POST',
				url : getRootPath() + "/project/init",
				success : function(data) {
					if (data) {
						var buopt = '<option value="" valueid=""></option>';
						_.each(data.businessUnit, function(value, index){
							if(value != '' &&value != null && value != 'null'){
								buopt += '<option value="'+ value.name +'" valueid="'+value.id+'">' + value.name + '</option>';
							}
						})
						$("#businessUnit").html(buopt);
						
						
						duArray = data.deliveryUnit;
						poList.changeSelect();
						poList.initSelectOpts(data.countArea, 'name', 'countArea');
						poList.initSelectOpts(data.projectType, 'name', 'projectType');
						poList.initSelectOpts(data.projectManager, 'name', 'projectManager');
					}
				}
			})
		}
		
		var url = getRootPath() + "/project/list";
		if(field){
			url += "?sort="+ field + "&order=" + order;
		}
		$.ajax({
			type : 'POST',
			url : url,
			data : {
				'no' : $("#projectNo").val(),
				'bu' : $("#businessUnit").val(),
				'pdu' : $("#deliveryUnit").val(),
				'area' : $("#countArea").val(),
				'projectType' : $("#projectType").val(),
				'name' : $("#projectName").val(),
				'pm' : $("#projectManager").val()
			},
			success : function(data) {
				var opt = $("#po-grid");
				opt.datagrid({
					// url : getRootPath() + "/project/list",
					fit : true,
					striped : true,
					idField : 'no',
					singleSelect : true,
					rownumbers : true,
					pagination : true,
					pagePosition : 'bottom',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40 ],
					columns : poList.columns,
					onSortColumn: function(field, order){
						poList.getProj(false, field, order);
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
					$("#po-grid").datagrid("loadData", data);
				}
			}
		})
	};
	
	PoList.prototype.changeSelect = function(){
		var valueOpt = $("#businessUnit option:selected").attr("valueid");
		$("#deliveryUnit").empty();
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
					if (value.parentId == valueOpt){
						buopt += '<option value="'+ value.name +'">' + value.name + '</option>';
					}
				}
			})
		}
		$("#deliveryUnit").html(buopt);
	}

	PoList.prototype.bindQuery = function() {
		$("#queryProj").click(function() {
			poList.getProj();
		})
	};
	
	PoList.prototype.bindAdd = function() {
		$("#addInfo").click(function() {	
         window.parent.initAddInfo();
		});
	};
	

	PoList.prototype.bindReset = function() {
		$("#resetProj").click(function() {
			$("#projectNo").val('');
			$("#businessUnit").val('');
			$("#deliveryUnit").val('');
			$("#countArea").val('');
			$("#projectType").val('');
			$("#projectName").val('');
			$("#projectManager").val('');
			$("#po-grid").datagrid('reload');
			poList.getProj(true);
			poList.getProj();
		})
	};

	PoList.prototype.bindDownload = function(){
		$("#downloadProj").click(function(){
			var $eleForm = $("<form method='get'></form>");
            $eleForm.attr("action",getRootPath() + "/project/download");
            $eleForm.append($('<input name="no" type = "hidden" value="'+$("#projectNo").val()+'"/>'))
			.append($('<input name="bu" type = "hidden" value="'+$("#businessUnit").val()+'"/>'))
			.append($('<input name="pdu" type = "hidden" value="'+$("#deliveryUnit").val()+'"/>'))
			.append($('<input name="area" type = "hidden" value="'+$("#countArea").val()+'"/>'))
			.append($('<input name="projectType" type = "hidden" value="'+$("#projectType").val()+'"/>'))
			.append($('<input name="name" type = "hidden" value="'+$("#projectName").val()+'"/>'))
			.append($('<input name="pm" type = "hidden" value="'+$("#projectManager").val()+'"/>'));
            $(document.body).append($eleForm);

            //提交表单，实现下载
            $eleForm.submit();
		})
	};

	PoList.prototype.bindScanBtn = function(){
			$("#importIpt").bind("change", function(){
				$("#filePath").val($("#importIpt").val());
				
			});
	};

	String.prototype.endWith=function(str){    
		  var reg=new RegExp(str+"$");    
		  return reg.test(this);       
	};
	
	
	PoList.prototype.bindImport = function(){
		$("#submitImport").click(function(){
			var filePath = $("#filePath").val();
			if(filePath == ''){
				$.messager.alert('提示', "请先选择要导入的文件!", 'info');
			}else if(!filePath.endWith('.xlsx')){
				$.messager.alert('提示', "文件格式需为xlsx", 'info');
			}else{
			var option = {
				url: getRootPath() + "/project/import",
				type: 'POST',
				dataType:  'json',
				success: function(data){
					if(data.STATUS == 'FAILED'){
						$.messager.alert('提示', data.MESSAGE, 'info');
					}else{
						   if(data.size==0){
							   $.messager.alert('提示', data.MESSAGE, 'info');  
						   }else{ 
							   $.messager.alert('提示', "成功导入"+data.size+"条数据!", 'info');
						   }
					}
					poList.getProj(true);
			  }
			}
			}
			$('#uploadModal').modal('hide');
			$("#importform").ajaxSubmit(option);
		})
	};
	
	$(document).ready(function() {
		poList.initGrid();
		poList.bindQuery();
		poList.bindReset();
		poList.bindAdd();
		poList.bindDownload();
		poList.bindScanBtn();
		poList.bindImport();
		
		$("#businessUnit").change(function() { poList.changeSelect(); });
	})

})()






