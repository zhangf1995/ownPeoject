package com.own.common.result;

import com.own.common.constant.ResultConstant;
import lombok.Data;

/*
 * @author  zf
 * @date  2020/5/24 10:36 上午
 * 简单返回类
 */
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result(Integer code,String message) {
        this.code = code;
        this.message = message;
    }

    public static Result ok(){
        return new Result(ResultConstant.SUCCESS_CODE,ResultConstant.SUCCESS_MESSAGE);
    }

    public static Result error(){
        return new Result(ResultConstant.ERROR_CODE,ResultConstant.FAIL_MESSAGE);
    }

    public Result responseBody(T t){
        this.data = t;
        return this;
    }
}
