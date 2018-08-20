package com.xhpower.qianmeng.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xhpower.qianmeng.entity.Category;
import com.xhpower.qianmeng.service.CategoryService;
import com.xhpower.qianmeng.utils.Result;
import com.xhpower.qianmeng.utils.SystemCode;

/**
 * <p>
 *  分类管理-前端控制器
 * </p>
 *
 * @author xc
 * @since 2018-07-27
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/list")
	public Result list(Page<Category> page,Category category){
		EntityWrapper<Category> wrapper = new EntityWrapper<Category>();
		if(StringUtils.isNotEmpty(category.getCategoryName()))
			wrapper.like("category_name",category.getCategoryName());
		if(StringUtils.isNotEmpty(category.getCategoryColumn()))
			wrapper.eq("category_column",category.getCategoryColumn());
		wrapper.eq("status", 1); //有效
		wrapper.orderBy("id", false);
		page = categoryService.selectPage(page, wrapper);
		return Result.success().page(page);
	}
	
	@RequestMapping(value = "/save")
	public Result save(@Valid Category category, BindingResult bindingResult){
		//新增
		if (bindingResult.hasErrors()) {
			ObjectError error = bindingResult.getAllErrors().get(0);
			return Result.error(SystemCode.VERIFICATION_FAILURE, error.getDefaultMessage());
		}
		return categoryService.insert(category) ? Result.success():Result.error();
	}
	
	@RequestMapping(value="/update")
    public Result update(@Valid Category category, BindingResult bindingResult){
		//验证
		if (bindingResult.hasErrors()) {
			ObjectError error = bindingResult.getAllErrors().get(0);
			return Result.error(SystemCode.VERIFICATION_FAILURE, error.getDefaultMessage());
		}
	 	return categoryService.updateById(category) ? Result.success():Result.error();
    }
	
	@RequestMapping(value="/delete")
	public Result delete(Category category){
		category.setStatus(0);  //状态改 0-无效 1-有效
		 return  categoryService.updateById(category)?Result.success():Result.error();
	}
	

	 @RequestMapping(value="/getListByColumn")
	 public Result getlistByColumn(String column){
		 EntityWrapper<Category> wrapper = new EntityWrapper<>();
		 wrapper.eq("category_column", column);
		 wrapper.eq("status", 1);      //有效
		 wrapper.orderBy("sort", false);
		 return  Result.success().put("data", categoryService.selectList(wrapper));
	 }
}

