package com.vendor.bean.merchandise;

import java.util.List;

public class CategoryExt extends Category {


    public List<CategoryExt> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryExt> subCategories) {
        this.subCategories = subCategories;
    }

    List<CategoryExt> subCategories;


}
