<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="panel-fit">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>项目参数编辑</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/bootstrap/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/common.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/underscore/underscore.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/comm/comm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/view/projectMaintain/parameterEditor.js"></script>
<script type="text/javascript">
	function changeGridHeight() {
	    var ifm = document.getElementById("gridContent");
	    ifm.style.height = (document.documentElement.clientHeight - 210)+'px';
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
		<div class="easyui-panel" title="查询条件" data-options="footer:'#ft'">
		<input id="hidNo"  name="hidNo" value="${projNo}" style="display:none">
			<table width="100%" class="searchTable">
				<tr>
					<td width="10%" align="right">年度：</td>
					<td width="15%" align="left">
						<select type="text" name="selectYear"  id="selectYear"></select>
					</td>
					<td width="10%" align="right">月份：</td>
					<td width="15%" align="left">
						<select type="text" name="selectMonth"  id="selectMonth"></select>
					</td>
					<td width="10%" align="right">参数大类型：</td>
					<td width="15%" align="left">
						<select type="text" name="bigTypeValue"  id="bigTypeValue"></select>
					</td>
					<td width="10%" align="right">参数小类型：</td>
					<td width="15%" align="left">
						<select type="text" name="smallTypeValue"  id="smallTypeValue"></select>
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">参数名称：</td>
					<td width="15%" align="left">
						<input name="measurementParameter" id="measurementParameter"></input>
					</td>
					<td width="10%" align="right">单位：</td>
					<td width="15%" align="left">
						<input name="unit" id="unit"></input>
					</td>
					<td width="10%" align="right">数据源：</td>
					<td width="15%" align="left">
						<select type="text" name="sourceValue"  id="sourceValue"></select>
					</td>
					<td width="10%" align="right">是否显示：</td>
					<td width="15%" align="left">
						<select type="text" name="selectIsDisplay"  id="selectIsDisplay"></select>
					</td>
					
				</tr>
			</table>
		</div>
		<div id="ft" class='footContent'>
			<button type="button" class="btn btn-primary" id="queryPara">查询</button>
			<button type="button" class="btn btn-default" id="resetPara">重置</button>
		</div>
		<div class="easyui-panel" title="数据列表">
		<div style="padding:5px;">
			<button type="button" class="btn btn-default" data-toggle="modal" data-target="#uploadDialog">上传项目指标数据</button>
		</div>
		<div id="gridContent">
    		<div id="para-grid" ></div>
    	</div>
		</div>
		<div class="modal fade" id="uploadDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
						</button>
						<h4 class="modal-title" id="uploadModalLabel">
							上传项目指标数据
						</h4>
					</div>
					<div class="modal-body">
						<div style="height: 80px;">
							<div style="width:65%">
							
				                <div class="input-group" style="display: flex;align-items: center;">
				                   	 <div style="display:table-cell;width: 27%;">文件路径：</div><input type="text" readonly="readonly" id="filePathInfo" class="form-control">
				                    <span class="input-group-btn">
				                    	<form method="post" id="importForm"  enctype="multipart/form-data">
					                        <button class="btn btn-default" type="button" id="importBtn" style="margin-top: 4px;border-radius:0px">浏览</button>
					                        <input type="file" class="trans" id="importInput" name="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" ></input>
					                    </form>
				                    </span>
				                </div><!-- /input-group -->
				            </div><!-- /.col-lg-6 -->
				            <a id="templateDownload" >下载手动数据模板</a>
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