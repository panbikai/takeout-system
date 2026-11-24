package com.nnu.takeout.dto;

import com.nnu.takeout.entity.OrderDetail;
import com.nnu.takeout.entity.Orders;

import java.util.List;

/**
 * 订单 DTO：订单主体 + 订单明细列表。
 */
public class OrdersDto extends Orders {

    private List<OrderDetail> orderDetails;

    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }
}
