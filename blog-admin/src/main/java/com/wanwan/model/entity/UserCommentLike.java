package com.wanwan.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户评论关系表
 * @TableName user_comment_like
 */
@TableName(value ="user_comment_like")
@Data
@Getter
@Setter
public class UserCommentLike implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("用户ID")
    private Long user_id;

    @ApiModelProperty("评论ID")
    private Integer comment_id;

    @ApiModelProperty("创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime create_time;

}