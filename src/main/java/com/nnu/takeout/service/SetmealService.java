package com.nnu.takeout.service;

import com.nnu.takeout.dto.SetmealDto;

import java.util.List;

public interface SetmealService {

    /** 条件查询套餐列表 */
    List<SetmealDto> list(String name, Long categoryId, Integer status);

    /** 新增套餐（含套餐内菜品关系） */
    void save(SetmealDto setmealDto);

    /** 根据 id 查询套餐（含菜品明细） */
    SetmealDto getById(Long id);

    /** 修改套餐（含套餐内菜品关系） */
    void update(SetmealDto setmealDto);

    /** 删除套餐（启售状态下禁止删除） */
    void delete(Long id);

    /** 启售 / 停售 */
    void updateStatus(Integer status, Long id);
}
