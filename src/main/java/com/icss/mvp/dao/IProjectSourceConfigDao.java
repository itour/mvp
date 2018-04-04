package com.icss.mvp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icss.mvp.entity.ProjectDataSourceInfo;

public interface IProjectSourceConfigDao {
	public List<ProjectDataSourceInfo> queryProjDSInfo(@Param("projNo")String projNo,@Param("sort") String sort, @Param("order")String order);
	public int insertProjDS(ProjectDataSourceInfo projDSInfo);
	public int updateProjDS(ProjectDataSourceInfo projDSInfo);
	public int delProjDS(ProjectDataSourceInfo projDSInfo);
	public ProjectDataSourceInfo queryProjDSByKey(@Param("projNo")String projNo,@Param("source_value")String sourceValue);
	public List<ProjectDataSourceInfo> queryAllDS();
	public List<ProjectDataSourceInfo> queryDSBySource(String source);
}
