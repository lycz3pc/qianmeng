package com.xhpower.qianmeng.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * @author xc
 * @since 2018-07-30
 */
@TableName("personnel")
public class Personnel extends Model<Personnel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 所属分类
     */
    @TableField("category_id")
    private Integer categoryId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 职位
     */
    private String position;
    /**
     * 照片
     */
    private String picture;
    /**
     * 个人简介
     */
    private String intro;
    /**
     * 排序，值越大越靠前
     */
    private Integer sort;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        return "Personnel{" +
        ", id=" + id +
        ", categoryId=" + categoryId +
        ", name=" + name +
        ", position=" + position +
        ", picture=" + picture +
        ", intro=" + intro +
        ", sort=" + sort +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", categoryName=" + categoryName +
        "}";
    }
}
