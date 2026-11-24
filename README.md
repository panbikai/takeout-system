# 外卖管理系统的设计与实现（项目一）

> 课程：数字化系统开发实践　　学生：潘必凯　　专业：2024 级智能科学与技术
> 技术栈：Spring Boot 2.7 + Spring MVC + MyBatis + MySQL + 原生 HTML/JS

## 一、项目简介

本系统为外卖点餐与后台管理一体化 Web 系统，分为**后台管理端**（员工使用）与**用户端**（顾客使用）两部分：

- 后台管理端：员工登录/退出、分类管理、菜品管理、套餐管理、订单管理、员工管理。
- 用户端：手机号验证码登录、首页分类与菜品展示、购物车、地址管理、下单、我的订单。

## 二、技术栈与环境

| 分类 | 技术 / 版本 | 说明 |
| --- | --- | --- |
| 语言 | Java 8 | 与本机 JDK 1.8.0_291 匹配 |
| 框架 | Spring Boot 2.7.18 | 自动配置、内嵌 Tomcat |
| Web | Spring MVC | REST 接口、拦截器 |
| 持久层 | MyBatis 2.3.1 | ORM 映射、动态 SQL |
| 数据库 | MySQL 8.x | 字符集 utf8mb4 |
| 前端 | HTML + CSS + 原生 JS(fetch) | 无需 Node 构建 |
| 构建 | Maven 3.6+ | IDEA / Eclipse 自带 Maven 即可 |

## 三、目录结构

```
takeout-system/
├── pom.xml                         # Maven 依赖与构建配置
├── sql/takeout.sql                 # 建库建表 + 初始测试数据
├── doc/                            # 需求分析与设计文档
└── src/main/
    ├── java/com/nnu/takeout/
    │   ├── TakeoutApplication.java # 启动类
    │   ├── common/                 # 统一响应 R、常量、ThreadLocal、全局异常
    │   ├── config/                 # Spring MVC 配置（拦截器注册）
    │   ├── interceptor/            # 登录校验拦截器
    │   ├── entity/                 # 数据库实体（10 张表）
    │   ├── dto/                    # 业务传输对象（菜品/套餐/订单扩展字段）
    │   ├── mapper/                 # MyBatis Mapper 接口
    │   ├── service/ + impl/        # 业务接口与实现
    │   └── controller/
    │       ├── admin/              # 后台管理端接口
    │       └── user/               # 用户端接口
    └── resources/
        ├── application.yml         # 数据源、端口、MyBatis 配置
        ├── mapper/                 # MyBatis XML 映射文件
        └── static/
            ├── admin/              # 后台页面
            └── user/               # 用户端页面
```

## 四、运行步骤

1. **准备数据库**：启动 MySQL，执行 `sql/takeout.sql`，自动创建 `takeout_db` 库及表与测试数据。
2. **准备配置文件**：把 `src/main/resources/application.yml.example` 复制为同目录下的 `application.yml`，再把 `username`、`password`（端口不一致时改 `url` 里的端口）改成本机 MySQL 的配置。
3. **导入工程**：用 IDEA / Eclipse 以 Maven 工程导入，等待依赖下载完成。
4. **启动**：运行 `TakeoutApplication` 主类，默认端口 `8080`。
5. **访问页面**：
   - 后台管理端：http://localhost:8080/admin/login.html
   - 用户端：http://localhost:8080/user/login.html

### 测试账号

| 端 | 账号 | 密码 / 验证码 |
| --- | --- | --- |
| 后台员工 | admin | 密码 `123456` |
| 前台用户 | 13900000000 | 点击「获取验证码」，验证码见弹窗 / 后端控制台 |

## 五、考核功能对照表

| 序号 | 考核要求 | 后端实现 | 页面 |
| --- | --- | --- | --- |
| 1 | 员工登录、退出，登录后拥有后台权限，退出清除状态 | `EmployeeController` login/logout + `LoginCheckInterceptor` | admin/login.html、index.html |
| 2 | 分类新增、查询、修改、删除 | `CategoryController` + `CategoryService` | admin/category.html |
| 3 | 菜品新增、查询、修改、启售/停售、删除 | `DishController` + `DishService` | admin/dish.html |
| 4 | 套餐新增、查询、修改、启售/停售、删除 | `SetmealController` + `SetmealService` | admin/setmeal.html |
| 5 | 订单明细查询、修改订单状态 | admin `OrderController` | admin/orders.html |
| 6 | 用户登录（获取验证码 + 登录） | `UserController` sendMsg/login | user/login.html |
| 7 | 地址列表、新增、设默认、编辑、删除 | `AddressBookController` + `AddressBookService` | user/address.html |
| 8 | 首页展示全部分类及对应菜品 | `IndexController` | user/index.html |
| 9 | 购物车添加、查询、修改 | `ShoppingCartController` + `ShoppingCartService` | user/cart.html |
| 10 | 提交结算生成订单 | `UserOrderController` submit + `OrdersService` | user/cart.html、user/orders.html |

## 六、主要接口清单

### 后台管理端

| 方法 | 路径 | 功能 |
| --- | --- | --- |
| POST | /employee/login | 员工登录 |
| POST | /employee/logout | 员工退出 |
| GET | /employee/list | 员工列表 |
| POST | /employee | 新增员工 |
| PUT | /employee | 修改员工 |
| GET | /category/list | 分类列表 |
| POST | /category | 新增分类 |
| PUT | /category | 修改分类 |
| DELETE | /category/{id} | 删除分类 |
| GET | /dish/list | 菜品列表 |
| POST | /dish | 新增菜品 |
| PUT | /dish | 修改菜品 |
| PUT | /dish/status/{status} | 菜品启售/停售 |
| DELETE | /dish/{id} | 删除菜品 |
| GET | /setmeal/list | 套餐列表 |
| POST | /setmeal | 新增套餐 |
| PUT | /setmeal | 修改套餐 |
| PUT | /setmeal/status/{status} | 套餐启售/停售 |
| DELETE | /setmeal/{id} | 删除套餐 |
| GET | /employee/order/list | 订单列表 |
| GET | /employee/order/{id} | 订单明细 |
| PUT | /employee/order/status | 修改订单状态 |

### 用户端

| 方法 | 路径 | 功能 |
| --- | --- | --- |
| POST | /user/sendMsg | 获取验证码 |
| POST | /user/login | 用户登录 |
| POST | /user/logout | 用户退出 |
| GET | /user/category/list | 首页分类列表 |
| GET | /user/dish/list | 分类下菜品 |
| GET | /address/list | 地址列表 |
| POST | /address | 新增地址 |
| PUT | /address | 编辑地址 |
| PUT | /address/default/{id} | 设为默认地址 |
| DELETE | /address/{id} | 删除地址 |
| GET | /cart/list | 查询购物车 |
| POST | /cart/add | 加入购物车 |
| PUT | /cart | 修改购物车数量 |
| DELETE | /cart/{id} | 删除购物车项 |
| POST | /user/order/submit | 提交订单 |
| GET | /user/order/list | 我的订单 |
| GET | /user/order/{id} | 订单详情 |

## 七、考核知识点对应

- **Spring IOC**：Service / Mapper / Controller 均通过 `@Autowired` 注入，对象由 Spring 容器创建与管理。
- **Spring AOP**：登录校验拦截器、全局异常处理器以切面方式横切业务方法，无侵入式完成鉴权与异常封装。
- **MyBatis ORM**：实体属性与表字段通过 XML `<resultMap>` 映射，`map-underscore-to-camel-case` 完成下划线转驼峰。
- **Spring Boot 自动配置**：依赖 `spring-boot-starter-web` 自动装配内嵌 Tomcat、Spring MVC、JSON 转换器。
- **Spring MVC 请求流程**：前端请求 → 拦截器 → `@RestController` → Service → Mapper → 数据库，统一返回 `R<T>`。
- **MyBatis 动态 SQL**：`<where>`、`<if>`、`<foreach>` 实现条件查询与批量插入。

## 八、说明

- 本项目为课程实践作品，验证码、密码均为模拟实现；生产环境应接入短信网关并对密码加密存储（如 BCrypt）。
- 订单状态字典：1 待付款、2 待派送、3 已派送、4 已完成、5 已取消。
- 登录拦截器对 `/**/*.html`、css/js/图片等静态资源放行，避免未登录访问页面时被拦截成 NOTLOGIN 的 JSON；业务接口仍受拦截器保护。
- 加入购物车只接收 `dishId` / `setmealId` 与数量，商品名称、图片、单价一律由后端查库回填并校验启售状态，客户端无法指定价格；单项数量限制在 1~99。
