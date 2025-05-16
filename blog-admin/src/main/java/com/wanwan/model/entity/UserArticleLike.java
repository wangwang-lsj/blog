package com.wanwan.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName user_article_like
 */
@TableName(value ="user_article_like")
@Data
@Getter
@Setter
public class UserArticleLike implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("文章ID")
    private Integer articleId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("点赞时间")
    private Date createTime;
}