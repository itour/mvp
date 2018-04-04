package com.icss.mvp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icss.mvp.entity.CodeMasterInfo;

public interface ICodeMasterInfoDao {

	public List<CodeMasterInfo> getList(@Param("codeM")CodeMasterInfo codeM);
	
}

