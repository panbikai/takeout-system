package com.nnu.takeout.mapper;

import com.nnu.takeout.entity.SetmealDish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealDishMapper {

    /** 批量插入套餐-菜品关系 */
    int batchInsert(@Param("list") List<SetmealDish> setmealDishes);

    /** 根据套餐 id 查询包含的菜品 */
    List<SetmealDish> listBySetmealId(@Param("setmealId") Long setmealId);

    /** 根据套餐 id 删除关系 */
    int deleteBySetmealId(@Param("setmealId") Long setmealId);

    /** 统计某菜品被多少套餐引用 */
    Long countByDishId(@Param("dishId") Long dishId);
}
