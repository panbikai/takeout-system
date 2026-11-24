package com.nnu.takeout.service;

import com.nnu.takeout.entity.Category;

import java.util.List;

public interface CategoryService {

    /** 分类列表（可按类型过滤） */
    List<Category> list(Integer type, String name);

    /** 新增分类 */
    void save(Category category);

    /** 修改分类 */
    void update(Category category);

    /** 删除分类（若下挂菜品或套餐则禁止删除） */
    void delete(Long id);

    Category getById(Long id);
}
