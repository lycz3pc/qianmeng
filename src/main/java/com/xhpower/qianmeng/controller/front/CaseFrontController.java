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
import com.xhpower.qianmeng.service.CaseService;
import com.xhpower.qianmeng.service.CategoryService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuyoucheng
 * @since 2018-08-01
 */
@Controller
@RequestMapping("/case")
public class CaseFrontController {
	
	@Autowired
	private CaseService caseService;
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 
	 * 返回案例展示
	 *
	 * @Title: detail
	 * @return String 返回类型
	 * @author liuyoucheng 
	 * @date 2018年8月1日
	 */
	@RequestMapping(value = {"/example", "/example/{category}"})
	public String showteam(Map<String, Object> map,@PathVariable(required = false) Integer category,Page<Case> page) {
		EntityWrapper<Category> wrapper = new EntityWrapper<>();
		wrapper.eq("category_column", "case");
		wrapper.orderBy("sort", false);
		List<Category> categoryList = categoryService.selectList(wrapper);
		map.put("categories", categoryList);
		page.setSize(9);
		Integer category_id = null;
		if(!categoryList.isEmpty()){
			category_id = category == null ? categoryList.get(0).getId() :  category;
		}
		EntityWrapper<Case> pWrapper = new EntityWrapper<>();
		pWrapper.orderBy("sort", false);
		pWrapper.eq("category_id", category_id);
		map.put("casePage", caseService.selectPage(page, pWrapper));
		map.put("categoryId", category_id);
		map.put("category", categoryService.selectById(category_id));
		return "example";
	}
	
	/**
	 * 
	 * 返回案例展示
	 *
	 * @Title: detail
	 * @return String 返回类型
	 * @author liuyoucheng 
	 * @date 2018年8月1日
	 */
	@RequestMapping(value = {"/detail", "/detail/{caseId}"})
	public String detail(Map<String, Object> map,@PathVariable(required = false) Integer caseId) {
		EntityWrapper<Case> cwrapper = new EntityWrapper<>();
		cwrapper.eq("id", caseId);
		Case entity = caseService.selectOne(cwrapper);
		Category category = categoryService.selectById(entity.getCategoryId());
		entity.setCategoryName(category.getCategoryName());
		map.put("case", entity);
		return "spdetails";
	}
	
}

