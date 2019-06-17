package com.vendor.user_mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vendor.entity.Departments;
import com.vendor.entity.UserRoles;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 部门。 Mapper 接口
 * </p>
 *
 * @author lpy
 * @since 2019-06-14
 */
public interface UserRoleMapper extends BaseMapper<UserRoles> {


    //@Select("select `us`.`name` AS `name`,`us`.`mobile` AS `mobile`,`us`.`uuid` AS `userUUID`,`uo`.`ownerUUID` AS `ownerUUID`,`ur`.`roleUuid` AS `roleUUID` from ((`vd_users` `us` join `userorganizations` `uo` on((`us`.`userOrganizationUUID` = `uo`.`uuid`))) join `userrolememberships` `ur` on((`us`.`uuid` = `ur`.`userUuid`))) ${ew.customSqlSegment}")
   // List<UserRoles> getUserRoles(Page<UserRoles> var1, @Param(Constants.WRAPPER) Wrapper wrapper);


}
