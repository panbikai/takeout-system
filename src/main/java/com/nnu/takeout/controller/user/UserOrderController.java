package com.nnu.takeout.controller.user;

import com.nnu.takeout.common.BaseContext;
import com.nnu.takeout.common.R;
import com.nnu.takeout.dto.OrdersDto;
import com.nnu.takeout.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户端：下单、我的订单、订单详情
 */
@RestController
@RequestMapping("/user/order")
public class UserOrderController {

    @Autowired
    private OrdersService ordersService;

    /** 提交结算，生成订单 */
    @PostMapping("/submit")
    public R<OrdersDto> submit(@RequestBody OrdersDto ordersDto) {
        OrdersDto result = ordersService.submit(ordersDto, BaseContext.getCurrentId());
        return R.success(result);
    }

    /** 我的订单列表（可按状态筛选） */
    @GetMapping("/list")
    public R<List<OrdersDto>> list(@RequestParam(required = false) Integer status) {
        return R.success(ordersService.listForUser(BaseContext.getCurrentId(), status));
    }

    /** 订单详情 */
    @GetMapping("/{id}")
    public R<OrdersDto> detail(@PathVariable Long id) {
        return R.success(ordersService.getDetail(id));
    }
}
