package com.nnu.takeout.controller.admin;

import com.nnu.takeout.common.R;
import com.nnu.takeout.entity.Category;
import com.nnu.takeout.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理：新增、查询、修改、删除
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /** 查询分类列表（type：1菜品分类 2套餐分类） */
    @GetMapping("/list")
    public R<List<Category>> list(@RequestParam(required = false) Integer type,
                                  @RequestParam(required = false) String name) {
        return R.success(categoryService.list(type, name));
    }

    /** 新增分类 */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /** 修改分类 */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.update(category);
        return R.success("修改分类成功");
    }

    /** 删除分类 */
    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return R.success("删除分类成功");
    }
}
