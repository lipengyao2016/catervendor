package com.vendor.service;

import com.vendor.bean.merchandise.Merchandise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vendor.bean.merchandise.MerchandiseExt;
import com.vendor.entity.ListResponse;
import com.vendor.queryvo.merchandise.MerchandiseQueryVo;

import java.util.List;

/**
 * <p>
 * 商品。 服务类
 * </p>
 *
 * @author lpy
 * @since 2019-06-18
 */
public interface IMerchandiseService extends IBasePlusService<Merchandise,MerchandiseQueryVo> {

    public MerchandiseExt get(Long uuid, List<String> expands);

    public ListResponse<MerchandiseExt> list(MerchandiseQueryVo queryObj, Integer page, Integer rows
    , List<String> expands);



}
