<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="panel-fit">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>度量参数信息</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/bootstrap/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/easyui/themes/default/easyui.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/comm/comm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/view/parameterInfo/parameterInfo.js"></script>
</head>

<body>
<div class="easyui-panel" title="度量参数信息" style="width:100%;height:600px">
			<!-- <div class="left-algin" style="height:10%">
				<button type="button" class="btn btn-default" id="edit">编辑</button>
				<button type="button" class="btn btn-default" id="save">保存</button>
				<button type="button" class="btn btn-default" id="cancel">取消</button>
			</div> -->
	       <div class="grid-content" style="height:90%">
		        <div id="po-grid" sltyle="width:90%;height:80%"></div>
	      </div>
</div>
</body>
</html>