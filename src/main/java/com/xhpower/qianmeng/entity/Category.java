package com.xhpower.qianmeng.entity;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2018-07-27
 */
@TableName("category")
public class Category extends Model<Category> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 类型名称
     */
    @TableField("category_name")
    private String categoryName;
    /**
     * 所属栏目（article-新闻动态 case-精品案例 job-人才招聘 personnel-团队展示 等
     */
    @TableField("category_column")
    private String categoryColumn;
    /**
     * 排序 值越大越靠前
     */
    private Integer sort;
    /**
     * 状态 0-无效 1-有效
     */
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryColumn() {
        return categoryColumn;
    }

    public void setCategoryColumn(String categoryColumn) {
        this.categoryColumn = categoryColumn;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Category{" +
        ", id=" + id +
        ", categoryName=" + categoryName +
        ", categoryColumn=" + categoryColumn +
        ", sort=" + sort +
        ", status=" + status +
        "}";
    }
}
