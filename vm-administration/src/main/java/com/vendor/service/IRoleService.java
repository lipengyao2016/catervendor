package com.vendor.service;

import com.vendor.admin.Role;
import com.vendor.admin.RolePermission;
import com.vendor.admin.RoleQueryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lpy
 * @since 2019-06-20
 */
public interface IRoleService extends IBasePlusService<Role,RoleQueryVo> {

    public Long create(RolePermission obj);
}
