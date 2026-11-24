package com.nnu.takeout.mapper;

import com.nnu.takeout.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {

    /** 条件查询分类列表（可按类型 type 过滤，按 sort 升序） */
    List<Category> list(@Param("type") Integer type, @Param("name") String name);

    /** 新增分类 */
    int insert(Category category);

    /** 根据 id 查询分类 */
    Category getById(@Param("id") Long id);

    /** 修改分类 */
    int update(Category category);

    /** 删除分类 */
    int deleteById(@Param("id") Long id);

    /** 统计该分类下关联的菜品数量 */
    Long countByDish(@Param("categoryId") Long categoryId);

    /** 统计该分类下关联的套餐数量 */
    Long countBySetmeal(@Param("categoryId") Long categoryId);
}
