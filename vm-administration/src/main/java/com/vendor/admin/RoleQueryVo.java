package com.vendor.admin;

import io.swagger.annotations.ApiModelProperty;

public class RoleQueryVo {

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "创建时间")
    private String createdDt;

    @ApiModelProperty(value = "修改人")
    private String updatedBy;

    @ApiModelProperty(value = "修改时间")
    private String updatedDt;

    @ApiModelProperty(value = "商户编码")
    private String merchantId;

    @ApiModelProperty(value = "是否是管理员。1为是，0为否。")
    private String isAdmin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
}
