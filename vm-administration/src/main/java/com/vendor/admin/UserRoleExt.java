package com.vendor.admin;

import java.util.List;

public class UserRoleExt extends User {

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }
    private List<Long> roles;
}
