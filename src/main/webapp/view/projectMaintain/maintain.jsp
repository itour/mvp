<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="panel-fit">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>项目度量信息列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/bootstrap/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/easyui/themes/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/comm/comm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/view/projectMaintain/projDetailInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/view/projectMaintain/projDataSourceConf.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/view/projectMaintain/parameterInfo.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/common.css">
<script type="text/javascript">
	function changeGridHeight() {
	    var ifm = document.getElementById("gridContent");
	    ifm.style.height = (document.documentElement.clientHeight - 364)+'px';
	}
</script>
</head>
<body onload="changeGridHeight();">
	<div class='mainContent'>
		<input id="noHid"  name="no" value="${no}" style="display:none">
		<div id="projectInfo" class="easyui-panel" title="项目基本信息">
			<table width="100%" class="searchTable">
				<tr>
					<td width="10%" align="right">项目名称：</td>
					<td width="10%" align="left">
						<input id="name" labelPosition="left"  readonly="readonly">
					</td>
					<td width="10%" align="right">项目编码：</td>
					<td width="10%" align="left">
						<input id="no" labelPosition="left" readonly="readonly">						
					</td>
					<td width="10%" align="right">项目类型：</td>
					<td width="10%" align="left">
						<input id="projectType"  labelPosition="left" readonly="readonly">
					</td>
					<td width="10%" align="right">交付类型：</td>
					<td width="10%" align="left">
						<input id="type" labelPosition="left"  readonly="readonly">
					</td>
					<td width="10%" align="right">事业部：</td>
					<td width="10%" align="left">
						<input id="bu" labelPosition="left" readonly="readonly">
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">产品部：</td>
					<td width="10%" align="left">
						<input id="pdu" labelPosition="left" readonly="readonly">
					</td>
					<td width="10%" align="right">交付部：</td>
					<td width="10%" align="left">
						<input id="du" labelPosition="left" readonly="readonly">
					</td>
					<td width="10%" align="right">地域：</td>
					<td width="10%" align="left">
						<input id="area" labelPosition="left" readonly="readonly">
					</td>
				    <td width="10%" align="right">内部PO号：</td>
				    <td width="10%" align="left">
				        <input id="PO" labelPosition="left" readonly="readonly">
				   <td width="10%" align="right">版本号：</td>
					<td width="10%" align="left">
						<input id="version" labelPosition="left" readonly="readonly">
					</td>   
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">预计费用：</td>
					<td width="10%" align="left">
						<input id="predictMoney" labelPosition="left" readonly="readonly" >
					</td>
					
					<td width="10%" align="right">需求人数：</td>
					<td width="10%" align="left">
						<input id="workerCount" labelPosition="left" readonly="readonly" >
					</td>
					<td width="10%" align="right">项目经理：</td>
					<td width="10%" align="left">
						<input id="manager" labelPosition="left" readonly="readonly">
					</td>

					<td width="10%" align="right">开始日期：</td>
					<td width="10%" align="left">
						<input id="startDate" labelPosition="left" readonly="readonly" >
					</td>
					<td width="10%" align="right">结束日期：</td>
					<td width="10%" align="left">
						<input id="endDate" labelPosition="left" readonly="readonly" >
					</td>	
				</tr>
				<tr>

	                <td width="10%" align="right">预计工作(人天)：</td>
                    <td width="10%" align="left">
                       <input id="countOfday" labelPosition="left" readonly="readonly">
					</td>
                    <td width="10%" align="right">预计工作(人月)：</td>
					<td width="10%" align="left">
						<input id="countOfmonth" labelPosition="left" readonly="readonly">
					</td>	
                </tr>
			</table>
				<div style="margin-top:10px;margin-bottom:10px">
		          <button type="button" style="margin-left:500px" class="btn btn-default" id="updateInfo">项目信息更新</button>
		      </div>
		</div>
		<div class="easyui-panel" title="数据源配置信息" style="width:100%;height:150px;">
			<div class="controlContent">
				<button type="button" class="btn btn-default" id="add" onclick="insertfordsc()">添加</button>
			</div>
			<table id="projectDSConfig" ></table>
		</div>
		<div class="easyui-panel" title="度量参数信息">
			<div id="gridContent">
				<table id="po-grid"></table>
			</div>
		</div>
	</div>
</body>
</html>