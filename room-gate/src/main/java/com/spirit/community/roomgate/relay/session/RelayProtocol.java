package com.spirit.community.roomgate.relay.session;

import com.spirit.tba.core.TbaRpcHead;

public class RelayProtocol extends Protocol {
    private TbaRpcHead head;
    private byte[] data;

    public void setHead(TbaRpcHead head) {
        this.head = head;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public TbaRpcHead getHead() {
        return head;
    }

    public byte[] getData() {
        return data;
    }
}
