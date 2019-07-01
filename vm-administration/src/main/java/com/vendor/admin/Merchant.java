package com.vendor.admin;


import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lpy
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Merchant对象", description="")
public class Merchant implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "5:平台商，6：园区，8：供应商店铺。")
    private Integer type;

    @ApiModelProperty(value = "商户名称(简称)")
    private String name;

    @ApiModelProperty(value = "商户编号")
    private String number;

    @ApiModelProperty(value = "Y:启用，N:禁用。")
    private String status;

    @ApiModelProperty(value = "上级商户ID")
    private Long upMerchantId;

    @ApiModelProperty(value = "公司全称")
    private String companyName;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市。")
    private String city;

    @ApiModelProperty(value = "区。")
    private String district;

    @ApiModelProperty(value = "详细地址。")
    private String street;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "联系人手机号")
    private String contactMobile;

    @ApiModelProperty(value = "企业简介")
    private String summary;

    @ApiModelProperty(value = "营业执照")
    private String businessLicenceHref;

    private Date createdDt;

    private Date updatedDt;

    @ApiModelProperty(value = "创建人。")
    private String createdBy;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUpMerchantId() {
        return upMerchantId;
    }

    public void setUpMerchantId(Long upMerchantId) {
        this.upMerchantId = upMerchantId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBusinessLicenceHref() {
        return businessLicenceHref;
    }

    public void setBusinessLicenceHref(String businessLicenceHref) {
        this.businessLicenceHref = businessLicenceHref;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ApiModelProperty(value = "更新人")
    private String updatedBy;

    private Long id;

}
