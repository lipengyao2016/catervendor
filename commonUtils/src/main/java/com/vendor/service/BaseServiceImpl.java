package com.vendor.service;

import com.vendor.entity.ListResponse;
import com.vendor.utils.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseServiceImpl<T, QY_T> implements IBaseService {

    private Log log = LogFactory.getLog(BaseServiceImpl.class);


    private JpaRepository m_jpaRepository;
    private JpaSpecificationExecutor m_jpaSpecificationExecutor;
    private Class entityClass;
    private Class queryClass;

    public BaseServiceImpl() {

    }

    public BaseServiceImpl(Object obj, Class entityClass,Class queryClass) {
        m_jpaRepository = (JpaRepository) obj;
        m_jpaSpecificationExecutor = (JpaSpecificationExecutor) obj;
        this.entityClass = entityClass;
        this.queryClass = queryClass;
    }


    public JpaRepository getM_jpaRepository() {
        return m_jpaRepository;
    }

    public void setM_jpaRepository(JpaRepository m_jpaRepository) {
        this.m_jpaRepository = m_jpaRepository;
    }

    public JpaSpecificationExecutor getM_jpaSpecificationExecutor() {
        return m_jpaSpecificationExecutor;
    }

    public void setM_jpaSpecificationExecutor(JpaSpecificationExecutor m_jpaSpecificationExecutor) {
        this.m_jpaSpecificationExecutor = m_jpaSpecificationExecutor;
    }

    @Override
    public Object create(Object obj) {
        return null;
    }

    @Override
    public Object get(Long id) {
        return null;
    }

    @Override
    public ListResponse list(Object queryObj, Integer page, Integer rows) {
        return null;
    }

    @Override
    public Object update(Long id, Object updateObj) throws DataNotFoundException {
        return null;
    }

    @Override
    public Object delete(Long id) {
        return null;
    }

    @Override
    public Object update(Object updateObj) throws DataNotFoundException {
        return null;
    }

    @Override
    public int batchInsert(List record) {
        return 0;
    }

    @Override
    public Object batchUpdate(List ids, Object updateObj) throws DataNotFoundException {
        return null;
    }

    @Override
    public int batchDelete(List ids) {
        return 0;
    }

    /*public Object create(Object obj)  {

        try {
            ReflectUtils.setField(obj, "uuid", UUIDUtils.createUUID(),false);
            ReflectUtils.setField(obj, "createdAt", new Date(),true);
            ReflectUtils.setField(obj, "modifiedAt", new Date(),true);
            ReflectUtils.setField(obj, "status", "enabled",false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return m_jpaRepository.save(obj);
    }


    public Object get(String uuid) {
        Object queryObj = null;
        try {
            queryObj = ReflectUtils.createInstance(this.entityClass);
            try {
                ReflectUtils.setField(queryObj, "uuid", uuid,true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        ExampleMatcher matcher = ExampleMatcher.matching();
        //创建实例
        Example ex = Example.of(queryObj, matcher);
        List findRoles = m_jpaRepository.findAll(ex);
        if(findRoles.size() > 0)
        {
            return findRoles.get(0);
        }
        else
        {
            throw new DataNotFoundException("5003","get db not found uuid:" + uuid);
        }

    }


    public ListResponse<T> list(Object queryObj, Integer page, Integer rows) {
       *//* Integer page = null;
        Integer rows = null;
        try {
            page = (Integer) ReflectUtils.getField(queryObj,"page");
            rows = (Integer) ReflectUtils.getField(queryObj,"rows");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
*//*

        Pageable pageable= null;
        if(page == null)
        {
           page = 1;
        }
        if(rows == null)
        {
            rows = 10000000;
        }
        pageable= new PageRequest(page-1, rows, Sort.Direction.ASC,"createdAt");

        Specification<T> specification = new Specification<T>() {

            @SuppressWarnings("unchecked")
            private <T, R> Path<R> getPath(Class<R> resultType, Path<T> root, String path) {
                String[] pathElements = path.split("\\.");
                Path<?> retVal = root;
                for (String pathEl : pathElements) {
                    retVal = (Path<R>) retVal.get(pathEl);
                }
                return (Path<R>) retVal;
            }

            public void addQueryCond(List<Predicate> predicatesList, CriteriaBuilder cb,Root<T> root,String ziduanName,String value)
            {
                if(value != null)
                {
                    Path ziduanPath = this.getPath(String.class,root,ziduanName);
                    if (value.contains("[")) {
                        List<Object> valueList = GsonUtils.ToObjectList( value);

                        Object firstValue = valueList.get(0);
                        StrUtils.emObjType objType = StrUtils.getObjectType(firstValue);
                        if (valueList.size() == 2 &&  objType == StrUtils.emObjType.Obj_Str
                                && StrUtils.isValidDate((String) valueList.get(0))
                                && StrUtils.isValidDate((String) valueList.get(1))) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date beginDate = sdf.parse((String)valueList.get(0));
                                Date endDate = sdf.parse((String)valueList.get(1));
                                predicatesList.add(cb.and(cb.between(ziduanPath, beginDate, endDate)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            CriteriaBuilder.In<Object> in = cb.in(ziduanPath);
                            for (Object valSta : valueList) {
                                log.info("valSta  :" + valSta);
                                in.value(valSta);
                            }
                            predicatesList.add(cb.and(in));
                        }
                    } else if (value.contains("*")) {
                        value = value.replace("*", "%");
                        predicatesList.add(cb.and(cb.like(ziduanPath, value)));
                    } else {
                        predicatesList.add(cb.and(cb.equal(ziduanPath, value)));
                    }
                }
            }

            public void addQueryObj(List<Predicate> predicatesList, CriteriaBuilder cb,Root<T> root,String supperObjFieldName,
                                    Object queryObj)
            {
                Field[] fieldArray = queryObj.getClass().getDeclaredFields();
                for (Field f : fieldArray) {

                    if(f.getName().contentEquals("page") || f.getName().contentEquals("rows"))
                    {
                        continue;
                    }
                    if(f.getType().getName().contains("com."))
                    {
                        try {
                            Object objVal = (Object) ReflectUtils.getField(queryObj,f.getName());
                            if(objVal != null)
                            {
                                String objFieldName = f.getName();
                                this.addQueryObj(predicatesList,cb,root,objFieldName,objVal);
                            }


                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try {
                            String value = null;
                            try {
                                value = (String) ReflectUtils.getField(queryObj,f.getName());
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                            log.info("field name:" + f.getName() + ",value:" + value);
                            if(value != null)
                            {
                                String ziduanName =f.getName();
                                if(!supperObjFieldName.isEmpty())
                                {
                                    ziduanName = supperObjFieldName  +"."+ziduanName;
                                }
                                this.addQueryCond(predicatesList,cb,root,ziduanName,value);
                            }

                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }

            //toPredicate就是查询条件
            //root根对象表示实体类,要查询的类型,query所需要添加查询条件,cb构建Predicate
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicatesList = new ArrayList<>();

                this.addQueryObj(predicatesList,cb,root,"",queryObj);
                return cb.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
            }
        };
        Page findRoles = null;

        findRoles = m_jpaSpecificationExecutor.findAll(specification,pageable);


        ListResponse response = new ListResponse();
        response.setItems(findRoles.getContent());
        response.setCurrentPage(page);
        response.setPageSize(rows);
        response.setTotalSize((int) findRoles.getTotalElements());
        response.setTotalPageCount(findRoles.getTotalPages());

        return response;
    }


    public Object update(String uuid, Object updateObj) {
        Object oldRole = this.get(uuid);
        if(oldRole != null)
        {
            BeanHelper.copyPropertiesIgnoreNull(updateObj,oldRole);
            try {
                ReflectUtils.setField(oldRole, "modifiedAt", new Date(),true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            m_jpaRepository.save(oldRole);
        }
        else
        {
           throw new DataNotFoundException("5000","update db not found uuid:" + uuid);
        }

        return oldRole;
    }


    public Object delete(String uuid) {
        Object oldRole = this.get(uuid);
        if(oldRole != null)
        {
            m_jpaRepository.delete(oldRole);
        }
        else
        {
            throw new DataNotFoundException("5001","delete22 db not found uuid:" + uuid);
        }
         return  oldRole;
    }

    @Override
    public Object update(Object updateObj) throws DataNotFoundException {
        return m_jpaRepository.save(updateObj);
    }

    @Override
    public int batchInsert(List record) {

        //批量存储的集合
        List singleBatchData = new ArrayList<>();

        //批量存储
        for(Object obj : record) {
            if(singleBatchData.size() == 100) {
                this.m_jpaRepository.save(singleBatchData);
                singleBatchData.clear();
            }

            singleBatchData.add(obj);
        }

        if(!singleBatchData.isEmpty()) {
            this.m_jpaRepository.save(singleBatchData);
        }

        return record.size();

    }

    @Override
    public Object batchUpdate(List uuids, Object updateObj) throws DataNotFoundException {
        String uuidQueryStr  = GsonUtils.ToJson(uuids,List.class);
        Object queryObj = null;
        try {
            queryObj = ReflectUtils.createInstance(this.queryClass);
            try {
                ReflectUtils.setField(queryObj, "uuid", uuidQueryStr,true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        ListResponse queryList = this.list(queryObj,null,null);
        for (Object persistObj : queryList.getItems())
        {

        }

        m_jpaRepository.save(queryList.getItems());

        return updateObj;
    }*/




}
