package com.vendor.vo;

import com.vendor.model.Role;

import java.util.ArrayList;
import java.util.List;

public class UserLoginInfoVo {
    private String userLoginId;
    private String ifSuperAdmin;
    private String ifAdmin;
    private Long merchantId;
    private Long id;
    private String name;
    private String phone;
    private String token;
    private String shaHexPwd;
    private String status;
    private String currentPassword;
    private String imageString;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    private List<Role> roleList = new ArrayList<>();

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShaHexPwd() {
        return shaHexPwd;
    }

    public void setShaHexPwd(String shaHexPwd) {
        this.shaHexPwd = shaHexPwd;
    }

    public String getIfAdmin() {
        return ifAdmin;
    }

    public void setIfAdmin(String ifAdmin) {
        this.ifAdmin = ifAdmin;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getIfSuperAdmin() {
        return ifSuperAdmin;
    }

    public void setIfSuperAdmin(String ifSuperAdmin) {
        this.ifSuperAdmin = ifSuperAdmin;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
