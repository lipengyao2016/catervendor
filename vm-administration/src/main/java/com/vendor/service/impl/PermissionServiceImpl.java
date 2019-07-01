package com.vendor.service.impl;

import com.vendor.service.BaseMybaticsPlusServiceImpl;
import com.vendor.admin.Permission;
import com.vendor.mapper.PermissionMapper;
import com.vendor.queryvo.admin.PermissionQueryVo;
import com.vendor.service.IPermissionService;
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
public class PermissionServiceImpl
        extends BaseMybaticsPlusServiceImpl<PermissionMapper, Permission,PermissionQueryVo>
        implements IPermissionService {

}
