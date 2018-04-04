package com.icss.mvp.dao;

import java.util.List;

import com.icss.mvp.entity.ProjectInfo;

public interface IProjectManagerDao
{
    /**
     * 
     * <pre>
     * <b>描述：获取所有项目</b>
     * <b>任务编号：</b>
     * <b>作者：张鹏飞</b> 
     * <b>创建日期： 2017年5月19日 下午5:43:18</b>
     *  @return
     * </pre>
     */
    List<ProjectInfo> getAllProjectInfo();
    
}
