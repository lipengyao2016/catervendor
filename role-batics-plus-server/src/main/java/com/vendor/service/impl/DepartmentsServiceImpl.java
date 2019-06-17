package com.vendor.service.impl;

import com.vendor.entity.Departments;

import com.vendor.service.IDepartmentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vendor.user_mapper.DepartmentsMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门。 服务实现类
 * </p>
 *
 * @author lpy
 * @since 2019-06-14
 */
@Service
public class DepartmentsServiceImpl extends ServiceImpl<DepartmentsMapper, Departments> implements IDepartmentsService {

}
