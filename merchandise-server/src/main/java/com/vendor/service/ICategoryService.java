package com.vendor.service;

import com.vendor.bean.merchandise.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vendor.bean.merchandise.CategoryExt;
import com.vendor.entity.ListResponse;
import com.vendor.queryvo.merchandise.CategoryQueryVo;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * 商品分类。 服务类
 * </p>
 *
 * @author lpy
 * @since 2019-06-18
 */
public interface ICategoryService extends IBasePlusService<Category,CategoryQueryVo> {

    public List<CategoryExt> listTreeCategory();

    public List<Category> getCategoryByName(String name);

    public List<Category> getCategoryByParentId(String parentCategoryId);

}
