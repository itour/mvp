// 描述：获取url当前项目的url地址头信息
function getRootPath()
{
	// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPath = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

	return (localhostPath + projectName);
}

function getParameter(param)
{
	var query = window.location.search;// 获取URL地址中？后的所有字符
	var iLen = param.length;// 获取你的参数名称长度
	var iStart = query.indexOf(param);// 获取你该参数名称的其实索引
	if (iStart == -1)// -1为没有该参数
		return "";
	iStart += iLen + 1;
	var iEnd = query.indexOf("&", iStart);// 获取第二个参数的其实索引
	if (iEnd == -1)// 只有一个参数
		return query.substring(iStart);// 获取单个参数的参数值
	return query.substring(iStart, iEnd);// 获取第二个参数的值
}


/**
 *
 * @param value
 * @returns
 *为项目详情页面各页签获取数据
 */
function initGrid(projNo, bigType, smallType, gridId, selId)
{
	var defer = $.Deferred();
    $.ajax({
        url: getRootPath() + "/bu/meausreResult" ,
        type: 'POST',
        data: {
            projNo: projNo,
            bigType: bigType,
            smallType: smallType
        },
        success: function(data){
            var options = {
                rownumbers: false,
                striped: true,
                fitColumns:false,
                nowrap: true,
	            onLoadSuccess:function(data){   
	            	$("#" + gridId).datagrid('doCellTip',{cls:{},delay:1000, titleUnit:$("#" + gridId).data('gridUnit')});   
	            }
            };
            var wdth = Math.round(100 / data.title.length);
            var lastWd = Math.round(100 / data.title.length) * data.title.length;
            lastWd = 100 > lastWd ? (wdth + 100 - lastWd) : ( wdth - lastWd + 100);
            _.each(data.title, function(val, index){
            	val.width = wdth  + '%';
            })
            data.title[data.title.length -1].width = lastWd  + '%';
            $("#" + gridId).data('gridUnit', data.units);
            options.columns = [data.title];
            $("#" + gridId).datagrid(options);
        	var gridDatas = {};
        	$.extend(true, gridDatas, data.gridDatas);
            $("#" + gridId).datagrid("loadData", gridDatas);
            if(selId){
            	var options = "";
            	_.each(data.title, function(val, index){
            		if(val.field != 'month'){
            			options += '<option value="'+ val.title +'">' + val.title +'</option>';
            		}
            	})
            	$('#' + selId).html(options);
            }
            $("#" + gridId).data('gridData', data.gridDatas);

            defer.resolve();
        }
    })
    return defer;
}

/**
 * 
 * @param indicatorId
 * @param name
 * 绘制柱状图
 */
function reloadChart(gridData, name, chartOpt, chartId){
	chartOpt.xAxis = [];
	chartOpt.series = [];
	if(gridData && !_.isEmpty(gridData.rows)){
		chartOpt.xAxis = [{
			data: _.pluck(gridData.rows, 'month')
		}];
		chartOpt.series = [{
	        name: name,
	        type: 'bar',
	        data: _.pluck(gridData.rows, name)
	    }];
	}
	chartOpt.legend = {data: [name]};
	chartId.setOption(chartOpt);
};

function initMouseOverMenu(){
    $('.list > .yiji ul li').hover(function () {
        var eq = $('.list > .yiji ul li').index(this), 			//获取当前滑过是第几个元素
		h = $('.list').offset().top, 					//获取当前下拉菜单距离窗口多少像素
		s = $(window).scrollTop(), 								//获取游览器滚动了多少高度
		i = $(this).offset().top, 								//当前元素滑过距离窗口多少像素
		item = $(this).children('.item').height(), 			//下拉菜单子类内容容器的高度
		sort = $('.list').height(); 					//父类分类列表容器的高度

        if (item < sort) {												//如果子类的高度小于父类的高度
            if (eq == 0) {
                $(this).children('.item').css('top', (i - h));
            } else {
                $(this).children('.item').css('top', (i - h) + 1);
            }
        } else {
            if (s > h) {												//判断子类的显示位置，如果滚动的高度大于所有分类列表容器的高度
                if (i - s > 0) {											//则 继续判断当前滑过容器的位置 是否有一半超出窗口一半在窗口内显示的Bug,
                    $(this).children('.item').css('top', (s - h) + 2);
                } else {
                    $(this).children('.item').css('top', (s - h) - (-(i - s)) + 2);
                }
            } else {
                $(this).children('.item').css('top', 3);
            }
        }

        $(this).addClass('hover');
        $(this).children('.item').css('display', 'block');
    }, function () {
        $(this).removeClass('hover');
        $(this).children('.item').css('display', 'none');
    });
};
function initDownMenu(){
	$('.inactive').click(function (e) {
        e.preventDefault();
        if ($(this).siblings('ul').css('display') == 'none') {
            $(this).parent('li').siblings('li').children('a').removeClass('inactives');
            $(this).addClass('inactives');
            $(this).parents('li').siblings('li').children('ul').slideUp(100);
            $(this).siblings('ul').slideDown(100).children('li');
        } else {
            //控制自身变成+号
            $(this).removeClass('inactives');
            //控制自身菜单下子菜单隐藏
            $(this).siblings('ul').slideUp(100);
            //控制自身子菜单变成+号
//                $(this).siblings('ul').children('li').children('ul').parent('li').children('a').addClass('inactives');
//                //控制自身菜单下子菜单隐藏
            $(this).siblings('ul').children('li').children('ul').slideUp(100);

            //控制同级菜单只保持一个是展开的（-号显示）
            $(this).siblings('ul').children('li').children('a').removeClass('inactives');
        }
    });
};

/**
 * 
 * @param value
 * @returns
 * 为easyui提供显示时间格式的数据
 */

function formatDatebox(value) {  
    if (value == null || value == '') {  
        return '';  
    }  
    var dt;  
    if (value instanceof Date) {  
        dt = value;  
    }  
    else {  
        dt = new Date(value);  
        if (isNaN(dt)) {  
            value = value.replace(/\/Date(−?\d+)\//, '$1'); //标红的这段是关键代码，将那个长字符串的日期值转换成正常的JS日期格式  
            dt = new Date();  
            dt.setTime(value);  
        }  
    }  
  
    return dt.format("yyyy-MM-dd");   //这里用到一个javascript的Date类型的拓展方法，这个是自己添加的拓展方法，在后面的步骤3定义  
} 


$.extend(
        $.fn.datagrid.defaults.editors, {  
            datebox: {  
                init: function (container, options) {  
                    var input = $('').appendTo(container);  
                    input.datebox(options);  
                    return input;  
                },  
                destroy: function (target) {  
                    $(target).datebox('destroy');  
                },  
                getValue: function (target) {  
                    return $(target).datebox('getValue');  
                },  
                setValue: function (target, value) {  
                    $(target).datebox('setValue', formatDatebox(value));  
                },  
                resize: function (target, width) {  
                    $(target).datebox('resize', width);  
                }  
            }  
        });  
Date.prototype.format = function (format)   
{  
    var o = {  
        "M+": this.getMonth() + 1, //month   
        "d+": this.getDate(),    //day   
        "h+": this.getHours(),   //hour   
        "m+": this.getMinutes(), //minute   
        "s+": this.getSeconds(), //second   
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter   
        "S": this.getMilliseconds() //millisecond   
    }  
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,  
    (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    for (var k in o) if (new RegExp("(" + k + ")").test(format))  
        format = format.replace(RegExp.$1,  
      RegExp.$1.length == 1 ? o[k] :  
        ("00" + o[k]).substr(("" + o[k]).length));  
    return format;  
}  


	function pagerFilter(data){
		if ($.isArray(data)){	// is array
			data = {
				total: data.length,
				rows: data
			}
		}
		var target = this;
		var dg = $(target);
		var state = dg.data('datagrid');
		var opts = dg.datagrid('options');
		if (!state.allRows){
			state.allRows = (data.rows);
		}
		if (!opts.remoteSort && opts.sortName){
			var names = opts.sortName.split(',');
			var orders = opts.sortOrder.split(',');
			state.allRows.sort(function(r1,r2){
				var r = 0;
				for(var i=0; i<names.length; i++){
					var sn = names[i];
					var so = orders[i];
					var col = $(target).datagrid('getColumnOption', sn);
					var sortFunc = col.sorter || function(a,b){
						return a==b ? 0 : (a>b?1:-1);
					};
					r = sortFunc(r1[sn], r2[sn]) * (so=='asc'?1:-1);
					if (r != 0){
						return r;
					}
				}
				return r;
			});
		}
		var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
		var end = start + parseInt(opts.pageSize);
		data.rows = state.allRows.slice(start, end);
		return data;
	}

	var loadDataMethod = $.fn.datagrid.methods.loadData;
	var deleteRowMethod = $.fn.datagrid.methods.deleteRow;
	$.extend($.fn.datagrid.methods, {
		clientPaging: function(jq){
			return jq.each(function(){
				var dg = $(this);
                var state = dg.data('datagrid');
                var opts = state.options;
                opts.loadFilter = pagerFilter;
                var onBeforeLoad = opts.onBeforeLoad;
                opts.onBeforeLoad = function(param){
                    state.allRows = null;
                    return onBeforeLoad.call(this, param);
                }
                var pager = dg.datagrid('getPager');
				pager.pagination({
					onSelectPage:function(pageNum, pageSize){
						opts.pageNumber = pageNum;
						opts.pageSize = pageSize;
						pager.pagination('refresh',{
							pageNumber:pageNum,
							pageSize:pageSize
						});
						dg.datagrid('loadData',state.allRows);
					}
				});
                $(this).datagrid('loadData', state.data);
                if (opts.url){
                	$(this).datagrid('reload');
                }
			});
		},
        loadData: function(jq, data){
            jq.each(function(){
                $(this).data('datagrid').allRows = null;
            });
            return loadDataMethod.call($.fn.datagrid.methods, jq, data);
        },
        deleteRow: function(jq, index){
        	return jq.each(function(){
        		var row = $(this).datagrid('getRows')[index];
        		deleteRowMethod.call($.fn.datagrid.methods, $(this), index);
        		var state = $(this).data('datagrid');
        		if (state.options.loadFilter == pagerFilter){
        			for(var i=0; i<state.allRows.length; i++){
        				if (state.allRows[i] == row){
        					state.allRows.splice(i,1);
        					break;
        				}
        			}
        			$(this).datagrid('loadData', state.allRows);
        		}
        	});
        },
        getAllRows: function(jq){
        	return jq.data('datagrid').allRows;
        }
	})
