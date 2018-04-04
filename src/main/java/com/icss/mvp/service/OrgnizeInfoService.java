package com.icss.mvp.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.mvp.dao.ICodeMasterInfoDao;
import com.icss.mvp.dao.IOrgnizeInfoDao;
import com.icss.mvp.entity.CodeMasterInfo;
import com.icss.mvp.entity.OrganizeInfo;
import com.icss.mvp.entity.OrganizeMgmer;

@Service("orgnaizeInfoService")
@Transactional
public class OrgnizeInfoService {

	@Resource
	private IOrgnizeInfoDao orgnizeInfoDao;
	
	public List<OrganizeInfo> getList(@Param("org")OrganizeInfo org)
	{
		return orgnizeInfoDao.getList(org);	
	}
	
	public int getNodeId(@Param("org")OrganizeInfo org)
	{
		return orgnizeInfoDao.getNodeId(org);	
	}
	
	public List<OrganizeInfo> getParameterId(OrganizeInfo org)
	{
		return orgnizeInfoDao.getParamId(org);	
	}
	
	public List<OrganizeInfo> getPDU(int parentId)
	{
		return orgnizeInfoDao.getPDU(parentId);	
	}
	
	public List<OrganizeInfo> getBU(OrganizeInfo org)
	{
		return orgnizeInfoDao.getBUName(org);
	}
	
	public List<OrganizeInfo> getOrignBU(OrganizeInfo org)
	{
		return orgnizeInfoDao.getOrignBU(org);
	}
}
