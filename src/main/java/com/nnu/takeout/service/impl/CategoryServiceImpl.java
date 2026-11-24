package com.nnu.takeout.service.impl;

import com.nnu.takeout.entity.Category;
import com.nnu.takeout.mapper.CategoryMapper;
import com.nnu.takeout.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> list(Integer type, String name) {
        return categoryMapper.list(type, name);
    }

    @Override
    public void save(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.insert(category);
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void delete(Long id) {
        Long dishCount = categoryMapper.countByDish(id);
        Long setmealCount = categoryMapper.countBySetmeal(id);
        if (dishCount != null && dishCount > 0) {
            throw new RuntimeException("该分类下仍有菜品，不能删除");
        }
        if (setmealCount != null && setmealCount > 0) {
            throw new RuntimeException("该分类下仍有套餐，不能删除");
        }
        categoryMapper.deleteById(id);
    }

    @Override
    public Category getById(Long id) {
        return categoryMapper.getById(id);
    }
}
