package com.vendor.service;

import com.vendor.admin.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vendor.queryvo.admin.UserRoleQueryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lpy
 * @since 2019-06-20
 */
public interface IUserRoleService extends IBasePlusService<UserRole,UserRoleQueryVo> {

    public List<Long> getRoleIDsByUserId(Long userId);
}
