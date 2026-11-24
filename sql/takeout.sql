-- =====================================================================
-- 项目一《外卖管理系统的设计与实现》数据库脚本
-- 数据库：takeout_db    字符集：utf8mb4
-- 说明：本脚本包含建库、建表与初始测试数据，可在 MySQL 8.x 中直接执行。
-- =====================================================================

DROP DATABASE IF EXISTS takeout_db;
CREATE DATABASE takeout_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE takeout_db;

-- ---------------------------------------------------------------------
-- 1. 员工表（后台管理端）
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(32)  NOT NULL COMMENT '姓名',
    username    VARCHAR(32)  NOT NULL COMMENT '登录用户名',
    password    VARCHAR(64)  NOT NULL COMMENT '密码（实践项目明文存储，生产环境应加密）',
    phone       VARCHAR(11)  NOT NULL COMMENT '手机号',
    sex         VARCHAR(2)   COMMENT '性别：1男 0女',
    id_number   VARCHAR(18)  COMMENT '身份证号',
    status      INT          NOT NULL DEFAULT 1 COMMENT '状态：1启用 0禁用',
    create_time DATETIME     COMMENT '创建时间',
    update_time DATETIME     COMMENT '修改时间',
    create_user BIGINT       COMMENT '创建人',
    update_user BIGINT       COMMENT '修改人',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '员工表';

-- ---------------------------------------------------------------------
-- 2. 分类表（菜品分类 / 套餐分类）
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS category;
CREATE TABLE category (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    type        INT          NOT NULL COMMENT '类型：1菜品分类 2套餐分类',
    name        VARCHAR(32)  NOT NULL COMMENT '分类名称',
    sort        INT          NOT NULL DEFAULT 0 COMMENT '排序号，越小越靠前',
    status      INT          NOT NULL DEFAULT 1 COMMENT '状态：1启用 0禁用',
    create_time DATETIME     COMMENT '创建时间',
    update_time DATETIME     COMMENT '修改时间',
    create_user BIGINT       COMMENT '创建人',
    update_user BIGINT       COMMENT '修改人',
    PRIMARY KEY (id),
    UNIQUE KEY uk_category_name (name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '分类表';

-- ---------------------------------------------------------------------
-- 3. 菜品表
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS dish;
CREATE TABLE dish (
    id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(32)   NOT NULL COMMENT '菜品名称',
    category_id BIGINT        NOT NULL COMMENT '所属分类id',
    price       DECIMAL(10,2) NOT NULL COMMENT '售价',
    image       VARCHAR(255)  COMMENT '图片地址',
    description VARCHAR(255)  COMMENT '描述',
    status      INT           NOT NULL DEFAULT 1 COMMENT '售卖状态：1启售 0停售',
    sort        INT           NOT NULL DEFAULT 0 COMMENT '排序号',
    create_time DATETIME      COMMENT '创建时间',
    update_time DATETIME      COMMENT '修改时间',
    create_user BIGINT        COMMENT '创建人',
    update_user BIGINT        COMMENT '修改人',
    PRIMARY KEY (id),
    UNIQUE KEY uk_dish_name (name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '菜品表';

-- ---------------------------------------------------------------------
-- 4. 套餐表
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS setmeal;
CREATE TABLE setmeal (
    id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(32)   NOT NULL COMMENT '套餐名称',
    category_id BIGINT        NOT NULL COMMENT '所属分类id',
    price       DECIMAL(10,2) NOT NULL COMMENT '套餐售价',
    image       VARCHAR(255)  COMMENT '图片地址',
    description VARCHAR(255)  COMMENT '描述',
    status      INT           NOT NULL DEFAULT 1 COMMENT '售卖状态：1启售 0停售',
    create_time DATETIME      COMMENT '创建时间',
    update_time DATETIME      COMMENT '修改时间',
    create_user BIGINT        COMMENT '创建人',
    update_user BIGINT        COMMENT '修改人',
    PRIMARY KEY (id),
    UNIQUE KEY uk_setmeal_name (name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '套餐表';

-- ---------------------------------------------------------------------
-- 5. 套餐菜品关系表
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS setmeal_dish;
CREATE TABLE setmeal_dish (
    id         BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    setmeal_id BIGINT        NOT NULL COMMENT '套餐id',
    dish_id    BIGINT        NOT NULL COMMENT '菜品id',
    name       VARCHAR(32)   COMMENT '菜品名称（冗余）',
    price      DECIMAL(10,2) COMMENT '菜品单价（冗余）',
    copies     INT           NOT NULL DEFAULT 1 COMMENT '份数',
    PRIMARY KEY (id),
    KEY idx_setmeal_id (setmeal_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '套餐菜品关系表';

-- ---------------------------------------------------------------------
-- 6. 用户表（前台 C 端）
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id         BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    name       VARCHAR(32) COMMENT '昵称',
    phone      VARCHAR(11) NOT NULL COMMENT '手机号（登录账号）',
    sex        VARCHAR(2)  COMMENT '性别：1男 0女',
    id_number  VARCHAR(18) COMMENT '身份证号',
    avatar     VARCHAR(255) COMMENT '头像地址',
    status     INT         NOT NULL DEFAULT 1 COMMENT '状态：1正常 0禁用',
    PRIMARY KEY (id),
    UNIQUE KEY uk_phone (phone)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '用户表';

-- ---------------------------------------------------------------------
-- 7. 地址簿表
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS address_book;
CREATE TABLE address_book (
    id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id     BIGINT      NOT NULL COMMENT '所属用户id',
    consignee   VARCHAR(32) NOT NULL COMMENT '收货人',
    phone       VARCHAR(11) NOT NULL COMMENT '联系电话',
    sex         VARCHAR(2)  COMMENT '性别：1男 0女',
    province    VARCHAR(32) COMMENT '省份',
    city        VARCHAR(32) COMMENT '城市',
    district    VARCHAR(32) COMMENT '区县',
    detail      VARCHAR(255) COMMENT '详细地址',
    label       VARCHAR(32) COMMENT '标签：家/公司/学校',
    is_default  INT         NOT NULL DEFAULT 0 COMMENT '是否默认：1是 0否',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '地址簿表';

-- ---------------------------------------------------------------------
-- 8. 购物车表
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS shopping_cart;
CREATE TABLE shopping_cart (
    id         BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    name       VARCHAR(32)   COMMENT '商品名称（冗余）',
    image      VARCHAR(255)  COMMENT '图片地址（冗余）',
    user_id    BIGINT        NOT NULL COMMENT '所属用户id',
    dish_id    BIGINT        COMMENT '菜品id',
    setmeal_id BIGINT        COMMENT '套餐id',
    dish_flavor VARCHAR(255) COMMENT '口味/规格',
    number     INT           NOT NULL DEFAULT 1 COMMENT '购买数量',
    amount     DECIMAL(10,2) NOT NULL COMMENT '单价',
    create_time DATETIME      COMMENT '加入时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '购物车表';

-- ---------------------------------------------------------------------
-- 9. 订单表
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    id              BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    number          VARCHAR(32)   NOT NULL COMMENT '订单号',
    status          INT           NOT NULL DEFAULT 1 COMMENT '状态：1待付款 2待派送 3已派送 4已完成 5已取消',
    user_id         BIGINT        NOT NULL COMMENT '下单用户id',
    address_book_id BIGINT        COMMENT '收货地址id',
    consignee       VARCHAR(32)   COMMENT '收货人',
    phone           VARCHAR(11)   COMMENT '联系电话',
    address         VARCHAR(255)  COMMENT '收货地址（拼接）',
    remark          VARCHAR(255)  COMMENT '备注',
    amount          DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
    pay_method      INT           NOT NULL DEFAULT 1 COMMENT '支付方式：1微信 2支付宝 3现金',
    pay_status      INT           NOT NULL DEFAULT 0 COMMENT '支付状态：0未支付 1已支付',
    create_time     DATETIME      COMMENT '下单时间',
    checkout_time   DATETIME      COMMENT '结算时间',
    pay_time        DATETIME      COMMENT '支付时间',
    update_time     DATETIME      COMMENT '修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_number (number),
    KEY idx_user_id (user_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '订单表';

-- ---------------------------------------------------------------------
-- 10. 订单明细表
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS order_detail;
CREATE TABLE order_detail (
    id         BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    name       VARCHAR(32)   COMMENT '商品名称（冗余）',
    image      VARCHAR(255)  COMMENT '图片地址（冗余）',
    order_id   BIGINT        NOT NULL COMMENT '订单id',
    dish_id    BIGINT        COMMENT '菜品id',
    setmeal_id BIGINT        COMMENT '套餐id',
    dish_flavor VARCHAR(255) COMMENT '口味/规格',
    number     INT           NOT NULL DEFAULT 1 COMMENT '购买数量',
    amount     DECIMAL(10,2) COMMENT '单价',
    PRIMARY KEY (id),
    KEY idx_order_id (order_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '订单明细表';

-- =====================================================================
-- 初始测试数据
-- =====================================================================

-- 后台员工：账号 admin，密码 123456
INSERT INTO employee (name, username, password, phone, sex, status, create_time, update_time)
VALUES ('管理员', 'admin', '123456', '13800000000', '1', 1, NOW(), NOW());

-- 分类：type=1 菜品分类，type=2 套餐分类
INSERT INTO category (type, name, sort, status, create_time, update_time) VALUES
(1, '热销单品', 1, 1, NOW(), NOW()),
(1, '主食面点', 2, 1, NOW(), NOW()),
(1, '汤品饮品', 3, 1, NOW(), NOW()),
(2, '单人套餐', 1, 1, NOW(), NOW()),
(2, '双人套餐', 2, 1, NOW(), NOW());

-- 菜品（category_id 对应上面分类）
INSERT INTO dish (name, category_id, price, image, description, status, sort, create_time, update_time) VALUES
('宫保鸡丁', 1, 28.00, '', '鸡肉、花生米、干辣椒，香辣下饭', 1, 1, NOW(), NOW()),
('麻婆豆腐', 1, 18.00, '', '嫩豆腐配麻辣肉酱，经典川菜', 1, 2, NOW(), NOW()),
('红烧肉',   1, 32.00, '', '五花腩慢炖，肥而不腻', 1, 3, NOW(), NOW()),
('扬州炒饭', 2, 16.00, '', '火腿、鸡蛋、青豆粒粒分明', 1, 1, NOW(), NOW()),
('牛肉拉面', 2, 20.00, '', '手工拉面配清炖牛肉', 1, 2, NOW(), NOW()),
('紫菜蛋花汤', 3, 8.00, '', '清淡爽口，解腻佳品', 1, 1, NOW(), NOW()),
('可乐',     3, 5.00,  '', '冰镇碳酸饮料 330ml', 1, 2, NOW(), NOW());

-- 套餐
INSERT INTO setmeal (name, category_id, price, image, description, status, create_time, update_time) VALUES
('单人商务套餐A', 4, 35.00, '', '主食 + 菜品 + 饮品', 1, NOW(), NOW()),
('双人欢聚套餐', 5, 68.00, '', '两荤一素 + 两份主食 + 饮品', 1, NOW(), NOW());

-- 套餐菜品关系（setmeal_id=1 单人套餐A：宫保鸡丁 + 扬州炒饭 + 可乐）
INSERT INTO setmeal_dish (setmeal_id, dish_id, name, price, copies) VALUES
(1, 1, '宫保鸡丁', 28.00, 1),
(1, 4, '扬州炒饭', 16.00, 1),
(1, 7, '可乐',     5.00,  1);

-- 前台测试用户：手机号 13900000000（验证码登录，验证码见后端控制台 / 文档说明）
INSERT INTO user (name, phone, sex, status) VALUES ('测试用户', '13900000000', '1', 1);
