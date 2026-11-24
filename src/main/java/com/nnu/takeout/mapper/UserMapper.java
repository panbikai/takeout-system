package com.nnu.takeout.mapper;

import com.nnu.takeout.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /** 根据手机号查询用户 */
    User getByPhone(@Param("phone") String phone);

    /** 新用户自动注册 */
    int insert(User user);
}
