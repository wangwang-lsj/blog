package com.wanwan.response;

import com.wanwan.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author：玩玩
 * @since：2024/1/23 21:35
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private String code;
    private String msg;
    private T data;

    /**
     * 成功返回，无数据
     */
    public static <T> Result<T> success() {
        return new Result<>("200", "操作成功", null);
    }

    /**
     * 成功返回，带有数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>("200", "操作成功", data);
    }

    /**
     * 自定义成功的消息和数据
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>("200", message, data);
    }

    /**
     * 错误返回，带有错误码和消息
     */
    public static <T> Result<T> error(String code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 使用枚举错误码返回错误结果
     */
    public static <T> Result<T> error(ResultCodeEnum resultCodeEnum) {
        return new Result<>(resultCodeEnum.getCode(), resultCodeEnum.getMsg(), null);
    }

    /**
     * 系统错误
     */
    public static <T> Result<T> systemError() {
        return new Result<>("500", "系统错误", null);
    }
}