package com.vendor.service;


import com.vendor.bean.role.Roles;
import com.vendor.bean.role.RolesCriteria;
import com.vendor.mapper.RolesMapper;
import com.vendor.queryvo.RoleQueryVo;
import com.vendor.utils.DBEntityUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleBaticsServiceImpl {

    private Log log = LogFactory.getLog(RoleBaticsServiceImpl.class);

    private RolesMapper roleDao;
    private IBaseService<Roles,RoleQueryVo> baseService;

    public RoleBaticsServiceImpl()
    {
        System.out.println("RoleServiceImpl init");
    }


    @Autowired(required = true)
    public RoleBaticsServiceImpl(RolesMapper roleDao
            ,DBEntityUtils dbEntityUtils)
    {
        this.roleDao = roleDao;
        this.baseService = new BaseMyBaticsServiceImpl<>(this.roleDao,Roles.class
                ,RolesCriteria.class,dbEntityUtils);
    }

    public  IBaseService<Roles,RoleQueryVo> getBaseService()
    {
        return  this.baseService;
    }

}
