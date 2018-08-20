package com.xhpower.qianmeng.controller.front;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.qianmeng.entity.Article;
import com.xhpower.qianmeng.entity.Category;
import com.xhpower.qianmeng.service.ArticleService;
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
@RequestMapping("/article")
public class ArticleFrontController {
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 
	 * 返回文章展示
	 *
	 * @Title: detail
	 * @return String 返回类型
	 * @author liuyoucheng 
	 * @date 2018年8月1日
	 */
	@RequestMapping(value = {"/news", "/news/{category}"})
	public String shownews(Map<String, Object> map,@PathVariable(required = false) Integer category,Page<Article> page) {
		EntityWrapper<Category> wrapper = new EntityWrapper<>();
		wrapper.eq("category_column", "article");
		wrapper.orderBy("sort", false);
		List<Category> categoryList = categoryService.selectList(wrapper);
		map.put("categories", categoryList);
		EntityWrapper<Article> aWrapper = new EntityWrapper<>();
		page.setSize(5);
		Integer category_id = null;
		if(!categoryList.isEmpty()){
			category_id= category == null ? categoryList.get(0).getId() :  category;
		}
		aWrapper.eq("category_id", category_id);
		aWrapper.orderBy("update_time", false);
		aWrapper.eq("status", 1);
		map.put("articlePage", articleService.selectPage(page, aWrapper));
		map.put("categoryId", category_id);
		return "news";
	}
	
	/**
	 * 
	 * 返回文章详情展示
	 *
	 * @Title: detail
	 * @return String 返回类型
	 * @author liuyoucheng 
	 * @date 2018年8月1日
	 */
	@RequestMapping(value = {"/detail", "/detail/{articleId}"})
	public String detail(Map<String, Object> map,@PathVariable(required = false) Integer articleId) {
		EntityWrapper<Article> awrapper = new EntityWrapper<>();
		awrapper.eq("id", articleId);
		Article entity = articleService.selectOne(awrapper);
		EntityWrapper<Category> cwrapper = new EntityWrapper<>();
		cwrapper.eq("id", entity.getCategoryId());
		map.put("category", categoryService.selectOne(cwrapper));
		map.put("article", entity);
		//上一篇 
		EntityWrapper<Article> wrapper = new EntityWrapper<>();
		wrapper.eq("category_id", entity.getCategoryId());
		wrapper.orderBy("update_time", false);
		wrapper.lt("update_time", entity.getUpdateTime());
		Article lastArticle = articleService.selectOne(wrapper);
		//下一篇
		EntityWrapper<Article> wrapper2 = new EntityWrapper<>();
		wrapper2.eq("category_id", entity.getCategoryId());
		wrapper2.orderBy("update_time", true);
		wrapper2.gt("update_time", entity.getUpdateTime());
		Article nextArticle = articleService.selectOne(wrapper2);
		
		if (lastArticle != null) {
			map.put("lastArticle", lastArticle);
		}
		
		// 下一篇
		if (nextArticle != null) {
			map.put("nextArticle", nextArticle);
		}
		//相关动态
		Page<Article> page = new Page<>(1,4);
		EntityWrapper<Article> xwrapper = new EntityWrapper<>();
		xwrapper.eq("category_id", entity.getCategoryId());
		xwrapper.ne("id", articleId);
		xwrapper.orderBy("update_time", false);
		page = articleService.selectPage(page, xwrapper);
		map.put("relarticles", page.getRecords());
		return "newsdetail";
	}
}

