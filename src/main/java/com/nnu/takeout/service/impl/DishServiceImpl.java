package com.nnu.takeout.service.impl;

import com.nnu.takeout.dto.DishDto;
import com.nnu.takeout.entity.Dish;
import com.nnu.takeout.mapper.DishMapper;
import com.nnu.takeout.mapper.SetmealDishMapper;
import com.nnu.takeout.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    public List<DishDto> list(String name, Long categoryId, Integer status) {
        return dishMapper.list(name, categoryId, status);
    }

    @Override
    public void save(DishDto dishDto) {
        dishDto.setCreateTime(LocalDateTime.now());
        dishDto.setUpdateTime(LocalDateTime.now());
        if (dishDto.getStatus() == null) {
            dishDto.setStatus(1);
        }
        dishMapper.insert(dishDto);
    }

    @Override
    public DishDto getById(Long id) {
        Dish dish = dishMapper.getById(id);
        if (dish == null) {
            return null;
        }
        DishDto dto = new DishDto();
        org.springframework.beans.BeanUtils.copyProperties(dish, dto);
        return dto;
    }

    @Override
    public void update(DishDto dishDto) {
        dishDto.setUpdateTime(LocalDateTime.now());
        dishMapper.update(dishDto);
    }

    @Override
    public void delete(Long id) {
        Long used = setmealDishMapper.countByDishId(id);
        if (used != null && used > 0) {
            throw new RuntimeException("该菜品已被套餐引用，不能删除");
        }
        dishMapper.deleteById(id);
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        dishMapper.updateStatus(status, id);
    }

    @Override
    public List<Dish> listByCategoryId(Long categoryId) {
        return dishMapper.listByCategoryId(categoryId);
    }
}
