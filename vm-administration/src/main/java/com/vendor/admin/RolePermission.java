package com.vendor.admin;

import java.util.List;

public class RolePermission extends Role {

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    List<Permission>  permissions;


}
