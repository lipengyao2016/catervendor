package com.vendor.bean.merchant;

import java.util.Date;

public class MerchantNumber {
    private Long id;

    private String number;

    private String status;

    private Date createdDt;

    private Date updatedDt;

    private Short merchantType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public Short getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(Short merchantType) {
        this.merchantType = merchantType;
    }
}