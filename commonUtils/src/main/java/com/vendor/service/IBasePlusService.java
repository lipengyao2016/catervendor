package com.vendor.service;

import com.vendor.entity.ListResponse;
import com.vendor.utils.DataNotFoundException;

import java.util.List;

public interface IBasePlusService<T,QY_T>  {
     public boolean create(T obj);
     public  T get(Long uuid);
     public ListResponse<T> list(QY_T queryObj, Integer page, Integer rows);
     public  boolean update(Long uuid, T updateObj) throws DataNotFoundException;
     public  boolean delete(Long uuid);

     public  boolean update(T updateObj) throws DataNotFoundException;

     public boolean batchInsert(List<T> record);

     public boolean batchDelete(List<Long> uuids);

     public  boolean batchUpdate(List<Long> uuids, T updateObj) throws DataNotFoundException;
}
