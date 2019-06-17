package com.vendor.mapper;

import com.vendor.bean.user.UserRoleMemberships;
import com.vendor.bean.user.UserRoleMembershipsCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMembershipsMapper {
    int countByExample(UserRoleMembershipsCriteria example);

    int deleteByExample(UserRoleMembershipsCriteria example);

    int insert(UserRoleMemberships record);

    int insertSelective(UserRoleMemberships record);

    List<UserRoleMemberships> selectByExample(UserRoleMembershipsCriteria example);

    int updateByExampleSelective(@Param("record") UserRoleMemberships record, @Param("example") UserRoleMembershipsCriteria example);

    int updateByExample(@Param("record") UserRoleMemberships record, @Param("example") UserRoleMembershipsCriteria example);
	
	int batchInsert(List<UserRoleMemberships> record);
}