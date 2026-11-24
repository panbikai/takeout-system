package com.nnu.takeout.mapper;

import com.nnu.takeout.entity.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingCartMapper {

    /** 查询某用户购物车列表 */
    List<ShoppingCart> listByUserId(@Param("userId") Long userId);

    /** 按用户 + 菜品/套餐精确查询一条购物车记录（用于数量累加） */
    ShoppingCart getOne(@Param("userId") Long userId,
                        @Param("dishId") Long dishId,
                        @Param("setmealId") Long setmealId);

    /** 加入购物车 */
    int insert(ShoppingCart shoppingCart);

    /** 修改购物车商品数量 */
    int updateNumber(ShoppingCart shoppingCart);

    /** 清空某用户购物车（下单后调用） */
    int deleteByUserId(@Param("userId") Long userId);

    /** 删除购物车单条记录 */
    int deleteById(@Param("id") Long id);
}
