package com.vendor.service;


import com.vendor.bean.user.UserOrganizations;

public interface IUserOrganizationService extends IBaseService<UserOrganizations,UserOrganizations> {

   public UserOrganizations getOrCreateUserOrganization(Long ownerID);
}
