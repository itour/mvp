package com.icss.mvp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.icss.mvp.dao.IProjectListDao;
import com.icss.mvp.entity.Page;
import com.icss.mvp.entity.ProjectDetailInfo;
import com.icss.mvp.entity.ProjectInfo;
import com.icss.mvp.entity.ProjectManager;
import com.icss.mvp.service.ProjectListService;

/**
 * 
 * @author zhuwei
 * @see 项目度量信息列表
 * 
 */
@RestController
@RequestMapping("/project")
public class ProjectListController
{
	@Resource
	private ProjectListService projectListService;
	@Resource
	private IProjectListDao projectListDao;

	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> queryList(ProjectInfo proj, Page page)
	{
		return projectListService.getList(proj, page);
	}

	@RequestMapping("/download")
	public void download(HttpServletResponse response, ProjectInfo proj) throws Exception
	{
		byte[] fileContents = projectListService.exportExcel(proj);
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "项目信息" + sf.format(new Date()).toString() + ".xlsx";
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));

		response.getOutputStream().write(fileContents);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	@RequestMapping("maintain")
	public String goMaintainPage(HttpServletRequest request, String no)
	{
		request.setAttribute("no", no);
		return "/projectMaintain/maintain";
	}

	@RequestMapping("parameterEditor")
	public String goParameterEditorPage(HttpServletRequest request, String projNo)
	{
		request.setAttribute("projNo", projNo);
		return "/projectMaintain/projectParameterEditor";
	}

	@RequestMapping("proj")
	public String goProjPage(HttpServletRequest request)
	{
		return "/projectList/projectList";
	}

	@RequestMapping("init")
	@ResponseBody
	public Map<String, Object> init()
	{
		return projectListService.getProjectSelectInfo();
	}

	@RequestMapping("addInfo")
	public String addProjInfo(HttpServletRequest request, String parma)
	{
		request.setAttribute("parma", parma);
		return "/projectInfo/projectInfo";
	}

	@RequestMapping("updateInfo")
	public String updateProjInfo(HttpServletRequest request, String no)
	{
		request.setAttribute("no", no);
		return "/projectInfo/projectInfo";
	}

	@RequestMapping("/insertInfo")
	@ResponseBody
	public Map<String,String> insertInfo(HttpServletRequest request) throws ParseException
	{
		ProjectDetailInfo proj=new ProjectDetailInfo();
		String param=request.getParameter("param");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 String date=df.format(new Date());
		 Date imptDate=df.parse(date);
		 proj.setImportDate(imptDate);
		 proj.setImportUser("导入者");
		 Date start=null;
		 Date end=null;
		 if(startTime!=""){
			 start=df.parse(startTime);
		 }
		 if(endTime!=""){
		  end=df.parse(endTime);
		 }
		 proj.setStartDate(start);
		 proj.setEndDate(end); 
		 proj.setNo(request.getParameter("no"));
		 proj.setName(request.getParameter("name"));
		 proj.setProjectType(request.getParameter("projectType"));
		 proj.setType(request.getParameter("type"));
		 proj.setBu(request.getParameter("bu"));
		 proj.setPdu(request.getParameter("pdu"));
		 proj.setDu(request.getParameter("du"));
		 proj.setArea(request.getParameter("area"));
		 proj.setPo(request.getParameter("po"));
		 proj.setVersion(request.getParameter("version"));
		 proj.setCountOfDay(projectListService.StringToDouble(request.getParameter("countOfDay")));
		 proj.setCountOfMonth(projectListService.StringToDouble(request.getParameter("countOfMonth")));
		 proj.setWorkerCount(projectListService.StringToDouble(request.getParameter("workerCount")));
		 proj.setPredictMoney(projectListService.StringToDouble(request.getParameter("predictMoney")));
		 ProjectManager projManger=new ProjectManager();
		  String manager=request.getParameter("manager");
		  projManger.setNo(proj.getNo());
		  projManger.setName(manager);
		  projManger.setPosition("PM");
		  projManger.setAccount(manager);
		  projManger.setStartDate(proj.getStartDate());
		  projManger.setEndDate(proj.getEndDate());
		  projManger.setImportDate(imptDate);
		 return projectListService.insertInfo(proj,param,projManger,imptDate);
		
	}
	
	@RequestMapping("/queryPM")
	@ResponseBody
	public Map<String, Object> queryName(String projNo)
	{

		return projectListService.queryName(projNo);
	}

	@RequestMapping("/isExitProj")
	@ResponseBody
	public String isExit(String no){

		ProjectDetailInfo projInfo=projectListDao.isExit(no);
		String message=null;
		if(projInfo!=null){
			return message="项目已存在，请重新输入！";
		}
		return message;
	}

	@RequestMapping("projectTemplate")
	@ResponseBody
	public void downloadParamTemplate(HttpServletResponse response)
			throws IOException
	{
		InputStream is = this.getClass().getResourceAsStream(
				"/project-info-template.xlsx");

		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "项目信息模板" + sf.format(new Date()).toString()
				+ ".xlsx";
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));

		response.getOutputStream().write(IOUtils.toByteArray(is));
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	@RequestMapping("/import")
	@ResponseBody
	public Map<String, Object> importProjList(
			@RequestParam(value = "file") MultipartFile file)
	{
		Map<String, Object> result = projectListService.importProjects(file);
		return result;
   }
			

}