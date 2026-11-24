package com.nnu.takeout.controller.admin;

import com.nnu.takeout.common.Constants;
import com.nnu.takeout.common.R;
import com.nnu.takeout.entity.Employee;
import com.nnu.takeout.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 后台员工登录 / 退出 / 员工管理
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /** 员工登录：校验通过后把员工 ID 写入 Session */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        Employee db = employeeService.login(employee);
        request.getSession().setAttribute(Constants.EMPLOYEE_SESSION, db.getId());
        Employee vo = new Employee();
        vo.setId(db.getId());
        vo.setName(db.getName());
        vo.setUsername(db.getUsername());
        return R.success(vo);
    }

    /** 员工退出：清除登录状态，返回登录页 */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.EMPLOYEE_SESSION);
        return R.success("已退出登录");
    }

    /** 员工列表（按姓名模糊查询） */
    @GetMapping("/list")
    public R<List<Employee>> list(@RequestParam(required = false) String name) {
        return R.success(employeeService.list(name));
    }

    /** 根据 id 查询员工 */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        return R.success(employeeService.getById(id));
    }

    /** 新增员工 */
    @PostMapping
    public R<String> save(@RequestBody Employee employee) {
        employeeService.save(employee);
        return R.success("新增成功");
    }

    /** 修改员工（含启用 / 禁用） */
    @PutMapping
    public R<String> update(@RequestBody Employee employee) {
        employeeService.update(employee);
        return R.success("修改成功");
    }
}
