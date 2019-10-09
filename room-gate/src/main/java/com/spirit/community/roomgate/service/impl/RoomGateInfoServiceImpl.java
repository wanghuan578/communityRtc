package com.spirit.community.roomgate.service.impl;

import com.spirit.community.common.pojo.RoomGateInfo;
import com.spirit.community.roomgate.service.RoomGateInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RoomGateInfoServiceImpl implements RoomGateInfoService {

    @Value("${roomgate.id}")
    private String roomgateId;

    @Value("${roomgate.ip}")
    private String roomgateIp;

    @Value("${roomgate.port}")
    private int roomgatePort;

    @Override
    public RoomGateInfo getRoomGateInfo() {
        RoomGateInfo gate = new RoomGateInfo();
        gate.setRoomGateId(roomgateId);
        gate.setPort(roomgatePort);
        gate.setIp(roomgateIp);
        return gate;
    }
}
