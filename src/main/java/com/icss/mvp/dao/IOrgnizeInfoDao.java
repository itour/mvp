package com.icss.mvp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icss.mvp.entity.CodeMasterInfo;
import com.icss.mvp.entity.OrganizeInfo;
import com.icss.mvp.entity.OrganizeMgmer;

public interface IOrgnizeInfoDao {

	public List<OrganizeInfo> getList(@Param("org")OrganizeInfo org);
	public int getNodeId(@Param("org")OrganizeInfo org);
	public List<OrganizeInfo> getPDU(int parentId);
	public List<OrganizeInfo> getBU(@Param("nodeName")String nodeName);
	
	public List<OrganizeInfo> getParamId(@Param("org")OrganizeInfo org);
	public List<OrganizeInfo> getBUName(@Param("org")OrganizeInfo org);
	public List<OrganizeInfo> getOrignBU(@Param("org")OrganizeInfo org);
}

