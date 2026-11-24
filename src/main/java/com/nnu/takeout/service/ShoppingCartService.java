package com.nnu.takeout.service;

import com.nnu.takeout.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /** 查询购物车 */
    List<ShoppingCart> list(Long userId);

    /** 加入购物车（同款数量累加） */
    void add(ShoppingCart shoppingCart);

    /** 修改购物车商品数量 */
    void update(ShoppingCart shoppingCart);

    /** 删除单条购物车记录 */
    void deleteItem(Long id);

    /** 清空购物车（下单成功后调用） */
    void clean(Long userId);
}
