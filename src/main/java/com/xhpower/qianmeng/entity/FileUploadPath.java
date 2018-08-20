package com.xhpower.qianmeng.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadPath {

	/**
	 * 图片上传根路径
	 */
	private String rootPath;
	
	/**
	 * 文章图片存放路径
	 */
	private String articleImagesPath;
	
	/**
	 * 工程案例图片存放路径
	 */
	private String caseImagesPath;
	
	/**
	 * 团队成员图片存放路径
	 */
	private String personnelImagesPath;
	
	/**
	 * 分公司图片存放路径
	 */
	private String subcompanyImagesPath;

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	
	public String getArticleImagesPath() {
		return articleImagesPath;
	}

	public void setArticleImagesPath(String articleImagesPath) {
		this.articleImagesPath = articleImagesPath;
	}

	public String getCaseImagesPath() {
		return caseImagesPath;
	}

	public void setCaseImagesPath(String caseImagesPath) {
		this.caseImagesPath = caseImagesPath;
	}

	public String getPersonnelImagesPath() {
		return personnelImagesPath;
	}

	public void setPersonnelImagesPath(String personnelImagesPath) {
		this.personnelImagesPath = personnelImagesPath;
	}
	
	public String getSubcompanyImagesPath() {
		return subcompanyImagesPath;
	}

	public void setSubcompanyImagesPath(String subcompanyImagesPath) {
		this.subcompanyImagesPath = subcompanyImagesPath;
	}
}
