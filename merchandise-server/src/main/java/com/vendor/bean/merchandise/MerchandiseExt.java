package com.vendor.bean.merchandise;

public class MerchandiseExt extends Merchandise {
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private Brand brand;
    private Category category;


}
