package com.nnu.takeout.entity;

import java.math.BigDecimal;

/**
 * 套餐菜品关系实体
 */
public class SetmealDish {

    private Long id;
    private Long setmealId;
    private Long dishId;
    private String name;
    private BigDecimal price;
    private Integer copies;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSetmealId() { return setmealId; }
    public void setSetmealId(Long setmealId) { this.setmealId = setmealId; }
    public Long getDishId() { return dishId; }
    public void setDishId(Long dishId) { this.dishId = dishId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getCopies() { return copies; }
    public void setCopies(Integer copies) { this.copies = copies; }
}
