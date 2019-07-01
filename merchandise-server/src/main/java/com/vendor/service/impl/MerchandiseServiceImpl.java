package com.vendor.service.impl;

import com.vendor.bean.merchandise.Brand;
import com.vendor.bean.merchandise.Category;
import com.vendor.bean.merchandise.Merchandise;
import com.vendor.bean.merchandise.MerchandiseExt;
import com.vendor.entity.ListResponse;
import com.vendor.mapper.CategoryMapper;
import com.vendor.mapper.MerchandiseMapper;
import com.vendor.queryvo.merchandise.CategoryQueryVo;
import com.vendor.queryvo.merchandise.MerchandiseQueryVo;
import com.vendor.service.BaseMybaticsPlusServiceImpl;
import com.vendor.service.IBrandService;
import com.vendor.service.ICategoryService;
import com.vendor.service.IMerchandiseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vendor.utils.BeanHelper;
import com.vendor.utils.DataNotFoundException;
import com.vendor.utils.GsonUtils;
import com.vendor.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品。 服务实现类
 * </p>
 *
 * @author lpy
 * @since 2019-06-18
 */
@Service
public class MerchandiseServiceImpl extends BaseMybaticsPlusServiceImpl<MerchandiseMapper,
        Merchandise,MerchandiseQueryVo>
        implements IMerchandiseService {

    @Autowired
    MerchandiseMapper merchandiseMapper;
    
    @Autowired
    ICategoryService categoryService;

    @Autowired
    IBrandService brandService;

    @Override
    public Long create(Merchandise obj)
    {
        this.fillMerchantIdByCategory(obj);
        return super.create(obj);
    }

    protected Merchandise fillMerchantIdByCategory(Merchandise obj)
    {
        if(obj.getCategoryId() != null)
        {
            Category Category = categoryService.get(obj.getCategoryId());
            if(Category == null)
            {
                throw  new DataNotFoundException(6002," Category is not exist!!!");
            }
            obj.setMerchantId(Category.getMerchantId());
        }
        return  obj;
    }

    @Override
    public boolean batchInsert(List<Merchandise> record)
    {
        MerchandiseServiceImpl curThis = this;
        List<Merchandise> merchandiseList =  record.stream().map(merchandise -> {
            return curThis.fillMerchantIdByCategory(merchandise);
        }).collect(Collectors.toList());

        return super.batchInsert(merchandiseList);
    }

    protected MerchandiseExt addExtToMerchandise(Merchandise merchandise, List<String> expands)
    {
        MerchandiseExt merchandiseExt = new MerchandiseExt();
        if(merchandise == null)
        {
            return  null;
        }
        BeanHelper.copyPropertiesIgnoreNull(merchandise,merchandiseExt);
        if(expands.contains("category"))
        {
            if(merchandise.getCategoryId() != null)
            {
                Category category = categoryService.get(merchandise.getCategoryId());
                merchandiseExt.setCategory(category);
            }
        }

        if(expands.contains("brand"))
        {
            if(merchandise.getBrandId() != null)
            {
                Brand brand =  brandService.get(merchandise.getBrandId());
                merchandiseExt.setBrand(brand);
            }
        }


        return  merchandiseExt;
    }

    @Override
    public MerchandiseExt get(Long uuid, List<String> expands)
    {
        Merchandise merchandise = super.get(uuid);
        MerchandiseExt merchandiseExt = this.addExtToMerchandise(merchandise,expands);
        return  merchandiseExt;
    }

    @Override
    public ListResponse<MerchandiseExt> list(MerchandiseQueryVo queryObj, Integer page, Integer rows
            , List<String> expands) {

        if(StringUtil.isNotEmpty(queryObj.getCategoryName()))
        {
            List<Category> queryCategories  = categoryService.getCategoryByName(queryObj.getCategoryName());
            if(queryCategories != null && queryCategories.size() > 0)
            {
                queryObj.setCategoryId(String.valueOf(queryCategories.get(0).getId()));
            }
            queryObj.setCategoryName(null);
        }

        if(StringUtil.isNotEmpty(queryObj.getCategoryId()))
        {
            List<Category> queryCategories  = categoryService.getCategoryByParentId(queryObj.getCategoryId());
            if(queryCategories != null && queryCategories.size() > 0)
            {
                List<Long> categoryIds = queryCategories.stream().map(category -> {
                    return category.getId();
                }).collect(Collectors.toList());
                queryObj.setCategoryId(GsonUtils.ToJson(categoryIds,List.class));
            }
        }




        ListResponse<Merchandise>  merchandiseListResponse = this.list(queryObj,page,rows);
        MerchandiseServiceImpl curThis = this;
        List<MerchandiseExt>  merchandiseExts =  merchandiseListResponse.getItems().stream().map(merchandise -> {
             return curThis.addExtToMerchandise(merchandise,expands);
         }).collect(Collectors.toList());

        ListResponse<MerchandiseExt> merchandiseExtListResponse = new ListResponse<>();
        BeanHelper.copyPropertiesExcludeAttr(merchandiseListResponse,merchandiseExtListResponse,new String[]{"items"});

        merchandiseExtListResponse.setItems(merchandiseExts);
        return merchandiseExtListResponse;
    }

}
