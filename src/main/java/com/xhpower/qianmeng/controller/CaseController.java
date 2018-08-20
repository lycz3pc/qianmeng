package com.xhpower.qianmeng.controller;


import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.qianmeng.entity.Case;
import com.xhpower.qianmeng.entity.Category;
import com.xhpower.qianmeng.entity.FileUploadPath;
import com.xhpower.qianmeng.entity.Personnel;
import com.xhpower.qianmeng.service.CaseService;
import com.xhpower.qianmeng.service.CategoryService;
import com.xhpower.qianmeng.service.PersonnelService;
import com.xhpower.qianmeng.utils.Result;
import com.xhpower.qianmeng.utils.SystemCode;
import com.xhpower.qianmeng.utils.UploadUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xc
 * @since 2018-07-27
 */
@RestController
@RequestMapping("/admin/case")
public class CaseController {
	
	@Autowired
	private CaseService caseService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private FileUploadPath fileUploadPath;
	/**
	 * 案例列表查询
	 *
	 * @Title: list
	 * @param page
	 * @return Result 返回类型
	 * @author Liu Youcheng
	 * @date 2018年7月27日
	 */
	@RequestMapping("/list")
	public Result list(Page<Case> page) {
		EntityWrapper<Case> wrapper = new EntityWrapper<>();
		Map<String,Object> condition = page.getCondition();
		if(condition != null){
			for (String key : condition.keySet()) {
				if("case_name".equals(key))
					wrapper.like(key, condition.get(key).toString());
				if("category_id".equals(key))
					wrapper.eq(key, condition.get(key).toString());
			}
			condition.clear();
		}
		wrapper.orderBy("update_time", false);
		page = caseService.selectPage(page,wrapper);
		List<Case> casList = page.getRecords();
		for (Case cases : casList) {
			Category category = categoryService.selectById(cases.getCategoryId());
			cases.setCategoryName(category.getCategoryName());
		}
		page.setRecords(casList);
		return Result.success().page(page);
		
	}
	
	/**
	 * 
	 * 案例保存
	 *
	 * @Title: save
	 * @param article
	 * @param bindingResult
	 * @param file
	 * @return Result 返回类型
	 * @author Lian Youjie
	 * @date 2018年5月9日
	 */
	@RequestMapping("/save")
	public Result save(@Valid Case cases, BindingResult bindingResult, MultipartFile file) {
		if (bindingResult.hasErrors()) {
			ObjectError error = bindingResult.getAllErrors().get(0);
			return Result.error(SystemCode.VERIFICATION_FAILURE, error.getDefaultMessage());
		}
		if(file != null && !file.isEmpty()){
			String path = UploadUtil.uploadFile(fileUploadPath.getArticleImagesPath(), file, fileUploadPath.getRootPath());
			cases.setCaseImage(path);
		}
		return caseService.insertOrUpdate(cases) ? Result.success() : Result.error();
	}
	
	/**
	 * 案例删除
	 *
	 * @Title: delete
	 * @param page
	 * @return Result 返回类型
	 * @author Liu Youcheng
	 * @date 2018年7月27日
	 */
	@RequestMapping("/delete")
	public Result delete(Integer id) {
		if (null == id) {
			return Result.error("案例不存在");
		}
		return caseService.deleteById(id) ? Result.success() : Result.error();
	}
	
	 @RequestMapping(value="/getListPerson")
	 public Result getListByColumn(){
		 EntityWrapper<Personnel> wrapper = new EntityWrapper<>();
		 List<Personnel> personnelList = personnelService.selectList(wrapper);
		 for (Personnel personnel : personnelList) {
			 Category category = categoryService.selectById(personnel.getCategoryId());
			 personnel.setCategoryName(category.getCategoryName());
		 }
		 return  Result.success().put("data", personnelList);
	 }
}

