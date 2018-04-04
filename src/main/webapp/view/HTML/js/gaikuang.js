(function() {
	var projNo = window.parent.projNo;
	var buName = window.parent.projBU;
	var units = {};
	
	function initData() {
		$.ajax({
			url : getRootPath() + "/bu/projOverviewData",
			type : 'POST',
			data : {
				no : projNo
			},
			success : function(data) {
				
			}
		})
	};
//	function initDataGrid() {
//		if (!$('#projViewGrid').data('datagrid')) {
//			$.ajax({
//				url : getRootPath() + "/bu/projOverview",
//				type : 'POST',
//				data : {
//					no : projNo
//				},
//				success : function(data) {
//					var options = {
//						height : 'auto',
//						rownumbers : false,
//						striped : true,
//						fitColumns : false,
//						nowrap : false,
//						onLoadSuccess : function(data) {
//							$('#projViewGrid').datagrid('doCellTip', {
//								cls : {},
//								delay : 1000,
//								titleUnit : units
//							});
//						}
//					};
//					units = data.units
//					var wdth = Math.round(100 / data.title.length);
//					var lastWd = Math.round(100 / data.title.length)
//							* data.title.length;
//					lastWd = 100 > lastWd ? (wdth + 100 - lastWd) : (wdth
//							- lastWd + 100);
//					_.each(data.title, function(val, index) {
//						val.width = wdth + '%';
//					})
//					data.title[data.title.length - 1].width = lastWd + '%';
//					options.columns = [ data.title ];
//					$('#projViewGrid').datagrid(options);
//					$('#projViewGrid').datagrid("loadData", data.gridDatas);
//				}
//			})
//		}
//	};
//	function initProductChart() {
//		$.ajax({
//			url : getRootPath() + "/bu/getSpecProjChartData",
//			type : 'post',
//			data : {
//				no : projNo
//			},
//			success : function(data) {
//				debugger;
//				var datas = [];
//				_.each(data, function(val, index) {
//					if (val.month) {
//						datas.push(val);
//					}
//				})
//				option1.xAxis = [ {
//					data : _.pluck(datas, "month")
//				} ];
//				option1.series = [ {
//					name : '月度生产率',
//					type : 'bar',
//					data : _.pluck(datas, "ydscl")
//				}, {
//					name : '项目E2E生产率',
//					type : 'bar',
//					data : _.pluck(datas, "e2escl")
//				}, {
//					name : '迭代生产率',
//					type : 'bar',
//					data : _.pluck(datas, "ddscl")
//				} ];
//				myChart1.setOption(option1);
//				option2.xAxis = [ {
//					data : _.pluck(datas, "month")
//				} ];
//				option2.series[0] = {
//					name : '代码Review缺陷密度',
//					type : 'line',
//					data : _.pluck(datas, "dmreviewqxmd")
//				};
//				myChart2.setOption(option2);
//				option3.xAxis = [ {
//					data : _.pluck(datas, "month")
//				} ];
//				option3.series[0] = {
//					name : '问题单趋势分析',
//					type : 'line',
//					data : _.pluck(datas, "wtdwclxl")
//				};
//				myChart3.setOption(option3);
//			}
//		});
//
//	}

		$(document).ready(function(){
			initDataGrid();
	//		initProductChart();
		})
})()

var oldtable;
var oldtable1;
var oldtable2;
function tableAdd(tableid) {
	var tab = '<tr><td>&nbsp;</td><td></td><td></td><td></td></tr>';
	$("#"+tableid+" tbody").append(tab);
}
function tableAdd2() {
	var tab = '<tr><td></td><td></td><td></td><td></td>'
	   +'<td>●</td></tr>';
	$("#cp-review tbody").append(tab);
}
function tableCancel(tableid) {
	$("#"+tableid+" tbody").empty();
	if(tableid=='guanjianjuese'){
		$("#"+tableid+" tbody").append(oldtable);
	}else{
		$("#"+tableid+" tbody").append(oldtable1);
	}
	
}
function tableCancel2() {
	$("#cp-review tbody").empty();
	$("#cp-review tbody").append(oldtable2);
}
function tableSave(editid,tableid) {
	var edit=$("#"+editid);
	edit.css('display','block');
	edit.attr("data_obj","1");
	$("#"+tableid).css('display','none');
}
function tableSave2() {
	var edit=$("#edit2");
	edit.css('display','block');
	edit.attr("data_obj","1");
	$("#cp-reviewdo").css('display','none');
}

function tableEdit(editid,divid) {
	var edit=$("#"+editid);
	edit.css('display','none');
	if(divid=='guanjianjuesedo'){
		oldtable=$("#guanjianjuese tbody").html();
	}else if(divid=='guanjianjuesedo1'){
		oldtable1=$("#guanjianjuese1 tbody").html();
	}else{
		oldtable2=$("#cp-review tbody").html();
	}
	if(edit.attr("data_obj")=="1"){
		$("#"+divid).css('display','block'); 
		edit.attr("data_obj","0")
	}
}

function editable(td){
	 // 根据表格文本创建文本框 并加入表表中--文本框的样式自己调整
	var text = td.text();
   var txt = $("<input type='text'>").val(text);
   txt.blur(function(){
       // 失去焦点，保存值。于服务器交互自己再写,最好ajax
       var newText = $(this).val();
        
       // 移除文本框,显示新值
       $(this).remove();
       td.text(newText);
   });
   td.text("");
   td.append(txt);
   $(txt).focus();
}
$(document).on("dblclick", "#guanjianjuese td", function () {
	if($("#edit").attr("data_obj")=="1"){
		return;
	}
    var td = $(this);
    editable(td);
	
});
$(document).on("dblclick", "#guanjianjuese1 td", function () {
	if($("#edit1").attr("data_obj")=="1"){
		return;
	}
    var td = $(this);
    editable(td);
	
});
$(document).on("dblclick", "#cp-review td", function () {
	if($("#edit2").attr("data_obj")=="1"){
		return;
	}
    var td = $(this);
    editable(td);
	
});
