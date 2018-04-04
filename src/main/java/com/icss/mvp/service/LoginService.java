package com.icss.mvp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icss.mvp.dao.ILoginDao;
import com.icss.mvp.entity.UserInfo;

/**
 * 
 * <pre>
 * <b>描述：登录模块service层</b>
 * <b>任务编号：</b>
 * <b>作者：张鹏飞</b> 
 * <b>创建日期： 2017年5月12日 下午2:56:40</b>
 * </pre>
 */
@Service
public class LoginService
{
    
    @Autowired
    ILoginDao dao;
    
    public UserInfo getUserInfo(UserInfo user)
    {
        return dao.getUserInfo(user);
    }
    
}
