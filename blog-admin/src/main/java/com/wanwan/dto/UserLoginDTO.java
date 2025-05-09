package com.wanwan.dto;

import javax.validation.constraints.Size;

/**
 * @author 玩玩
 * @description
 * @since 2025/4/29  0:36
 */
public class UserLoginDTO {
    @Size(min = 6, max = 20, message = "密码长度在6-20之间")
    private String username;
    @Size(min = 6, max = 20, message = "密码长度在6-20之间")
    private String password;
}
