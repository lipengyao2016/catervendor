package com.vendor.bean.merchandise;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品。
 * </p>
 *
 * @author lpy
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Merchandise对象", description="商品。")
public class Merchandise implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品名称。")
    private String name;

    @ApiModelProperty(value = "商品编码。")
    private String no;

    @ApiModelProperty(value = "规格。")
    private String spec;

    @ApiModelProperty(value = "所属商户编码。")
    private Long merchantId;

    @ApiModelProperty(value = "分类ID.")
    private Long categoryId;

    @ApiModelProperty(value = "所属的品牌ID.")
    private Long brandId;

    @ApiModelProperty(value = "成本价。单位为分。")
    private Integer costPrice;

    @ApiModelProperty(value = "销售价。单位为分。")
    private Integer salePrice;

    @ApiModelProperty(value = "最低价。单位为分。")
    private Integer lowestPrice;

    @ApiModelProperty(value = "最高价。单位为分。")
    private Integer highestPrice;

    @ApiModelProperty(value = "商品描述。")
    private String description;

    @ApiModelProperty(value = "商品重量，单位为g.")
    private Integer weight;

    @ApiModelProperty(value = "偏移重量。单位为g.")
    private Integer offsetWeight;

    @ApiModelProperty(value = "计价方式，0为按件，1为按g")
    private Integer chargeMode;

    @ApiModelProperty(value = "最佳货道库存。")
    private Integer bestStoreQuatity;

    @ApiModelProperty(value = "退款截止时间。")
    private Integer refundDelayHour;

    @ApiModelProperty(value = "横屏图片。")
    private String landscapeImageUrl;

    @ApiModelProperty(value = "竖屏图片。")
    private String verticalImageUrl;

    @ApiModelProperty(value = "Y:启用，N:禁用。")
    private String status;

    //出参时间格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //入参时，请求报文只需要传入yyyymmddhhmmss字符串进来，则自动转换为Date类型数据
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createdDt;

    //出参时间格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //入参时，请求报文只需要传入yyyymmddhhmmss字符串进来，则自动转换为Date类型数据
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date updatedDt;

    @ApiModelProperty(value = "创建人。")
    private String createdBy;

    @ApiModelProperty(value = "修改人。")
    private String updatedBy;

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

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Integer lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Integer getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Integer highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getOffsetWeight() {
        return offsetWeight;
    }

    public void setOffsetWeight(Integer offsetWeight) {
        this.offsetWeight = offsetWeight;
    }

    public Integer getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(Integer chargeMode) {
        this.chargeMode = chargeMode;
    }

    public Integer getBestStoreQuatity() {
        return bestStoreQuatity;
    }

    public void setBestStoreQuatity(Integer bestStoreQuatity) {
        this.bestStoreQuatity = bestStoreQuatity;
    }

    public Integer getRefundDelayHour() {
        return refundDelayHour;
    }

    public void setRefundDelayHour(Integer refundDelayHour) {
        this.refundDelayHour = refundDelayHour;
    }

    public String getLandscapeImageUrl() {
        return landscapeImageUrl;
    }

    public void setLandscapeImageUrl(String landscapeImageUrl) {
        this.landscapeImageUrl = landscapeImageUrl;
    }

    public String getVerticalImageUrl() {
        return verticalImageUrl;
    }

    public void setVerticalImageUrl(String verticalImageUrl) {
        this.verticalImageUrl = verticalImageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    private Long id;

}
