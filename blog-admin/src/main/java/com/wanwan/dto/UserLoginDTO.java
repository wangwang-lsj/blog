package com.wanwan.dto;

import com.wanwan.entity.Menu;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 玩玩
 * @description
 * @since 2025/4/29  0:36
 */
@Getter
@Setter
public class UserLoginDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @Size(min = 6, max = 18, message = "密码长度在6-18之间")
    @NotBlank(message = "密码不能为空")
    private String password;

}
