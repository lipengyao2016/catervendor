package com.vendor.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vendor.entity.ListResponse;
import com.vendor.utils.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BaseMybaticsPlusServiceImpl<M extends BaseMapper<T>, T,QY_T> extends ServiceImpl
        implements IBasePlusService<T,QY_T> {

    private Log log = LogFactory.getLog(BaseMybaticsPlusServiceImpl.class);

    @Autowired
    DBEntityUtils dbEntityUtils;

    @Override
    public Long create(T obj) {
        dbEntityUtils.preCreate(obj);
        Long lId = null;
        try {
             lId = (Long) ReflectUtils.getField(obj,"id");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this.save(obj) ? lId : null;
    }

    @Override
    public T get(Long uuid) {

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",uuid);
        return (T)this.getOne(queryWrapper);
    }

    @Override
    public ListResponse list(QY_T queryObj, Integer page, Integer rows) {

        if(page == null)
        {
            page = 1;
        }
        if(rows == null)
        {
            rows = 10000;
        }
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        Class entityCls = ReflectionKit.getSuperClassGenericType(getClass(), 1);
        Class queryCls = ReflectionKit.getSuperClassGenericType(getClass(), 2);

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
            } else {
                try {
                    String value = null;
                    try {
                        value = (String) ReflectUtils.getField(queryObj, f.getName());
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    // log.info("field name:" + f.getName() + ",value:" + value);

                    Field curField = ReflectUtils.getFieldInfo(entityCls,f.getName());



                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (value != null && curField != null) {
                        String fieldName = f.getName();
                        String dbEntityName = StrUtils.camelToUnderline(fieldName);

                        if (value.contains("[")) {
                            List<Object> valueList = GsonUtils.ToObjectList(value);

                            Object firstValue = valueList.get(0);
                            StrUtils.emObjType objType = StrUtils.getObjectType(firstValue);
                            if (valueList.size() == 2 && objType == StrUtils.emObjType.Obj_Str
                                    && StrUtils.isValidDate((String) valueList.get(0))
                                    && StrUtils.isValidDate((String) valueList.get(1))) {

                                try
                                {
                                   // String fieldLikeMethodName = "and" + f.getName() + "Between";
                                    if( curField.getType().getName().equals(Integer.class.getName()) )
                                    {
                                        int beginVal = Integer.parseInt((String) valueList.get(0));
                                        int endVal = Integer.parseInt((String) valueList.get(1));

                                        queryWrapper.between(dbEntityName,beginVal,endVal);
                                    }
                                    else if( curField.getType().getName().equals(Long.class.getName()) )
                                    {
                                        Long beginVal = Long.parseLong((String) valueList.get(0));
                                        Long endVal = Long.parseLong((String) valueList.get(1));

                                        queryWrapper.between(dbEntityName,beginVal,endVal);
                                    }
                                    else if( curField.getType().getName().equals(Date.class.getName())  )
                                    {
                                        Date beginDate = sdf.parse((String) valueList.get(0));
                                        Date endDate = sdf.parse((String) valueList.get(1));
                                        queryWrapper.between(dbEntityName,beginDate,endDate);
                                    }
                                    else
                                    {
                                        String beginVal = (String) valueList.get(0);
                                        String endVal = (String) valueList.get(1);
                                        queryWrapper.between(dbEntityName,beginVal,endVal);
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            else
                            {
                                Type t1 = curField.getType();
                                String fieldLikeMethodName = "and" + f.getName() + "In";
                                log.info("field type:" + curField.getType() + ",class:"+
                                        Integer.class.getName());

                                if( curField.getType().getName().equals(Integer.class.getName()))
                                {
                                    List<Integer> retList = new ArrayList();
                                    for (Object valSta : valueList) {
                                        log.info("int valSta  :" + valSta);
                                        retList.add(((Long)valSta).intValue());
                                    }
                                    queryWrapper.in(dbEntityName,retList);
                                }
                                else if(curField.getType().getName().equals(Long.class.getName()))
                                {
                                    List<Long> retList = new ArrayList();
                                    for (Object valSta : valueList) {
                                        log.info("Long valSta  :" + valSta);
                                        retList.add((Long)valSta);
                                    }
                                    queryWrapper.in(dbEntityName,retList);
                                }
                                else if(curField.getType().getName().equals(Date.class.getName()))
                                {
                                    List<Date> retList = new ArrayList();
                                    for (Object valSta : valueList) {
                                        log.info("date valSta  :" + valSta);
                                        retList.add(sdf.parse((String) valSta));
                                    }
                                    queryWrapper.in(dbEntityName,retList);
                                }
                                else
                                {
                                    List<String> retList = new ArrayList();
                                    for (Object valSta : valueList) {
                                        log.info("int valSta  :" + valSta);
                                        retList.add((String) valSta);
                                    }
                                    queryWrapper.in(dbEntityName,retList);
                                }


                            }
                        } else if (value.contains("*")) {

                            boolean bStartMofu = false,bEndMofu = false;
                            if(value.startsWith("*"))
                            {
                                bStartMofu = true;
                            }
                            if(value.endsWith("*"))
                            {
                                bEndMofu = true;
                            }
                            value = value.replace("*", "");

                            if(bStartMofu && bEndMofu)
                            {
                                queryWrapper.like(dbEntityName,value);
                            }
                            else if(bStartMofu)
                            {
                                queryWrapper.likeLeft(dbEntityName,value);
                            }
                            else if(bEndMofu)
                            {
                                queryWrapper.likeRight(dbEntityName,value);
                            }

                        } else {

                            Object realVal ;
                            if( curField.getType().getName().equals(Integer.class.getName()) )
                            {
                                realVal = Integer.parseInt(value);
                            }
                            else if( curField.getType().getName().equals(Long.class.getName()) )
                            {
                                realVal =  Long.parseLong(value);
                            }
                            else if( curField.getType().getName().equals(Date.class.getName()) )
                            {
                                realVal =  sdf.parse(value);
                            }
                            else
                            {
                                realVal = value;
                            }

                            queryWrapper.eq(dbEntityName,realVal);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        IPage rolesIPage = new Page<T>(page, rows);
        IPage rolesList =  this.page(rolesIPage,queryWrapper);

        ListResponse response = new ListResponse();
        response.setItems(rolesList.getRecords());
        response.setTotalSize( ((Long)rolesList.getTotal()).intValue());
        response.setTotalPageCount( ((Long)rolesList.getPages()).intValue());
        response.setPageSize(rows);
        response.setCurrentPage(page);

        return  response;

    }

    @Override
    public boolean update(Long uuid, T updateObj) throws DataNotFoundException {
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",uuid);

        dbEntityUtils.preUpdate(updateObj);
        return this.update(updateObj,updateWrapper);
    }

    @Override
    public boolean delete(Long uuid) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",uuid);

        return  this.remove(queryWrapper);
    }

    @Override
    public boolean update(T updateObj) throws DataNotFoundException {
        Long uuid = null ;
        try {
            uuid = (Long) ReflectUtils.getField(updateObj,"id");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this.update(uuid,updateObj);
    }

    @Override
    public boolean batchInsert(List<T> record) {
        for (Object obj:record) {
            dbEntityUtils.preCreate(obj);
        }
        return this.saveBatch(record);
    }

    @Override
    public boolean batchUpdate(List<Long> uuids, T updateObj)  {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",uuids);

        dbEntityUtils.preUpdate(updateObj);

        return this.update(updateObj,queryWrapper);
    }

    @Override
    public boolean batchDelete(List<Long> uuids) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",uuids);

        return this.remove(queryWrapper);
    }
}
