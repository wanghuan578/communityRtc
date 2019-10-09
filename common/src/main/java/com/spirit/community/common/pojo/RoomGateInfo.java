package com.spirit.community.common.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoomGateInfo implements Serializable {
    private String roomGateId;
    private String ip;
    private int port;
}
