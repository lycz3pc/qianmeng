package com.xhpower.qianmeng.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xhpower.qianmeng.entity.FileUploadPath;
import com.xhpower.qianmeng.entity.Subcompany;
import com.xhpower.qianmeng.service.SubcompanyService;
import com.xhpower.qianmeng.utils.Result;
import com.xhpower.qianmeng.utils.SystemCode;
import com.xhpower.qianmeng.utils.UploadUtil;

/**
 * <p>
 *  子公司管理---前端控制器
 * </p>
 *
 * @author xc
 * @since 2018-08-01
 */
@RestController
@RequestMapping("/admin/subcompany")
public class SubcompanyController {
	@Autowired
	private SubcompanyService subcompanyService;
	
	@Autowired
	private FileUploadPath fileUploadPath;
	
	@RequestMapping(value = "/list")
	public Result list(Page<Subcompany> page,Subcompany subcompany){
		EntityWrapper<Subcompany> wrapper = new EntityWrapper<Subcompany>();
		if(StringUtils.isNotEmpty(subcompany.getName()))
			wrapper.like("name",subcompany.getName());
		if(StringUtils.isNotEmpty(subcompany.getProvince()))
			wrapper.eq("province",subcompany.getProvince());
		wrapper.orderBy("sort", false);
		page = subcompanyService.selectPage(page, wrapper);
		return Result.success().page(page);
	}
	
	@RequestMapping(value = "/save")
	public Result save(@Valid Subcompany subcompany, BindingResult bindingResult, MultipartFile file){
		//新增
		if (bindingResult.hasErrors()) {
			ObjectError error = bindingResult.getAllErrors().get(0);
			return Result.error(SystemCode.VERIFICATION_FAILURE, error.getDefaultMessage());
		}
		if(file != null && !file.isEmpty()){
			String picturePath = UploadUtil.uploadFile(fileUploadPath.getSubcompanyImagesPath(), file, fileUploadPath.getRootPath());
			subcompany.setPicture(picturePath);
		}
		return subcompanyService.insertOrUpdate(subcompany) ? Result.success():Result.error();
	}

	
	@RequestMapping(value="/delete")
	public Result delete(Integer id){
		if (null == id) {
			return Result.error(SystemCode.VERIFICATION_FAILURE, "无效的请求参数");
		}
		return subcompanyService.deleteById(id) ? Result.success() : Result.error();
	}
}

