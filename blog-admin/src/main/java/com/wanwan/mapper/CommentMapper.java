package com.wanwan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanwan.entity.Comment;
import com.wanwan.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 玩玩
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2024-03-14 18:02:20
* @Entity com.wanwan.springboot.entity.Comment
*/

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    IPage<CommentDTO> queryPage(@Param("page") IPage<Comment> page,@Param("articleId") Integer articleId,@Param("currentUserId") Integer currentUserId);

    List<CommentDTO> queryChildrenByPage(@Param("commentId") Integer commentId, @Param("startIndex") Integer startIndex, @Param("count") Integer count,@Param("currentUserId") Integer currentUserId);

    CommentDTO getSingleComment(@Param("id") Integer id);

    void commentLike(@Param("commentId") Integer commentId);
    void commentDisLike(@Param("commentId") Integer commentId);
    void like(@Param("commentId") Integer commentId, @Param("userId")Integer userId);
    void disLike(@Param("commentId")Integer commentId, @Param("userId")Integer userId);

    Boolean isLike(@Param("commentId")Integer commentId, @Param("userId")Integer userId);
    Integer getCountByArticleId(@Param("articleId")Integer articleId);

    Boolean selectUserCommentLike(@Param("commentId") Integer commentId, @Param("userId")Integer userId);

}




