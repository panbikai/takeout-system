package com.nnu.takeout.service.impl;

import com.nnu.takeout.dto.SetmealDto;
import com.nnu.takeout.entity.Setmeal;
import com.nnu.takeout.entity.SetmealDish;
import com.nnu.takeout.mapper.SetmealDishMapper;
import com.nnu.takeout.mapper.SetmealMapper;
import com.nnu.takeout.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    public List<SetmealDto> list(String name, Long categoryId, Integer status) {
        return setmealMapper.list(name, categoryId, status);
    }

    @Override
    @Transactional
    public void save(SetmealDto setmealDto) {
        setmealDto.setCreateTime(LocalDateTime.now());
        setmealDto.setUpdateTime(LocalDateTime.now());
        if (setmealDto.getStatus() == null) {
            setmealDto.setStatus(1);
        }
        setmealMapper.insert(setmealDto);

        Long setmealId = setmealDto.getId();
        List<SetmealDish> dishes = setmealDto.getSetmealDishes();
        if (dishes != null && !dishes.isEmpty()) {
            dishes.forEach(d -> d.setSetmealId(setmealId));
            setmealDishMapper.batchInsert(dishes);
        }
    }

    @Override
    public SetmealDto getById(Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        if (setmeal == null) {
            return null;
        }
        SetmealDto dto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, dto);
        dto.setSetmealDishes(setmealDishMapper.listBySetmealId(id));
        return dto;
    }

    @Override
    @Transactional
    public void update(SetmealDto setmealDto) {
        setmealDto.setUpdateTime(LocalDateTime.now());
        setmealMapper.update(setmealDto);

        // 先删除旧关系，再写入新关系
        setmealDishMapper.deleteBySetmealId(setmealDto.getId());
        List<SetmealDish> dishes = setmealDto.getSetmealDishes();
        if (dishes != null && !dishes.isEmpty()) {
            dishes.forEach(d -> d.setSetmealId(setmealDto.getId()));
            setmealDishMapper.batchInsert(dishes);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        if (setmeal != null && setmeal.getStatus() != null && setmeal.getStatus() == 1) {
            throw new RuntimeException("启售中的套餐不能删除，请先停售");
        }
        setmealDishMapper.deleteBySetmealId(id);
        setmealMapper.deleteById(id);
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        setmealMapper.updateStatus(status, id);
    }
}
