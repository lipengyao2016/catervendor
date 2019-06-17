package com.vendor.mapper;

import com.vendor.bean.role.Roles;
import com.vendor.bean.role.RolesCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolesMapper {
    int countByExample(RolesCriteria example);

    int deleteByExample(RolesCriteria example);

    int insert(Roles record);

    int insertSelective(Roles record);

    List<Roles> selectByExample(RolesCriteria example);

    int updateByExampleSelective(@Param("record") Roles record, @Param("example") RolesCriteria example);

    int updateByExample(@Param("record") Roles record, @Param("example") RolesCriteria example);
}