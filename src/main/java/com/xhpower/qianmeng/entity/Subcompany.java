package com.xhpower.qianmeng.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xc
 * @since 2018-08-01
 */
@TableName("subcompany")
public class Subcompany extends Model<Subcompany> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 子公司名称
     */
    private String name;
    /**
     * 图片
     */
    private String picture;
    /**
     * 所在省份
     */
    private String province;
    /**
     * 简介
     */
    private String summary;
    /**
     * 详情
     */
    private String detail;
    /**
     * 排序，值越大越靠前
     */
    private Integer sort;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Subcompany{" +
        ", id=" + id +
        ", name=" + name +
        ", picture=" + picture +
        ", province=" + province +
        ", summary=" + summary +
        ", detail=" + detail +
        ", sort=" + sort +
        "}";
    }
}
