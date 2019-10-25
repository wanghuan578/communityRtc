package com.spirit.community.roomgate.relay.session;

import com.spirit.tba.core.TsRpcHead;

public class RelayProtocol extends Protocol {
    private TsRpcHead head;
    private byte[] data;

    public void setHead(TsRpcHead head) {
        this.head = head;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public TsRpcHead getHead() {
        return head;
    }

    public byte[] getData() {
        return data;
    }
}
