package com.jerico.springboot.mybatis.exception;

import lombok.Data;

@Data
public class ParamErrorException extends RuntimeException {
    protected int code;
    protected String message;

    public ParamErrorException(){

    }
    public ParamErrorException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
