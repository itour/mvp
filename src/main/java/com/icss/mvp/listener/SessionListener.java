package com.icss.mvp.listener;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.icss.mvp.Constants;
import com.icss.mvp.entity.UserInfo;
import com.icss.mvp.util.StringMD5Util;

/**
 * 
 * <pre>
 * <b>描述：session监听器</b>
 * <b>任务编号：</b>
 * <b>作者：fpy</b> 
 * <b>创建日期： 2017年8月10日 下午2:55:48</b>
 * </pre>
 */
public class SessionListener implements  HttpSessionListener {
	private static Logger logger = Logger.getLogger(SessionListener.class);
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		logger.info("##### sessionDestroyed ++++++++++++++++++++++++++++++++++++++ start #####");
	       //在session销毁的时候 把Map中保存的键值对清除  
        UserInfo user = (UserInfo)event.getSession().getAttribute(Constants.USER_KEY);  
        if(user!=null){  
        	StringMD5Util.userLogout(user.getUSERNAME());
        }  
        logger.info("##### sessionDestroyed ********************************* end #####");
	}

}
