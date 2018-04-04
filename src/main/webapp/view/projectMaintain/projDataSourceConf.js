/**
 * @author 刘文艳
 * @see 项目度量信息维护--数据源配置
 */
	function getCurUser(){
		return 'admin';
	}
	function updateDSCActions(index){
		$('#projectDSConfig').datagrid('refreshRow',index);
	}
	function getDSCRowIndex(target){
		var tr = $(target).closest('tr.datagrid-row');
		return parseInt(tr.attr('datagrid-row-index'));
	}
	function editdscrow(target){
		$("#projectDSConfig").datagrid('removeEditor','source_value');
		$('#projectDSConfig').datagrid('beginEdit', getDSCRowIndex(target));
	}
	function deletedscrow(target){
		$.messager.confirm('Confirm','Are you sure?',function(r){
			if (r){
				var tar = getDSCRowIndex(target);
				var rows = $('#projectDSConfig').datagrid('getRows');
				var row = rows[tar];
				$.ajax({
					type:'POST',
					url:getRootPath()+'/project/deldatasource',
					data:{
						'no':$("#noHid").val(),
						'source_value':row.source_value						
					},
					success:function(data){
						if(1!=data.resultCode){
							$.messager.alert('提示',"数据删除失败："+data.resultCode, 'info');
						}						
					}
				});
				$('#projectDSConfig').datagrid('deleteRow', getDSCRowIndex(target));
			}
		});
	}
	function isValidProjDSInfo(rows,tar){
		var row = rows[tar];
		var index;
		for(index =0;index <rows.length;index++){
			var eachRow = rows[index];
			if(index != tar){
				if(eachRow.source_value == row.source_value){
					return false;
				}
			}
		}
		return true;
	}
    $.extend($.fn.datagrid.methods, {    
        addEditor : function(jq, param) {   
            if (param instanceof Array) {   
                $.each(param, function(index, item) {  
                    var e = $(jq).datagrid('getColumnOption', item.field); 
                    e.editor = item.editor; }); 
                } else {    
                    var e = $(jq).datagrid('getColumnOption', param.field);    
                    e.editor = param.editor;    
                }   
            },  
        removeEditor : function(jq, param) {    
            if (param instanceof Array) {   
                $.each(param, function(index, item) {  
                    var e = $(jq).datagrid('getColumnOption', item);   
                    e.editor = {};  
                    }); 
            } else {    
                var e = $(jq).datagrid('getColumnOption', param);
                e.editor = {};  
            }   
        }
    });
   
	var deleteLabel = true;
	function savedscrow(target){
		var tar = getDSCRowIndex(target);
		$('#projectDSConfig').datagrid('endEdit', tar);
		var rows = $('#projectDSConfig').datagrid('getRows');
		var row = rows[tar];
		if(!isValidProjDSInfo(rows,tar)||row.source_value == undefined){
			// 提示输入有误
			$.messager.alert('提示',"数据插入失败，该数据源已配置或为空", 'info');
			$('#projectDSConfig').datagrid('deleteRow', tar);
		}
		else{
			var operateDate = new Date();
			if(row.creator){
				// update
				var updateUser= getCurUser();
				$.ajax({
					type:'POST',
					url:getRootPath()+'/project/udpatedatasource',
					data:{
						'no':$("#noHid").val(),
						'source_value':row.source_value,
						'url':row.url,
						'user':row.user,
						'password':row.password,
						'version':row.version,
						'updateDate':operateDate,
						'updateUser':updateUser				
					},
					success:function(data){
						if(1 != data.resultCode){
							$.messager.alert('提示',"数据修改失败："+data.resultCode, 'info');
						}
//						alert("数据修改成功"+data.resultCode);
					}
				});
	
			}else{
				// insert
				deleteLabel = false;
				var createUser= getCurUser();
				$.ajax({
					type:'POST',
					url:getRootPath()+'/project/insertdatasource',
					data:{
						'no':$("#noHid").val(),
						'source_value':row.source_value,
						'url':row.url,
						'user':row.user,
						'password':row.password,
						'version':row.version,
						'createDate':operateDate,
						'creator':createUser		
					},
					success:function(data){
						if(1!=data.resultCode){
							$.messager.alert('提示', "数据源添加失败"+data.resultCode, 'info');
						}						
					}
				});
			}
		}
	}
	
	function canceldscrow(target){
		var tar = getDSCRowIndex(target);
		var rows = $('#projectDSConfig').datagrid('getRows');
		var row = rows[tar];
		$('#projectDSConfig').datagrid('cancelEdit', tar);
		$('#projectDSConfig').datagrid('reload');
		if(row.source_value == null){
			if (deleteLabel) {
				$('#projectDSConfig').datagrid('deleteRow', tar);
			}
		};
	}
	function insertfordsc(){
		var row = $('#projectDSConfig').datagrid('getSelected');
		if (row){
			var index = $('#projectDSConfig').datagrid('getRowIndex', row);
		} else {
			index = 0;
		}
		$('#projectDSConfig').datagrid('insertRow', {
			index: index,
			row:{
				status:'P'
			}
		});
		deleteLabel = true;
		$('#projectDSConfig').datagrid('selectRow',index);
		$('#projectDSConfig').datagrid('beginEdit',index);
	}
(function() {
	function ProjDSConf() {

	};
	var projDSList = new ProjDSConf();
	// getDataSourceTypeList
	var cmForDS ;
	ProjDSConf.prototype.getcmForDS = function() {
		$.ajax({
			type : 'GET',
			async:false,
			url : getRootPath() + "/project/queryprojcmfords",
			success : function(data){
				if(data){
					cmForDS = new Array();
					
					var index = 0;
					for(index=0;index<data.total;index++){
						var eachdata = new Object();
						eachdata = data.rows[index];
						cmForDS[index]=eachdata;
					}		
				}
			}
		}
		);
		
	};
	var projectNo;
	ProjDSConf.prototype.getProjDSList = function(init, field, order) {
		var url = getRootPath() + "/project/queryprojdsinfo";
		if(field){
			url += "?sort="+ field + "&order=" + order;
		}
		$.ajax({
			type : 'POST',
			url : url,
			data : {
				'projNo' : projectNo
			},
			success : function(data) {
				if (data) {
					$('#projectDSConfig').datagrid("loadData", data);
				}
			}
		});

	};
	ProjDSConf.prototype.initGrid = function(){
		var table = $("#projectDSConfig");
		table.datagrid({
			iconCls:'icon-edit',
			singleSelect:true,
			idField:'source_value',
			rownumbers : true,
			columns:[[
			          {field:'source_value',title:'源名称',width:'16%',align:'center',sortable : true,
			        	  formatter:function(value){
							for(var i=0; i<cmForDS.length; i++){
								if (cmForDS[i].value == value) return cmForDS[i].name;
							}
							return value;
			        	  },
						editor:{
							type:'combobox',
							options:{
								valueField:'value',
								textField:'name',
								data:cmForDS,
								required:true,
								editable:false
							}
						}
			          },
			          {field:'url',title:'URL',width:'26%',align:'center',sortable : true,editor:'textbox'},
			          {field:'user',title:'用户名',width:'16%',align:'center',sortable : true,editor:'textbox'},
			          {field:'password',title:'密码',width:'16%',align:'center',sortable :true,
			        	  formatter:function(){
			        		  return '●●●●●●●●'; 
			        	  },
			        	  editor:{ 
			        	  type:'passwordbox', 
			        	  options:{
			        		  showEye: false
							}	
			        	  }
			          },
			          {field:'version',title:'版本号',width:'14%',align:'center',sortable : true,editor:'textbox'},
			          {field:'action',title:'操作',width:'12%',align:'center',
			        	  formatter:function(value,row,index){
			        		  if(row.editing){
			        			  var s='<a href="#" onclick="savedscrow(this)">保存</a> ';
			        			  var c = '<a href="#" onclick="canceldscrow(this)">取消</a>';
			        			  return s+c;
			        		  }else{
			        			  var e = '<a href="#" onclick="editdscrow(this)">编辑</a> ';
			        			  var d = '<a href="#" onclick="deletedscrow(this)">删除</a>';
			        			  return e+d;
			        		  }
			        	  }
			          }
			          ]],
			          onSortColumn: function(field, order){
			        	  projDSList.getProjDSList(false, field, order);
						},
			          onBeforeEdit:function(index,row){
			        	  row.editing = true;
			        	  updateDSCActions(index);
			          },
			          onAfterEdit:function(index,row){
			        	  row.editing = false;
			        	  updateDSCActions(index);
			      		$("#projectDSConfig").datagrid('addEditor',
			      				                       [{field:'source_value',
							                                    editor:{ 
								                                       type:'combobox',
								                                       options:{
									                                           valueField:'value',
									                                           textField:'name',
									                                           data:cmForDS,
									                                           required:true,
									                                           editable:false
								                                               }
							                                           }
			                                        }]);
			      		
			      		
			             },
			          
			          onCancelEdit:function(index,row){
							row.editing = false;
							updateDSCActions(index);
						}
		});
		
		projDSList.getProjDSList(true);
	};
	$(document).ready(function() {
		projectNo = $("#noHid").val();
		projDSList.getcmForDS();
		projDSList.initGrid();
	});
})();


