package com.vendor.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 部门。
 * </p>
 *
 * @author lpy
 * @since 2019-06-14
 */
@TableName(value = "departments")
public class Departments  {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpLevelDepartmentUUID() {
        return upLevelDepartmentUUID;
    }

    public void setUpLevelDepartmentUUID(String upLevelDepartmentUUID) {
        this.upLevelDepartmentUUID = upLevelDepartmentUUID;
    }

    public String getUserOrganizationUUID() {
        return userOrganizationUUID;
    }

    public void setUserOrganizationUUID(String userOrganizationUUID) {
        this.userOrganizationUUID = userOrganizationUUID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @TableId(value = "id" ,type = IdType.AUTO)
    private Integer id;


    private String uuid;

    /**
     * 名称。
     */
    private String name;

    /**
     * 描述。
     */
    private String description;

    /**
     * 上级部门UUID.
     */
    @TableField("upLevelDepartmentUUID")
    private String upLevelDepartmentUUID;

    /**
     * 所属的用户组织UUID.
     */
    @TableField("userOrganizationUUID")
    private String userOrganizationUUID;

    private String status;

    //出参时间格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //入参时，请求报文只需要传入yyyymmddhhmmss字符串进来，则自动转换为Date类型数据
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("createdAt")
    private Date createdAt;

    //出参时间格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //入参时，请求报文只需要传入yyyymmddhhmmss字符串进来，则自动转换为Date类型数据
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("modifiedAt")
    private Date modifiedAt;

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;


}
