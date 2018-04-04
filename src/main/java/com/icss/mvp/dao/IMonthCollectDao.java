package com.icss.mvp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icss.mvp.entity.ProjectInfo;

public interface IMonthCollectDao
{
    /**
     * 
     * <pre>
     * <b>描述：初始化月度数据汇总表</b>
     * <b>任务编号：</b>
     * <b>作者：张鹏飞</b> 
     * <b>创建日期： 2017年5月19日 下午5:43:40</b>
     *  @param month
     *  @param listProject
     * </pre>
     */
    void initMonthInfo(@Param("month") String month, @Param("listProject") List<ProjectInfo> listProject);
    
}
