package com.vendor.service;


import com.vendor.bean.merchant.Merchant;
import com.vendor.bean.merchant.MerchantNumber;
import com.vendor.queryvo.merchant.MerchantQueryVo;


public interface IMerchantService {

    public IBaseService<Merchant, MerchantQueryVo> getBaseService();

    public Merchant create(Merchant obj);



}
