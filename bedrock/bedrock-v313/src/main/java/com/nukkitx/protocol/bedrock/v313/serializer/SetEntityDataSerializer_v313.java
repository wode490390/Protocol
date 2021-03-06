package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetEntityDataPacket;
import com.nukkitx.protocol.bedrock.v313.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetEntityDataSerializer_v313 implements PacketSerializer<SetEntityDataPacket> {
    public static final SetEntityDataSerializer_v313 INSTANCE = new SetEntityDataSerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, SetEntityDataPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        BedrockUtils.writeMetadata(buffer, packet.getMetadata());
    }

    @Override
    public void deserialize(ByteBuf buffer, SetEntityDataPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        BedrockUtils.readMetadata(buffer, packet.getMetadata());
    }
}
