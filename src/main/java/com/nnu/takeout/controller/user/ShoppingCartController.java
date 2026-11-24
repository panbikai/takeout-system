package com.nnu.takeout.controller.user;

import com.nnu.takeout.common.BaseContext;
import com.nnu.takeout.common.R;
import com.nnu.takeout.entity.ShoppingCart;
import com.nnu.takeout.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车：添加、查询、修改
 */
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /** 查询购物车 */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        return R.success(shoppingCartService.list(BaseContext.getCurrentId()));
    }

    /** 添加购物车 */
    @PostMapping("/add")
    public R<String> add(@RequestBody ShoppingCart shoppingCart) {
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCartService.add(shoppingCart);
        return R.success("已加入购物车");
    }

    /** 修改购物车商品数量 */
    @PutMapping
    public R<String> update(@RequestBody ShoppingCart shoppingCart) {
        shoppingCartService.update(shoppingCart);
        return R.success("购物车已更新");
    }

    /** 删除购物车单条记录 */
    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        shoppingCartService.deleteItem(id);
        return R.success("已移除");
    }
}
