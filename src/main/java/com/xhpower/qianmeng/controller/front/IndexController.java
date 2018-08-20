package com.xhpower.qianmeng.controller.front;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xhpower.qianmeng.entity.Category;
import com.xhpower.qianmeng.service.CategoryService;
import com.xhpower.qianmeng.service.PersonnelService;

@Controller
public class IndexController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PersonnelService personnelService;

	/**
	 * 
	 * 首页
	 *
	 * @Title: detail
	 * @param map
	 * @param request
	 * @return String 返回类型
	 * @author liuyoucheng 
	 * @date 2018年7月31日
	 */
	@RequestMapping(value = { "", "/index" })
	public String index(Map<String, Object> map, HttpServletRequest request) {
		EntityWrapper<Category> wrapper = new EntityWrapper<>();
		wrapper.eq("category_column", "personnel");
		wrapper.orderBy("sort", false);
		map.put("categories", categoryService.selectList(wrapper));
		map.put("personnels", personnelService.selectList(null));
		//map.put(key, value)
		return "front_index";
	}
	
	/**
	 * 
	 * 返回单首页
	 *
	 * @Title: detail
	 * @param pgname 页面名称
	 * @return String 返回类型
	 * @author liuyoucheng 
	 * @date 2018年7月31日
	 */
	@RequestMapping(value = "/qm/{pgname}")
	public String pgname(@PathVariable String pgname) {
		return pgname;
	}
	
}