package com.nnu.takeout.mapper;

import com.nnu.takeout.dto.SetmealDto;
import com.nnu.takeout.entity.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealMapper {

    /** 条件查询套餐列表（关联分类表带出分类名） */
    List<SetmealDto> list(@Param("name") String name, @Param("categoryId") Long categoryId, @Param("status") Integer status);

    /** 新增套餐 */
    int insert(Setmeal setmeal);

    /** 根据 id 查询套餐 */
    Setmeal getById(@Param("id") Long id);

    /** 修改套餐 */
    int update(Setmeal setmeal);

    /** 删除套餐 */
    int deleteById(@Param("id") Long id);

    /** 修改套餐售卖状态 */
    int updateStatus(@Param("status") Integer status, @Param("id") Long id);
}
