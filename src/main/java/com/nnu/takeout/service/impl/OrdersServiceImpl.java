package com.nnu.takeout.service.impl;

import com.nnu.takeout.common.Constants;
import com.nnu.takeout.dto.OrdersDto;
import com.nnu.takeout.entity.*;
import com.nnu.takeout.mapper.*;
import com.nnu.takeout.service.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    @Transactional
    public OrdersDto submit(OrdersDto ordersDto, Long userId) {
        // 1. 查询当前用户购物车
        List<ShoppingCart> carts = shoppingCartMapper.listByUserId(userId);
        if (carts == null || carts.isEmpty()) {
            throw new RuntimeException("购物车为空，无法下单");
        }

        // 2. 汇总订单总金额
        BigDecimal amount = BigDecimal.ZERO;
        for (ShoppingCart cart : carts) {
            BigDecimal subtotal = cart.getAmount().multiply(BigDecimal.valueOf(cart.getNumber()));
            amount = amount.add(subtotal);
        }

        // 3. 校验收货地址
        AddressBook address = addressBookMapper.getById(ordersDto.getAddressBookId());
        if (address == null) {
            throw new RuntimeException("请选择收货地址");
        }
        String fullAddress = nullToEmpty(address.getProvince()) + nullToEmpty(address.getCity())
                + nullToEmpty(address.getDistrict()) + nullToEmpty(address.getDetail());

        // 4. 组装订单主体
        Orders orders = new Orders();
        orders.setNumber(generateOrderNumber());
        orders.setStatus(Constants.ORDER_PENDING_DELIVERY);
        orders.setUserId(userId);
        orders.setAddressBookId(address.getId());
        orders.setConsignee(address.getConsignee());
        orders.setPhone(address.getPhone());
        orders.setAddress(fullAddress);
        orders.setRemark(ordersDto.getRemark());
        orders.setAmount(amount);
        orders.setPayMethod(ordersDto.getPayMethod() == null ? 1 : ordersDto.getPayMethod());
        orders.setPayStatus(1);
        LocalDateTime now = LocalDateTime.now();
        orders.setCreateTime(now);
        orders.setCheckoutTime(now);
        orders.setPayTime(now);
        orders.setUpdateTime(now);
        ordersMapper.insert(orders);

        // 5. 购物车明细转为订单明细
        List<OrderDetail> details = new ArrayList<>();
        for (ShoppingCart cart : carts) {
            OrderDetail detail = new OrderDetail();
            detail.setName(cart.getName());
            detail.setImage(cart.getImage());
            detail.setOrderId(orders.getId());
            detail.setDishId(cart.getDishId());
            detail.setSetmealId(cart.getSetmealId());
            detail.setDishFlavor(cart.getDishFlavor());
            detail.setNumber(cart.getNumber());
            detail.setAmount(cart.getAmount());
            details.add(detail);
        }
        orderDetailMapper.batchInsert(details);

        // 6. 下单成功后清空购物车
        shoppingCartMapper.deleteByUserId(userId);

        // 7. 封装返回
        OrdersDto result = new OrdersDto();
        BeanUtils.copyProperties(orders, result);
        result.setOrderDetails(details);
        return result;
    }

    @Override
    public List<OrdersDto> listForUser(Long userId, Integer status) {
        List<Orders> orders = ordersMapper.list(userId, status, null);
        return assembleDetails(orders);
    }

    @Override
    public List<OrdersDto> listForAdmin(Integer status, String number) {
        List<Orders> orders = ordersMapper.list(null, status, number);
        return assembleDetails(orders);
    }

    @Override
    public OrdersDto getDetail(Long id) {
        Orders orders = ordersMapper.getById(id);
        if (orders == null) {
            return null;
        }
        OrdersDto dto = new OrdersDto();
        BeanUtils.copyProperties(orders, dto);
        dto.setOrderDetails(orderDetailMapper.listByOrderId(id));
        return dto;
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Orders orders = new Orders();
        orders.setId(id);
        orders.setStatus(status);
        orders.setUpdateTime(LocalDateTime.now());
        ordersMapper.update(orders);
    }

    /** 组装订单 + 明细 */
    private List<OrdersDto> assembleDetails(List<Orders> ordersList) {
        List<OrdersDto> result = new ArrayList<>();
        if (ordersList == null) {
            return result;
        }
        for (Orders orders : ordersList) {
            OrdersDto dto = new OrdersDto();
            BeanUtils.copyProperties(orders, dto);
            dto.setOrderDetails(orderDetailMapper.listByOrderId(orders.getId()));
            result.add(dto);
        }
        return result;
    }

    /** 生成订单号：DD + 时间戳 + 4 位随机数 */
    private String generateOrderNumber() {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int random = new Random().nextInt(9000) + 1000;
        return "DD" + time + random;
    }

    private String nullToEmpty(String s) {
        return s == null ? "" : s;
    }
}
