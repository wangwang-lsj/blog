package com.wanwan.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 玩玩
 * @description
 * @since 2025/5/13  23:07
 */
@Data
public class UserPageDTO {
    @NotBlank(message = "页码不能为空")
    private Integer pageNum;
    @NotBlank(message = "页大小不能为空")
    private Integer pageSize;
    private String username = "";
    private String nickname = "";
    private String address = "";
    private String phone = "";
    private String email = "";

}
