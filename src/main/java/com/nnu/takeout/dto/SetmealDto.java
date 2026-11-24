package com.nnu.takeout.dto;

import com.nnu.takeout.entity.Setmeal;
import com.nnu.takeout.entity.SetmealDish;

import java.util.List;

/**
 * 套餐 DTO：套餐实体 + 分类名称 + 套餐内含菜品明细。
 */
public class SetmealDto extends Setmeal {

    private String categoryName;

    private List<SetmealDish> setmealDishes;

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public List<SetmealDish> getSetmealDishes() { return setmealDishes; }
    public void setSetmealDishes(List<SetmealDish> setmealDishes) { this.setmealDishes = setmealDishes; }
}
