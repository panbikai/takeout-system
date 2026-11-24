package com.nnu.takeout.service.impl;

import com.nnu.takeout.common.Constants;
import com.nnu.takeout.entity.Employee;
import com.nnu.takeout.mapper.EmployeeMapper;
import com.nnu.takeout.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee login(Employee employee) {
        Employee db = employeeMapper.getByUsername(employee.getUsername());
        if (db == null) {
            throw new RuntimeException("用户名不存在");
        }
        if (!db.getPassword().equals(employee.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        if (db.getStatus() == null || db.getStatus() == Constants.DISABLE) {
            throw new RuntimeException("账号已被禁用，请联系管理员");
        }
        return db;
    }

    @Override
    public List<Employee> list(String name) {
        return employeeMapper.list(name);
    }

    @Override
    public Employee getById(Long id) {
        return employeeMapper.getById(id);
    }

    @Override
    public void save(Employee employee) {
        employee.setStatus(Constants.ENABLE);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.insert(employee);
    }

    @Override
    public void update(Employee employee) {
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.update(employee);
    }
}
