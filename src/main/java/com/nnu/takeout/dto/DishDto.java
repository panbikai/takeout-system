package com.nnu.takeout.dto;

import com.nnu.takeout.entity.Dish;

/**
 * 菜品 DTO：在菜品实体基础上携带分类名称，供前端展示。
 */
public class DishDto extends Dish {

    /** 分类名称（关联 category 表查询得到） */
    private String categoryName;

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
