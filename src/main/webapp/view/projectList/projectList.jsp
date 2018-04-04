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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/common.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/underscore/underscore.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/comm/comm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/view/projectList/projectList.js"></script>
<script type="text/javascript">
	function changeGridHeight() {
	    var ifm = document.getElementById("gridContent");
	    ifm.style.height = (document.documentElement.clientHeight - 213)+'px';
	}
</script>
<style>
.trans {
	display: none;
	width: 54px;
	height: 37px;
	opacity: 0;
	position: relative;
	margin-top: -34px;
	cursor: pointer;
}
</style>
</head>
<body onload="changeGridHeight();">
	<div class='mainContent'>
		<div class="easyui-panel" title="查询条件" data-options="footer:'#ft'" id="tabs">
			<table width="100%" class="searchTable">
					<tr>
						<td width="10%" align="right">事业部：</td>
						<td width="15%" align="left">
							<select type="text" name="businessUnit"  id="businessUnit"></select>
						</td>
						<td width="10%" align="right">产品：</td>
						<td width="15%" align="left">
							<select type="text" name="deliveryUnit"  id="deliveryUnit"></select>
						</td>
						<td width="10%" align="right">计费地域：</td>
						<td width="15%" align="left">
							<select type="text" name="countArea"  id="countArea"></select>
						</td>
						<td width="10%" align="right">项目类型：</td>
						<td width="15%" align="left">
							<select type="text" name="projectType" id="projectType"></select>
						</td>
					</tr>
					<tr>
						<td width="10%" align="right">项目名称：</td>
						<td width="15%" align="left">
							<input name="projectName" id="projectName"></input>
						</td>
						<td width="10%" align="right">项目编号：</td>
						<td width="15%" align="left">
							<input name="projectNo" id="projectNo"></input>
						</td>
						<td width="10%" align="right">PM：</td>
						<td width="15%" align="left">
							<select type="text" name="projectManager" id="projectManager"></select>
						</td>
						<td width="10%" align="right"></td>
						<td width="15%" align="left">
						</td>
					</tr>
				</table>
		    
		    <div id="ft" class='footContent'>
				<button type="button" class="btn btn-primary" id="queryProj">查询</button>
			    <button type="button" class="btn btn-default" id="resetProj">重置</button>
			</div>
		</div>
		<div class="easyui-panel" title="数据列表">
		    <div class="controlContent">
		        <button type="button" class="btn btn-default" id="addInfo">添加</button>
		        <button type="button" class="btn btn-default" id="downloadProj">下载项目列表</button>
		        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#uploadModal">手动上传项目数据</button>
		    </div>
		</div>
		<div id="gridContent">
    		<div id="po-grid" ></div>
    	</div>
		<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
						</button>
						<h4 class="modal-title" id="myModalLabel">
							手动上传项目数据
						</h4>
					</div>
					<div class="modal-body">
						<div style="height: 80px;">
							<div style="width:65%">
							
				                <div class="input-group" style="display: flex;align-items: center;">
				                   	 <div style="display:table-cell;width: 27%;">文件路径：</div><input type="text" id="filePath" class="form-control">
				                    <span class="input-group-btn">
				                    	<form method="post" id="importform"  enctype="multipart/form-data">
					                        <button class="btn btn-default" type="button" id="importBtn" style="margin-top: 4px;border-radius:0px">浏览</button>
					                        <input type="file" class="trans" id="importIpt" name="file"></input>
					                    </form>
				                    </span>
				                </div><!-- /input-group -->
				            </div><!-- /.col-lg-6 -->
				            <a href="<%=request.getContextPath() %>/project/projectTemplate">下载手动数据模板</a>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="submitImport">
							确定
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							取消
						</button>

					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</div>
</body>
</html>