package com.vendor.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vendor.entity.Departments;
import com.vendor.entity.UserRoles;
import com.vendor.service.IDepartmentsService;
import com.vendor.user_mapper.DepartmentsMapper;
import com.vendor.user_mapper.UserRoleMapper;
import com.vendor.utils.DBEntityUtils;
import com.vendor.utils.GsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 部门。 前端控制器
 * </p>
 *
 * @author lpy
 * @since 2019-06-14
 */
@RestController
//@RequestMapping("/vendor/departments")
public class DepartmentsController {

    private Log log = LogFactory.getLog(DepartmentsController.class);
    //  @Autowired
    // departmentService departmentService;

    @Autowired
    IDepartmentsService departmentService;

    @Autowired
    DepartmentsMapper departmentDao;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    DBEntityUtils dbEntityUtils;

    @RequestMapping(value = "/api/{ver}/departments", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public Departments createDepartment(@PathVariable("ver") String version, 
                                        @RequestBody Departments department) {

        log.info("department:" + department.getUuid() + " name:"
                + department.getName() + ",version:" + version);


        dbEntityUtils.preCreate(department);
        departmentService.save(department);
        return department;
        //return  null;
    }

    @RequestMapping(value = "/api/{ver}/userRoles", method = {RequestMethod.GET})
    @ResponseBody
    public IPage<UserRoles> listUserRoles(@PathVariable("ver") String version,
                                                 UserRoles role
            , Integer page, Integer rows) {

        log.info(",version:" + version);
        log.info("role  :" + GsonUtils.ToJson(role, UserRoles.class));
        QueryWrapper<UserRoles> queryWrapper = new QueryWrapper<>();
        if(role.getRoleId() != null)
        {
            queryWrapper.eq("role_id",role.getRoleId());
        }
        Page<UserRoles> userRolesPage = new Page<UserRoles>(page, rows);
        List<UserRoles> t1 = departmentDao.getUserRoles(userRolesPage,queryWrapper);
        userRolesPage.setRecords(t1);
        return userRolesPage;
    }

}

