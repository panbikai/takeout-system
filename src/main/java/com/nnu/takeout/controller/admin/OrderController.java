package com.nnu.takeout.controller.admin;

import com.nnu.takeout.common.R;
import com.nnu.takeout.dto.OrdersDto;
import com.nnu.takeout.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 后台订单管理：订单明细查询、修改订单状态
 */
@RestController
@RequestMapping("/employee/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    /** 订单列表（可按状态、订单号筛选） */
    @GetMapping("/list")
    public R<List<OrdersDto>> list(@RequestParam(required = false) Integer status,
                                   @RequestParam(required = false) String number) {
        return R.success(ordersService.listForAdmin(status, number));
    }

    /** 订单明细查询 */
    @GetMapping("/{id}")
    public R<OrdersDto> detail(@PathVariable Long id) {
        return R.success(ordersService.getDetail(id));
    }

    /** 修改订单状态：请求体 {id, status} */
    @PutMapping("/status")
    public R<String> updateStatus(@RequestBody Map<String, Long> body) {
        Long id = body.get("id");
        Integer status = body.get("status").intValue();
        ordersService.updateStatus(id, status);
        return R.success("订单状态已更新");
    }
}
