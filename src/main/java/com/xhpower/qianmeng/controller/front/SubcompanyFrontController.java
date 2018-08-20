package com.xhpower.qianmeng.controller.front;


import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xhpower.qianmeng.entity.Subcompany;
import com.xhpower.qianmeng.service.SubcompanyService;

/**
 * <p>
 *  分公司---前端控制器
 * </p>
 *
 * @author xc
 * @since 2018-08-01
 */
@Controller
@RequestMapping("/subcompany")
public class SubcompanyFrontController {
	@Autowired
	private SubcompanyService subcompanyService;
	
	@RequestMapping(value = {"/list","/list/{province}"})
	public String list(Map<String, Object> map,@PathVariable(required=false) String province){
		EntityWrapper<Subcompany> wrapper = new EntityWrapper<Subcompany>();
		if(StringUtils.isNotEmpty(province)) {
			wrapper.eq("province", province);
		}
		wrapper.orderBy("sort", false);
		map.put("subcompanyList", subcompanyService.selectList(wrapper));
		
		//有数据的省份
		wrapper = new EntityWrapper<Subcompany>();
		wrapper.setSqlSelect("province").groupBy("province").orderBy("province");
		map.put("provinceList", subcompanyService.selectMaps(wrapper));
		return "subcompany_list";
	}
	
	@RequestMapping(value = "/detail/{id}")
	public String detail(Map<String, Object> map,@PathVariable Integer id){
		map.put("subcompany", subcompanyService.selectById(id));
		return "subcompany_detail";
	}
}

