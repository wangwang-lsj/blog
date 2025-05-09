package com.wanwan.dto;

import com.wanwan.entity.Menu;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author：玩玩
 * @since：2024/1/23 18:27
 * @description:
 */

@Data
public class UserDTO {
    private Long id;
    @NotNull(message = "用户名不能为空")
    private String username;
    @Size(min = 6, max = 20, message = "密码长度在6-20之间")
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;
}
