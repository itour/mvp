package com.icss.mvp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.icss.mvp.Constants;

public class LoginInterceptor implements HandlerInterceptor {
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 获取请求的URL
		String url = request.getRequestURI();
		// URL:login.jsp是公开的;这个demo是除了login.jsp是可以公开访问的，其它的URL都进行拦截控制
		if (url.indexOf("login.html") >= 0) {
			return true;
		}
		Object userInfo = request.getSession().getAttribute(Constants.USER_KEY);

		if (null == userInfo) {
			// 判断是否是ajax请求
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase(
							"XMLHttpRequest"))// 如果是ajax请求响应头会有，x-requested-with；
			{
				response.setHeader("sessionstatus", "timeOut");// 在响应头设置session状态
				response.getWriter().print("timeOut"); // 打印一个返回值，
			} else {
				request.getRequestDispatcher("/view/login.html").forward(
						request, response);

			}
			return false;
		} else {
			return true;
		}
	}

}
