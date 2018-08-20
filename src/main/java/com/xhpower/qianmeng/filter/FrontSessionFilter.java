package com.xhpower.qianmeng.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xhpower.qianmeng.entity.Category;
import com.xhpower.qianmeng.service.CategoryService;

public class FrontSessionFilter implements Filter {
	
	@Autowired
	private CategoryService categoryService;
	
	private static final Logger logger = LoggerFactory.getLogger(FrontSessionFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("init FrontSessionFilter....");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String uri = httpRequest.getRequestURI();
		EntityWrapper<Category> wrapper = new EntityWrapper<>();
		wrapper.orderBy("sort", false);
		httpRequest.setAttribute("categoryList", categoryService.selectList(wrapper));
		//uri:          /qm/joinplat 平台入驻 /qm/joinplat   团队展示/personnel/showteam  精品案例  /case/example 招盟家商 /qm/joinsum 新闻动态/article/news
		if(uri.contains("/index") || uri.contains("/qm/introduction") || uri.contains("/subcompany") || uri.contains("/joinus")){
			httpRequest.setAttribute("nav_select", "about");
		}else if(uri.contains("/qm/service")){
			httpRequest.setAttribute("nav_select", "service");
		}else if(uri.contains("/qm/joinplat")){
			httpRequest.setAttribute("nav_select", "joinplat");
		}else if(uri.contains("/personnel")){
			httpRequest.setAttribute("nav_select", "showteam");
		}else if(uri.contains("/case")){
			httpRequest.setAttribute("nav_select", "example");
		}else if(uri.contains("/qm/joinsum")){
			httpRequest.setAttribute("nav_select", "joinsum");
		}else if(uri.contains("/article")){
			httpRequest.setAttribute("nav_select", "news");
		}else{
			httpRequest.setAttribute("nav_select", "");
		}
		chain.doFilter(httpRequest, httpResponse);
	}

	@Override
	public void destroy() {
		logger.info("destroy FrontSessionFilter....");
		
	}

}
