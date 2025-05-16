package com.wanwan.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanwan.annotation.AuthAccess;
import com.wanwan.model.dto.CommentDTO;
import com.wanwan.response.Result;
import com.wanwan.model.entity.Comment;
import com.wanwan.service.ICommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author：玩玩
 * @since：2024/3/14 18:10
 * @description:
 */
@RestController
@RequestMapping("/api/admin/comments")
public class CommentController {
    @Resource
    private ICommentService commentService;
    @AuthAccess
    @GetMapping("/page")
    public Result<IPage<CommentDTO>> queryPageByCondition(@RequestParam Integer pageNum,
                                                          @RequestParam Integer pageSize,
                                                          @RequestParam Integer articleId,
                                                          @RequestParam Integer currentUserId
    ){

        return Result.success(commentService.pageComment(pageNum,pageSize,articleId,currentUserId));
    }
    @AuthAccess
    @GetMapping("/replies")
    public Result<List<CommentDTO>> queryReplies(@RequestParam Integer commentId,
                                                 @RequestParam Integer startIndex,
                                                 @RequestParam Integer count,
                                                 @RequestParam Integer currentUserId) {
        return Result.success(commentService.pageSecondComment(commentId, startIndex,count,currentUserId));
    }
    @AuthAccess
    @GetMapping("/{articleId}")
    public Result<Integer> queryCountByArticleId(@PathVariable Integer articleId){
        return Result.success(commentService.countComment(articleId));
    }

    @PostMapping()
    public Result<CommentDTO> createComment(@RequestBody Comment comment) {
        return Result.success(commentService.saveComment(comment));
    }
    @PostMapping("/like/{commentId}/{userId}")
    public Result<Integer> updateLike(@PathVariable Integer commentId,@PathVariable Integer userId){
        commentService.like(commentId,userId);
        return Result.success();
    }
    @PostMapping("/dislike/{commentId}/{userId}")
    public Result<Integer> updateDisLike(@PathVariable Integer commentId,@PathVariable Integer userId){
        commentService.disLike(commentId,userId);
        return Result.success();
    }

}
