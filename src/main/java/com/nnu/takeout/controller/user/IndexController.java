package com.nnu.takeout.controller.user;

import com.nnu.takeout.common.R;
import com.nnu.takeout.entity.Category;
import com.nnu.takeout.entity.Dish;
import com.nnu.takeout.service.CategoryService;
import com.nnu.takeout.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户端首页：分类展示 + 分类下菜品展示
 */
@RestController
@RequestMapping("/user")
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishService dishService;

    /** 首页展示所有分类（默认菜品分类 type=1） */
    @GetMapping("/category/list")
    public R<List<Category>> categoryList(@RequestParam(required = false) Integer type) {
        return R.success(categoryService.list(type == null ? 1 : type, null));
    }

    /** 根据选中分类查询其包含的启售菜品 */
    @GetMapping("/dish/list")
    public R<List<Dish>> dishList(@RequestParam Long categoryId) {
        return R.success(dishService.listByCategoryId(categoryId));
    }
}
