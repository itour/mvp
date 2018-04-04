package com.icss.mvp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;



import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.mvp.dao.IDtsTaskDao;
import com.icss.mvp.dao.IProjectListDao;
import com.icss.mvp.dao.IProjectSourceConfigDao;
import com.icss.mvp.entity.ProjectDataSourceInfo;
import com.icss.mvp.service.dts.DtsIndicatorComputer;
import com.icss.mvp.util.HttpClientUtil;

@Service
@EnableScheduling
@PropertySource("classpath:task.properties")
public class DtsTakService
{
	@Autowired
	private IProjectSourceConfigDao sourceDao;

	@Autowired
	private IDtsTaskDao dtsTaskDao;

	private final static Logger LOG = Logger.getLogger(DtsTakService.class);

	@Value("${dts_file_path}")
	public String fPath;
	
	@Resource
	private DtsIndicatorComputer computer;

	
	@Resource
	private IProjectListDao projectListDao;
	
	/**
	 * 每天凌晨获取DTS数据并入库
	 */
	@Transactional
	@Scheduled(cron = "${dtsTask_scheduled}")
	public void getDTSDatas()
	{
		List<ProjectDataSourceInfo> dtsLists = sourceDao.queryDSBySource("5");
		for (ProjectDataSourceInfo dataSource : dtsLists)
		{
			String fileName = HttpClientUtil.getInstance().createDTSFile(
					dataSource.getUser(), dataSource.getPassword(),
					dataSource.getNo());
			if (StringUtils.isNotEmpty(fileName))
			{
				readDts2DB(fileName);
			}
		}
		computer.collectIndicators();
	}

	/**
	 * 读取DTS文件并入库
	 * 
	 * @param fileName
	 */
	private void readDts2DB(String fileName)
	{
		try
		{
			InputStream in = new FileInputStream(new File(fileName));
			ExcelReader excelReader = new ExcelReader(in);
			excelReader.readDts(fileName, projectListDao);
		} catch (FileNotFoundException e)
		{
			LOG.error("file not found!" + fileName, e);
		} catch (IOException e)
		{
			LOG.error("parse excel failed!" + fileName, e);
		}

	}

	/**
	 * 月度汇总DTS数据
	 * 
	 * @param month
	 */
	public void doMonthCollect(String month)
	{
		// TODO Auto-generated method stub
	}

}
