package com.xhpower.qianmeng.controller;

import javax.servlet.http.HttpServletRequest;

import com.xhpower.qianmeng.entity.User;

public class BaseController {
	
	/**   
	 * @Fields NO_LOGIN : TODO(没有登录)   
	 */ 
	public static final String NO_LOGIN="403";
	/**   
	 * @Fields SYS_ERR : TODO(系统错误)   
	 */ 
	public static final String SYS_ERR="500";
	/**   
	 * @Fields SUCCSS : TODO(成功请求)   
	 */ 
	public static final String SUCCSS="200";
	
	public User getUser(HttpServletRequest request) {
		User admin = (User) request.getSession().getAttribute("ADMIN_USER");		
		return null==admin?null:admin;
	}
	
	public boolean isLogin(HttpServletRequest request) {		
		return null==getUser(request)?false:true;
	}
}
