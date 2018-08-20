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
 * @since 2018-07-27
 */
@TableName("job")
public class Job extends Model<Job> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 职位分类
     */
    @TableField("category_id")
    private Integer categoryId;
    
    /**
     * 职位分类名称
     */
    @TableField(exist=false)
	private String categoryName;
    
	/**
     * 职位名称
     */
    @TableField("job_name")
    private String jobName;
    /**
     * 工作城市
     */
    @TableField("job_city")
    private String jobCity;
    /**
     * 岗位职责
     */
    @TableField("job_detail")
    private String jobDetail;
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
    
    public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobCity() {
        return jobCity;
    }

    public void setJobCity(String jobCity) {
        this.jobCity = jobCity;
    }

    public String getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(String jobDetail) {
        this.jobDetail = jobDetail;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Job{" +
        ", id=" + id +
        ", categoryId=" + categoryId +
        ", jobName=" + jobName +
        ", jobCity=" + jobCity +
        ", jobDetail=" + jobDetail +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
