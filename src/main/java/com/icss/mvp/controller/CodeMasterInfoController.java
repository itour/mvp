package com.icss.mvp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.icss.mvp.entity.CodeMasterInfo;
import com.icss.mvp.service.CodeMasterInfoService;

@RestController
@RequestMapping("/codeMaster")
public class CodeMasterInfoController {
    
	@Resource
	private CodeMasterInfoService codeMasterInfoService;
	
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> getCodeMaster(CodeMasterInfo c){
		List<CodeMasterInfo> list = codeMasterInfoService.getList(c);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", list);
		result.put("total", list.size());
		return result;
	}
}
