package com.vendor.queryvo.admin;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class UserRoleQueryVo {


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(String updatedDt) {
        this.updatedDt = updatedDt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ApiModelProperty(value = "角色编码")
    private String roleId;
    @ApiModelProperty(value = "用户编码")
    private String userId;

    @ApiModelProperty(value = "创建时间")
    private String createdDt;

    @ApiModelProperty(value = "更新时间")
    private String updatedDt;

    private String id;
}
