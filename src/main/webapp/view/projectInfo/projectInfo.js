var parma;
(function() {

	function ProjectInfo() {

	};
	var projectInfo = new ProjectInfo();
	ProjectInfo.prototype.initSelectOpts = function(datas, name, id){
		var bus = _.uniq(_.pluck(datas, name));
		var buopt;
		_.each(bus, function(value, index){
			if(value != null && value != 'null'){
				buopt+= '<option value="'+ value +'">' + value + '</option>';
			}
		});
		$("#" + id).html(buopt);	
		
	};

	ProjectInfo.prototype.getArea=function(){
		$.ajax({
			type : 'POST',
			data:{
				'codekey' : 'area'
			},
			async: false,
			url :getRootPath()+"/codeMaster/list",
		    success : function(data) {
						var datas = data.rows;
					projectInfo.initSelectOpts(datas, 'name', 'area');	
	}
  });
};

ProjectInfo.prototype.getType=function(){
	$.ajax({
		type : 'POST',
		data:{
			'codekey' : 'payType'
		},
		async: false,
		url :getRootPath()+"/codeMaster/list",
	    success : function(data) {
					var datas = data.rows;
				projectInfo.initSelectOpts(datas, 'name', 'payType');
}
});
};

ProjectInfo.prototype.getProType=function(){
	$.ajax({
		type : 'POST',
		data:{
			'codekey' : 'projectType'
		},
		async: false,
		url :getRootPath()+"/codeMaster/list",
	    success : function(data) {
					var datas = data.rows;
				projectInfo.initSelectOpts(datas, 'name', 'projectType');
}
});
};
ProjectInfo.prototype.getPayDepart=function(){
	$.ajax({
		type : 'POST',
		data:{
			'codekey' : 'DeliveryDepartment'
		},
		async: false,
		url :getRootPath()+"/codeMaster/list",
	    success : function(data) {
					var datas = data.rows;
				projectInfo.initSelectOpts(datas, 'name', 'deliveryUnit');
}
});
};

ProjectInfo.prototype.getBU=function(){
	$.ajax({
		type : 'POST',
		data:{
			'level' : 0
		},
		async: false,
		url :getRootPath()+"/organiseMg/list",
	    success : function(data) {
					var datas = data.rows;
				projectInfo.initSelectOpts(datas, 'nodeName', 'businessUnit');
					
}
});
};


ProjectInfo.prototype.getPDU=function(){
	$("#businessUnit").change(function(){
		if($("#businessUnit").val()!=''){
		$.ajax({
				type : 'POST',
				data:{
					'nodeName' : $("#businessUnit").val(),
					'level':0
				},
				async: false,
				url :getRootPath()+"/organiseMg/listPDU",
			    success : function(data) {
							var datas = data.rows;
						projectInfo.initSelectOpts(datas, 'nodeName', 'productUnit');
		   }
		   });
		}
		});	
};

ProjectInfo.prototype.getOrignBU=function(){
	$.ajax({
		type : 'POST',
		data:{
			'nodeId' : 1,
			'level':0
		},
		async: false,
		url :getRootPath()+"/organiseMg/getOrignBU",
	    success : function(data) {
	    	projectInfo.initPDU(data.rows.nodeName);
 }
})
}



ProjectInfo.prototype.initPDU=function(nodeName){
		$.ajax({
				type : 'POST',
				data:{
					'nodeName' : nodeName,
					'level':0
				},
				async: false,
				url :getRootPath()+"/organiseMg/listPDU",
			    success : function(data) {
							var datas = data.rows;
						projectInfo.initSelectOpts(datas, 'nodeName', 'productUnit');
		   }
		   });
}

ProjectInfo.prototype.judgePDU=function(nodeName){
	$.ajax({
			type : 'POST',
			data:{
				'nodeName' : nodeName
			},
			async: false,
			url :getRootPath()+"/organiseMg/getPDU",
		    success : function(data) {
						var datas = data.rows;
					projectInfo.initSelectOpts(datas, 'nodeName', 'productUnit');
	   }
	   });
}

ProjectInfo.prototype.judgeBU=function(nodeName){
	$.ajax({
		type : 'POST',
		data:{
			'nodeName' : nodeName
		},
		async: false,
		url :getRootPath()+"/organiseMg/getBU",
	    success : function(data) {
					var datas = data.rows;
				 projectInfo.selectData(datas.nodeName,'businessUnit');
}
});
}




var projectNo;
ProjectInfo.prototype.initData = function(name, id) {
	$("#" + id).val(name);
};
ProjectInfo.prototype.convertDate = function(name, id) {
	var dateTMp = '';
	if(name){
		dateTMp = new Date(name).format('yyyy-MM-dd');
	}
	$("#"+id).datebox("setValue", dateTMp);
};

ProjectInfo.prototype.selectData=function(name,id){
	$("#" + id).each(function(){
        $(this).children("option").each(function(){
            if($(this).val() == name){
                $(this).attr("selected","selected");
            }
    });
	
});
	
};
ProjectInfo.prototype.init=function(init){
	$.ajax({
		type : 'POST',
		url : getRootPath() + "/project/queryprojinfo",
		data:{
			projNo: projectNo
		},
	    success : function(data) {
	    	if (data) {
				if (init) {
					var datas = data.projInfo;
					if (null != datas) {
						projectInfo.initData(datas.name, 'projName');
						projectInfo.initData(datas.no, 'projNo');
						projectInfo.getProType();
						projectInfo.selectData(datas.projectType,'projectType');
						projectInfo.getType();
						projectInfo.selectData(datas.type,'payType');
						if(datas.bu==null && datas.pdu==null){
							 projectInfo.getBU();
							 projectInfo.getOrignBU();
						}
						if(datas.bu!=null&& datas.pdu!=null){
							projectInfo.getBU();
							projectInfo.selectData(datas.bu,'businessUnit');
							projectInfo.initPDU(datas.bu);
							projectInfo.selectData(datas.pdu,'productUnit');
						}
						if(datas.bu!=null && datas.pdu==null){
							projectInfo.getBU();
							projectInfo.selectData(datas.bu,'businessUnit');
							projectInfo.initPDU(datas.bu);
						}
						if(datas.bu==null&& datas.pdu!=null){
							projectInfo.getBU();
							projectInfo.judgeBU(datas.pdu);
							projectInfo.judgePDU(datas.pdu);
							projectInfo.selectData(datas.pdu,'productUnit');
						}
						
						projectInfo.getPayDepart();
						projectInfo.selectData(datas.du,'deliveryUnit');
						projectInfo.getArea();
						projectInfo.selectData(datas.area, 'area');
						projectInfo.initData(datas.predictMoney,
									'predictMoney');
						projectInfo.initData(datas.workerCount,
									'workerCount');
						projectInfo.convertDate(datas.startDate,'startDate');
						projectInfo.convertDate(datas.endDate,'endDate');
						projectInfo.initData(datas.po,'PO');
						projectInfo.initData(datas.version,'version');
						projectInfo.initData(datas.countOfDay,'countOfday');
						projectInfo.initData(datas.countOfMonth,'countOfmonth');
							}
						}

					}
				}

			});
	
}
ProjectInfo.prototype.getPM=function(){
	$.ajax({
		type : 'POST',
		url : getRootPath() + "/project/queryPM",
		data:{
			projNo: projectNo
		},
		success : function(data) {
			var datas=data.rows;
			if(null!=datas){
			 projectInfo.initData(datas.name,'manager'); 
			}
			}
		});
}


$(document).ready(function() {
	 parma=$("#parm").val(); 
	if(parma=='add'){
		 $("#projNo").attr("readOnly",false);
		 projectInfo.getArea();
		 projectInfo.getType();
		 projectInfo.getPayDepart();
		 projectInfo.getProType();
		 projectInfo.getBU();
		 projectInfo.getOrignBU();
		 projectInfo.getPDU();	
		 }else{
			 $("#projNo").attr("readOnly",true);
			 projectNo=$("#noHid").val();
			 ProjectInfo.prototype.getPDU();
			 projectInfo.init(true);
			 projectInfo.getPM();
		 }
	});

})();



