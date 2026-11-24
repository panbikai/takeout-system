package com.nnu.takeout.service;

import com.nnu.takeout.dto.DishDto;
import com.nnu.takeout.entity.Dish;

import java.util.List;

public interface DishService {

    /** 条件查询菜品列表 */
    List<DishDto> list(String name, Long categoryId, Integer status);

    /** 新增菜品 */
    void save(DishDto dishDto);

    /** 根据 id 查询菜品（含分类名） */
    DishDto getById(Long id);

    /** 修改菜品 */
    void update(DishDto dishDto);

    /** 删除菜品（被套餐引用时禁止删除） */
    void delete(Long id);

    /** 启售 / 停售 */
    void updateStatus(Integer status, Long id);

    /** 按分类查询启售菜品（用户端首页） */
    List<Dish> listByCategoryId(Long categoryId);
}
