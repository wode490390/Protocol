package com.nukkitx.protocol.attacker;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockClientSession;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.*;

public class AttackerPacketHandler implements BedrockPacketHandler {

    private final BedrockClientSession session;
    private final TaskManager taskManager;

    public long runtimeId = 0;
    public Vector3i spawnPosition = Vector3i.ZERO;
    public boolean initialized = false;

    public AttackerPacketHandler(BedrockClientSession session) {
        this.session = session;
        this.taskManager = new TaskManager(session);
    }

    @Override
    public boolean handle(DisconnectPacket packet) {
        System.out.println(String.format("%d disconnect", System.currentTimeMillis()));

        this.taskManager.getTimer().cancel();
        this.session.disconnect();

        return true;
    }

    @Override
    public boolean handle(ResourcePacksInfoPacket packet) {
        ResourcePackClientResponsePacket pk = new ResourcePackClientResponsePacket();
        pk.setStatus(ResourcePackClientResponsePacket.Status.HAVE_ALL_PACKS);
        this.session.sendPacket(pk);

        return true;
    }

    @Override
    public boolean handle(ResourcePackStackPacket packet) {
        ResourcePackClientResponsePacket pk = new ResourcePackClientResponsePacket();
        pk.setStatus(ResourcePackClientResponsePacket.Status.COMPLETED);
        this.session.sendPacket(pk);

        return true;
    }

    @Override
    public boolean handle(StartGamePacket packet) {
        System.out.println(String.format("%d start", System.currentTimeMillis()));

        this.runtimeId = packet.getRuntimeEntityId();
        this.spawnPosition = packet.getDefaultSpawn();

        RequestChunkRadiusPacket pk = new RequestChunkRadiusPacket();
        pk.setRadius(1);
        this.session.sendPacket(pk);

        return true;
    }

    @Override
    public boolean handle(PlayStatusPacket packet) {
        switch (packet.getStatus()) {
            case PLAYER_SPAWN:
                if (!this.initialized) {
                    this.initialized = true;

                    System.out.println(String.format("%d init", System.currentTimeMillis()));

                    SetLocalPlayerAsInitializedPacket pk = new SetLocalPlayerAsInitializedPacket();
                    pk.setRuntimeEntityId(this.runtimeId);
                    this.session.sendPacket(pk);
                }
                break;
            case LOGIN_SUCCESS:
                System.out.println(String.format("%d login", System.currentTimeMillis()));

                ClientCacheStatusPacket clientCacheStatusPacket = new ClientCacheStatusPacket();
                clientCacheStatusPacket.setSupported(false);
                this.session.sendPacket(clientCacheStatusPacket);
                break;
        }

        return true;
    }

    @Override
    public boolean handle(RespawnPacket packet) {
        System.out.println(String.format("%d respawn", System.currentTimeMillis()));

        if (packet.getState() == RespawnPacket.State.SERVER_SEARCHING) {
            RespawnPacket pk = new RespawnPacket();
            pk.setState(RespawnPacket.State.CLIENT_READY);
            this.session.sendPacket(pk);
        }

        return true;
    }

    @Override
    public boolean handle(SetHealthPacket packet) {
        if (packet.getHealth() < 1) {
            PlayerActionPacket pk = new PlayerActionPacket();
            pk.setAction(PlayerActionPacket.Action.RESPAWN);
            pk.setBlockPosition(this.spawnPosition);
            pk.setFace(0);
            pk.setRuntimeEntityId(this.runtimeId);
            this.session.sendPacket(pk);
        }

        return true;
    }

    @Override
    public boolean handle(UpdateAttributesPacket packet) {
        if (packet.getRuntimeEntityId() == this.runtimeId) {
            packet.getAttributes().forEach(attr -> {
                if (attr.getName().equals("minecraft:health") && attr.getValue() < 1) {
                    PlayerActionPacket pk = new PlayerActionPacket();
                    pk.setAction(PlayerActionPacket.Action.RESPAWN);
                    pk.setBlockPosition(this.spawnPosition);
                    pk.setFace(0);
                    pk.setRuntimeEntityId(this.runtimeId);
                    this.session.sendPacket(pk);
                }
            });
        }

        return true;
    }
}
