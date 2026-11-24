package com.nnu.takeout.service.impl;

import com.nnu.takeout.entity.User;
import com.nnu.takeout.mapper.UserMapper;
import com.nnu.takeout.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String sendMsg(String phone) {
        // 生成 6 位数字验证码；实践项目不接真实短信网关，验证码通过控制台 / 返回值下发
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        System.out.println("【模拟短信】手机号 " + phone + " 的登录验证码：" + code);
        return code.toString();
    }

    @Override
    public User login(String phone, String codeInput, String codeInSession) {
        if (codeInSession == null || !codeInSession.equals(codeInput)) {
            throw new RuntimeException("验证码错误或已过期");
        }
        User user = userMapper.getByPhone(phone);
        if (user == null) {
            // 首次登录自动注册
            user = new User();
            user.setPhone(phone);
            user.setName("用户" + phone.substring(phone.length() - 4));
            user.setStatus(1);
            userMapper.insert(user);
        }
        if (user.getStatus() == null || user.getStatus() == 0) {
            throw new RuntimeException("账号已被禁用");
        }
        return user;
    }
}
