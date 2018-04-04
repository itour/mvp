<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="panel-fit">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>项目基本信息</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/bootstrap/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/easyui/themes/default/easyui.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/underscore/underscore.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/comm/comm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/view/projectInfo/projectInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/view/projectInfo/saveProjectInfo.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/common.css">
<script type="text/javascript">
	 function myformatter(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		}
		function myparser(s){
			if (!s) return new Date();
			var ss = (s.split('-'));
			var y = parseInt(ss[0],10);
			var m = parseInt(ss[1],10);
			var d = parseInt(ss[2],10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		} 
		 function onSelect(date){
			if($("#startDate").val()!=""){
				 $("#TextNote15").html("");
			if($("#endDate").val()!=""){
			   $("#TextNote16").html("");
		}
		} 
		}
	</script>
</head>
<body>	
	<div class='mainContent'>
	   <input id="noHid"  name="no" value="${no}" style="display:none">
	   <input id="parm"  name="par" value="${parma}" style="display:none">
		<div id="projectInfo" class="easyui-panel" title="项目基本信息">
			<table width="100%" class="searchTable" id="tableContent">
				<tr>
					<td width="10%" align="right">项目名称：</td>
					<td width="15%" align="left">
						<input id="projName" labelPosition="left"  onkeyup="changeName()"/><br/>
						<span id="TextNote1" style="color:red"></span>
					</td>
					<td width="10%" align="right">项目编码：</td>
					<td width="15%" align="left">
						<input name="no" id="projNo" labelPosition="left" readOnly onblur="changeNo()"><br/>
						<span id="TextNote2" style="color:red"></span>
					</td>
					<td width="10%" align="right">项目类型：</td>
					<td width="15%" align="left">
						<select type="text" name="projectType" id="projectType"  "></select>
					</td>
					<td width="10%" align="right">交付类型：</td>
					<td width="15%" align="left">
						<select type="text" name="payType" id="payType" ></select>
					</td>
				</tr>
			  <tr>
					<td width="10%" align="right">事业部：</td>
					<td width="15%" align="left">
						<select type="text" name="businessUnit"  id="businessUnit" ></select>
					</td>
					<td width="10%" align="right">产品部：</td>
					<td width="15%" align="left">
						<select type="text" name="productUnit"  id="productUnit" ></select>
					</td>
		
					<td width="10%" align="right">交付部：</td>
					<td width="15%" align="left">
						<select type="text" name="deliveryUnit"  id="deliveryUnit"></select>
					</td>
					<td width="10%" align="right">地域：</td>
					<td width="15%" align="left">
						<select type="text" name="area"  id="area" ></select>
					</td>
              </tr>
              <tr>
					<td width="10%" align="right">预计费用：</td>
					<td width="15%" align="left">
						<input id="predictMoney" labelPosition="left">
					</td>
					 <td width="10%" align="right">预计工作(人天)：</td>
                    <td width="15%" align="left">
                   <input id="countOfday" labelPosition="left">
					</td>
                    <td width="10%" align="right">预计工作(人月)：</td>
					<td width="15%" align="left">
						<input id="countOfmonth" labelPosition="left">
					</td>	 
                    <td width="10%" align="right">需求人数：</td>
					<td width="15%" align="left">
						<input id="workerCount" labelPosition="left">
					</td>	      	
                </tr>
                
                 <tr>
					<td width="10%" align="right">项目经理：</td>
					<td width="15%" align="left">
						<input id="manager" labelPosition="left">
					</td>
				    <td width="10%" align="right">内部PO号：</td>
				    <td width="15%" align="left">
				   <input id="PO" labelPosition="left" >
					</td>
                       <td width="10%" align="right">开始日期：</td>
				    <td width="15%" align="left">
				   <input class="easyui-datebox" id="startDate" labelPosition="top" data-options="formatter:myformatter,parser:myparser,onSelect:onSelect" style="width:80%;">
					</td>
                    <td width="10%" align="right"> 结束日期：</td>
                    <td width="15%" align="left">
                    <input class="easyui-datebox" id="endDate" labelPosition="top" data-options="formatter:myformatter,parser:myparser,onSelect:onSelect" style="width:80%;">
					</td>  	
                </tr>
                <tr>
					<td width="10%" align="right">版本号：</td>
					<td width="15%" align="left">
						<input id="version" labelPosition="left">
					</td>	    	
                </tr>
	</table>
		  <div style="margin-top:10px;margin-bottom:10px">
		   <button type="button" style="margin-left:500px" class="btn btn-default" onclick="save()">保存</button>
		  </div>
		</div>
	</div>
</body>
</html>