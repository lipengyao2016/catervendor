package com.vendor.service;


import com.vendor.bean.merchant.MerchantNumber;
import com.vendor.bean.user.UserRoleOrgQueryVo;
import com.vendor.bean.user.UserRoleOrgs;
import com.vendor.bean.user.Users;
import com.vendor.entity.ListResponse;
import com.vendor.queryvo.user.UserCreateVo;
import com.vendor.queryvo.user.UserQueryVo;


public interface IMerchantNumberService  {

    public IBaseService<MerchantNumber, MerchantNumber> getBaseService();

    public void initNumber(Short merchantType, Integer numberCnt);

    public String getUnUsedMerchantNumber(Short merchantType);


}
