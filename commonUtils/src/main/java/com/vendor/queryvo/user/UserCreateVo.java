package com.vendor.queryvo.user;



import com.vendor.bean.user.UserRoleMemberships;

import java.util.Date;
import java.util.List;

public class UserCreateVo {


    private Long id;

    private String name;

    private String email;

    private String sex;

    private String mobile;

    private String address;

    private Integer age;

    private String description;

    private String status;

    private String type;

    private String headimgHref;

    private Long accountId;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    private Long ownerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeadimgHref() {
        return headimgHref;
    }

    public void setHeadimgHref(String headimgHref) {
        this.headimgHref = headimgHref;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }


    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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

    private Long departmentId;

    private Date createdDt;

    private Date updatedDt;


    public List<UserRoleMemberships> getUserRoleMemberShips() {
        return userRoleMemberShips;
    }

    public void setUserRoleMemberShips(List<UserRoleMemberships> userRoleMemberShips) {
        this.userRoleMemberShips = userRoleMemberShips;
    }


    private List<UserRoleMemberships> userRoleMemberShips ;
}
