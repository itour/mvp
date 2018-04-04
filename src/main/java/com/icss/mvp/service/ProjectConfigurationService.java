package com.icss.mvp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icss.mvp.dao.ICodeMasterDao;
import com.icss.mvp.dao.IProjectListDao;
import com.icss.mvp.dao.IProjectSourceConfigDao;
import com.icss.mvp.entity.CodeMasterInfo;
import com.icss.mvp.entity.Page;
import com.icss.mvp.entity.ProjectDataSourceInfo;
import com.icss.mvp.entity.ProjectDetailInfo;
import com.icss.mvp.util.StringMD5Util;

/*
 * @author liuwenyan
 * 项目试题信息配置service
 */
@Service
public class ProjectConfigurationService {
	@Autowired
	private IProjectListDao projectListDao;
	@Autowired
	private IProjectSourceConfigDao projectDSConfDao;
	@Autowired
	private ICodeMasterDao codeMasterDao;

	private final static Logger LOG = Logger
			.getLogger(ProjectListService.class);

	private final int SUCCESS_CODE = 1;

	private final int FAIL_CODE = 0;

	private final int ALREADY_EXIST = 2;

	private final String DATASOURCE_KEY = "Source";

	public Map<String, Object> queryProjInfo(String projNo) {
		LOG.info("queryProjInfo begin.");
		Map<String, Object> result = new HashMap<String, Object>();
		List<ProjectDetailInfo> projList = projectListDao.queryProjInfo(projNo);
		if (null == projList || projList.isEmpty()) {
			LOG.warn("This Project does not exist.");
			result.put("resultCode", FAIL_CODE);
			result.put("projInfo", null);
			return result;
		}
		LOG.info("queryProjInfo success.");
		result.put("resultCode", SUCCESS_CODE);
		result.put("projInfo", projList.get(0));
		return result;
	}

	public Map<String, Object> queryProjDataSrcInfo(String projNo,Page page) {
		LOG.info("queryProjDataSrcInfo begin.");
		Map<String, Object> result = new HashMap<String, Object>();
		List<ProjectDataSourceInfo> projDSList = projectDSConfDao
				.queryProjDSInfo(projNo,page.getSort(),page.getOrder());
		result.put("rows", projDSList);
		result.put("total", projDSList.size());
		LOG.info("queryProjDataSrcInfo success.");
		return result;
	}
	

	public Map<String, Object> insertDataSourceForProj(
			ProjectDataSourceInfo projDSInfo) {
		LOG.info("insertDataSourceForProj begin.");
		Map<String, Object> result = new HashMap<String, Object>();
		ProjectDataSourceInfo projDS = projectDSConfDao.queryProjDSByKey(
				projDSInfo.getNo(), projDSInfo.getSource_value());
		if (null != projDS) {
			result.put("resultCode", ALREADY_EXIST);
			LOG.info("insertDataSourceForProj  proj and datasource already exist.");
			return result;
		}
		if (null != projDSInfo.getCreateDate()) {
			Date date = convertDate(projDSInfo.getCreateDate());
			projDSInfo.setCreateDate(date);
		}
		String passWord=StringMD5Util.MD5String(projDSInfo.getPassword());
		projDSInfo.setPassword(passWord);
		int insertRslt = projectDSConfDao.insertProjDS(projDSInfo);
		result.put("resultCode", insertRslt);
		LOG.info("insertDataSourceForProj success.");
		return result;
	}

	public Map<String, Object> updateDataSourceForProj(
			ProjectDataSourceInfo projDSInfo) {
		LOG.info("updateDataSourceForProj begin.");
		Map<String, Object> result = new HashMap<String, Object>();
		// step1:check NO+source_value是否存在;step2:未存在，则插入；若已存在，则更新
		ProjectDataSourceInfo projDS = projectDSConfDao.queryProjDSByKey(
				projDSInfo.getNo(), projDSInfo.getSource_value());
		if (null != projDSInfo.getUpdateDate()) {
			Date date = convertDate(projDSInfo.getUpdateDate());
			projDSInfo.setUpdateDate(date);
		}
		int rslt = FAIL_CODE;
		if (null == projDS) {
			result.put("resultCode", rslt);
			LOG.info("updateDataSourceForProj fail: proj and datasource does not exist.");
			return result;
		}
		String passWord=StringMD5Util.MD5String(projDSInfo.getPassword());
		projDSInfo.setPassword(passWord);
		rslt = projectDSConfDao.updateProjDS(projDSInfo);
		result.put("resultCode", rslt);
		LOG.info("updateDataSourceForProj success.");
		return result;
	}

	private Date convertDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dd = format.format(date);
		Date ddd;
		try {
			ddd = format.parse(dd);

		} catch (ParseException e) {
			return null;
		}
		return ddd;
	}

	public Map<String, Object> delDataSourceForProj(
			ProjectDataSourceInfo projDSInfo) {
		LOG.info("delDataSourceForProj begin.");
		Map<String, Object> result = new HashMap<String, Object>();
		int rslt = projectDSConfDao.delProjDS(projDSInfo);
		result.put("resultCode", rslt);
		LOG.info("delDataSourceForProj success.");
		return result;
	}

	public Map<String, Object> queryProjCMForDS() {
		LOG.info("queryProjCMForDS begin.");
		Map<String, Object> result = new HashMap<String, Object>();
		List<CodeMasterInfo> cmforDSList = codeMasterDao
				.getCodeNameListbyKey(DATASOURCE_KEY);
		result.put("rows", cmforDSList);
		result.put("total", cmforDSList.size());
		LOG.info("queryProjCMForDS success.");
		return result;
	}

}
