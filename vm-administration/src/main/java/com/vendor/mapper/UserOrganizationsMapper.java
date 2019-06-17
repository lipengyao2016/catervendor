package com.vendor.mapper;

import com.vendor.bean.user.UserOrganizations;
import com.vendor.bean.user.UserOrganizationsCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserOrganizationsMapper {
    int countByExample(UserOrganizationsCriteria example);

    int deleteByExample(UserOrganizationsCriteria example);

    int insert(UserOrganizations record);

    int insertSelective(UserOrganizations record);

    List<UserOrganizations> selectByExample(UserOrganizationsCriteria example);

    int updateByExampleSelective(@Param("record") UserOrganizations record, @Param("example") UserOrganizationsCriteria example);

    int updateByExample(@Param("record") UserOrganizations record, @Param("example") UserOrganizationsCriteria example);
}