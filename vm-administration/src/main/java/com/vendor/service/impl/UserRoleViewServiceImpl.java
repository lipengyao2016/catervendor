package com.vendor.service.impl;



import com.vendor.config.DataSourceProperties;
import com.vendor.entity.ListResponse;

import com.vendor.mapper.UserRoleViewsMapper;
import com.vendor.bean.user.UserRoleViews;
import com.vendor.bean.user.UserRoleViewsCriteria;
import com.vendor.queryvo.user.UserRoleViewQueryVo;
import com.vendor.service.BaseMyBaticsServiceImpl;
import com.vendor.service.IBaseService;
import com.vendor.service.IUserRoleViewService;
import com.vendor.utils.DataNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleViewServiceImpl implements IUserRoleViewService {

    private Log log = LogFactory.getLog(UserRoleViewServiceImpl.class);

    private UserRoleViewsMapper UserRoleViewDao;
    private IBaseService<UserRoleViews,UserRoleViewQueryVo> baseService;

   // @Autowired
    private SqlSessionFactory sqlSessionFactory;


    private DataSourceProperties dataSourceProperties;

    public UserRoleViewServiceImpl()
    {
        System.out.println("UserRoleViewServiceImpl init");
    }

    @Autowired(required = true)
    public UserRoleViewServiceImpl(UserRoleViewsMapper UserRoleViewDao, SqlSessionFactory sqlSessionFactory, DataSourceProperties dataSourceProperties)
    {
        this.UserRoleViewDao = UserRoleViewDao;
        this.baseService = new BaseMyBaticsServiceImpl<>(this.UserRoleViewDao,UserRoleViews.class
                ,UserRoleViewsCriteria.class);
        this.sqlSessionFactory = sqlSessionFactory;
    }


    @Override
    public UserRoleViews create(UserRoleViews obj) {
         return this.baseService.create(obj);
    }

    @Override
    public UserRoleViews get(String uuid) {
         return this.baseService.get(uuid);
    }

    @Override
    public ListResponse<UserRoleViews> list(UserRoleViewQueryVo queryObj , Integer page, Integer rows) {
        return this.baseService.list(queryObj,page,rows);
    }

    @Override
    public UserRoleViews update(String uuid, UserRoleViews updateObj) {
        return this.baseService.update(uuid,updateObj);
    }

    @Override
    public UserRoleViews delete(String uuid) {
        return this.baseService.delete(uuid);
    }

    @Override
    public UserRoleViews update(UserRoleViews updateObj) throws DataNotFoundException {
        return null;
    }

    @Override
    public int batchInsert(List<UserRoleViews> record) {
        return this.baseService.batchInsert(record);
    }

    @Override
    public int batchDelete(List<String> uuids) {
        return this.baseService.batchDelete(uuids);
    }

    @Override
    public UserRoleViews batchUpdate(List<String> uuids, UserRoleViews updateObj) throws DataNotFoundException {
        return this.baseService.batchUpdate(uuids,updateObj);
    }
}
