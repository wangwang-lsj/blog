package com.wanwan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author：玩玩
 * @since：2024/2/16 19:35
 * @description:
 */
@TableName("role_menu")
@Data
public class RoleMenu {
    private Long roleId;
    private Long menu;
}
