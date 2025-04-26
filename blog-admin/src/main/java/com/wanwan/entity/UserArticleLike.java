package com.wanwan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName user_article_like
 */
@TableName(value ="user_article_like")
@Data
public class UserArticleLike implements Serializable {
    private Long userId;

    private Long articleId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}