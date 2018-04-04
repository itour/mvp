package com.icss.mvp.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.icss.mvp.dao.IMonthCollectDao;
import com.icss.mvp.dao.IProjectManagerDao;
import com.icss.mvp.util.DateUtils;

@Service
@EnableScheduling
@PropertySource("classpath:task.properties")
public class MonthCollectTaskService
{
    private static Logger logger = Logger.getLogger(MonthCollectTaskService.class);
    
    @Autowired
    private SvnTaskService svnTaskService;
    
    @Autowired
    private DtsTakService dtsTaskService;
    
    @Autowired
    private IProjectManagerDao projectDao;
    
    @Autowired
    private IMonthCollectDao dao;
    
    /**
     * 
     * <pre>
     * <b>描述：月度数据汇总任务</b>
     * <b>任务编号：</b>
     * <b>作者：张鹏飞</b> 
     * <b>创建日期： 2017年5月19日 下午5:37:48</b>
     * </pre>
     */
    @Scheduled(cron = "${month_Task_scheduled}")
    public void collectTask()
    {
        logger.info("Monthly collection task start!");
        String month = DateUtils.getSystemFewMonth(-1);
        // 统计svn数据并入库
        svnTaskService.doMonthCollect(month);
        // TODO 统计dts数据并入库
        dtsTaskService.doMonthCollect(month);
        
        logger.info("Monthly collection task end!");
    }
    
}
