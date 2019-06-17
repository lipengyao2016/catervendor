package com.vendor.service.impl;


import com.vendor.config.DataSourceProperties;
import com.vendor.entity.ListResponse;
import com.vendor.mapper.DepartmentsMapper;
import com.vendor.bean.user.Departments;
import com.vendor.bean.user.DepartmentsCriteria;
import com.vendor.queryvo.user.DepartmentQueryVo;
import com.vendor.service.BaseMyBaticsServiceImpl;
import com.vendor.service.IBaseService;
import com.vendor.service.IDepartmentService;
import com.vendor.utils.DataNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    private Log log = LogFactory.getLog(DepartmentServiceImpl.class);

    private DepartmentsMapper DepartmentDao;
    private IBaseService<Departments,DepartmentQueryVo> baseService;

   // @Autowired
    private SqlSessionFactory sqlSessionFactory;


    private DataSourceProperties dataSourceProperties;

    public DepartmentServiceImpl()
    {
        System.out.println("DepartmentServiceImpl init");
    }

    @Autowired(required = true)
    public DepartmentServiceImpl(DepartmentsMapper DepartmentDao,SqlSessionFactory sqlSessionFactory,DataSourceProperties dataSourceProperties)
    {
        this.DepartmentDao = DepartmentDao;
        this.baseService = new BaseMyBaticsServiceImpl<>(this.DepartmentDao,Departments.class
                ,DepartmentsCriteria.class);
        this.sqlSessionFactory = sqlSessionFactory;
    }


    @Override
    public Departments create(Departments obj) {
         return this.baseService.create(obj);
    }

    @Override
    public Departments get(String uuid) {
         return this.baseService.get(uuid);
    }

    @Override
    public ListResponse<Departments> list(DepartmentQueryVo queryObj , Integer page, Integer rows) {
        return this.baseService.list(queryObj,page,rows);
    }

    @Override
    public Departments update(String uuid, Departments updateObj) {
        return this.baseService.update(uuid,updateObj);
    }

    @Override
    public Departments delete(String uuid) {
        return this.baseService.delete(uuid);
    }

    @Override
    public Departments update(Departments updateObj) throws DataNotFoundException {
        return null;
    }

    @Override
    public int batchInsert(List<Departments> record) {
        return this.baseService.batchInsert(record);
    }

    @Override
    public int batchDelete(List<String> uuids) {
        return this.baseService.batchDelete(uuids);
    }

    @Override
    public Departments batchUpdate(List<String> uuids, Departments updateObj) throws DataNotFoundException {
        return this.baseService.batchUpdate(uuids,updateObj);
    }
}
