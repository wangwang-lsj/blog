package com.wanwan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName slideshow
 */
@TableName(value ="slideshow")
@Data
@Getter
@Setter
public class Slideshow implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;
    private String url;

    private Long sortNum;

    private Boolean enable;
    @TableField(exist = false)
    private Date updateTime;
    @TableField(exist = false)
    private Date createTime;

    private static final long serialVersionUID = 1L;

}