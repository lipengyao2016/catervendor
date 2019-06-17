package com.vendor.queryvo.user;

import com.vendor.bean.role.Roles;

public class UserRoleCreateVo {


    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public UserCreateVo getUser() {
        return user;
    }

    public void setUser(UserCreateVo user) {
        this.user = user;
    }

    private  UserCreateVo user;
    private Roles role;
}
