package com.spirit.community.roomServer.common.exception;


public class MainStageException extends Exception {

    private ExceptionCode type;
    private String code;
    private String text;

    public MainStageException(String code, String text) {
        super(text);
        this.code = code;
        this.text = text;
    }
    public MainStageException(ExceptionCode type) {
        super(type.text());
        this.type = type;
    }
    public MainStageException(String text) {
        super(text);
        this.text = text;
    }
    public ExceptionCode getResultType() {
        return type;
    }
    public String getCode() {
        return type != null ? type.code() : code;
    }
    public String getText() {
        return type != null ? type.text() : text;
    }
}
