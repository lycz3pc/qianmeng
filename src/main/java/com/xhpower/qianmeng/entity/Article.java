package com.xhpower.qianmeng.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lyc
 * @since 2018-07-27
 */
@TableName("article")
public class Article extends Model<Article> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 所属分类
     */
    @TableField("category_id")
    private Integer categoryId;
    /**
     * 文章标题
     */
    @NotBlank(message = "标题不能为空!")
   	@Size(max=30,message="标题不能超过30个字")
    private String title;
    /**
     * 文章副标题
     */
   	@Size(max=50,message="副标题不能超过50个字")
    private String subtitle;
    /**
     * 文章摘要
     */
   	@Size(max=200,message="摘要不能超过200个字")
    private String summary;
    /**
     * 文章来源
     */
    private String source;
    /**
     * 封面图片
     */
    private String image;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章作者
     */
    private String author;
    /**
     * 创建人id
     */
    private Integer userid;
    /**
     * 状态 0-隐藏 1-显示
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    
    @TableField(exist=false)
	private String categoryName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getCategoryName() {
  		return categoryName;
  	}

  	public void setCategoryName(String categoryName) {
  		this.categoryName = categoryName;
  	}
  	
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Article{" +
        ", id=" + id +
        ", categoryId=" + categoryId +
        ", title=" + title +
        ", subtitle=" + subtitle +
        ", summary=" + summary +
        ", source=" + source +
        ", image=" + image +
        ", content=" + content +
        ", author=" + author +
        ", userid=" + userid +
        ", status=" + status +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", categoryName=" + categoryName +
        "}";
    }
}
