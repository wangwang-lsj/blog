package com.wanwan.exception;

import com.wanwan.common.enums.ResultCodeEnum;
import lombok.Getter;

/**
 * @author：玩玩
 * @since：2024/1/23 22:25
 * @description:
 */
@Getter
public class ServiceException extends RuntimeException{
    private String code;
    public ServiceException(String code,String msg){
        super(msg);
        this.code=code;
    }
    public ServiceException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.msg);
        this.code = resultCodeEnum.code;
    }

}
