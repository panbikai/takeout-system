package com.nnu.takeout.mapper;

import com.nnu.takeout.dto.DishDto;
import com.nnu.takeout.entity.Dish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DishMapper {

    /** 条件分页/列表查询菜品（关联分类表带出分类名） */
    List<DishDto> list(@Param("name") String name, @Param("categoryId") Long categoryId, @Param("status") Integer status);

    /** 新增菜品 */
    int insert(Dish dish);

    /** 根据 id 查询菜品 */
    Dish getById(@Param("id") Long id);

    /** 修改菜品 */
    int update(Dish dish);

    /** 删除菜品 */
    int deleteById(@Param("id") Long id);

    /** 批量/单个修改售卖状态 */
    int updateStatus(@Param("status") Integer status, @Param("id") Long id);

    /** 按分类查询启售菜品（用户端首页展示） */
    List<Dish> listByCategoryId(@Param("categoryId") Long categoryId);
}
