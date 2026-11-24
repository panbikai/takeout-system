package com.nnu.takeout.common;

/**
 * 系统常量：Session 键、启用/停售状态、订单状态
 */
public class Constants {

    /** 后台员工登录后存入 Session 的键 */
    public static final String EMPLOYEE_SESSION = "employee";

    /** 前台用户登录后存入 Session 的键 */
    public static final String USER_SESSION = "user";

    /** 短信/验证码登录时存入 Session 的验证码键 */
    public static final String CAPTCHA_SESSION = "captcha";

    /** 启用 / 启售 / 默认地址 */
    public static final int ENABLE = 1;

    /** 禁用 / 停售 / 非默认地址 */
    public static final int DISABLE = 0;

    /** 订单状态：待付款 */
    public static final int ORDER_PENDING_PAYMENT = 1;

    /** 订单状态：待派送（已付款） */
    public static final int ORDER_PENDING_DELIVERY = 2;

    /** 订单状态：已派送 */
    public static final int ORDER_DELIVERED = 3;

    /** 订单状态：已完成 */
    public static final int ORDER_COMPLETED = 4;

    /** 订单状态：已取消 */
    public static final int ORDER_CANCELLED = 5;
}
