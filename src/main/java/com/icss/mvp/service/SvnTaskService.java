package com.icss.mvp.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tmatesoft.svn.core.SVNException;

import com.icss.mvp.dao.IProjectListDao;
import com.icss.mvp.dao.IProjectParameterValueDao;
import com.icss.mvp.dao.IProjectSourceConfigDao;
import com.icss.mvp.dao.ISvnTaskDao;
import com.icss.mvp.entity.CodeQualityInfo;
import com.icss.mvp.entity.ProjectDetailInfo;
import com.icss.mvp.entity.SvnLogs;
import com.icss.mvp.util.SvnkitUtils;

@Service
@EnableScheduling
@PropertySource("classpath:task.properties")
public class SvnTaskService
{
    private static Logger logger = Logger.getLogger(SvnTaskService.class);
    
    @Autowired
    ISvnTaskDao dao;
    
    @Autowired
    private IProjectParameterValueDao paramDao;
    
    @Autowired
    private IProjectSourceConfigDao sourceDao;
    
    @Resource
	private ProjectParameterValueService projectParameterValueService;
    
    @Resource
	private IProjectListDao projectListDao;
    
    @Resource
     HttpServletRequest request;
	    
    @Resource
	 HttpServletResponse response;
    
    /**
     * 
     * <pre>
     * <b>描述：svn日志抓取任务</b>
     * <b>任务编号：</b>
     * <b>作者：张鹏飞</b> 
     * <b>创建日期： 2017年5月19日 下午5:38:24</b>
     * </pre>
     * @throws UnsupportedEncodingException 
     */
    @Transactional
    @Scheduled(cron = "${svnTask_scheduled}")
    public int getSvnlog() {
//    	List<ProjectDataSourceInfo> lists = sourceDao.queryDSBySource("1");

    	String password="";
    	String username="";
    	if(request.getCookies()!=null){
    	for(Cookie cookie:request.getCookies()){
    		if(cookie.getName().equals("username")){
        		try {
    				username=URLDecoder.decode(cookie.getValue(),"utf-8");
    			} catch (UnsupportedEncodingException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        	}
        	if(cookie.getName().equals("password")){
        		try {
    				password=URLDecoder.decode(cookie.getValue(),"utf-8");
    			} catch (UnsupportedEncodingException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        	}
    	}
    	}

        try
        {
            Date now = new Date();
            Date dayAgo = new Date(now.getTime() - 3600000 * 24);
//            for(ProjectDataSourceInfo item : lists)
//            {
            	SvnkitUtils.setupLibrary(username, password, "https://10.100.14.153:8443/svn/mvp");
                List<SvnLogs> logList = SvnkitUtils.staticticsCodeAddByTime(dayAgo, now,"HWHZP5FF1606F03X11");
                return dao.saveLogList(logList);
//            }
        }
        catch (ParseException e)
        {
            logger.error(e.getMessage());
        }
        catch (SVNException e)
        {
            logger.error(e.getMessage());
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        return 0;
    }
    
    /**
     * 
     * <pre>
     * <b>描述：svn代码新增行数月度汇总</b>
     * <b>任务编号：</b>
     * <b>作者：张鹏飞</b> 
     * <b>创建日期： 2017年5月19日 下午5:42:27</b>
     *  @param month
     * </pre>
     *  
     */
    public void doMonthCollect(String month)
    {
        List<CodeQualityInfo> list = dao.getMonthCollect(month);
        DateFormat format = new SimpleDateFormat("yyyyMMdd"); 
        String monthAll = month + "01";
        Date dateMonth = null;
        
		try {
			dateMonth = format.parse(monthAll);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dateMonth != null){
			for(CodeQualityInfo info : list)
	        {
				double codeValue = Double.valueOf(info.getCodetotal());
				
	        	projectParameterValueService.insertParameterValue(info.getNo(), dateMonth, 129, codeValue);
	        	ProjectDetailInfo projectDetailInfo = projectListDao.isExit(info.getNo());
	        	double rateValue = 0;
	        	if (projectDetailInfo != null && projectDetailInfo.getCountOfDay() > 0 ){
	        		rateValue = codeValue/projectDetailInfo.getCountOfDay();
	        	}
	        	projectParameterValueService.insertParameterValue(info.getNo(), dateMonth, 83, rateValue);
	        }
		}
    }
}
