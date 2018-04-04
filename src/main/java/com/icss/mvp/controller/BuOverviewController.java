package com.icss.mvp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icss.mvp.entity.MonthSummary;
import com.icss.mvp.entity.OrganizeMgmer;
import com.icss.mvp.entity.Page;
import com.icss.mvp.entity.ProjProductivity;
import com.icss.mvp.entity.ProjectInfo;
import com.icss.mvp.entity.ProjectSummary;
import com.icss.mvp.service.BuOverviewService;
import com.icss.mvp.service.ProjectListService;

@Controller
@RequestMapping("/bu")
public class BuOverviewController
{
	@Resource
	private BuOverviewService buOverviewService;
	@Resource
	private ProjectListService projectListService;
	
	@RequestMapping("/opts")
	@ResponseBody
	public List<OrganizeMgmer> getBUs()
	{
		return buOverviewService.getBUs();
	}
	
	@RequestMapping("/projSummary")
	@ResponseBody
	public List<ProjectSummary> getProjSummary(String buName)
	{
		return buOverviewService.getProjSummaries(buName);
	}
	
	@RequestMapping("/monthSummary")
	@ResponseBody
	public List<MonthSummary> getMonthSummary(String buName)
	{
		return buOverviewService.getMonthSummary(buName);
	}
	
	@RequestMapping("/projProduct")
	@ResponseBody
	public List<ProjProductivity> getProjProductivity(String buName)
	{
		return buOverviewService.getProjProductity(buName);
	}
	
	@RequestMapping("/projDetailTab")
	@ResponseBody
	public Map<String, Object> getProjDetail(String buName, Page page, ProjProductivity proj)
	{
		return buOverviewService.getProjDetail(buName, page, proj);
	}
	
	@RequestMapping("/download")
	public void download(String buName, ProjProductivity proj, HttpServletResponse response) throws Exception
	{
		byte[] fileContents = buOverviewService.exportExcel(buName, proj);
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "项目详细信息" + sf.format(new Date()).toString() + ".xls";
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));

		response.getOutputStream().write(fileContents);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	@RequestMapping("/projIndicators")
	@ResponseBody
	public Map<String, Object> getProjIndicators(ProjProductivity proj)
	{
		return buOverviewService.getProjIndicators(proj);
	}
	
	@RequestMapping("/projView")
	public String getProjView(HttpServletRequest request, String buName, String projNo)
	{
		request.setAttribute("proj", projectListService.getProjInfo(buName, projNo));
		return "HTML/xiangmu";
	}
	
	@RequestMapping("/projOverview")
	@ResponseBody
	public Map<String, Object> getProjOverviewGrid(String buName, String no)
	{
		return buOverviewService.getProjOverveiw(no);
	}
	
	@RequestMapping("/getSpecProjChartData")
	@ResponseBody
	public List<ProjProductivity> getSpecProjChartData(String no)
	{
		return buOverviewService.getSpecProjChartData(no);
	}
	
	@RequestMapping("/ProjCategory")
	@ResponseBody
	public Map<String, Map<String, Map<String, List<ProjectInfo>>>> getProjCategory()
	{
		return buOverviewService.getProjCategory();
	}
	
	@RequestMapping("/meausreResult")
	@ResponseBody
	public Map<String, Object> getMeasureResult(String projNo, String bigType, String smallType)
	{
		return buOverviewService.getMeasureResult(projNo, bigType, smallType);
		
	}
}
