package com.wanwan.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName comment
 */
@TableName(value ="comment")
@Data
@Getter
@Setter
public class Comment implements Serializable {
    @ApiModelProperty("ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @ApiModelProperty("父评论ID")
    private Long parentId;
    @ApiModelProperty("回复用户ID")
    private Long replyUserId;
    @ApiModelProperty("被回复评论ID")
    private Long replyCommentId;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("评论内容")
    private String commentContent;
    @ApiModelProperty("点赞数")
    private Integer likeNum;
    @ApiModelProperty("文章ID")
    private Integer articleId;
    @ApiModelProperty("评论时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}