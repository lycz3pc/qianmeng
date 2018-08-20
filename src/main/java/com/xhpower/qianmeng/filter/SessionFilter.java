package com.xhpower.qianmeng.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {
	/**
	 * 封装，不需要过滤的list列表
	 */
	protected static List<Pattern> patterns = new ArrayList<Pattern>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;	
		HttpSession session = httpRequest.getSession();
        patterns.add(Pattern.compile("admin/tologin"));
        patterns.add(Pattern.compile("admin/login"));
		String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		if (url.startsWith("/") && url.length() > 1) {
			url = url.substring(1);			
		}
		if (isInclude(url)){
            chain.doFilter(httpRequest, httpResponse);
            return;
        } else {				
			if (session.getAttribute("ADMIN_USER") != null&&url.indexOf("admin/login")<=-1) {		        	
				// session存在
				chain.doFilter(httpRequest, httpResponse);
				return;
			} else {
				// session不存在 准备跳转失败						
				httpResponse.sendRedirect("/admin/tologin");//重定向到new.jsp								 
			}
		}

	}

	@Override
	public void destroy() {

	}
	/**
	 * 是否需要过滤
	 * 
	 * @param url
	 * @return
	 */
	private boolean isInclude(String url) {
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(url);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}


}
