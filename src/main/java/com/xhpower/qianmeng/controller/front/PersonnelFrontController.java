package com.xhpower.qianmeng.controller.front;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.qianmeng.entity.Case;
import com.xhpower.qianmeng.entity.Category;
import com.xhpower.qianmeng.entity.Personnel;
import com.xhpower.qianmeng.service.CaseService;
import com.xhpower.qianmeng.service.CategoryService;
import com.xhpower.qianmeng.service.PersonnelService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuyoucheng
 * @since 2018-08-01
 */
@Controller
@RequestMapping("/personnel")
public class PersonnelFrontController {
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private CaseService caseService;
	
	/**
	 * 
	 * 返回团队展示
	 *
	 * @Title: detail
	 * @return String 返回类型
	 * @author liuyoucheng 
	 * @date 2018年8月1日
	 */
	@RequestMapping(value = {"/showteam", "/showteam/{categoryId}"})
	public String showteam(Map<String, Object> map,@PathVariable(required = false) Integer categoryId,Page<Personnel> page) {
		EntityWrapper<Category> wrapper = new EntityWrapper<>();
		wrapper.eq("category_column", "personnel");
		wrapper.orderBy("sort", false);
		List<Category> categoryList = categoryService.selectList(wrapper);
		map.put("categories", categoryList);
		page.setSize(9);
		if(!categoryList.isEmpty()){
			categoryId = categoryId == null ? categoryList.get(0).getId() :  categoryId;
		}
		EntityWrapper<Personnel> pWrapper = new EntityWrapper<>();
		pWrapper.eq("category_id", categoryId);
		pWrapper.orderBy("sort", false);
		map.put("personPage", personnelService.selectPage(page, pWrapper));
		map.put("category", categoryService.selectById(categoryId));
		map.put("categoryId", categoryId);
		return "showteam";
	}
	
	/**
	 * 
	 * 返回个人相关案例
	 *
	 * @Title: detail
	 * @return String 返回类型
	 * @author liuyoucheng 
	 * @date 2018年8月1日
	 */
	@RequestMapping(value = {"/teamdetail", "/teamdetail/{personnelId}"})
	public String teamdetail(Map<String, Object> map,@PathVariable(required = false) String personnelId,Page<Case> page) {
		EntityWrapper<Case> wrapper = new EntityWrapper<>();
		wrapper.eq("personnel_ids", personnelId);
		map.put("personCases", caseService.selectPage(page,wrapper));
		EntityWrapper<Personnel> pwrapper = new EntityWrapper<>();
		pwrapper.eq("id", personnelId);
		map.put("person", personnelService.selectOne(pwrapper));
		return "teamdetail";
	}
}

