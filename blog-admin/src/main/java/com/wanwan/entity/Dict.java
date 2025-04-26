package com.wanwan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 玩玩
 * @description
 * @since 2025/4/24  23:28
 */
@TableName("dict")
@Data
public class Dict {
    private String name;
    private String value;
    private String type;
}
