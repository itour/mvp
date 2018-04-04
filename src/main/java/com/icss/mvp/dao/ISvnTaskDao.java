package com.icss.mvp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icss.mvp.entity.CodeQualityInfo;
import com.icss.mvp.entity.SvnLogs;

/**
 * 
 * <pre>
 * <b>描述：svn数据操作dao层</b>
 * <b>任务编号：</b>
 * <b>作者：张鹏飞</b> 
 * <b>创建日期： 2017年5月19日 下午5:39:17</b>
 * </pre>
 */
public interface ISvnTaskDao
{
    /**
     * 
     * <pre>
     * <b>描述：保存svn日志</b>
     * <b>任务编号：</b>
     * <b>作者：张鹏飞</b> 
     * <b>创建日期： 2017年5月19日 下午5:39:57</b>
     *  @param logList
     *  @return
     * </pre>
     */
    int saveLogList(List<SvnLogs> logList);
    
    /**
     * 
     * <pre>
     * <b>描述：获取svn代码新增行数月度汇总</b>
     * <b>任务编号：</b>
     * <b>作者：张鹏飞</b> 
     * <b>创建日期： 2017年5月19日 下午5:40:27</b>
     *  @param month
     *  @return
     * </pre>
     */
    List<CodeQualityInfo> getMonthCollect(@Param("month") String month);
    
    /**
     * 
     * <pre>
     * <b>描述：更新svn代码新增行数月度汇总</b>
     * <b>任务编号：</b>
     * <b>作者：张鹏飞</b> 
     * <b>创建日期： 2017年5月19日 下午5:41:11</b>
     *  @param list
     *  @param month
     *  @return
     * </pre>
     */
    int updateSvnMonthCollect(@Param("list")CodeQualityInfo list, @Param("month") String month);

	void insertSvnMonthCollect(@Param("list")CodeQualityInfo info);
	
	
	public List<SvnLogs> serchSvn(@Param("no")String no);
	
	public int addCode(@Param("month")int month, @Param("no")String no, @Param("author")String author);
	
	public List<Integer> searchByAuthor(@Param("author")String author);
     
}
