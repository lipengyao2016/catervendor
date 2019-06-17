package com.vendor.mapper;

import com.vendor.bean.user.UserRoleOrgQueryVo;
import com.vendor.bean.user.UserRoleOrgs;
import com.vendor.bean.user.Users;
import com.vendor.bean.user.UsersCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersMapper {
    int countByExample(UsersCriteria example);

    int deleteByExample(UsersCriteria example);

    int insert(Users record);

    int insertSelective(Users record);

    List<Users> selectByExample(UsersCriteria example);

    int updateByExampleSelective(@Param("record") Users record, @Param("example") UsersCriteria example);

    int updateByExample(@Param("record") Users record, @Param("example") UsersCriteria example);

    List<UserRoleOrgs> getUserRole(UserRoleOrgQueryVo userRoleOrgQueryVo);
}