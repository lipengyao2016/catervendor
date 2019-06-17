package com.vendor.mapper;

import com.vendor.bean.merchant.MerchantNumber;
import com.vendor.bean.merchant.MerchantNumberCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchantNumberMapper {
    int countByExample(MerchantNumberCriteria example);

    int deleteByExample(MerchantNumberCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(MerchantNumber record);

    int insertSelective(MerchantNumber record);

    List<MerchantNumber> selectByExample(MerchantNumberCriteria example);

    MerchantNumber selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MerchantNumber record, @Param("example") MerchantNumberCriteria example);

    int updateByExample(@Param("record") MerchantNumber record, @Param("example") MerchantNumberCriteria example);

    int updateByPrimaryKeySelective(MerchantNumber record);

    int updateByPrimaryKey(MerchantNumber record);

    int batchInsert(List<MerchantNumber> merchantNumbers);

    MerchantNumber selectUnusedByStatus(String status);
}