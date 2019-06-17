package com.vendor.mapper;

import com.vendor.bean.user.UserRoleMemberships;
import com.vendor.bean.user.UserRoleMembershipsCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMembershipsMapper {
    int countByExample(UserRoleMembershipsCriteria example);

    int deleteByExample(UserRoleMembershipsCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(UserRoleMemberships record);

    int insertSelective(UserRoleMemberships record);

    List<UserRoleMemberships> selectByExample(UserRoleMembershipsCriteria example);

    UserRoleMemberships selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserRoleMemberships record, @Param("example") UserRoleMembershipsCriteria example);

    int updateByExample(@Param("record") UserRoleMemberships record, @Param("example") UserRoleMembershipsCriteria example);

    int updateByPrimaryKeySelective(UserRoleMemberships record);

    int updateByPrimaryKey(UserRoleMemberships record);
}