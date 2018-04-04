package com.icss.mvp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icss.mvp.entity.ParameterInfo;



public interface IParameterInfo {
	List<ParameterInfo> queryParameterInfo();
	ParameterInfo queryParamId(@Param("paramId")int paramId);
	
}
