package com.vendor.mapper;

import com.vendor.bean.user.UserRoleViews;
import com.vendor.bean.user.UserRoleViewsCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleViewsMapper {
    int countByExample(UserRoleViewsCriteria example);

    int deleteByExample(UserRoleViewsCriteria example);

    int insert(UserRoleViews record);

    int insertSelective(UserRoleViews record);

    List<UserRoleViews> selectByExample(UserRoleViewsCriteria example);

    int updateByExampleSelective(@Param("record") UserRoleViews record, @Param("example") UserRoleViewsCriteria example);

    int updateByExample(@Param("record") UserRoleViews record, @Param("example") UserRoleViewsCriteria example);
}