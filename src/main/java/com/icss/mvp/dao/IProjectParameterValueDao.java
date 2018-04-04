package com.icss.mvp.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icss.mvp.entity.ParameterValueNew;
import com.icss.mvp.entity.ProjectParameterValue;

public interface IProjectParameterValueDao
{

	public List<ProjectParameterValue> getList(
			@Param("projectParameterValue") ProjectParameterValue projectParameterValue,
			@Param("sort") String sort, @Param("order") String order);

	public int deleteParameterValue(
			@Param("paraValue") ParameterValueNew paraValue);

	public int insertParameterValue(
			@Param("paraValue") ParameterValueNew paraValue);

//	public List<ProjectParameterValue> getProjDml(@Param("no") String no,
//			@Param("month") String month);
	
	public List<ParameterValueNew> getParameterValue(@Param("paraValue") ParameterValueNew paraValue);

	public void insertParams(@Param("list") List<ParameterValueNew> paramList);
	
//	public List<ProjectParameterValue> getExistIndicatorNum(@Param("c_version")String c_version,
//			@Param("month")String month, @Param("parameterId")String tr5di);
//
//	public void insertIndicatorVal(@Param("c_version")String c_version, @Param("paramValue")String value, @Param("month")String month,
//			@Param("paramId")String tr5di);
//
//	public void updateIndicatorVal(@Param("c_version")String c_version, @Param("paramValue")String value,
//			@Param("month")String month, @Param("paramId")String tr5di);
//
//
//	public List<ProjectParameterValue> getProjDtsDensityNum(@Param("version")String version,@Param("month")String month,@Param("id")String id);
//	public  void insertDtsDensityNum(@Param("version")String version,@Param("month")String month,@Param("value") Double value,@Param("id") String id);
//	public  void updateDtsDensityNum(@Param("version")String version,@Param("month")String month,@Param("value") Double value,@Param("id") String id);
	
}
