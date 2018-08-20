package com.xhpower.qianmeng.controller;


import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xhpower.qianmeng.entity.User;
import com.xhpower.qianmeng.service.UserService;
import com.xhpower.qianmeng.utils.Md5Util;
import com.xhpower.qianmeng.utils.Result;
import com.xhpower.qianmeng.utils.SystemCode;

/**
 * <p>
 *  用户管理
 * </p>
 *
 * @author xc
 * @since 2018-07-24
 */
@RestController
@RequestMapping("/admin/user")
public class UserController{
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/list")
	public Result list(Page<User> page, String keyword){
		EntityWrapper<User> wrapper = new EntityWrapper<User>();
		wrapper.like("username", keyword);
		wrapper.or().like("real_name", keyword);
		wrapper.or().like("phone", keyword);
		wrapper.orderBy("create_tm", false);
		page = userService.selectPage(page, wrapper);
		return Result.success().page(page);
	}
	
	@RequestMapping(value = "/save")
	public Result save(@Valid User user, BindingResult bindingResult){
		//新增
		if (bindingResult.hasErrors()) {
			ObjectError error = bindingResult.getAllErrors().get(0);
			return Result.error(SystemCode.VERIFICATION_FAILURE, error.getDefaultMessage());
		}
		user.setPassword(Md5Util.stringByMD5(user.getPassword()));
		return userService.insert(user) ? Result.success():Result.error();
	}
	
	@RequestMapping(value="/update")
    public Result update(@Valid User user, BindingResult bindingResult){
		//验证
		if (bindingResult.hasErrors()) {
			ObjectError error = bindingResult.getAllErrors().get(0);
			return Result.error(SystemCode.VERIFICATION_FAILURE, error.getDefaultMessage());
		}
	 	if(!StringUtils.isEmpty(user.getPassword())){
			user.setPassword(Md5Util.stringByMD5(user.getPassword()));
		}else {
			user.setPassword(null);
		}
	 	return userService.updateById(user) ? Result.success():Result.error();
    }
	
	@RequestMapping(value = "/updateStatus")
	public Result updateStatus(Long[] ids,Integer status){
		 User entity = new User();
		 entity.setStatus(status); //状态改 0-无效 1-有效
		 EntityWrapper<User> wrapper = new EntityWrapper<>();
		 wrapper.in("id", ids);
		 return  userService.update(entity, wrapper)?Result.success():Result.error();
	}
	
	@RequestMapping(value = "/delete")
	public Result delete(Long[] ids){
		return userService.deleteBatchIds(Arrays.asList(ids))?Result.success():Result.error();
	}
	
	 @RequestMapping(value="/resetPassword",method=RequestMethod.POST)
	 public Result resetPassword(Long[] ids){
		 User entity = new User();
		 entity.setPassword(Md5Util.stringByMD5("123456"));
		 EntityWrapper<User> wrapper = new EntityWrapper<>();
		 wrapper.in("id", ids);
		 return  userService.update(entity, wrapper)?Result.success():Result.error();
	 }
	
     @RequestMapping(value="/valiUsername",method=RequestMethod.POST)
     public Result valiUserName(User user) {
          EntityWrapper<User> wrapper = new EntityWrapper<User>();
          if(user.getId() != null){
        	  wrapper.ne("id", user.getId());
          } 
          wrapper.eq("username", user.getUsername());
          return userService.selectCount(wrapper) == 0 ? Result.success():Result.error();
     }
}

