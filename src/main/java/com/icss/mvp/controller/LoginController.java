package com.icss.mvp.controller;

import com.icss.mvp.Constants;
import com.icss.mvp.entity.UserInfo;
import com.icss.mvp.service.LoginService;
import com.icss.mvp.service.UserManagerService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <pre>
 * <b>描述：登录模块controller</b>
 * <b>任务编号：</b>
 * <b>作者：张鹏飞</b> 
 * <b>创建日期： 2017年5月12日 下午2:55:48</b>
 * </pre>
 */

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Resource
	private UserManagerService userManagerService;
	@Resource
	HttpServletRequest request;
	@Resource
	HttpServletResponse response;

	private static Logger logger = Logger.getLogger(LoginController.class);

	@RequestMapping("/dologin")
	@ResponseBody
	public Object dologin(UserInfo user) {
		logger.info("##### LoginController dologin start #####");
		UserInfo userInfo = new UserInfo();
		if (user.getISONLINE() == 1) {
			if (!W3Login(user.getUSERNAME(), user.getPASSWORD())) {
				return Constants.FAILED;
			}
			userInfo.setUSERNAME(user.getUSERNAME());
			userInfo.setPASSWORD(userManagerService.MD5Password(user.getPASSWORD()));
		} else {
			UserInfo requestUser = new UserInfo();
			requestUser.setUSERNAME(user.getUSERNAME());
			requestUser.setPASSWORD(userManagerService.MD5Password(user.getPASSWORD()));

			userInfo = loginService.getUserInfo(requestUser);
			if (null == userInfo) {
				return Constants.FAILED;
			}
		}
		// 检查session是否已经存在此用户信息
		Object userIn = request.getSession().getAttribute(Constants.USER_KEY);
		if (null == userIn) {
			request.getSession().setAttribute(Constants.USER_KEY, userInfo);
		}
		logger.info("##### LoginController dologin end #####");
		try {
			addNameCookie(user.getUSERNAME(), response, request);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			addPassCookie(user.getPASSWORD(), response, request);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Constants.SUCCESS;
	}

	// 将用户名保存到本地cookie中
	private static void addNameCookie(String username, HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		if (StringUtils.isNotBlank(username)) {

			Cookie nameCookie = new Cookie("username", URLEncoder.encode(username, "utf-8"));
			nameCookie.setPath("/");
			nameCookie.setMaxAge(7 * 24 * 60 * 60);
			response.addCookie(nameCookie);
		}
	}

	// 将密码保存到本地cookie中
	private static void addPassCookie(String password, HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		if (StringUtils.isNotBlank(password)) {

			Cookie passCookie = new Cookie("password", URLEncoder.encode(password, "utf-8"));
			passCookie.setPath("/");
			passCookie.setMaxAge(7 * 24 * 60 * 60);
			response.addCookie(passCookie);
		}
	}

	@RequestMapping("/loginout")
	@ResponseBody
	public void Logout() {
		request.getSession().invalidate();
		try {
			response.sendRedirect("../view/login.html");
		} catch (IOException e) {
			logger.info("##### LoginController Logout fail #####");
		}
	}

	public boolean W3Login(String userId, String password) {
		List<NameValuePair> valuePairs = new ArrayList<>();
		valuePairs.add(new BasicNameValuePair("actionFlag", "loginAuthenticate"));
		valuePairs.add(new BasicNameValuePair("uid", userId));
		valuePairs.add(new BasicNameValuePair("loginPageType", "mix"));
		valuePairs.add(new BasicNameValuePair("password", password));
		valuePairs.add(new BasicNameValuePair("loginMethod", "login"));
		valuePairs.add(new BasicNameValuePair("verifyCode", "2345"));

		HttpPost httpPost = new HttpPost("https://login.huawei.com/login/login.do");
		httpPost.setEntity(new UrlEncodedFormEntity(valuePairs, StandardCharsets.UTF_8));

		boolean isLogin = false;
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			List<org.apache.http.cookie.Cookie> cookies = client.getCookieStore().getCookies();
			isLogin = cookies.size() == 19;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return isLogin;
	}
}
