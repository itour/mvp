/**
 * @author 刘文艳
 * @see 项目度量信息维护--项目详细信息
 */
(function() {
	function ProjDInfo() {
	};
	var projDInfo = new ProjDInfo();
	/*
	 * ProjDInfo.prototype.initSelectOpts = function(datas, name, id){ var bus =
	 * _.uniq(_.pluck(datas, name)); var buopt = '<option value=""></option>';
	 * _.each(bus, function(value, index){ if(value != null && value != 'null'){
	 * buopt += '<option value="'+ value +'">' + value + '</option>'; } })
	 * $("#" + id).html(buopt); };
	 */

	var projectNo;
	ProjDInfo.prototype.initData = function(name, id) {
		$("#" + id).val(name);
	};
	ProjDInfo.prototype.convertDate = function(name, id) {debugger;
		var dateTMp = '';
		if(name){
			dateTMp = new Date(name).format('yyyy-MM-dd');
		}
		$("#" + id).val(dateTMp);
	};
	ProjDInfo.prototype.getPM = function() {
		$.ajax({
			type : 'POST',
			url : getRootPath() + "/project/queryPM",
			data : {
				projNo : projectNo
			},
			success : function(data) {
				var datas = data.rows;
				if (null != datas) {
					projDInfo.initData(datas.name, 'manager');
				}
			}
		});
	};
	ProjDInfo.prototype.initProjInfo = function(init) {
		$.ajax({
			type : 'POST',
			url : getRootPath() + "/project/queryprojinfo",
			data : {
				'projNo' : projectNo
			},
			success : function(data) {
				if (data) {
					if (init) {
						var datas = data.projInfo;
						if (null != datas) {
							projDInfo.initData(datas.name, 'name');
							projDInfo.initData(datas.no, 'no');
							projDInfo.initData(datas.projectType,
										'projectType');
							projDInfo.initData(datas.type, 'type');
							projDInfo.initData(datas.bu, 'bu');
							projDInfo.initData(datas.pdu, 'pdu');
							projDInfo.initData(datas.du, 'du');
							projDInfo.initData(datas.area, 'area');
							projDInfo.initData(datas.predictMoney,
										'predictMoney');
							projDInfo.initData(datas.workerCount,
										'workerCount');
							projDInfo.convertDate(datas.startDate,'startDate');
							projDInfo.convertDate(datas.endDate,'endDate');
							projDInfo.initData(datas.po,'PO');
							projDInfo.initData(datas.version,'version');
							projDInfo.initData(datas.countOfDay,'countOfday');
							projDInfo.initData(datas.countOfMonth,'countOfmonth');
								}

							}

						}
					}

				});
	};
	ProjDInfo.prototype.bindAdd = function(no) {
		$("#updateInfo").click(function() {
          window.parent.initUpdateAddInfo(no);
		});
	};
	$(document).ready(function() {
		projectNo = $("#noHid").val();
		projDInfo.bindAdd(projectNo);
		projDInfo.initProjInfo(true);
		projDInfo.getPM();
	});

})()