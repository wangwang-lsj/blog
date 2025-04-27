package com.wanwan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanwan.entity.UserCommentLike;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 玩玩
* @description 针对表【user_comment_like(用户评论关系表)】的数据库操作Mapper
* @createDate 2024-05-24 00:00:10
* @Entity com.wanwan.entity.UserCommentLike
*/
@Mapper
public interface UserCommentLikeMapper extends BaseMapper<UserCommentLike> {

}




