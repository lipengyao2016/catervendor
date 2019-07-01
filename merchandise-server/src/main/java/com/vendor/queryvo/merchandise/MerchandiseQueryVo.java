package com.vendor.queryvo.merchandise;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MerchandiseQueryVo {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getOffsetWeight() {
        return offsetWeight;
    }

    public void setOffsetWeight(String offsetWeight) {
        this.offsetWeight = offsetWeight;
    }

    public String getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(String chargeMode) {
        this.chargeMode = chargeMode;
    }

    public String getBestStoreQuatity() {
        return bestStoreQuatity;
    }

    public void setBestStoreQuatity(String bestStoreQuatity) {
        this.bestStoreQuatity = bestStoreQuatity;
    }

    public String getRefundDelayHour() {
        return refundDelayHour;
    }

    public void setRefundDelayHour(String refundDelayHour) {
        this.refundDelayHour = refundDelayHour;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;

    /**
     * 商品编码。
     */
    private String no;

    private String spec;

    /**
     * 所属商户编码。
     */
    private String merchantId;

    /**
     * 分类ID.
     */
    private String categoryId;

    /**
     * 所属的品牌ID.
     */
    private String brandId;

    /**
     * 成本价。单位为分。
     */
    private String costPrice;

    /**
     * 销售价。单位为分。
     */
    private String salePrice;

    /**
     * 最低价。单位为分。
     */
    private String lowestPrice;

    /**
     * 最高价。单位为分。
     */
    private String highestPrice;

    /**
     * 商品描述。
     */
    private String description;

    /**
     * 商品重量，单位为g.
     */
    private String weight;

    /**
     * 偏移重量。单位为g.
     */
    private String offsetWeight;

    /**
     * 计价方式，0为按件，1为按g
     */
    private String chargeMode;

    /**
     * 最佳货道库存。
     */
    private String bestStoreQuatity;

    /**
     * 退款截止时间。
     */
    private String refundDelayHour;


    /**
     * Y:启用，N:禁用。
     */
    private String status;


    private String createdDt;


    private String updatedDt;

    private String createdBy;

    private String updatedBy;

    private String id;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private String categoryName;
}
