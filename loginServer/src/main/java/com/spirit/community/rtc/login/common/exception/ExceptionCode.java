package com.spirit.community.rtc.login.common.exception;


public enum ExceptionCode {


    SUCCESS("0000", "OK"),
    USERINFO_NOT_EXIST("0001", "用户不存在"),
    USERID_OR_PASSWD_INVALID("0002", "账号或密码错误"),
    DUPLICATED_REGISTER_EXCEPTION("0003", "重复注册监听"),
    SERVICE_LIST_EMPTY_EXCEPTION("0004", "服务列表为空"),
    NODE_SERVICE_DATA_EMPTY_EXCEPTION("0005", "节点服务数据为空"),
    PARAM_NONE_EXCEPTION("0006", "参数异常"),


    UNEXPECTED_EXCEPTION("1000", "未知异常"),



    ;

    private final String defaultCode = "9999";
    private String code;
    private String text;

    ExceptionCode(String code, String name) {
        this.code = code;
        this.text = name;
    }

    public String code() {
        return code;
    }

//    public ExceptionCode setCode(String code) {
//        this.code = code;
//        return this;
//    }
    public String text() {
        return text;
    }
    public ExceptionCode setTextDefault(String text) {
        this.text = text;
        this.code = defaultCode;
        return this;
    }
}
