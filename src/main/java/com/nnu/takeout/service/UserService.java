package com.nnu.takeout.service;

import com.nnu.takeout.entity.User;

public interface UserService {

    /** 生成 6 位短信验证码（实践项目模拟：返回验证码，由 Controller 写入 Session） */
    String sendMsg(String phone);

    /**
     * 验证码登录：校验验证码，用户不存在则自动注册。
     * @param phone        手机号
     * @param codeInput    用户输入的验证码
     * @param codeInSession Session 中保存的验证码
     */
    User login(String phone, String codeInput, String codeInSession);
}
