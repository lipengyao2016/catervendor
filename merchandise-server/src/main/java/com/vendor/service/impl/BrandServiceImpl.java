package com.vendor.service.impl;

import com.vendor.bean.merchandise.Brand;
import com.vendor.mapper.BrandMapper;
import com.vendor.queryvo.merchandise.BrandQueryVo;
import com.vendor.service.BaseMybaticsPlusServiceImpl;
import com.vendor.service.IBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌。 服务实现类
 * </p>
 *
 * @author lpy
 * @since 2019-06-18
 */
@Service
public class BrandServiceImpl extends BaseMybaticsPlusServiceImpl<BrandMapper, Brand,BrandQueryVo>
        implements IBrandService {

}
