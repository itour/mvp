(function(){
	function getRequest() {
		  var url = window.location.search; //获取url中"?"符后的字串
		  var theRequest = new Object();
		  if (url.indexOf("?") != -1) {
		    var str = url.substr(1);
		    strs = str.split("&");
		    for(var i = 0; i < strs.length; i ++) {
		       
		      theRequest[strs[i].split("=")[0]]=decodeURI(strs[i].split("=")[1]);
		       
		    }
		  }
		  return theRequest;
		}
	var projNo= getRequest().projNo;
	var buName = getRequest().projBU;
	
	
	var projNo1 = window.parent.projNo;
	var buName1 = window.parent.projBU;
	function refresh(){
		$.ajax({
				url: getRootPath() + '/svnTask/svn',
				type: 'post',
				success: function (data) {
					search();
				}
			});
	};
	
	function search(){
		$.ajax({
				url: getRootPath() + '/svnTask/searchGeRen',
				type: 'post',
				async: false,
				data: {
					projNo: 'HWHZP5FF1606F03X11'
				},
				success: function (data) {
					debugger
					var addNums = 0;
					var tab = "";
					$("#gerenCode tr:not(:first)").remove();
					if(!_.isEmpty(data)){
						var chartsData ={
								january:0,
								february:0,
								march:0,
								april:0,
								may:0,
								june:0,
								july:0,
								august:0,
								september:0,
								october:0,
								november:0,
								december:0
						};
						_.each(data, function(record, key){
							record.sum= record.january
							+record.february+record.march
							+record.april+record.may
							+record.june+record.july
							+record.august+record.september
							+record.october+record.november
							+record.december;
							tab += '<tr><td>'+ record.name +'</td>'
								   +'<td>'+ record.number +'</td>'
								   +'<td>'+ record.january +'</td>'
								   +'<td>'+ record.february +'</td>'
								   +'<td>'+ record.march +'</td>'
								   +'<td>'+ record.april +'</td>'
								   +'<td>'+ record.may +'</td>'
								   +'<td>'+ record.june +'</td>'
								   +'<td>'+ record.july +'</td>'
								   +'<td>'+ record.august +'</td>'
								   +'<td>'+ record.september +'</td>'
								   +'<td>'+ record.october +'</td>'
								   +'<td>'+ record.november +'</td>'
								   +'<td>'+ record.december +'</td>'
								   +'<td>'+ record.sum+'</td></tr>';
							
							chartsData.january+=record.january;
							chartsData.february+=record.february;
							chartsData.march+=record.march;
							chartsData.april+=record.april;
							chartsData.may+=record.may;
							chartsData.june+=record.june;
							chartsData.july+=record.july;
							chartsData.august+=record.august;
							chartsData.september+=record.september;
							chartsData.october+=record.october;
							chartsData.november+=record.november;
							chartsData.december+=record.december;
						})
					}
					$("#gerenCode tbody").append(tab);
					var montharray = [chartsData.january,chartsData.february,chartsData.march,chartsData.april,chartsData.may,chartsData.june,chartsData.july,chartsData.august,chartsData.september,chartsData.october,chartsData.november,chartsData.december];
					option1.series=[{
						data:montharray
					}];
					myChart1.setOption(option1);
				}
			});
	
	};
	$(document).ready(function(){
		refresh();
		search();
	})
})()