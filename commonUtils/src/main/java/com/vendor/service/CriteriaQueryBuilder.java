package com.vendor.service;

import com.vendor.utils.ReflectUtils;
import com.vendor.utils.StrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CriteriaQueryBuilder {

    private static Log log = LogFactory.getLog(CriteriaQueryBuilder.class);

    public static void buildBetweenQuery(Object criteria, Field f, Object firstVal,
                                         Object secondVal)
    {
        try
        {
            String fieldLikeMethodName = "and" + f.getName() + "Between";
            if( f.getType().getName().equals(Integer.class.getName()) )
            {
                int beginVal = Integer.parseInt((String) firstVal);
                int endVal = Integer.parseInt((String) secondVal);
                ReflectUtils.callMethod(criteria,fieldLikeMethodName,beginVal,endVal);
            }
            else if( f.getType().getName().equals(Date.class.getName())  )
            {
                Date beginDate = StrUtils.convertStrToDate(firstVal);
                Date endDate = StrUtils.convertStrToDate(secondVal);
                ReflectUtils.callMethod(criteria,fieldLikeMethodName,beginDate,endDate);
            }
            else
            {
                String beginVal = (String) firstVal;
                String endVal = (String) secondVal;
                ReflectUtils.callMethod(criteria,fieldLikeMethodName,beginVal,endVal);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static void buildInQuery(Object criteria, Field f, List<Object> valueList)
    {
        String fieldLikeMethodName = "and" + f.getName() + "In";
        log.info("field type:" + f.getType() + ",class:"+
                Integer.class.getName());

        if( f.getType().getName().equals(Integer.class.getName()))
        {
            List<Integer> retList = new ArrayList();
            for (Object valSta : valueList) {
                log.info("int valSta  :" + valSta);
                retList.add(((Long)valSta).intValue());
            }
            ReflectUtils.callMethod(criteria,fieldLikeMethodName,retList);
        }
        else if(f.getType().getName().equals(Date.class.getName()))
        {
            List<Date> retList = new ArrayList();
            for (Object valSta : valueList) {
                log.info("date valSta  :" + valSta);
                try {
                    retList.add(StrUtils.convertStrToDate(valSta));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            ReflectUtils.callMethod(criteria,fieldLikeMethodName,retList);
        }
        else
        {
            List<String> retList = new ArrayList();
            for (Object valSta : valueList) {
                log.info("int valSta  :" + valSta);
                retList.add((String) valSta);
            }
            ReflectUtils.callMethod(criteria,fieldLikeMethodName,retList);
        }
    }


    public static void buildLikeQuery(Object criteria, Field f, String value)
    {
        value = value.replace("*", "%");
        String fieldLikeMethodName = "and" + f.getName() + "Like";
        ReflectUtils.callMethod(criteria,fieldLikeMethodName,value);
    }

    public static void buildEqualQuery(Object criteria, Field f, String value)
    {
        String fieldEqualMethodName = "and" + f.getName() + "EqualTo";

        Object realVal = null ;
        if( f.getType().getName().equals(Integer.class.getName()) )
        {
            realVal = Integer.parseInt(value);
        }
        else if( f.getType().getName().equals(Date.class.getName()) )
        {
            try {
                realVal =  StrUtils.convertStrToDate(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else
        {
            realVal = value;
        }

        ReflectUtils.callMethod(criteria,fieldEqualMethodName,realVal);
    }

    public static void buildEqualQuery(Object criteria, Field f, Object value)
    {
        String fieldEqualMethodName = "and" + f.getName() + "EqualTo";
        ReflectUtils.callMethod(criteria,fieldEqualMethodName,value);

      /*  StrUtils.emObjType fieldObjType = StrUtils.getObjectType(value);
        if(fieldObjType == StrUtils.emObjType.Obj_Str)
        {
            ReflectUtils.callMethod(criteria,fieldEqualMethodName,(String)value);
        }
        else if(fieldObjType == StrUtils.emObjType.Obj_Int)
        {
            ReflectUtils.callMethod(criteria,fieldEqualMethodName,(Integer)value);
        }*/


    }



}
