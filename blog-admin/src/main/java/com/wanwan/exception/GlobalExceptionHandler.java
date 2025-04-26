package com.wanwan.exception;

import com.wanwan.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author：玩玩
 * @since：2024/1/23 22:15
 * @description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @ExceptionHandler相当于contconfirmPasswordroller的@RequestMapping
     * 如果抛出的的是ServiceException，则调用该方法
     * @param se 业务异常
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(),se.getMessage());
    }
}
