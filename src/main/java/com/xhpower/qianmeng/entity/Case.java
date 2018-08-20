package com.xhpower.qianmeng.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;

import javax.validation.constraints.Digits;
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
 * @author xc
 * @since 2018-07-27
 */
@TableName("cases")
public class Case extends Model<Case> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 所属分类
     */
    @TableField("category_id")
    private Integer categoryId;
    /**
     * 案例名称
     */
    @TableField("case_name")
    @NotBlank(message = "案例名称不能为空!")
   	@Size(max=30,message="案例名称不能超过30个字")
    private String caseName;
    /**
     * 案例图片
     */
    @TableField("case_image")
    private String caseImage;
    /**
     * 项目地点
     */
    @TableField("case_address")
 	@Size(max=50,message="项目地点不能超过50个字")
    private String caseAddress;
    /**
     * 业主单位
     */
    @TableField("case_company")
	@Size(max=50,message="业主单位不能超过50个字")
    private String caseCompany;
    /**
     * 项目规模
     */
    @TableField("case_scale")
    @Size(max=30,message="项目规模不能超过30个字")
    private String caseScale;
    /**
     * 施工时间
     */
    @TableField("case_date")
    @Size(max=30,message="施工时间不能超过30个字")
    private String caseDate;
    /**
     * 案列简介
     */
    @TableField("case_intro")
    private String caseIntro;
    /**
     * 参与人员；多人英文逗号,分隔
     */
    @TableField("personnel_ids")
    private String personnelIds;
    /**
     * 排序 值越大越靠前
     */
    @Digits(message="排序只能是3位数字", fraction = 0, integer = 3)
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
	/**
     * 个人名称
     */
	@TableField(exist=false)
	private String name;


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

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseImage() {
        return caseImage;
    }

    public void setCaseImage(String caseImage) {
        this.caseImage = caseImage;
    }

    public String getCaseAddress() {
        return caseAddress;
    }

    public void setCaseAddress(String caseAddress) {
        this.caseAddress = caseAddress;
    }

    public String getCaseCompany() {
        return caseCompany;
    }

    public void setCaseCompany(String caseCompany) {
        this.caseCompany = caseCompany;
    }

    public String getCaseScale() {
        return caseScale;
    }

    public void setCaseScale(String caseScale) {
        this.caseScale = caseScale;
    }

    public String getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(String caseDate) {
        this.caseDate = caseDate;
    }

    public String getCaseIntro() {
        return caseIntro;
    }

    public void setCaseIntro(String caseIntro) {
        this.caseIntro = caseIntro;
    }

    public String getPersonnelIds() {
        return personnelIds;
    }

    public void setPersonnelIds(String personnelIds) {
        this.personnelIds = personnelIds;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Case{" +
        ", id=" + id +
        ", categoryId=" + categoryId +
        ", caseName=" + caseName +
        ", caseImage=" + caseImage +
        ", caseAddress=" + caseAddress +
        ", caseCompany=" + caseCompany +
        ", caseScale=" + caseScale +
        ", caseDate=" + caseDate +
        ", caseIntro=" + caseIntro +
        ", personnelIds=" + personnelIds +
        ", sort=" + sort +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", categoryName=" + categoryName +
        ", name=" + name +
        "}";
    }
}
