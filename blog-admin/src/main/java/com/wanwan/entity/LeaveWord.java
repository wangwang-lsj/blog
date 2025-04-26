package com.wanwan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 玩玩
 * @description
 * @since 2025/4/24  23:30
 */
@Getter
@Setter
@ApiModel(value = "leave_word", description = "")
public class LeaveWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("留言人名称")
    private String nickName;

    @ApiModelProperty("ip地址")
    private String ip;

    @ApiModelProperty("留言内容")
    private String content;

    @ApiModelProperty("回复人ID")
    private Long replyUserId;

    @TableField(exist = false)
    private String replyUserName;

    @ApiModelProperty("是否回复")
    private Boolean replied;

    @ApiModelProperty("回复内容")
    private String replyContent;

    @ApiModelProperty("是否展示")
    private Boolean enable;

    @ApiModelProperty("回复时间")
    private Date replyTime;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
