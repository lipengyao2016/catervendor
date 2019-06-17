package com.vendor.service.impl;


import com.vendor.config.DataSourceProperties;
import com.vendor.entity.ListResponse;
import com.vendor.mapper.UserOrganizationsMapper;
import com.vendor.bean.user.UserOrganizations;
import com.vendor.bean.user.UserOrganizationsCriteria;
import com.vendor.service.BaseMyBaticsServiceImpl;
import com.vendor.service.IBaseService;
import com.vendor.service.IUserOrganizationService;
import com.vendor.utils.DBEntityUtils;
import com.vendor.utils.DataNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserOrganizatinServiceImpl implements IUserOrganizationService {

    private Log log = LogFactory.getLog(UserOrganizatinServiceImpl.class);

    private UserOrganizationsMapper UserOrganizationDao;
    private IBaseService<UserOrganizations,UserOrganizations> baseService;

   // @Autowired
    private SqlSessionFactory sqlSessionFactory;


    private DataSourceProperties dataSourceProperties;

    public UserOrganizatinServiceImpl()
    {
        System.out.println("UserOrganizationServiceImpl init");
    }

    @Autowired(required = true)
    public UserOrganizatinServiceImpl(UserOrganizationsMapper UserOrganizationDao, SqlSessionFactory sqlSessionFactory, DataSourceProperties dataSourceProperties)
    {
        this.UserOrganizationDao = UserOrganizationDao;
        this.baseService = new BaseMyBaticsServiceImpl<>(this.UserOrganizationDao,UserOrganizations.class
                ,UserOrganizationsCriteria.class);
        this.sqlSessionFactory = sqlSessionFactory;
    }


    @Override
    public UserOrganizations create(UserOrganizations obj) {
         return this.baseService.create(obj);
    }

    @Override
    public UserOrganizations get(String uuid) {
         return this.baseService.get(uuid);
    }

    @Override
    public ListResponse<UserOrganizations> list(UserOrganizations queryObj , Integer page, Integer rows) {
        return this.baseService.list(queryObj,page,rows);
    }

    @Override
    public UserOrganizations update(String uuid, UserOrganizations updateObj) {
        return this.baseService.update(uuid,updateObj);
    }

    @Override
    public UserOrganizations delete(String uuid) {
        return this.baseService.delete(uuid);
    }

    @Override
    public UserOrganizations update(UserOrganizations updateObj) throws DataNotFoundException {
        return null;
    }

    @Override
    public int batchInsert(List<UserOrganizations> record) {
        return this.baseService.batchInsert(record);
    }

    @Override
    public int batchDelete(List<String> uuids) {
        return this.baseService.batchDelete(uuids);
    }

    @Override
    public UserOrganizations batchUpdate(List<String> uuids, UserOrganizations updateObj) throws DataNotFoundException {
        return this.baseService.batchUpdate(uuids,updateObj);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public UserOrganizations getOrCreateUserOrganization(String ownerUUID) {
        UserOrganizations querObj = new UserOrganizations();
        querObj.setOwneruuid(ownerUUID);
        ListResponse<UserOrganizations> userOrganizationsListResponse = this.list(querObj,1,10);
        if(userOrganizationsListResponse.getItems().size() <= 0)
        {
            DBEntityUtils.preCreate(querObj);
            return this.create(querObj);
        }
        else
        {
            return  userOrganizationsListResponse.getItems().get(0);
        }
    }
}
