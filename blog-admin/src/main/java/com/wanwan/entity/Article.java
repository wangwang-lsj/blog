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
 * @since 2025/4/24  23:28
 */
@Getter
@Setter
@ApiModel(value = "Article对象", description = "文章(博客）")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("标签")
    private String tags;

    @ApiModelProperty("作者ID")
    private Long authorId;

    @TableField(exist = false)
    @ApiModelProperty("发布日期")
    private Date publicDate;
    @TableField(exist = false)
    @ApiModelProperty("更新日期")
    private Date updateDate;

    @ApiModelProperty("浏览量")
    private Long readCount;

    @ApiModelProperty("喜欢数")
    private Long likes;

    @ApiModelProperty("类别ID")
    private Long categoryId;

    @TableField(exist = false)
    @ApiModelProperty("作者用户名")
    private String userName;

    @TableField(exist = false)
    @ApiModelProperty("类别名称")
    private String categoryName;

    @TableField(exist = false)
    @ApiModelProperty("评论数量")
    private Long commentCount;
}
