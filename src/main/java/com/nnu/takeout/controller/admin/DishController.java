package com.nnu.takeout.controller.admin;

import com.nnu.takeout.common.R;
import com.nnu.takeout.dto.DishDto;
import com.nnu.takeout.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理：新增、查询、修改、启售 / 停售、删除
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /** 菜品列表（按名称、分类、状态筛选） */
    @GetMapping("/list")
    public R<List<DishDto>> list(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) Long categoryId,
                                 @RequestParam(required = false) Integer status) {
        return R.success(dishService.list(name, categoryId, status));
    }

    /** 新增菜品 */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.save(dishDto);
        return R.success("新增菜品成功");
    }

    /** 根据 id 查询菜品（编辑回显） */
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id) {
        return R.success(dishService.getById(id));
    }

    /** 修改菜品 */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.update(dishDto);
        return R.success("修改菜品成功");
    }

    /** 启售 / 停售：status 1 启售 0 停售 */
    @PutMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, @RequestParam Long id) {
        dishService.updateStatus(status, id);
        return R.success(status == 1 ? "已启售" : "已停售");
    }

    /** 删除菜品 */
    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        dishService.delete(id);
        return R.success("删除菜品成功");
    }
}
