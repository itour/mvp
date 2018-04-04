function save(){
	if($("#projName").val()!="" && $("#projNo").val()!=""){
$.ajax({
	   type : 'POST',
	   url :getRootPath()+"/project/insertInfo",
	   data:{
		'name': $("#projName").val(),
         'no' : $("#projNo").val(),
         'projectType':$("#projectType").val(),
         'type':$("#payType").val(),
         'bu' :$("#businessUnit").val(),
         'pdu':$("#productUnit").val(),
         'du':$("#deliveryUnit").val(),
         'area':$("#area").val(),
         'predictMoney':$("#predictMoney").val(),
         'manager':$("#manager").val(),
         'po':$("#PO").val(),
         'countOfDay':$("#countOfday").val(),
         'countOfMonth':$("#countOfmonth").val(),
         'workerCount':$("#workerCount").val(),
         'startTime':$("#startDate").val(),
         'endTime' :$("#endDate").val(),
         'version' :$("#version").val(),
         'param':parma
	},
    success : function(data) {
    	if(data.message=="保存失败"){
    		$.messager.alert('提示', data.message, 'info');
    	}else{
              if(data.param=" "){
            	  window.parent.$('#tbs').tabs('close','项目参数列表'+$("#projNo").val());
            	  window.parent.initMaintainList($("#projNo").val());
            	  window.parent.$('#tbs').tabs('close','项目基本信息'+$("#projNo").val()); //关闭tabs中的当前页面
            	  
              }
                 window.parent.initMaintainList($("#projNo").val());
            	 window.parent.$('#tbs').tabs('close','项目基本信息');   	 
    	}
  }
});
}else{
	judge();
}
}

function judge(){

if ($("#projName").val() == ""){
	  $("#TextNote1").html("请输入项目名称！");
} else{
	    $("#TextNote1").html("");
	     } 

if ($("#projNo").val() == ""){
	  $("#TextNote2").html("请输入项目编号！");
} else{
	    $("#TextNote2").html("");
	     } 
}
function changeName(){
	if($("#projName").val()!=" "){
		 $("#TextNote1").html("");	
}
}

function changeNo(){
	  $.ajax({
			 type : 'POST',
			  url :getRootPath()+"/project/isExitProj",
			   data:{
		         'no' : $("#projNo").val()
			   },
	     success : function(data) {
	    	var proj=document.getElementById("projNo").readOnly;
	    	 if(proj==false){
	    		$("#TextNote2").html(data);
	    		}else{
	    		$("#TextNote2").html("");	
	    	 }
	    }
	  });
	}





