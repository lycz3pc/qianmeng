package com.xhpower.qianmeng.controller;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.qianmeng.entity.Article;
import com.xhpower.qianmeng.entity.Category;
import com.xhpower.qianmeng.entity.FileUploadPath;
import com.xhpower.qianmeng.entity.User;
import com.xhpower.qianmeng.service.ArticleService;
import com.xhpower.qianmeng.service.CategoryService;
import com.xhpower.qianmeng.utils.Result;
import com.xhpower.qianmeng.utils.SystemCode;
import com.xhpower.qianmeng.utils.UploadUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lyc
 * @since 2018-07-27
 */
@RestController
@RequestMapping("/admin/article")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private FileUploadPath fileUploadPath;
	
	/**
	 * 文章列表查询
	 *
	 * @Title: list
	 * @param page
	 * @return Result 返回类型
	 * @author Liu Youcheng
	 * @date 2018年7月27日
	 */
	@RequestMapping("/list")
	public Result list(Page<Article> page) {
		EntityWrapper<Article> wrapper = new EntityWrapper<>();
		Map<String,Object> condition = page.getCondition();
		if(condition != null){
			String[] eqs = {"category_id","status"};
			for (String key : condition.keySet()) {
				if("title".equals(key))
					wrapper.like(key, condition.get(key).toString());
				if(Arrays.asList(eqs).contains(key))
					wrapper.eq(key, condition.get(key).toString());
			}
			condition.clear();
		}
		wrapper.orderBy("update_time", false);
		page = articleService.selectPage(page,wrapper);
		List<Article> artList = page.getRecords();
		for (Article article : artList) {
			Category category = categoryService.selectById(article.getCategoryId());
			article.setCategoryName(category.getCategoryName());
		}
		page.setRecords(artList);
		return Result.success().page(page);
		
	}
	
	/**
	 * 
	 * 文章保存
	 *
	 * @Title: save
	 * @param article
	 * @param bindingResult
	 * @param file
	 * @return Result 返回类型
	 * @author Liu Youcheng
	 * @date 2018年5月9日
	 */
	@RequestMapping("/save")
	public Result save(@Valid Article article, BindingResult bindingResult, MultipartFile file, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			ObjectError error = bindingResult.getAllErrors().get(0);
			return Result.error(SystemCode.VERIFICATION_FAILURE, error.getDefaultMessage());
		}
		if(file != null && !file.isEmpty()){
			String path = UploadUtil.uploadFile(fileUploadPath.getArticleImagesPath(), file, fileUploadPath.getRootPath());
			article.setImage(path);
		}
		if(StringUtils.isEmpty(article.getAuthor())){
			User user = (User) request.getSession().getAttribute("ADMIN_USER");
			article.setAuthor(user.getRealName());
		}
		return articleService.insertOrUpdate(article) ? Result.success() : Result.error();
	}
	
	
	/**
	 * 文章删除
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
			return Result.error("文章不存在");
		}
		return articleService.deleteById(id) ? Result.success() : Result.error();
	}
	
	/**
	 * 
	 * 上传文章内容媒体文件
	 *
	 * @Title: upload
	 * @param request
	 * @param dir
	 * @return Result 返回类型
	 * @author Lian Youjie
	 * @throws IOException
	 * @date 2018年7月1日
	 */
	@RequestMapping("/upload")
	public JSONObject upload(HttpServletRequest httpRequest, String action) throws IOException {
		JSONObject jsonObject = new JSONObject();
		if ("config".equals(action)) {
			InputStreamReader reader = new InputStreamReader(
					this.getClass().getClassLoader().getResourceAsStream("config.json"));
			BufferedReader bufferedReader = new BufferedReader(reader);
			StringBuffer input = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				input.append(line);
			}
			jsonObject = JSONObject.parseObject(input.toString());
			System.out.println(jsonObject+"test");
			return jsonObject;
		}
		jsonObject.put("state", "SUCCESS");
		jsonObject.put("info", "上传成功!");
		String imageExt = "gif,jpg,jpeg,png,bmp";
		MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpRequest;
		Iterator<String> item = request.getFileNames();
		while (item.hasNext()) {
			String fileName = (String) item.next();
			MultipartFile file = request.getFile(fileName);
			String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
					.toLowerCase();

			if (!"image".equals(action)) {
				jsonObject.put("state", "ERROR");
				jsonObject.put("info", "文件类型不允许上传!");
			}
			if (!Arrays.asList(imageExt.split(",")).contains(fileExt)) {
				jsonObject.put("state", "ERROR");
				jsonObject.put("info", action + "文件只允许" + imageExt + "格式。");
			}
			String uploadFolder = File.separator + "uploadcmsimages" + File.separator + "content" + File.separator
					+ action;

			String fileParh = UploadUtil.uploadFile(uploadFolder, file, fileUploadPath.getRootPath());
			jsonObject.put("url", fileParh);
		}
		return jsonObject;
	}
}

