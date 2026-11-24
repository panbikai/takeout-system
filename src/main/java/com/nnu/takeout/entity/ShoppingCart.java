package com.nnu.takeout.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车实体
 */
public class ShoppingCart {

    private Long id;
    private String name;
    private String image;
    private Long userId;
    private Long dishId;
    private Long setmealId;
    private String dishFlavor;
    private Integer number;
    private BigDecimal amount;
    private LocalDateTime createTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getDishId() { return dishId; }
    public void setDishId(Long dishId) { this.dishId = dishId; }
    public Long getSetmealId() { return setmealId; }
    public void setSetmealId(Long setmealId) { this.setmealId = setmealId; }
    public String getDishFlavor() { return dishFlavor; }
    public void setDishFlavor(String dishFlavor) { this.dishFlavor = dishFlavor; }
    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
