package com.nnu.takeout.service.impl;

import com.nnu.takeout.common.Constants;
import com.nnu.takeout.entity.Dish;
import com.nnu.takeout.entity.Setmeal;
import com.nnu.takeout.entity.ShoppingCart;
import com.nnu.takeout.mapper.DishMapper;
import com.nnu.takeout.mapper.SetmealMapper;
import com.nnu.takeout.mapper.ShoppingCartMapper;
import com.nnu.takeout.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    /** 单个商品最多购买份数，防止前端传入异常数量 */
    private static final int MAX_ITEM_NUMBER = 99;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public List<ShoppingCart> list(Long userId) {
        return shoppingCartMapper.listByUserId(userId);
    }

    @Override
    public void add(ShoppingCart shoppingCart) {
        Long userId = shoppingCart.getUserId();
        int number = normalizeNumber(shoppingCart.getNumber());

        // 单价、名称、图片一律以数据库为准，忽略前端传入值，避免篡改价格
        fillProductInfo(shoppingCart);

        ShoppingCart exist = shoppingCartMapper.getOne(userId, shoppingCart.getDishId(), shoppingCart.getSetmealId());
        if (exist != null) {
            int total = normalizeNumber(exist.getNumber()) + number;
            exist.setNumber(Math.min(total, MAX_ITEM_NUMBER));
            shoppingCartMapper.updateNumber(exist);
        } else {
            shoppingCart.setNumber(number);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        if (shoppingCart.getNumber() == null) {
            throw new RuntimeException("商品数量不能为空");
        }
        if (shoppingCart.getNumber() <= 0) {
            shoppingCartMapper.deleteById(shoppingCart.getId());
            return;
        }
        shoppingCart.setNumber(Math.min(shoppingCart.getNumber(), MAX_ITEM_NUMBER));
        shoppingCartMapper.updateNumber(shoppingCart);
    }

    /**
     * 按 dishId / setmealId 从数据库回填商品信息，并校验是否存在、是否启售。
     * 前端只允许传 id 与数量，价格不可由客户端指定。
     */
    private void fillProductInfo(ShoppingCart shoppingCart) {
        if (shoppingCart.getDishId() != null) {
            Dish dish = dishMapper.getById(shoppingCart.getDishId());
            if (dish == null) {
                throw new RuntimeException("菜品不存在或已下架");
            }
            if (dish.getStatus() == null || dish.getStatus() != Constants.ENABLE) {
                throw new RuntimeException("「" + dish.getName() + "」已停售，无法加入购物车");
            }
            shoppingCart.setName(dish.getName());
            shoppingCart.setImage(dish.getImage());
            shoppingCart.setAmount(dish.getPrice());
            shoppingCart.setSetmealId(null);
            return;
        }

        if (shoppingCart.getSetmealId() != null) {
            Setmeal setmeal = setmealMapper.getById(shoppingCart.getSetmealId());
            if (setmeal == null) {
                throw new RuntimeException("套餐不存在或已下架");
            }
            if (setmeal.getStatus() == null || setmeal.getStatus() != Constants.ENABLE) {
                throw new RuntimeException("「" + setmeal.getName() + "」已停售，无法加入购物车");
            }
            shoppingCart.setName(setmeal.getName());
            shoppingCart.setImage(setmeal.getImage());
            shoppingCart.setAmount(setmeal.getPrice());
            shoppingCart.setDishId(null);
            return;
        }

        throw new RuntimeException("请选择要加入购物车的菜品或套餐");
    }

    /** 数量归一化：为空或小于 1 时按 1 处理，上限 MAX_ITEM_NUMBER */
    private int normalizeNumber(Integer number) {
        if (number == null || number < 1) {
            return 1;
        }
        return Math.min(number, MAX_ITEM_NUMBER);
    }

    @Override
    public void deleteItem(Long id) {
        shoppingCartMapper.deleteById(id);
    }

    @Override
    public void clean(Long userId) {
        shoppingCartMapper.deleteByUserId(userId);
    }
}
