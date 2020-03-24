package com.nukkitx.protocol.attacker;

import com.nukkitx.protocol.attacker.task.InventoryTransactionPacketAttack;
import com.nukkitx.protocol.attacker.task.TextPacketAttack;
import com.nukkitx.protocol.bedrock.BedrockClientSession;

import java.util.Timer;

public class TaskManager {

    private final Timer timer;

    public TaskManager(BedrockClientSession session) {
        this.timer = new Timer(true);
        //this.timer.schedule(new TextPacketAttack(session), 5000, 1);
        //this.timer.schedule(new InventoryTransactionPacketAttack(session), 5000, 1);
        this.timer.schedule(new TextPacketAttack(session), 1, 1);
        this.timer.schedule(new InventoryTransactionPacketAttack(session), 1, 1);
    }

    public Timer getTimer() {
        return this.timer;
    }
}
