package com.nnu.takeout.controller.user;

import com.nnu.takeout.common.Constants;
import com.nnu.takeout.common.R;
import com.nnu.takeout.entity.User;
import com.nnu.takeout.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户端登录：获取验证码 + 验证码登录 + 退出
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /** 获取验证码：生成验证码写入 Session（实践项目模拟短信，验证码同时返回便于演示） */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(HttpServletRequest request, @RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        if (phone == null || phone.length() != 11) {
            return R.error("手机号格式不正确");
        }
        String code = userService.sendMsg(phone);
        request.getSession().setAttribute(Constants.CAPTCHA_SESSION, code);
        // 注意：生产环境验证码只走短信下发，不应返回给前端；此处为课程演示保留
        return R.success(code);
    }

    /** 用户登录：校验验证码，成功后把用户 ID 写入 Session */
    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        String code = body.get("code");
        String codeInSession = (String) request.getSession().getAttribute(Constants.CAPTCHA_SESSION);
        User user = userService.login(phone, code, codeInSession);
        request.getSession().setAttribute(Constants.USER_SESSION, user.getId());
        // 登录成功后清除验证码，防止重复使用
        request.getSession().removeAttribute(Constants.CAPTCHA_SESSION);
        User vo = new User();
        vo.setId(user.getId());
        vo.setName(user.getName());
        vo.setPhone(user.getPhone());
        return R.success(vo);
    }

    /** 用户退出 */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.USER_SESSION);
        return R.success("已退出登录");
    }
}
