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
 * @since 2025/5/11  1:25
 */
@Getter
@Setter
public class UserLoginResponseDTO {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;
}
