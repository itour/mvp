package com.icss.mvp.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icss.mvp.entity.GerenCode;
import com.icss.mvp.service.DevelopPageService;
import com.icss.mvp.service.SvnTaskService;

@Controller
@RequestMapping("/svnTask")
public class SvnTaskController
{
	@Resource
	private SvnTaskService svnTaskService;
	
	@Resource
	private DevelopPageService developPageService;
	
	
	@RequestMapping("/svn")
	@ResponseBody
	public int saveSvn()
	{
		int a = svnTaskService.getSvnlog();
		System.out.println(a);
		return a;
	}
	
	/**
	 * 员工个人代码量统计
	 * @param projNo
	 * @return
	 */
	@RequestMapping("/searchGeRen")
	@ResponseBody
	public List<GerenCode> searchGeRen(String projNo)
	{
		return developPageService.developSearch(projNo);
		
	}
}
