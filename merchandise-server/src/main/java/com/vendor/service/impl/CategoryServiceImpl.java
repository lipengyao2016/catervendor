package com.vendor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vendor.bean.merchandise.Category;
import com.vendor.bean.merchandise.CategoryExt;
import com.vendor.entity.ListResponse;
import com.vendor.mapper.CategoryMapper;
import com.vendor.queryvo.merchandise.CategoryQueryVo;
import com.vendor.service.BaseMybaticsPlusServiceImpl;
import com.vendor.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vendor.utils.BeanHelper;
import com.vendor.utils.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品分类。 服务实现类
 * </p>
 *
 * @author lpy
 * @since 2019-06-18
 */
@Service
public class CategoryServiceImpl extends BaseMybaticsPlusServiceImpl<CategoryMapper, Category,CategoryQueryVo>
        implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public Long create(Category obj)
    {
       this.fillMerchantIdByParentCategor(obj);
       return super.create(obj);
    }

    protected Category fillMerchantIdByParentCategor(Category obj)
    {
        if(obj.getUpCategoryId() != null)
        {
            Category parentCategory = this.get(obj.getUpCategoryId());
            if(parentCategory == null)
            {
                throw  new DataNotFoundException(6001,"up Category is not exist!!!");
            }
            obj.setMerchantId(parentCategory.getMerchantId());
        }
        return  obj;
    }

    @Override
    public boolean batchInsert(List<Category> record)
    {
        CategoryServiceImpl curThis = this;
        List<Category> categoryList =  record.stream().map(category -> {
            return curThis.fillMerchantIdByParentCategor(category);
        }).collect(Collectors.toList());

        return super.batchInsert(categoryList);
    }

    @Override
    public List<CategoryExt> listTreeCategory()
    {
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.isNull("up_category_id");
        List<Category> parentCategoryList = categoryMapper.selectList(categoryQueryWrapper);
        List<Long> parentCategoryIds = parentCategoryList.stream().map(category -> category.getId()).collect(Collectors.toList());

        List<CategoryExt> rootCategoryList = null;
        if(parentCategoryIds.size() > 0)
        {
            QueryWrapper<Category> subQueryWrapper = new QueryWrapper<>();
            subQueryWrapper.in("up_category_id",parentCategoryIds);
            List<Category> subCategoryList = categoryMapper.selectList(subQueryWrapper);

            rootCategoryList =  parentCategoryList.stream().map(parentCategory->{
                CategoryExt categoryExt = new CategoryExt();
                BeanHelper.copyPropertiesIgnoreNull(parentCategory,categoryExt);

                List<Category> curSubCategory =  subCategoryList.stream()
                        .filter(category -> category.getUpCategoryId().equals(parentCategory.getId()))
                        .collect(Collectors.toList());

                List<CategoryExt> curExtSubCategory   =    curSubCategory.stream().map(category -> {
                            CategoryExt subCategoryExt = new CategoryExt();
                            BeanHelper.copyPropertiesIgnoreNull(category,subCategoryExt);
                            return  subCategoryExt;
                        })
                        .collect(Collectors.toList());

                categoryExt.setSubCategories(curExtSubCategory);
                return  categoryExt;
            }).collect(Collectors.toList());
        }
        else
        {
            rootCategoryList = new ArrayList<>();
        }

        return  rootCategoryList;
    }

    @Override
    public List<Category> getCategoryByName(String name) {
        CategoryQueryVo categoryQueryVo = new CategoryQueryVo();
        categoryQueryVo.setName(name);
        ListResponse<Category> queryCategories  = this.list(categoryQueryVo,1,10);
        if(queryCategories.getItems().size() > 0)
        {
            return queryCategories.getItems();
        }
        else
        {
            return  null;
        }
    }

    @Override
    public List<Category> getCategoryByParentId(String parentCategoryId) {
        CategoryQueryVo categoryQueryVo = new CategoryQueryVo();
        categoryQueryVo.setUpCategoryId(parentCategoryId);
        ListResponse<Category> queryCategories  = this.list(categoryQueryVo,1,10000);
        if(queryCategories.getItems().size() > 0)
        {
            return queryCategories.getItems();
        }
        else
        {
            return  null;
        }
    }


}
