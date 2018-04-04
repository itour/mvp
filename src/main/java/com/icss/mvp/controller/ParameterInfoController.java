package com.icss.mvp.controller;







import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icss.mvp.entity.Page;
import com.icss.mvp.entity.ParameterInfo;
import com.icss.mvp.entity.ProjectParameter;
import com.icss.mvp.service.ProjectParameterService;


@Controller
@RequestMapping("/paraInfo")
public class ParameterInfoController {
	 @Autowired
	 private ProjectParameterService projectService; 
	
		
	   @RequestMapping("/query")
	   @ResponseBody
	    public Map<String,Object> queryParameterInfo(String projNo,Page page)
	    {
		   return projectService.queryProjectParameter(projNo,page);
	    }
	  
	   //查询数据源
	   @RequestMapping("/queryprojSV")
	   @ResponseBody
	    public Map<String,Object> queryDataSource()
	    {
		   return projectService.queryDataSource();
	    }
	     
	   @RequestMapping("/updateParam")
	   @ResponseBody
	   public String updateProjectParameter(ProjectParameter projParam){
		   String message=projectService.updateProjectParameter(projParam);
		   return message;  
	   }
	  /* @RequestMapping("/addParam")
	   @ResponseBody
	    public String addProjectParameter(ParameterInfo paramInfo,String projNo,int isDisplay)
	    {
		   String message=projectService.addProjectParameter(projNo,paramInfo,isDisplay);
		   return message;
	    }	  
	   
	   @RequestMapping("/delParam")
	   @ResponseBody
	   public String delProjectParameter(String projNo,ParameterInfo paramInfo){
		   String message=projectService.delProjectParameter(projNo,paramInfo);
		   return message;  
	   }*/  
}
