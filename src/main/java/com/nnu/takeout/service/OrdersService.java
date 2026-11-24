package com.nnu.takeout.service;

import com.nnu.takeout.dto.OrdersDto;

import java.util.List;

public interface OrdersService {

    /** 用户下单：由购物车生成订单及订单明细，并清空购物车 */
    OrdersDto submit(OrdersDto ordersDto, Long userId);

    /** 用户端订单列表（含明细） */
    List<OrdersDto> listForUser(Long userId, Integer status);

    /** 后台订单列表（含明细，可按状态/订单号筛选） */
    List<OrdersDto> listForAdmin(Integer status, String number);

    /** 订单详情（含明细） */
    OrdersDto getDetail(Long id);

    /** 后台修改订单状态 */
    void updateStatus(Long id, Integer status);
}
