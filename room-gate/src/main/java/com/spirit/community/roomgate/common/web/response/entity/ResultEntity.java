package com.spirit.community.roomgate.common.web.response.entity;


import com.spirit.community.roomgate.common.exception.ExceptionCode;
import lombok.Data;
import java.io.Serializable;

@Data
public class ResultEntity<T> implements Serializable {

    private static final long serialVersionUID = -3581261863966039090L;

    private String code;
    private String text;
    private T content;

    public ResultEntity(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public ResultEntity(String code, String text, T t) {
        this.code = code;
        this.text = text;
        this.content = t;
    }

    public ResultEntity() {

    }

    public ResultEntity succeed() {
        return new ResultEntity(ExceptionCode.SUCCESS.code(), ExceptionCode.SUCCESS.text());
    }

    public ResultEntity<T> succeed(T t) {
        return new ResultEntity<T>(ExceptionCode.SUCCESS.code(), ExceptionCode.SUCCESS.text(), t);
    }

    public ResultEntity succeed(String msg, Object obj) {
        return new ResultEntity<Object>(ExceptionCode.SUCCESS.code(), msg, obj);
    }

    public static ResultEntity failed(ExceptionCode type) {
        return new ResultEntity<Object>(type.code(), type.text(), null);
    }

    public static ResultEntity failed(ExceptionCode type, Object obj) {
        return new ResultEntity<Object>(type.code(), type.text(), obj);
    }
}
