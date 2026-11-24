package com.nnu.takeout.service;

import com.nnu.takeout.entity.Employee;

import java.util.List;

public interface EmployeeService {

    /** 员工登录校验，成功返回员工对象，失败抛出业务异常 */
    Employee login(Employee employee);

    /** 员工列表（按姓名模糊查询） */
    List<Employee> list(String name);

    /** 根据 id 查询 */
    Employee getById(Long id);

    /** 新增员工 */
    void save(Employee employee);

    /** 修改员工（含启用/禁用） */
    void update(Employee employee);
}
