package com.icss.mvp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.mvp.dao.IUserManagerDao;
import com.icss.mvp.entity.Page;
import com.icss.mvp.entity.UserDetailInfo;
import com.icss.mvp.entity.UserInfo;
import com.icss.mvp.util.StringMD5Util;

@Service("userManagerService")
@Transactional
public class UserManagerService {
	@Resource
	IUserManagerDao dao;
	
    private static Logger logger=Logger.getLogger(UserManagerService.class);
    
	public Map<String, Object> queryUserInfo(UserInfo userInfo, Page page) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserInfo> userList = dao.queryUserInfos(userInfo, page.getSort(),
				page.getOrder());
		result.put("rows", userList);
		result.put("total", userList.size());
		return result;
	}

	public int deleteUser(UserInfo user) {
		return dao.deleteUser(user);
	}

	public Map<String, Object> updateUser(UserInfo user) {
		Map<String, Object> result = new HashMap<String, Object>();
		String message;
		int counts = dao.updateUser(user);
		if (counts > 0) {
			message = "更新成功";
		} else {
			message = "更新失败";
		}
		result.put("message", message);
		return result;
	}

	public Map<String, Object> insertInfo(UserDetailInfo userDetailInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		String message;
		List<UserDetailInfo> users = dao.isExistsByAccount(userDetailInfo
				.getUserName());
		if (users.size() > 0) {
			message = "用户名已存在";
		} else {
			int userInfo = dao.addUserInfo(userDetailInfo);
			if (userInfo > 0) {
				message = "保存成功";
			} else {
				message = "保存失败";
			}
		}
		result.put("message", message);
		return result;
	}

	public UserInfo isExistsUserInfo(UserInfo userInfo, Page page) {
		return dao.isExistis(userInfo.getUSERNAME());
	}
	public String MD5Password(String password){
		logger.info("##### MD5Password begin password #####");
		String mdpassword=StringMD5Util.MD5String(password);
		logger.info("##### MD5Password end  password #####="+123456+"mdpassword"+654321);
		return mdpassword;
	}
}