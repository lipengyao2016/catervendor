package com.vendor.service.impl;


import com.vendor.bean.merchant.Merchant;
import com.vendor.bean.merchant.MerchantCriteria;
import com.vendor.mapper.MerchantMapper;
import com.vendor.queryvo.merchant.MerchantQueryVo;
import com.vendor.service.BaseMyBaticsServiceImpl;
import com.vendor.service.IBaseService;
import com.vendor.service.IMerchantNumberService;
import com.vendor.service.IMerchantService;
import com.vendor.utils.DBEntityUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl implements IMerchantService{

    private Log log = LogFactory.getLog(MerchantServiceImpl.class);

    private MerchantMapper merchantDao;
    private IBaseService<Merchant, MerchantQueryVo> baseService;

    @Autowired
    private IMerchantNumberService merchantNumberService;

    public MerchantServiceImpl() {
        System.out.println("MerchantServiceImpl init");
    }


    @Autowired(required = true)
    public MerchantServiceImpl(MerchantMapper merchantDao
            , DBEntityUtils dbEntityUtils) {
        this.merchantDao = merchantDao;
        this.baseService = new BaseMyBaticsServiceImpl<>(this.merchantDao, Merchant.class
                , MerchantCriteria.class, dbEntityUtils);
    }


    public IBaseService<Merchant, MerchantQueryVo> getBaseService() {
        return this.baseService;
    }

    @Override
    public Merchant create(Merchant obj)
    {
        if(StringUtils.isEmpty(obj.getNumber()))
        {
            String merchantNumber = merchantNumberService.getUnUsedMerchantNumber(obj.getType());
            obj.setNumber(merchantNumber);
        }
        return this.baseService.create(obj);
    }


}
