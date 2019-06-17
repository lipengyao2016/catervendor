package com.vendor.service;

import com.vendor.entity.ListResponse;
import com.vendor.utils.DataNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBaseService<T,QY_T> {
     public T create(T obj) ;
     public  T get(Long id);
     public ListResponse<T> list(QY_T queryObj , Integer page, Integer rows);
     public  T update(Long id, T updateObj) throws DataNotFoundException;
     public  T delete(Long id);

     public  T update(T updateObj) throws DataNotFoundException;

     public int batchInsert(List<T> record);

     public int batchDelete(List<Long> ids);

     public  T batchUpdate(List<Long> ids, T updateObj) throws DataNotFoundException;
}
