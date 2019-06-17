package com.vendor.user_mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vendor.entity.Departments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface DepartmentsMapper extends BaseMapper<Departments> {


    //@Select("select `us`.`name` AS `name`,`us`.`mobile` AS `mobile`,`us`.`uuid` AS `userUUID`,`uo`.`ownerUUID` AS `ownerUUID`,`ur`.`roleUuid` AS `roleUUID` from ((`vd_users` `us` join `userorganizations` `uo` on((`us`.`userOrganizationUUID` = `uo`.`uuid`))) join `userrolememberships` `ur` on((`us`.`uuid` = `ur`.`userUuid`))) ${ew.customSqlSegment}")
   // Page<UserRoles> getUserRoles(Page<UserRoles> var1, @Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("SELECT  us.NAME as name,us.mobile as mobile ,us.id  as id ,uo.owner_id as ownerId,ur.role_id as roleId FROM(( vd_user us JOIN user_organization uo ON ( ( us.user_organization_id = uo.id ) ) )JOIN user_role ur ON ( ( us.id = ur.user_id ) ) ) ${ew.customSqlSegment}")
    List<UserRoles> getUserRoles(Page<UserRoles> var1, @Param(Constants.WRAPPER) Wrapper wrapper);

    //${ew.customSqlSegment}
    //@Param(Constants.WRAPPER) Wrapper wrapper
}
