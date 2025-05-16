package com.wanwan.model.vo;

import com.wanwan.model.entity.Menu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 玩玩
 * @description
 * @since 2025/5/11  1:25
 */
@Getter
@Setter
public class UserLoginVO {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;
}
