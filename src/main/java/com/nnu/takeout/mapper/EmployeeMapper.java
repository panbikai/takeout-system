package com.nnu.takeout.mapper;

import com.nnu.takeout.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {

    /** 根据用户名查询员工（登录校验） */
    Employee getByUsername(@Param("username") String username);

    /** 新增员工 */
    int insert(Employee employee);

    /** 按姓名模糊查询员工列表 */
    List<Employee> list(@Param("name") String name);

    /** 根据 id 查询员工 */
    Employee getById(@Param("id") Long id);

    /** 修改员工信息 / 状态 */
    int update(Employee employee);
}
