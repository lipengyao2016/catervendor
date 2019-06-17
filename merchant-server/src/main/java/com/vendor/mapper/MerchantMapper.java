package com.vendor.mapper;

import com.vendor.bean.merchant.Merchant;
import com.vendor.bean.merchant.MerchantCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchantMapper {
    int countByExample(MerchantCriteria example);

    int deleteByExample(MerchantCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(Merchant record);

    int insertSelective(Merchant record);

    List<Merchant> selectByExample(MerchantCriteria example);

    Merchant selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Merchant record, @Param("example") MerchantCriteria example);

    int updateByExample(@Param("record") Merchant record, @Param("example") MerchantCriteria example);

    int updateByPrimaryKeySelective(Merchant record);

    int updateByPrimaryKey(Merchant record);
}