package com.vendor.vo;

public class UserLoginInfoVo {
    private String userLoginId;
    private String ifSuperAdmin;

    public String getIfSuperAdmin() {
        return ifSuperAdmin;
    }

    public void setIfSuperAdmin(String ifSuperAdmin) {
        this.ifSuperAdmin = ifSuperAdmin;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }
}
