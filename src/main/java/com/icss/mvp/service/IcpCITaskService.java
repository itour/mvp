package com.icss.mvp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.mvp.dao.IIcpCITaskDao;
import com.icss.mvp.dao.IProjectSourceConfigDao;
import com.icss.mvp.entity.CiAutospaceInfo;
import com.icss.mvp.entity.CiCCTInfo;
import com.icss.mvp.entity.CiCheckStyleInfo;
import com.icss.mvp.entity.CiCodeDEXWebInfo;
import com.icss.mvp.entity.CiCodeDexInfo;
import com.icss.mvp.entity.CiFindBugsInfo;
import com.icss.mvp.entity.CiLltCoverageInfo;
import com.icss.mvp.entity.CiProjInfo;
import com.icss.mvp.entity.CiSharpCounterInfo;
import com.icss.mvp.entity.CiSimianInfo;
import com.icss.mvp.entity.CiSourceMonitorInfo;
import com.icss.mvp.entity.CiUADPGardingInfo;
import com.icss.mvp.entity.ProjectDataSourceInfo;
import com.icss.mvp.util.HttpClientUtil;

@Service
@EnableScheduling
@PropertySource("classpath:task.properties")
public class IcpCITaskService
{
	@Autowired
	private IProjectSourceConfigDao sourceDao;
	@Autowired
	private IIcpCITaskDao icpCITaskDao;

	private final static Logger LOG = Logger.getLogger(IcpCITaskService.class);

	@Value("${icpci_file_path}")
	public String fPath;
	
	/**
	 * 每天凌晨获取CI数据并入库
	 */
	@Transactional
	@Scheduled(cron = "${icpcitask_scheduled}")
	public void getDTSDatas()
	{
		List<ProjectDataSourceInfo> icpCILists = sourceDao.queryDSBySource("6");
		for (ProjectDataSourceInfo dataSource : icpCILists)
		{
			String fileName = HttpClientUtil.getInstance().createDTSFile(
					dataSource.getUser(), dataSource.getPassword(),
					dataSource.getNo());
			if (StringUtils.isNotEmpty(fileName))
			{
			}
		}
	}
	/**
	 * 给子表相应的对象添加id
	 * @param list,CiProjInfos
	 */
	private List<CiProjInfo> insertCiProjInfo(List<CiProjInfo> CiProjInfos) {
		List<CiProjInfo> CiPros=new ArrayList<CiProjInfo>();
		for (CiProjInfo ciProjInfo : CiProjInfos) {
			icpCITaskDao.insertCI(ciProjInfo);
			int id = ciProjInfo.getId();
			if(id == 0){
				id=icpCITaskDao.getMaxId().getId();
			}
			CiPros.add(ciProjInfo);
		}
		return CiPros;
	}
	/**
	 * 封装UADPGranging
	 * @param list
	 */
	private void insertUADPGarding(List<CiUADPGardingInfo> uadplist,List<CiProjInfo> CiProjInfos)
	{
		icpCITaskDao.insertUADPGarding(uadplist);
	}
	/**
	 * 封装cct
	 * @param list
	 */
	private void insertCCT(List<CiCCTInfo> cctlist,List<CiProjInfo> CiProjInfos)
	{
		icpCITaskDao.insertCCT(cctlist);
	}
	/**
	 * 封装check
	 * @param list
	 */
	private void insertCheckStyle(List<CiCheckStyleInfo> listcheckstyle,List<CiProjInfo> CiProjInfos)
	{
		icpCITaskDao.insertCheckStyle(listcheckstyle);
	}
	/**
	 * 封装findbugs
	 * @param list
	 */
	private void insertFindBugs(List<CiFindBugsInfo> listfindbugs,List<CiProjInfo> CiProjInfos)
	{
		icpCITaskDao.insertFindBugs(listfindbugs);
	}
	/**
	 * 封装simian
	 * @param list
	 */
	private void insertSimian(List<CiSimianInfo> listsimian,List<CiProjInfo> CiProjInfos)
	{
		icpCITaskDao.insertSimian(listsimian);
	}
	/**
	 * 封装source
	 * @param list
	 */
	private void insertSourceMonitor(List<CiSourceMonitorInfo> listsourcemonitor,List<CiProjInfo> CiProjInfos)
	{
		icpCITaskDao.insertSourceMonitor(listsourcemonitor);
	}
	/**
	 * 封装sharp
	 * @param list
	 */
	private void insertSharpCounter(List<CiSharpCounterInfo> listsharp,List<CiProjInfo> CiProjInfos)
	{
		icpCITaskDao.insertSharpCounter(listsharp);
	}
	/**
	 * 封装CodeDex
	 * @param list
	 */
	private void insertCodeDex(List<CiCodeDexInfo> listcodeDex,List<CiProjInfo> CiProjInfos)
	{
		icpCITaskDao.insertCodeDex(listcodeDex);
	}
	/**
	 * 封装CodeWeb
	 * @param list
	 */
	private void insertCodeDEXWeb(List<CiCodeDEXWebInfo> listcodeWeb,List<CiProjInfo> CiProjInfos)
	{
		icpCITaskDao.insertCodeDEXWeb(listcodeWeb);
	}
	/**
	 * 封装Autospace
	 * @param list
	 */
	private void insertAutospace(List<CiAutospaceInfo> listauto,List<CiProjInfo> CiProjInfos)
	{
		icpCITaskDao.insertAutospace(listauto);
	}
	/**
	 * 封装Emma
	 * @param list
	 */
	private void inserteLltCoverage(List<CiLltCoverageInfo> listllt,List<CiProjInfo> CiProjInfos)
	{
		icpCITaskDao.inserteLltCoverage(listllt);
	}
//	/**
//	 * 封装Emma
//	 * @param list
//	 */
//	private void inserteEmma(List<CiEmmaInfo> listemma,List<CiProjInfo> CiProjInfos)
//	{
//		icpCITaskDao.inserteEmma(listemma);
//	}

}
