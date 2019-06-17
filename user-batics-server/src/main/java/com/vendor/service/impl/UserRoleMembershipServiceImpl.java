package com.vendor.service.impl;


import com.vendor.bean.user.UserRoleMemberships;
import com.vendor.bean.user.UserRoleMembershipsCriteria;
import com.vendor.entity.ListResponse;
import com.vendor.mapper.UserRoleMembershipsMapper;
import com.vendor.service.BaseMyBaticsServiceImpl;
import com.vendor.service.IBaseService;
import com.vendor.service.IUserRoleMembershipService;
import com.vendor.utils.DBEntityUtils;
import com.vendor.utils.DataNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleMembershipServiceImpl implements IUserRoleMembershipService {

    private Log log = LogFactory.getLog(UserRoleMembershipServiceImpl.class);

    private UserRoleMembershipsMapper UserRoleMembershipDao;
    private IBaseService<UserRoleMemberships,UserRoleMemberships> baseService;

   // @Autowired
    private SqlSessionFactory sqlSessionFactory;


    private DataSourceProperties dataSourceProperties;

    public UserRoleMembershipServiceImpl()
    {
        System.out.println("UserRoleMembershipServiceImpl init");
    }

    @Autowired(required = true)
    public UserRoleMembershipServiceImpl(UserRoleMembershipsMapper UserRoleMembershipDao,
                                         SqlSessionFactory sqlSessionFactory,
                                         DataSourceProperties dataSourceProperties,
                                         DBEntityUtils dbEntityUtils)
    {
        this.UserRoleMembershipDao = UserRoleMembershipDao;
        this.baseService = new BaseMyBaticsServiceImpl<>(this.UserRoleMembershipDao,UserRoleMemberships.class
                ,UserRoleMembershipsCriteria.class,dbEntityUtils);
        this.sqlSessionFactory = sqlSessionFactory;
    }


    @Override
    public UserRoleMemberships create(UserRoleMemberships obj) {
         return this.baseService.create(obj);
    }

    @Override
    public UserRoleMemberships get(Long id) {
         return this.baseService.get(id);
    }

    @Override
    public ListResponse<UserRoleMemberships> list(UserRoleMemberships queryObj , Integer page, Integer rows) {
        return this.baseService.list(queryObj,page,rows);
    }

    @Override
    public UserRoleMemberships update(Long id, UserRoleMemberships updateObj) {
        return this.baseService.update(id,updateObj);
    }

    @Override
    public UserRoleMemberships delete(Long id) {
        return this.baseService.delete(id);
    }

    @Override
    public UserRoleMemberships update(UserRoleMemberships updateObj) throws DataNotFoundException {
        return null;
    }

    @Override
    public int batchInsert(List<UserRoleMemberships> record) {
        return this.baseService.batchInsert(record);
    }

    @Override
    public int batchDelete(List<Long> ids) {
        return this.baseService.batchDelete(ids);
    }

    @Override
    public UserRoleMemberships batchUpdate(List<Long> ids, UserRoleMemberships updateObj) throws DataNotFoundException {
        return this.baseService.batchUpdate(ids,updateObj);
    }

    @Override
    public List<UserRoleMemberships> getUserRoles(Long userID) {
        UserRoleMemberships queryUserRole = new UserRoleMemberships();
        queryUserRole.setUserId(userID);
        ListResponse<UserRoleMemberships> userRoleMembershipsList = this.list(queryUserRole,1,10);
        return  userRoleMembershipsList.getItems();
    }


}
