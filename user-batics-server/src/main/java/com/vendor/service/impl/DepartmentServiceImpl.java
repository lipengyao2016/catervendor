package com.vendor.service.impl;


import com.vendor.bean.user.Departments;
import com.vendor.bean.user.DepartmentsCriteria;
import com.vendor.config.DataSourceProperties;
import com.vendor.entity.ListResponse;
import com.vendor.mapper.DepartmentsMapper;
import com.vendor.queryvo.user.DepartmentQueryVo;
import com.vendor.service.BaseMyBaticsServiceImpl;
import com.vendor.service.IBaseService;
import com.vendor.service.IDepartmentService;
import com.vendor.utils.DBEntityUtils;
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
    public DepartmentServiceImpl(DepartmentsMapper DepartmentDao,
                                 SqlSessionFactory sqlSessionFactory,
                                 DataSourceProperties dataSourceProperties
            ,DBEntityUtils dbEntityUtils)
    {
        this.DepartmentDao = DepartmentDao;
        this.baseService = new BaseMyBaticsServiceImpl<>(this.DepartmentDao,Departments.class
                ,DepartmentsCriteria.class,dbEntityUtils);
        this.sqlSessionFactory = sqlSessionFactory;
    }


    @Override
    public Departments create(Departments obj) {
         return this.baseService.create(obj);
    }

    @Override
    public Departments get(Long id) {
         return this.baseService.get(id);
    }

    @Override
    public ListResponse<Departments> list(DepartmentQueryVo queryObj , Integer page, Integer rows) {
        return this.baseService.list(queryObj,page,rows);
    }

    @Override
    public Departments update(Long id, Departments updateObj) {
        return this.baseService.update(id,updateObj);
    }

    @Override
    public Departments delete(Long id) {
        return this.baseService.delete(id);
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
    public int batchDelete(List<Long> ids) {
        return this.baseService.batchDelete(ids);
    }

    @Override
    public Departments batchUpdate(List<Long> ids, Departments updateObj) throws DataNotFoundException {
        return this.baseService.batchUpdate(ids,updateObj);
    }
}
