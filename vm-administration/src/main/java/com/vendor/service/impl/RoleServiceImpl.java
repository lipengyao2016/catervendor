package com.vendor.service.impl;

import com.vendor.service.BaseMybaticsPlusServiceImpl;
import com.vendor.utils.BeanHelper;
import com.vendor.admin.Role;
import com.vendor.admin.RolePermission;
import com.vendor.mapper.RoleMapper;
import com.vendor.admin.RoleQueryVo;
import com.vendor.service.IPermissionService;
import com.vendor.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lpy
 * @since 2019-06-20
 */
@Service
public class RoleServiceImpl
    extends BaseMybaticsPlusServiceImpl<RoleMapper, Role,RoleQueryVo>
        implements IRoleService {

    @Autowired
    private IPermissionService permissionService;

    @Override
    public Long create(RolePermission obj) {
        Role role = new Role();
        BeanHelper.copyPropertiesExcludeAttr(obj,role,new String[]{"permissions"});
        Long roleId = super.create(role);
        if(obj.getPermissions().size() > 0 )
        {
            obj.getPermissions().forEach(permission -> {
                permission.setRoleId(roleId);
            });
            permissionService.batchInsert(obj.getPermissions());
        }
        return roleId;
    }
}
