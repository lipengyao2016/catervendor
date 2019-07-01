package com.vendor.service;

import com.vendor.admin.User;
import com.vendor.admin.UserRoleExt;
import com.vendor.queryvo.admin.UserQueryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lpy
 * @since 2019-06-20
 */
public interface IUserService extends IBasePlusService<User,UserQueryVo> {

    public Long create(UserRoleExt obj);

    public UserRoleExt login(User obj);
}
