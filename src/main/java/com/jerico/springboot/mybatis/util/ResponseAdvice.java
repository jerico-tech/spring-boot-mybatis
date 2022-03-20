package com.jerico.springboot.mybatis.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一处理响应体，用ResponseUtil.success静态方法包装，
 * 在API接口使用时就可以直接返回原始类型.
 */
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 是否支持advice功能
     * @param returnType
     * @param converterType
     * @return true 支持，false 不支持
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 对返回的数据进行处理
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 正常响应则返回ResponseUtil中方法包装响应类；异常响应会走到全局异常处理(当然在未写全局异常捕获与处理时，异常也会走到这里)。
        // 如果响应体是ResponseDTO包装后的，直接返回。不要重复封装
        if (body instanceof ResponseDTO) {
            return body;
        }
        // 正常响应体则返回ResponseDTO包装的code+message+data的消息体
        return ResponseUtil.success(body);
    }
}
