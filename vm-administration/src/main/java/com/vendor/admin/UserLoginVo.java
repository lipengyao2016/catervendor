package com.vendor.admin;

public class UserLoginVo  {

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public UserRoleExt getUserRoleExt() {
        return userRoleExt;
    }

    public void setUserRoleExt(UserRoleExt userRoleExt) {
        this.userRoleExt = userRoleExt;
    }

    private UserRoleExt userRoleExt;
}
