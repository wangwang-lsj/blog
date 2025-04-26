package com.wanwan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 玩玩
 * @description
 * @since 2025/4/24  23:29
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long parentId;

    private Long replyUserId;

    private Long replyCommentId;

    private Long userId;

    private String commentContent;

    private Date createTime;

    private Long likeNum;

    private Long articleId;

    private static final long serialVersionUID = 1L;
}