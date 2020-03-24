package com.nukkitx.protocol.attacker;

import com.nukkitx.protocol.bedrock.BedrockClient;
import com.nukkitx.protocol.bedrock.v389.Bedrock_v389;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Attacker {

    private final AtomicBoolean running;

    public static void main(String[] args) {
        new Attacker();
    }

    private Attacker() {
        this.running = new AtomicBoolean(true);

        System.out.println(String.format("%d running", System.currentTimeMillis()));

        BedrockClient client = new BedrockClient(new InetSocketAddress("0.0.0.0", ThreadLocalRandom.current().nextInt(20000, 60000)));
        client.bind().join();
        client.connect(new InetSocketAddress("127.0.0.1", 19132)).whenComplete((session, throwable) -> {
            if (throwable == null) {
                session.setPacketCodec(Bedrock_v389.V389_CODEC);
                session.setPacketHandler(new AttackerPacketHandler(session));

                //session.sendPacketImmediately(ProfileHelper.randomLoginPacket());
                session.sendPacket(ProfileHelper.randomLoginPacket());
            }
        });

        this.loop();
    }

    private void loop() {
        while (this.running.get()) {
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException ignore) {

            }
        }
    }
}
