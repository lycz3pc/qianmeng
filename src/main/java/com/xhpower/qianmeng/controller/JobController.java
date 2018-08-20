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
import com.xhpower.qianmeng.entity.Job;
import com.xhpower.qianmeng.service.JobService;
import com.xhpower.qianmeng.utils.Result;
import com.xhpower.qianmeng.utils.SystemCode;

/**
 * <p>
 *  人才招聘-前端控制器
 * </p>
 *
 * @author xc
 * @since 2018-07-27
 */
@RestController
@RequestMapping("/admin/job")
public class JobController {
	@Autowired
	private JobService jobService;
	
	@RequestMapping(value = "/list")
	public Result list(Page<Job> page,Job job){
		EntityWrapper<Job> wrapper = new EntityWrapper<Job>();
		if(StringUtils.isNotEmpty(job.getJobName()))
			wrapper.like("job_name",job.getJobName());
		if(null != job.getCategoryId() && job.getCategoryId() > 0)
			wrapper.eq("category_id",job.getCategoryId());
		wrapper.orderBy("update_time", false);
		page = jobService.selectPage(page, wrapper);
		return Result.success().page(page);
	}
	
	@RequestMapping(value = "/save")
	public Result save(@Valid Job job, BindingResult bindingResult){
		//新增
		if (bindingResult.hasErrors()) {
			ObjectError error = bindingResult.getAllErrors().get(0);
			return Result.error(SystemCode.VERIFICATION_FAILURE, error.getDefaultMessage());
		}
		return jobService.insert(job) ? Result.success():Result.error();
	}
	
	@RequestMapping(value="/update")
    public Result update(@Valid Job job, BindingResult bindingResult){
		//验证
		if (bindingResult.hasErrors()) {
			ObjectError error = bindingResult.getAllErrors().get(0);
			return Result.error(SystemCode.VERIFICATION_FAILURE, error.getDefaultMessage());
		}
	 	return jobService.updateById(job) ? Result.success():Result.error();
    }
	
	@RequestMapping(value="/delete")
	public Result delete(Integer id){
		if (null == id) {
			return Result.error(SystemCode.VERIFICATION_FAILURE, "无效的请求参数");
		}
		return jobService.deleteById(id) ? Result.success() : Result.error();
	}
}

