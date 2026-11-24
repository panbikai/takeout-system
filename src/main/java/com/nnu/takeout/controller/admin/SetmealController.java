package com.nnu.takeout.controller.admin;

import com.nnu.takeout.common.R;
import com.nnu.takeout.dto.SetmealDto;
import com.nnu.takeout.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 套餐管理：新增、查询、修改、启售 / 停售、删除
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /** 套餐列表（按名称、分类、状态筛选） */
    @GetMapping("/list")
    public R<List<SetmealDto>> list(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) Long categoryId,
                                    @RequestParam(required = false) Integer status) {
        return R.success(setmealService.list(name, categoryId, status));
    }

    /** 新增套餐（含套餐内菜品） */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.save(setmealDto);
        return R.success("新增套餐成功");
    }

    /** 根据 id 查询套餐（含菜品明细，编辑回显） */
    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id) {
        return R.success(setmealService.getById(id));
    }

    /** 修改套餐 */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        setmealService.update(setmealDto);
        return R.success("修改套餐成功");
    }

    /** 启售 / 停售 */
    @PutMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, @RequestParam Long id) {
        setmealService.updateStatus(status, id);
        return R.success(status == 1 ? "已启售" : "已停售");
    }

    /** 删除套餐 */
    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        setmealService.delete(id);
        return R.success("删除套餐成功");
    }
}
