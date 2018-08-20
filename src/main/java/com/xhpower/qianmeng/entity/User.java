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
 * @since 2018-07-25
 */
@TableName("user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码MD5
     */
    private String password;
    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 1-管理员 2-编辑员
     */
    @TableField("role_id")
    private Integer roleId;
    /**
     * 0-无效 1-有效
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("create_tm")
    private Date createTm;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTm() {
        return createTm;
    }

    public void setCreateTm(Date createTm) {
        this.createTm = createTm;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
        ", id=" + id +
        ", username=" + username +
        ", password=" + password +
        ", realName=" + realName +
        ", phone=" + phone +
        ", roleId=" + roleId +
        ", status=" + status +
        ", createTm=" + createTm +
        "}";
    }
}
