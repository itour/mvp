(function(){
	
	var titleUnit = {};
	
	function initBuSel() {
		$.ajax({
				url: getRootPath() + '/bu/opts',
				type: 'post',
				success: function (data) {
					if(data){
						var opts = "";
						_.each(data, function(opt, index){
							if(opt.name=="中软院"){
								opts += '<option selected="selected" value="'+ opt.name +'">' + opt.name + '</option>';
							}
						})
						$("#bu_sel").html(opts);
						if (data.length >0 ){
							$("#bu_name").text(data[0].name);
//							initPoSummary(data[0].name);
//							initMonthChart(data[0].name);
//							initProjProduct(data[0].name);
							initProjIndicators(data[0].name);
						}
					}
				}
			});
	};

	function buChangeEvent(){
		$("#bu_sel").change(function(){
			$("#bu_name").text($(this).val());
//			initPoSummary($(this).val());
//			initMonthChart($(this).val());
//			initProjProduct($(this).val());
			$("#month-sel").val("");
			$("#pdu-sel").val("");
			$("#area-sel").val("");
			$("#type_sel").val("");
			$("#projNo").val("");
			$("#projName").val("");
			$("#projMgmr").val("");
			if($("#divTable").css("display") == 'block'){
				loadGridData(true);
			}
			initProjIndicators($(this).val());
		})
	};
	
//	function initPoSummary(buName){
//		$.ajax({
//				url: getRootPath() + '/bu/projSummary',
//				type: 'post',
//				data: {
//					buName: buName
//				},
//				success: function (data) {
//					var addNums = 0;
//					var tab = "";
//					$("#summaryTab tr:not(:first)").remove();
//					if(!_.isEmpty(data)){
//						_.each(data, function(record, key){
//							tab += '<tr><td>'+ record.name +'</td>'
//								   +'<td>'+ record.addNum +'</td>'
//								   +'<td>'+ record.overNum +'</td>'
//								   +'<td>'+ record.fpNum +'</td>'
//								   +'<td>'+ record.tmNum +'</td></tr>';
//							addNums += record.addNum;
//						})
//					}
//					$("#summaryTab tbody").append(tab);
//					$("#addedPoNum").text(addNums);
//				}
//			});
//	};
	
//	function initMonthChart(buName){
//		$.ajax({
//				url: getRootPath() + '/bu/monthSummary',
//				type: 'post',
//				data: {
//					buName: buName
//				},
//				success: function (data) {
//					if(!_.isEmpty(data)){
//						var addArray = _.pluck(data, 'addNum');
//						var overArray = _.pluck(data, 'overNum');
//						var totalArray = _.pluck(data, 'totalNum');
//						option.series = [{
//							name: '新增',
//			                type: 'bar',
//			                data: addArray
//						},{
//				             name: '结项',
//				             type: 'bar',
//				             data: overArray
//				     	},{
//				             name: '总项目',
//				             type: 'bar',
//				             data: totalArray
//				     	}];
//
//						option.xAxis = {
//				                data: _.pluck(data, 'month')
//				            };
//				     	myChart.setOption(option); 
//					}
//				}
//			});
//	};
	
//	function initProjProduct(buName){
//		$.ajax({
//				url: getRootPath() + '/bu/projProduct',
//				type: 'post',
//				data: {
//					buName: buName
//				},
//				success: function (data) {
//						var xData = [];
//						var ydscl = [];
//						var e2escl = [];
//						var ddscl = [];
//						_.each(data, function(record, index){
//							xData.push(record.pdu);
//							ydscl.push(record.ydscl);
//							e2escl.push(record.e2escl);
//							ddscl.push(record.ddscl);
//						})
//						option1.xAxis = {
//				            data: xData
//				         };
//						option1.series = [{
//			                name: '月度生产率',
//			                type: 'bar',
//			                data: ydscl
//			            },
//			            {
//			                name: '项目E2E生产率',
//			                type: 'bar',
//			                data: e2escl
//			            },
//			            {
//			                name: '迭代生产率',
//			                type: 'bar',
//			                data: ddscl
//			            }];
//			            myChart1.setOption(option1);
//				}
//			});
//	};

	function initSelectOpts(datas, name, id, defaults){
		var bus = _.uniq(_.pluck(datas, name));
		var buopt = '<option value=""></option>';
		_.each(bus, function(value, index){
			if(value != '' &&value != null && value != 'null'){
				buopt += '<option value="'+ value +'">' + value + '</option>';
			}
		})
		$("#" + id).html(buopt);
		if(defaults){
			$("#" + id).val(defaults);
		}
	};

	function loadGridData(init, sort, order){
		var url = getRootPath() + "/bu/projDetailTab";
		if(sort){
			url += "?sort="+ sort + "&order=" + order;
		}
		   $.ajax({
		        url: url,
		        type: 'POST',
		        data: {
		        	'buName':  $("#bu_sel").val(),
					'month': $("#month-sel").val(),
					'pdu': $("#pdu-sel").val(),
					'type': $("#type-sel").val(),
					'area': $("#area-sel").val(),
					'no': $("#projNo").val(),
					'name': $("#projName").val(),
					'pm': $("#projMgmr").val()
		        },
		        success: function(data){
		            var options = {
		                rownumbers: false,
		                striped: true,
		                pagination : true,
		                fitColumns:false,
		                nowrap: true,
						pagePosition : 'bottom',
						onSortColumn: function(field, order){
							loadGridData(false, field, order);
						},
			            onLoadSuccess:function(data){   
			                $('#projSummaryTable').datagrid('doCellTip',{cls:{},delay:1000, titleUnit:titleUnit, headerOverStyle:true});   
			            }  

		            };
		            titleUnit = data.titleUnit;
		            var wdth = Math.round(100 / data.gridTitles.length);
		            var lastWd = wdth * data.gridTitles.length;
		            lastWd = 100 > lastWd ? (wdth + 100 - lastWd) : ( wdth - lastWd + 100);
		           
		            _.each(data.gridTitles, function(val, index){
		            	val.sortable = true;
		            	val.width = wdth + '%';
		            	if(val.title == "项目名称"){
		            		val.formatter = function(val, row){
		            			return '<a style="color: #721b77;" title="'+val +'" href="' +getRootPath()+ '/bu/projView?projNo=' + row['项目编码'] + '&buName=' + $("#bu_sel").val() +'">'+val+'</a>';
		            		};
		            	}
		            		
		            });
		            data.gridTitles[data.gridTitles.length -1].width = lastWd  + '%';
		            options.columns = [data.gridTitles];
		            $('#projSummaryTable').datagrid(options).datagrid('clientPaging');

					var p = $('#projSummaryTable').datagrid('getPager');
					$(p).pagination({
						pageSize : 10,// 每页显示的记录条数，默认为10
						pageList : [ 10, 20, 30, 40 ],// 可以设置每页记录条数的列表
						beforePageText : '第',// 页数文本框前显示的汉字
						afterPageText : '页    共 {pages} 页',
						displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
					});
		
		        	var gridDatas = {};
		        	$.extend(true, gridDatas, data.gridDatas);
		            $('#projSummaryTable').datagrid("loadData", gridDatas);
		        	if(init){
		        		var datas = data.gridDatas.rows;
		        		if(!$("#month-sel").val()){
		        			initSelectOpts(datas, 'month', 'month-sel' );
		        		}
		        		if(!$("#pdu-sel").val()){
		        			initSelectOpts(datas, '产品部', 'pdu-sel', $("#pdu-sel").val());
		        		}
		        		if(!$("#area-sel").val()){
		        			initSelectOpts(datas, '地域', 'area-sel', $("#area-sel").val());
		        		}
		        		if(!$("#type-sel").val()){
		        			initSelectOpts(datas, '计费类型', 'type-sel', $("#type-sel").val());
		        		}
					}

		        }
		    })
	}
	function bindChangeModelEvent() {
		$("#changeStatusBtn").click(function(){
	        var obj = document.getElementById('btnChange');
	        if (obj.innerHTML == '切换为列表') {
	            obj.innerHTML = '切换为图表';
	            document.getElementById('divChart').style.display = "none";
	            document.getElementById('divTable').style.display = "block";
	            loadGridData(true);
	        }
	        else {
	            obj.innerHTML = '切换为列表';
	            document.getElementById('divChart').style.display = "block";
	            document.getElementById('divTable').style.display = "none";
	        }
		})

	};

	function bindQueryEvent(){
		$("#queryBtn").click(function(){
			loadGridData(false);
		})
	};
	
	function bindExportEvent(){
		$("#exportBtn").click(function(){
			var $eleForm = $("<form method='POST'></form>");
            $eleForm.attr("action",getRootPath() + "/bu/download");
            var $eleInput = $('<input type="hidden" name="buName" value="'+ $("#bu_sel").val() +'">');
            $eleForm.append($eleInput);
            $eleForm.append($('<input type="hidden" name="month" value="'+ $("#month-sel").val() +'">'));
            $eleForm.append($('<input type="hidden" name="pdu" value="'+ $("#pdu-sel").val() +'">'));
            $eleForm.append($('<input type="hidden" name="type" value="'+ $("#type-sel").val() +'">'));
            $eleForm.append($('<input type="hidden" name="area" value="'+ $("#area-sel").val() +'">'));
            $eleForm.append($('<input type="hidden" name="no" value="'+ $("#projNo").val() +'">'));
            $eleForm.append($('<input type="hidden" name="name" value="'+ $("#projName").val() +'">'));
            $eleForm.append($('<input type="hidden" name="pm" value="'+ $("#projMgmr").val() +'">'));
            $(document.body).append($eleForm);

            //提交表单，实现下载
            $eleForm.submit();
		})

	};

	function initIndicatorUL(ulName, indicator, colName){
		var data = indicator;
		var wtds_ul = "";
		_.each(data, function(val, index){
			wtds_ul += '<li><div><a href="' +getRootPath()+ '/bu/projView?projNo=' + val.no  + '&buName=' + $("#bu_sel").val() + '"><span title="'+ val.name + '">' + (index+1) + ' ' +  val.name +'</span></a></div><b>'
				+ val.value +'</b></li>';
		});
		$("#"+ulName).html(wtds_ul);
	};

	function initProjIndicators(){
		$.ajax({
				url: getRootPath() + "/bu/projIndicators",
				type: 'post',
				data: {
					bu: $("#bu_sel").val(),
					wtdhgbtggs: $('#wtds-div').text(),
					bbzcssbcs: $('#lltsbyls-div').html(),
					wswts: $('#ddqxs-div').html(),
					dml: $('#dmzl-div').html(),
					sdylzxxl: $('#zxyls-div').html(),
					ddckwtjjl: $('#ddqxxgxl-div').html()
				},
				success: function (data) {
					if(data){
						initIndicatorUL('wtds', data.wtds);
						initIndicatorUL('lltsbyls', data.llt);
						initIndicatorUL('ddqxs', data.ddqxs);
						initIndicatorUL('dmzl', data.xmdml);
						initIndicatorUL('zxyls', data.zxyls);
						initIndicatorUL('ddqxxgxl', data.ddqxxgxl);
					}
				}
			});
	};
	function sortBy(rev){
	    //第二个参数没有传递 默认升序排列
	    if(rev ==  undefined){
	        rev = 1;
	    }else{
	        rev = (rev) ? 1 : -1;
	    }
	    return function(a,b){

	        if(a < b){
	            return rev * -1;
	        }
	        if(a > b){
	            return rev * 1;
	        }
	        return 0;
	    }
	};
	function initLeftNav(){
		$.ajax({
				url: getRootPath() + "/bu/ProjCategory",
				type: 'post',
				success: function (data) {
					var nav_ul = "";
					_.map(data, function(val, key){
						nav_ul = $('<li><a href="index.html" class="inactive"><i class="num-3"></i>'+ key + '</a>'
                    			+'<ul style="display: none"></ul></li>');
                    	$("#leftNav").append(nav_ul);
                    	var bu_ul = "";
                    	_.map(val, function(bus, buName){
                    		bu_ul = $('<li><a href="javascript:void(0)" class="active">'+ buName  +'</a> </li>');
                    		$(nav_ul).find('ul').append(bu_ul);
                    		var tbl = $('<div class="item">'+
                            	'<table width="100%" border="0" cellspacing="0" cellpadding="0"></table></div>');
                    		$(bu_ul).find('a').after(tbl);
                    		var $tr = '';
                    		var months = [];
                    		_.map(bus, function(n, i){
                    			months.push(i);
                    		});
                    		months.sort(sortBy(false));
                    		_.each(months, function(data, index){ //阻止JS中MAP自动排序
                    			_.map(bus, function(monList, mon){
                    				if(mon == data){
	                        			$tr += ' <tr><td colspan="4"><div>'+ mon +'</div>  <hr /></td></tr>' + '<tr>';
	                        			_.each(monList, function(po, index){
	                        				$tr += '<td width="25%" valign="middle"> <a href="' +getRootPath()+ '/bu/projView?projNo=' + po.no + '&buName=' + key +'" class="current">'+po.name+'</a></td>';
	                        				if( index > 0 && index % 3 == 0){
	                        					$tr += '</tr><tr>';
	                        				}
	                        			})
	                        			$tr += '</tr>';
                    				}
                        		})
                    		})
                    	
                    		$(tbl).find('table').append($($tr));
                    	})
					})
					
					initDownMenu();
					initMouseOverMenu();
						
				}
		})
		
		
	};
	$(document).ready(function(){
		initLeftNav();
		initBuSel();
		buChangeEvent();
//		bindChangeModelEvent();
		bindQueryEvent();
		bindExportEvent();
	})
})()