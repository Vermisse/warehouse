package org.warehouse.filter;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.*;

/**
 * 登录权限过滤器
 */
@Configuration
public class SecurityFilter extends HandlerInterceptorAdapter {
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("user");
		String url = request.getServletPath().toString();
		
		if (user == null) { // 如果未登录直接拦截
			if (!url.equals("/") && !url.equals("/login.html")) { // 只放行登录页和登录操作
				response.sendRedirect(request.getContextPath() + "/");
				return false;
			}
			return super.preHandle(request, response, handler);
		}
		
		//如果已经是登录状态拦截登录操作
		if (url.equals("/login.html")) {
			response.sendRedirect(request.getContextPath() + "/");
			return super.preHandle(request, response, handler);
		}
		
		return super.preHandle(request, response, handler);
	}

	/**
	 * 将入参带回request
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		Enumeration<String> keys = request.getParameterNames();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (request.getAttribute(key) == null)
				request.setAttribute(key, request.getParameter(key));
		}
		super.postHandle(request, response, handler, modelAndView);
	}
}