package com.spirit.community.common.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoomgateUser implements Serializable {
    private Long uid;
    private RoomGateInfo roomGateInfo;
    private int timestampp;
}
