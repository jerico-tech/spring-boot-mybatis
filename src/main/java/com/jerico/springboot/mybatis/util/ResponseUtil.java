package com.jerico.springboot.mybatis.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static ResponseEntity<Object> response(String message, HttpStatus status, Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status);
        map.put("data", object);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    public static <T> ResponseDTO<T> success(T data) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(ResponseCodeEnum.SUCCESS.getCode());
        responseDTO.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
        responseDTO.setData(data);
        return responseDTO;
    }

    public static <T> ResponseDTO<T> fail(int code, String message, T data) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(code);
        responseDTO.setMessage(message);
        responseDTO.setData(data);
        return responseDTO;
    }
}
