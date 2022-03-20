package com.jerico.springboot.mybatis.util;

import lombok.Data;
import org.springframework.http.ResponseEntity;

/**
 * 全局统一响应类.这里根据个人实际情况可以按照如下两种方式选择其一即可。
 * 方式一：该类作为整个系统服务的统一响应类（成功或者失败），成功时业务实体对象放在data中，失败时把详细的错误信息放入data中；
 * 方式二：该类仅作为错误响应类，成功时直接返回业务实体对象，不再使用该类封装一层。
 *
 * @param <T>
 */
@Data
public class ResponseDTO<T> {
    // 业务状态码/错误码等，而非http状态码
    private int code;
    // 响应消息，页面可展示用户可理解的成功/错误消息
    private String message;
    // 成功的响应内容/错误详细内容方便定位问题
    private T data;

    public ResponseDTO(){
    }

    public ResponseDTO(int code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
