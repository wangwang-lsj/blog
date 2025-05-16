package com.wanwan.controller;


import cn.hutool.core.date.DateUtil;
import com.wanwan.annotation.AuthAccess;
import com.wanwan.response.Result;
import com.wanwan.model.entity.Message;
import com.wanwan.service.IMessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanwan
 * @since 2024-03-19
 */
@RestController
@RequestMapping("/api/admin/messages")
public class MessageController {
    @Resource
    private IMessageService messageService;


    // @GetMapping("/page")
    // public Result page(@RequestParam Integer pageNum,
    //                    @RequestParam Integer pageSize) {
    //         Map<String, Object> map = new HashMap<>();
    //         map.put("records", leaveWordService.getByPage(new Page<>(pageNum, pageSize)));
    //         map.put("total", leaveWordService.count());
    //         return Result.success(map);
    // }
    @AuthAccess
    @GetMapping("/page")
    public Result queryPage(@RequestHeader(name = "Wan-Source", required = false) String WanSource,
                       @RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam(defaultValue = "") String nickName
    ) {
        // System.out.println(WanSource);
        if (Objects.equals(WanSource, "manage")) {
            return Result.success(messageService.pageMessage(pageNum,pageSize,nickName,null));
        }
        return Result.success(messageService.pageAbleMessage(pageNum,pageSize,nickName,1));
    }

    // 新增
    @AuthAccess
    @PostMapping()
    public Result<Boolean> createMessage(@RequestBody Message message, HttpServletRequest request) {
        message = messageService.prepareMessageForSave(message, request);
        return Result.success(messageService.saveMessage(message));
    }

    @PatchMapping()
    public Result<Boolean> updateShow(@RequestBody Message message) {
        return Result.success(messageService.updateShow(message));
    }

    @PutMapping()
    public Result<Boolean> updateReply(@RequestBody Message message) {
        message.setReplyTime(DateUtil.date());
        return Result.success(messageService.reply(message));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteById(@PathVariable Integer id) {
        return Result.success(messageService.removeMessage(id));
    }

    @DeleteMapping()
    public Result<Boolean> deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(messageService.removeMessages(ids));
    }

}

