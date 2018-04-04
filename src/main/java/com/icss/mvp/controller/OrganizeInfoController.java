package com.icss.mvp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.icss.mvp.entity.CodeMasterInfo;
import com.icss.mvp.entity.OrganizeInfo;
import com.icss.mvp.entity.OrganizeMgmer;
import com.icss.mvp.service.CodeMasterInfoService;
import com.icss.mvp.service.OrgnizeInfoService;

@RestController
@RequestMapping("/organiseMg")
public class OrganizeInfoController {
    
	@Resource
	private OrgnizeInfoService orgnizeInfoService;
	
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> getInfobyLevel(OrganizeInfo c){
		List<OrganizeInfo> list = orgnizeInfoService.getList(c);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", list);
		result.put("total", list.size());
		return result;
	}
	
	@RequestMapping("/listPDU")
	@ResponseBody
	public Map<String, Object> getInfobyName(OrganizeInfo c){
		int nodeId= orgnizeInfoService.getNodeId(c);
		List<OrganizeInfo> list=orgnizeInfoService.getPDU(nodeId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", list);
		result.put("total", list.size());
		return result;
	}
	
	
	@RequestMapping("/getPDU")
	@ResponseBody
	public Map<String, Object> getPDUName(OrganizeInfo c){
		List<OrganizeInfo> orgInfo= orgnizeInfoService.getParameterId(c);
		List<OrganizeInfo> list=null;
		for(OrganizeInfo org:orgInfo){
			int parentId=org.getParentId();
			if(parentId!=0){
			list=orgnizeInfoService.getPDU(org.getParentId());
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", list);
		result.put("total", list.size());
		return result;
	}
	
	@RequestMapping("/getBU")
	@ResponseBody
	public Map<String, Object> getBUName(OrganizeInfo c){
		List<OrganizeInfo> orgInfo= orgnizeInfoService.getParameterId(c);
		List<OrganizeInfo> list=null;
		for(OrganizeInfo org:orgInfo){
			int parentId=org.getParentId();
			if(parentId!=0){
			list=orgnizeInfoService.getBU(org);
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", list.get(0));
		result.put("total", list.size());
		return result;
	}
	
	@RequestMapping("/getOrignBU")
	@ResponseBody
	public Map<String, Object> getOrigBUName(OrganizeInfo c){
		List<OrganizeInfo> orgInfo= orgnizeInfoService.getOrignBU(c);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", orgInfo.get(0));
		result.put("total", orgInfo.size());
		return result;
	}
	
}
