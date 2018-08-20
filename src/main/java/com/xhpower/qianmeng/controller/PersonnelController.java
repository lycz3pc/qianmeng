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
import com.xhpower.qianmeng.entity.Personnel;
import com.xhpower.qianmeng.service.PersonnelService;
import com.xhpower.qianmeng.utils.Result;
import com.xhpower.qianmeng.utils.SystemCode;
import com.xhpower.qianmeng.utils.UploadUtil;

/**
 * <p>
 *  团队展示-前端控制器
 * </p>
 *
 * @author xc
 * @since 2018-07-30
 */
@RestController
@RequestMapping("/admin/personnel")
public class PersonnelController {
	@Autowired
	private PersonnelService personnelService;
	
	@Autowired
	private FileUploadPath fileUploadPath;
	
	@RequestMapping(value = "/list")
	public Result list(Page<Personnel> page,Personnel personnel){
		EntityWrapper<Personnel> wrapper = new EntityWrapper<Personnel>();
		if(StringUtils.isNotEmpty(personnel.getName()))
			wrapper.like("name",personnel.getName());
		if(null != personnel.getCategoryId() && personnel.getCategoryId() > 0)
			wrapper.eq("category_id",personnel.getCategoryId());
		wrapper.orderBy("update_time", false);
		page = personnelService.selectPage(page, wrapper);
		return Result.success().page(page);
	}
	
	@RequestMapping(value = "/save")
	public Result save(@Valid Personnel personnel, BindingResult bindingResult, MultipartFile file){
		//新增
		if (bindingResult.hasErrors()) {
			ObjectError error = bindingResult.getAllErrors().get(0);
			return Result.error(SystemCode.VERIFICATION_FAILURE, error.getDefaultMessage());
		}
		if(file != null && !file.isEmpty()){
			String picturePath = UploadUtil.uploadFile(fileUploadPath.getPersonnelImagesPath(), file, fileUploadPath.getRootPath());
			personnel.setPicture(picturePath);
		}
		return personnelService.insertOrUpdate(personnel) ? Result.success():Result.error();
	}

	
	@RequestMapping(value="/delete")
	public Result delete(Integer id){
		if (null == id) {
			return Result.error(SystemCode.VERIFICATION_FAILURE, "无效的请求参数");
		}
		return personnelService.deleteById(id) ? Result.success() : Result.error();
	}
}

