package com.jerico.springboot.mybatis.util;

/**
 * 定义常用业务状态码
 * @author hairu
 */
public enum ResponseCodeEnum {
    SUCCESS(200, "Success"),
    ID_NOT_NULL(40001, "id can not be empty!"),
    NAME_NOT_NULL(40002, "name can not be empty!"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    /**
     * 自定义状态码
     */
    private final int code;

    /**
     * 自定义描述
     */
    private final String message;

    ResponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
