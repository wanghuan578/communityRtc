package com.spirit.community.common.constant;

public class RpcEventType {
    public static final int MT_HELLO_REQ                                                = 100;
    public static final int MT_HELLO_RES                                                = 101;
    public static final int MT_HELLO_NOTIFY                                             = 102;
    public static final int MT_KEEPALIVE_REQ                                            = 103;
    public static final int MT_KEEPALIVE_RES                                            = 104;

    public static final int MT_CLIENT_REGISTER_REQ  = com.spirit.community.protocol.thrift.login.MessageType.MT_CLIENT_REGISTER_REQ.getValue();
    public static final int MT_CLIENT_REGISTER_RES = com.spirit.community.protocol.thrift.login.MessageType.MT_CLIENT_REGISTER_RES.getValue();
    public static final int MT_CLIENT_PASSWORD_LOGIN_REQ = com.spirit.community.protocol.thrift.login.MessageType.MT_CLIENT_PASSWORD_LOGIN_REQ.getValue();
    public static final int MT_CLIENT_LOGIN_RES = com.spirit.community.protocol.thrift.login.MessageType.MT_CLIENT_LOGIN_RES.getValue();
    public static final int MT_CLIENT_LOGOUT_REQ  = com.spirit.community.protocol.thrift.login.MessageType.MT_CLIENT_LOGOUT_REQ.getValue();
    public static final int MT_CLIENT_LOGOUT_RES  = com.spirit.community.protocol.thrift.login.MessageType.MT_CLIENT_LOGOUT_RES.getValue();
    public static final int MT_UPDATE_AVAILABLE_RES  = com.spirit.community.protocol.thrift.login.MessageType.MT_UPDATE_AVAILABLE_RES.getValue();

    public final static int ROOMGATE_CONNECT_REQ = com.spirit.community.protocol.thrift.roomgate.MessageType.MT_ROOMGATE_CONNECT_REQ.getValue();
    public final static int ROOMGATE_CONNECT_RES = com.spirit.community.protocol.thrift.roomgate.MessageType.MT_ROOMGATE_CONNECT_RES.getValue();
    public final static int CONNECT_REQ = com.spirit.community.protocol.thrift.roomgate.MessageType.MT_CONNECT_REQ.getValue();
    public final static int CONNECT_RES = com.spirit.community.protocol.thrift.roomgate.MessageType.MT_CONNECT_RES.getValue();
    public final static int ROOMGATE_CHAT_REQ = com.spirit.community.protocol.thrift.roomgate.MessageType.MT_CHAT_REQ.getValue();
    public final static int ROOMGATE_CHAT_RES = com.spirit.community.protocol.thrift.roomgate.MessageType.MT_CHAT_RES.getValue();
    public final static int ROOMGATE_CHAT_NOTIFY = com.spirit.community.protocol.thrift.roomgate.MessageType.MT_CHAT_NOTIFY.getValue();
    public final static int ROOMGATE_CHAT_RELAY = 99;






}
