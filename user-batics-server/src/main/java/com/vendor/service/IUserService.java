package com.vendor.service;


import com.vendor.bean.user.Users;
import com.vendor.entity.ListResponse;
import com.vendor.bean.user.UserRoleOrgs;
import com.vendor.queryvo.user.UserCreateVo;
import com.vendor.queryvo.user.UserQueryVo;
import com.vendor.bean.user.UserRoleOrgQueryVo;


public interface IUserService extends IBaseService<Users,UserQueryVo> {

    public Users create(UserCreateVo userCreateVo);

    public Users update(UserCreateVo userCreateVo);


    public ListResponse<UserRoleOrgs> getUserRole(UserRoleOrgQueryVo userRoleOrgQueryVo, Integer page, Integer rows);

    public String betchAddUser();

}
