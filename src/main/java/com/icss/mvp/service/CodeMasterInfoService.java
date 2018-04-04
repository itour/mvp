package com.icss.mvp.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.mvp.dao.ICodeMasterInfoDao;
import com.icss.mvp.entity.CodeMasterInfo;

@Service("codeMasterInfoService")
@Transactional
public class CodeMasterInfoService {

	@Resource
	private ICodeMasterInfoDao codeMasterInfoDao;
	
	public CodeMasterInfo getCodeMaster(@Param("codeM")CodeMasterInfo codeM)
	{
		if((null == codeM.getName()||"".equals(codeM.getName()))&&(null == codeM.getValue() || "".equals(codeM.getValue())))
		{
			return codeM;
		}
		
		CodeMasterInfo codeMasterInfo = new CodeMasterInfo();
		List<CodeMasterInfo> list = codeMasterInfoDao.getList(codeM);
		for (CodeMasterInfo c : list) {
			codeMasterInfo = c;
		}
		return null != codeMasterInfo ? codeMasterInfo : codeM;
	}
	public List<CodeMasterInfo> getList(@Param("codeM")CodeMasterInfo codeM)
	{
		return codeMasterInfoDao.getList(codeM);	
	}
	
}
