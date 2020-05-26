package com.own.common.exception;

import com.own.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/*
 * @author  zf
 * @date  2020/5/24 4:10 下午
 */
@RestControllerAdvice
public class GlobalException {

    /**
     * 这里简单设置为exception，可以根据具体异常自己设置
     *
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result handleParamterException(HttpServletRequest request, Exception exception) {
        return Result.error().responseBody(exception.getMessage());

    }
}
