package com.icss.mvp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icss.mvp.entity.CiAutospaceInfo;
import com.icss.mvp.entity.CiCCTInfo;
import com.icss.mvp.entity.CiCheckStyleInfo;
import com.icss.mvp.entity.CiCodeDEXWebInfo;
import com.icss.mvp.entity.CiCodeDexInfo;
import com.icss.mvp.entity.CiEmmaInfo;
import com.icss.mvp.entity.CiFindBugsInfo;
import com.icss.mvp.entity.CiLltCoverageInfo;
import com.icss.mvp.entity.CiProjInfo;
import com.icss.mvp.entity.CiSharpCounterInfo;
import com.icss.mvp.entity.CiSimianInfo;
import com.icss.mvp.entity.CiSourceMonitorInfo;
import com.icss.mvp.entity.CiUADPGardingInfo;
public interface IIcpCITaskDao
{
	public void insertUADPGarding(@Param("listuadp") List<CiUADPGardingInfo> uadpList);
	public void insertCCT(@Param("listcct") List<CiCCTInfo> cctList);
	public void insertCheckStyle(@Param("listcheckstyle") List<CiCheckStyleInfo> checkStyleList);
	public void insertFindBugs(@Param("listfindbugs") List<CiFindBugsInfo> findBugsList);
	public void insertSimian(@Param("listsimian") List<CiSimianInfo> simianList);
	public void insertSourceMonitor(@Param("listsourcemonitor") List<CiSourceMonitorInfo> sourceMonitorList);
	public void insertSharpCounter(@Param("listsharpcounter") List<CiSharpCounterInfo> sharpCounterList);
	public void insertCodeDex(@Param("listcodedex") List<CiCodeDexInfo> codeDexList);
	public void insertCodeDEXWeb(@Param("listcodedexweb") List<CiCodeDEXWebInfo> codeDexWebList);
	public void insertAutospace(@Param("listautospace") List<CiAutospaceInfo> autospaceList);
	public void inserteEmma(@Param("listemma") List<CiEmmaInfo> emmaList);
	public void inserteLltCoverage(@Param("listlltcove") List<CiLltCoverageInfo> lltcovelist);
	public void insertCI(CiProjInfo ciInfo);
	public void updateCI(@Param("struct_time")String strcut_time,@Param("no") String no,@Param("ciInfo") CiProjInfo ciInfo);
	public CiProjInfo getMaxId();
	public List<HashMap<String, Object>> queryCodeDexPara();
	public List<HashMap<String, Object>> queryStructPara();
	public List<HashMap<String, Object>> queryUADPGardingPara();
	public List<HashMap<String, Object>> querySourceMonitorPara();
	public List<HashMap<String, Object>> queryLltCoveragePara();
	public List<HashMap<String, Object>> queryBugs();
	public List<HashMap<String, Object>> querySimian();
	public List<HashMap<String, Object>> queryCCT();
}

