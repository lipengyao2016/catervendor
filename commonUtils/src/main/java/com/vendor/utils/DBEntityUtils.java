package com.vendor.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DBEntityUtils {

    @Autowired
    private SnowFlake snowFlake;

    private Log log = LogFactory.getLog(DBEntityUtils.class);

    public void preCreate(Object obj) {
        try {
            long lid = snowFlake.nextId();


            log.info("lid:" + lid);

            ReflectUtils.setField(obj, "id", lid,false);
           // ReflectUtils.setField(obj, "uuid", UUIDUtils.createUUID(),false);
            ReflectUtils.setField(obj, "createdDt", new Date(),true);
            ReflectUtils.setField(obj, "updatedDt", new Date(),true);
           // ReflectUtils.setField(obj, "status", "enabled",false);
            ReflectUtils.setField(obj, "status", "Y",false);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void preUpdate(Object obj) {
        try {
            ReflectUtils.setField(obj, "updatedDt", new Date(), true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
