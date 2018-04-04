package com.icss.mvp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.icss.mvp.entity.Page;
import com.icss.mvp.entity.ProjectDataSourceInfo;
import com.icss.mvp.service.ProjectConfigurationService;

/**
 * 
 * @author liuwenyan
 * @see 项目度量信息配置
 * 
 */
@RestController
@RequestMapping("/project")
public class ProjectConfigurationController {
	@Autowired
	private ProjectConfigurationService projConfService;

	@RequestMapping("/queryprojinfo")
	@ResponseBody
	public Map<String, Object> queryProjInfo(String projNo) {

		return projConfService.queryProjInfo(projNo);

	}
	@RequestMapping("/queryprojcmfords")
	@ResponseBody
	public Map<String, Object> queryProjCMForDS() {

		return projConfService.queryProjCMForDS();
	}
	@RequestMapping("/queryprojdsinfo")
	@ResponseBody
	public Map<String, Object> queryProjDataSrcInfo(String projNo,Page page) {

		return projConfService.queryProjDataSrcInfo(projNo,page);
	}
	

	@RequestMapping("/insertdatasource")
	public Map<String, Object> insertDataSourceForProj(
			ProjectDataSourceInfo projDSInfo) {
		
		return projConfService.insertDataSourceForProj(projDSInfo);
		
	}

	@RequestMapping("/udpatedatasource")
	@ResponseBody
	public Map<String, Object> updateDataSourceForProj(ProjectDataSourceInfo projDSInfo) {
		
		return projConfService.updateDataSourceForProj(projDSInfo);
		
	}

	@RequestMapping("/deldatasource")
	@ResponseBody
	public Map<String, Object> delDataSourceForProj(ProjectDataSourceInfo projDSInfo) {
		return projConfService.delDataSourceForProj(projDSInfo);
	}

}
