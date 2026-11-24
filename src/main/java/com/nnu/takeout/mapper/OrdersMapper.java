package com.nnu.takeout.mapper;

import com.nnu.takeout.entity.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdersMapper {

    /** 新增订单 */
    int insert(Orders orders);

    /** 条件查询订单列表（后台按状态/订单号，用户端按用户） */
    List<Orders> list(@Param("userId") Long userId,
                      @Param("status") Integer status,
                      @Param("number") String number);

    /** 根据 id 查询订单 */
    Orders getById(@Param("id") Long id);

    /** 修改订单状态等信息 */
    int update(Orders orders);
}
