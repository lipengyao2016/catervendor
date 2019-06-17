package com.vendor.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vendor.entity.ListResponse;
import com.vendor.utils.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BaseMyBaticsServiceImpl<T, QY_T> implements  IBaseService{


    private Object dbProxy;

    private Log log = LogFactory.getLog(BaseServiceImpl.class);
    private Class entityClass;
    private Class dbExampleCls;


    private DBEntityUtils dbEntityUtils;

    public BaseMyBaticsServiceImpl()
    {
        System.out.println("RoleServiceImpl init");
    }

    @Autowired(required = true)
    public BaseMyBaticsServiceImpl(Object dbProxy, Class entityClass
            ,Class dbExampleCls,DBEntityUtils dbEntityUtils)
    {
        this.dbProxy = dbProxy;
        this.entityClass = entityClass;
        this.dbExampleCls =dbExampleCls;
        this.dbEntityUtils = dbEntityUtils;
    }


    @Override
    public Object create(Object obj) {
        dbEntityUtils.preCreate(obj);
        ReflectUtils.callMethod(this.dbProxy,"insert",obj);
        try {
            Long id = (Long) ReflectUtils.getField(obj,"id");
            return  this.get(id);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return  null;
    }

    protected void internetCreate(Object obj)
    {
        dbEntityUtils.preCreate(obj);
        ReflectUtils.callMethod(this.dbProxy,"insert",obj);
    }

    public Object createExample() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
       return   ReflectUtils.createInstance(this.dbExampleCls);
    }

    public Object createCriteria(Object example)
    {
        return  ReflectUtils.callMethod(example,"createCriteria");
    }

    public void addQuery(Object criteria,String methodName, Object... args)
    {
        ReflectUtils.callMethod(criteria,methodName,args);
    }

    public Object executeDBQuery(String methodName, Object... args)
    {
       return ReflectUtils.callMethod(this.dbProxy,methodName,args);
    }

    @Override
    public Object get(Long id) {
        try {
            Object example = this.createExample();
            Object criteria = this.createCriteria(example);
            this.addQuery(criteria,"andIdEqualTo",id);

            List roles = (List) this.executeDBQuery("selectByExample",example);
            if(roles.size() > 0)
            {
                return  roles.get(0);
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

        return null;
    }



    @Override
    public ListResponse list(Object queryObj, Integer page, Integer rows) {

        if(page == null)
        {
            page = 1;
        }
        if(rows == null)
        {
            rows = 10000;
        }

        PageHelper.startPage(page, rows);

        Object example = null;
        try {
            example = this.createExample();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Object criteria = this.createCriteria(example);

        if(queryObj != null)
        {
            Field[] fieldArray = queryObj.getClass().getDeclaredFields();
            for (Field f : fieldArray) {

                if (f.getName().contentEquals("page") || f.getName().contentEquals("rows")) {
                    continue;
                }
                if (f.getType().getName().contains("com.")) {
                    try {
                        Object objVal = (Object) ReflectUtils.getField(queryObj, f.getName());
                        if (objVal != null) {
                            String objFieldName = f.getName();
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
                        Object fieldVal = null;
                        try {
                            fieldVal =  ReflectUtils.getField(queryObj, f.getName());
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        // log.info("field name:" + f.getName() + ",value:" + value);

                        Field curField = ReflectUtils.getFieldInfo(this.entityClass,f.getName());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        if (fieldVal != null && curField != null) {
                            String fieldName = f.getName();
                            StrUtils.emObjType fieldObjType = StrUtils.getObjectType(fieldVal);

                            if(fieldObjType == StrUtils.emObjType.Obj_Str)
                            {
                                String value = (String) fieldVal;
                                if (value.contains("[")) {
                                    List<Object> valueList = GsonUtils.ToObjectList(value);

                                    Object firstValue = valueList.get(0);
                                    StrUtils.emObjType objType = StrUtils.getObjectType(firstValue);
                                    if (valueList.size() == 2 && objType == StrUtils.emObjType.Obj_Str
                                            && StrUtils.isValidDate((String) valueList.get(0))
                                            && StrUtils.isValidDate((String) valueList.get(1))) {
                                        CriteriaQueryBuilder.buildBetweenQuery(criteria,f,
                                                valueList.get(0),valueList.get(1));
                                    }
                                    else
                                    {
                                        CriteriaQueryBuilder.buildInQuery(criteria,f,valueList);
                                    }
                                } else if (value.contains("*")) {
                                    CriteriaQueryBuilder.buildLikeQuery(criteria,f,value);
                                } else {
                                    CriteriaQueryBuilder.buildEqualQuery(criteria,f,value);
                                }
                            }
                       /* else if(fieldObjType == StrUtils.emObjType.Obj_Array)
                        {
                            CriteriaQueryBuilder.buildInQuery(criteria,f,);
                        }*/
                            else
                            {
                                CriteriaQueryBuilder.buildEqualQuery(criteria,f,fieldVal);
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }



        List roles = (List) this.executeDBQuery("selectByExample",example);

        PageInfo pageInfo=new PageInfo<>(roles);
        ListResponse response = new ListResponse();
        response.setItems(pageInfo.getList());
        response.setTotalSize( ((Long) pageInfo.getTotal()).intValue() );
        response.setTotalPageCount(pageInfo.getPages());
        response.setPageSize(rows);
        response.setCurrentPage(page);

        return  response;
    }

    @Override
    public Object update(Long id, Object updateObj) throws DataNotFoundException {

        try {
            Object example = this.createExample();
            Object criteria = this.createCriteria(example);
            this.addQuery(criteria,"andIdEqualTo",id);
            dbEntityUtils.preUpdate(updateObj);
            this.executeDBQuery("updateByExampleSelective",updateObj,example);
            return this.get(id);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  null;
        //this.roleDao.updateByExampleSelective(updateObj,example);
    }

    @Override
    public Object delete(Long id) {

        Object example = null;
        try {
            example = this.createExample();
            Object criteria = this.createCriteria(example);
            this.addQuery(criteria,"andIdEqualTo",id);
            this.executeDBQuery("deleteByExample",example);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public Object update(Object updateObj) throws DataNotFoundException {
        Long id = null ;
        try {
             id = (Long) ReflectUtils.getField(updateObj,"id");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this.update(id,updateObj);
    }

    @Override
    public int batchInsert(List record) {

        Method batchInsertMethod = ReflectUtils.getMethodInfo(this.dbProxy.getClass(),"batchInsert");
        if(batchInsertMethod != null)
        {
            List<Object> newRecords = new ArrayList();
            for(Object obj:record)
            {
                dbEntityUtils.preCreate(obj);
                newRecords.add(obj);
            }
            int nRet = (int) ReflectUtils.callMethod(this.dbProxy,"batchInsert",record);
        }
        else
        {
            for(Object obj:record)
            {
                this.internetCreate(obj);
            }
        }

        return 0;
    }

    @Override
    public Object batchUpdate(List ids, Object updateObj) throws DataNotFoundException {
        try {
            Object example = this.createExample();
            Object criteria = this.createCriteria(example);
            this.addQuery(criteria,"andIdIn",ids);
            dbEntityUtils.preUpdate(updateObj);
            this.executeDBQuery("updateByExampleSelective",updateObj,example);
           return updateObj;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public int batchDelete(List ids) {
        Object example = null;
        try {
            example = this.createExample();
            Object criteria = this.createCriteria(example);
            this.addQuery(criteria,"andIdIn",ids);
            this.executeDBQuery("deleteByExample",example);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return  0;
    }
}
