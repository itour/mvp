package com.icss.mvp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.icss.mvp.Constants;
import com.icss.mvp.entity.Page;
import com.icss.mvp.entity.UserDetailInfo;
import com.icss.mvp.entity.UserInfo;
import com.icss.mvp.service.UserManagerService;

@RestController
@RequestMapping("/user")
public class UserManagerController {
	@Resource
	private HttpServletRequest request;

	@Resource
	private UserManagerService userManagerService;

	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryUserListInfo(UserInfo userInfo, Page page) {
		String userName = request.getParameter("username");
		userInfo.setUSERNAME(userName);
		return userManagerService.queryUserInfo(userInfo, page);
	}

	@RequestMapping("/insertInfo")
	@ResponseBody
	public Map<String, Object> insertInfo(HttpServletRequest request)
			throws ParseException {
		UserDetailInfo userDetailInfo = new UserDetailInfo();
		userDetailInfo.setUserName(request.getParameter("userName"));
	    String pwd = userManagerService.MD5Password(request.getParameter("password").trim());
		userDetailInfo.setPassword(pwd);
		String repwd = userManagerService.MD5Password(request.getParameter("repassword").trim());
		userDetailInfo.setRepassword(repwd);
		userDetailInfo.setUserId(request.getParameter("userName"));
		setCreater(userDetailInfo);
		// userDetailInfo.setIdentity(request.getParameter("identity"));
		userDetailInfo.setCreateData(getCreateDate());
		userDetailInfo.setParma(request.getParameter("parma"));
		return userManagerService.insertInfo(userDetailInfo);
	}

	private UserInfo getLoginUserInfo() {
		return (UserInfo) request.getSession().getAttribute(Constants.USER_KEY);
	}

	private void setCreater(UserDetailInfo userDetailInfo) {
		if (null != getLoginUserInfo()) {
			userDetailInfo.setCreater(getLoginUserInfo().getUSERNAME());
		} else {
			userDetailInfo.setCreater("");
		}
	}

	private Date getCreateDate() throws ParseException {
		Calendar currTime = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cdate = df.format(currTime.getTime());
		Date now = null;
		if (cdate != "") {
			now = df.parse(cdate);
		}
		return now;
	}

	@RequestMapping("/add")
	public String addUserInfo(String parma) {
		request.setAttribute("parma", parma);
		return "/userInfo/userInfo";
	}

	@RequestMapping("/update")
	public String updateUserList(String username, Page page) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUSERNAME(username);
		UserInfo userInfos = userManagerService
				.isExistsUserInfo(userInfo, page);
		request.setAttribute("user", userInfos);
		return "/userInfo/userInfo";
	}

	@RequestMapping("/updateUser")
	@ResponseBody
	public Map<String, Object> updateUser(String username, String password) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUSERNAME(username);
		userInfo.setPASSWORD(userManagerService.MD5Password(password));
		Map<String, Object> result = new HashMap<String, Object>();
		if (null != getLoginUserInfo()) {
			return result = userManagerService.updateUser(userInfo);
		}
		return result;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public int deleteUser(UserInfo user) {
		return userManagerService.deleteUser(user);
	}

	@RequestMapping("/users")
	public Object getUser() {
		return "/userList/userList";
	}
}