package com.jerico.springboot.mybatis.exception;

import com.jerico.springboot.mybatis.util.ResponseCodeEnum;
import com.jerico.springboot.mybatis.util.ResponseDTO;
import com.jerico.springboot.mybatis.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类，只要是代码运行过程中有异常就会进行捕获
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 默认全局异常处理。
     *
     * @param e the e
     * @return ResponseDTO
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO<Object> exception(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return ResponseUtil.fail(ResponseCodeEnum.INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), e);
    }

    /**
     * 自定义参数异常处理方法
     *
     * @param e 自定义参数异常
     * @return 响应
     */
    @ExceptionHandler(value = ParamErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<Object> paramErrorExceptionHandler(ParamErrorException e) {
        log.error("自定义参数异常信息 ex={}", e.getMessage(), e);
        return ResponseUtil.fail(e.getCode(), e.getMessage(), e);
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDTO<Object> notFoundExceptionHandler(NotFoundException e) {
        log.error("not found ex = {}", e.getMessage());
        return ResponseUtil.fail(HttpStatus.NOT_FOUND.value(), e.getMessage(), e);
    }
}
