package com.vendor.mapper;

import com.vendor.bean.user.Departments;
import com.vendor.bean.user.DepartmentsCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepartmentsMapper {
    int countByExample(DepartmentsCriteria example);

    int deleteByExample(DepartmentsCriteria example);

    int insert(Departments record);

    int insertSelective(Departments record);

    List<Departments> selectByExample(DepartmentsCriteria example);

    int updateByExampleSelective(@Param("record") Departments record, @Param("example") DepartmentsCriteria example);

    int updateByExample(@Param("record") Departments record, @Param("example") DepartmentsCriteria example);
}