package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.packet.PlaySoundPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaySoundSerializer_v291 implements PacketSerializer<PlaySoundPacket> {
    public static final PlaySoundSerializer_v291 INSTANCE = new PlaySoundSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, PlaySoundPacket packet) {
        BedrockUtils.writeString(buffer, packet.getSound());
        BedrockUtils.writeBlockPosition(buffer, packet.getPosition().mul(3).toInt());
        buffer.writeFloatLE(packet.getVolume());
        buffer.writeFloatLE(packet.getPitch());
    }

    @Override
    public void deserialize(ByteBuf buffer, PlaySoundPacket packet) {
        packet.setSound(BedrockUtils.readString(buffer));
        packet.setPosition(BedrockUtils.readBlockPosition(buffer).toFloat().div(8));
        packet.setVolume(buffer.readFloatLE());
        packet.setPitch(buffer.readFloatLE());
    }
}
