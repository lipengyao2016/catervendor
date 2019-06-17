package com.vendor.service.impl;

import com.vendor.entity.Roles;
import com.vendor.queryvo.RoleQueryVo;
import com.vendor.mapper.RolesMapper;
import com.vendor.service.BaseMybaticsPlusServiceImpl;
import com.vendor.service.IRolesService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author lpy
 * @since 2019-06-12
 */
@Service
//public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements IRolesService
public class RolesServiceImpl extends BaseMybaticsPlusServiceImpl<RolesMapper, Roles,RoleQueryVo>
        implements IRolesService{

}
