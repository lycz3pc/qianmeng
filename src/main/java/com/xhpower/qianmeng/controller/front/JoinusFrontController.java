package com.xhpower.qianmeng.controller.front;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xhpower.qianmeng.entity.Category;
import com.xhpower.qianmeng.entity.Job;
import com.xhpower.qianmeng.service.CategoryService;
import com.xhpower.qianmeng.service.JobService;

/**
 * <p>
 *  人才招聘---前端控制器
 * </p>
 *
 * @author xc
 * @since 2018-08-01
 */
@Controller
@RequestMapping("/joinus")
public class JoinusFrontController {
	@Autowired
	private JobService jobService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = {"","/{categoryId}"})
	public String list(Map<String, Object> map,@PathVariable(required=false) Integer categoryId){
		EntityWrapper<Category> cWrapper = new EntityWrapper<>();
		cWrapper.eq("category_column", "job");
		cWrapper.orderBy("sort", false);
		List<Category> categoryList = categoryService.selectList(cWrapper);
		map.put("categoryList", categoryList);
		
		EntityWrapper<Job> jobWrapper = new EntityWrapper<Job>();
		if(null != categoryId) {
			jobWrapper.eq("category_id", categoryId);
		}
		jobWrapper.orderBy("update_time", false);
		map.put("jobs", jobService.selectList(jobWrapper));

		return "joinus";
	}
}

