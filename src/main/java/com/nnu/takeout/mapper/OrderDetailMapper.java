package com.nnu.takeout.mapper;

import com.nnu.takeout.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDetailMapper {

    /** 批量插入订单明细 */
    int batchInsert(@Param("list") List<OrderDetail> orderDetails);

    /** 根据订单 id 查询明细列表 */
    List<OrderDetail> listByOrderId(@Param("orderId") Long orderId);
}
