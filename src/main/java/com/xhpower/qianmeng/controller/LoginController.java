package com.xhpower.qianmeng.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xhpower.qianmeng.controller.LoginController;
import com.xhpower.qianmeng.entity.User;
import com.xhpower.qianmeng.service.UserService;
import com.xhpower.qianmeng.utils.Md5Util;

/**
*
* @Description: 登录、注销
* @author xc 
* @date 2018年7月24日 上午11:32:04
*/

@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userServiceImpl;

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		User user = getUser(request);
		if(null != user) {
			return new ModelAndView("index").addObject("user", user);
		}
		return new ModelAndView("redirect:/admin/tologin");
	}
	
	@RequestMapping("/tologin")
	public ModelAndView tologin(HttpServletRequest request) {
		return new ModelAndView("/login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(User user, HttpServletRequest request) {
		Map<String, String> errMap = new HashMap<>();
		errMap.put("username", user.getUsername());
		
		logger.info("userLogin||username:"+user.getUsername()+",password:"+user.getPassword());
		if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
			errMap.put("errMsg", "用户名或密码不能为空");
		}
		user.setPassword(Md5Util.stringByMD5(user.getPassword()));
		user = userServiceImpl.selectOne(new EntityWrapper<>(user));
		if (null != user) {
			if(!user.getStatus().equals(1)) { //不等于1，表示账号禁用
				errMap.put("errMsg", "该账号已被禁用！");
			}else {
				request.getSession().setAttribute("ADMIN_USER", user);
				return new ModelAndView("redirect:/admin/index");
			}
		} else {
			errMap.put("errMsg", "用户名或密码错误");
		}
		return new ModelAndView("/login", errMap);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		if (isLogin(request)) {
			return new ModelAndView("redirect:/admin/index");
		}
		return new ModelAndView("redirect:/admin/tologin");
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().removeAttribute("ADMIN_USER");
		return new ModelAndView("/login");
	}
}
