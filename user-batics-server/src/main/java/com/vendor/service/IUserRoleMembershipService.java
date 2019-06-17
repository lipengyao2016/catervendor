package com.vendor.service;


import com.vendor.bean.user.UserRoleMemberships;

import java.util.List;


public interface IUserRoleMembershipService extends IBaseService<UserRoleMemberships,UserRoleMemberships> {

    public List<UserRoleMemberships> getUserRoles(Long userID);
}
