<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="panel-fit">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>度量参数列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/bootstrap/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/common.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/underscore/underscore.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/comm/comm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/view/parameterList/parameterList.js"></script>
<script type="text/javascript">
	function changeGridHeight() {
	    var ifm = document.getElementById("gridContent");
	    ifm.style.height = (document.documentElement.clientHeight - 213)+'px';
	}
</script>
</head>

<body onload="changeGridHeight();">
	<div class='mainContent'>
		<div class="easyui-panel" title="查询条件" data-options="footer:'#ft'">
			<table width="100%" class="searchTable">
				<tr>
					<td width="10%" align="right">参数大类型：</td>
					<td width="15%" align="left">
						<select type="text" name="bigTypeValue"  id="bigTypeValue"></select>
					</td>
					<td width="10%" align="right">参数小类型：</td>
					<td width="15%" align="left">
						<select type="text" name="smallTypeValue"  id="smallTypeValue"></select>
					</td>
					<td width="10%" align="right">参数名称：</td>
					<td width="15%" align="left">
						<input name="measurementParameter" id="measurementParameter"></input>
					</td>
					<td width="10%" align="right">单位：</td>
					<td width="15%" align="left">
						<input name="unit" id="unit"></input>
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">数据源：</td>
					<td width="15%" align="left">
						<select type="text" name="sourceValue"  id="sourceValue"></select>
					</td>
					<td width="10%" align="right">对应参数：</td>
					<td width="15%" align="left">
						<input name="parameter" id="parameter"></input>
					</td>
					
					<td width="10%" align="right"></td>
					<td width="15%" align="left">
					</td>
					<td width="10%" align="right"></td>
					<td width="15%" align="left">
					</td>
				</tr>
			</table>
		</div>
		<div id="ft" class='footContent'>
			<button type="button" class="btn btn-primary" id="queryPara">查询</button>
		    <button type="button" class="btn btn-default" id="resetPara">重置</button>
		</div>
		<div class="easyui-panel" title="数据列表">
			<div class="controlContent">
		        <button type="button" class="btn btn-default" onclick="insert()">添加</button>
		    </div>
		</div>
		<div id="gridContent">
    		<div id="para-grid" ></div>
    	</div>
	</div>
</body>
</html>