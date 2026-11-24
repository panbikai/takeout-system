package com.nnu.takeout.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器：将业务异常统一封装为 R.error 返回，避免前端收到 500 错误页。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public R<String> handleRuntimeException(RuntimeException e) {
        return R.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R<String> handleException(Exception e) {
        return R.error("系统异常：" + e.getMessage());
    }
}
