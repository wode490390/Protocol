package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextSerializer_v388 implements PacketSerializer<TextPacket> {
    public static final TextSerializer_v388 INSTANCE = new TextSerializer_v388();


    @Override
    public void serialize(ByteBuf buffer, TextPacket packet) {
        /*TextPacket.Type type = packet.getType();
        buffer.writeByte(type.ordinal());
        buffer.writeBoolean(packet.isNeedsTranslation());

        switch (type) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                BedrockUtils.writeString(buffer, packet.getSourceName());
            case RAW:
            case TIP:
            case SYSTEM:
                BedrockUtils.writeString(buffer, packet.getMessage());
                break;
            case TRANSLATION:
            case POPUP:
            case JUKEBOX_POPUP:
                BedrockUtils.writeString(buffer, packet.getMessage());
                BedrockUtils.writeArray(buffer, packet.getParameters(), BedrockUtils::writeString);
                break;
        }

        BedrockUtils.writeString(buffer, packet.getXuid());
        BedrockUtils.writeString(buffer, packet.getPlatformChatId());*/

        buffer.writeByte(2);
        buffer.writeBoolean(true);
        BedrockUtils.writeString(buffer, "");
        VarInts.writeUnsignedInt(buffer, Integer.MAX_VALUE - 2);
    }

    @Override
    public void deserialize(ByteBuf buffer, TextPacket packet) {
        TextPacket.Type type = TextPacket.Type.values()[buffer.readUnsignedByte()];
        packet.setType(type);
        packet.setNeedsTranslation(buffer.readBoolean());
        switch (type) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                packet.setSourceName(BedrockUtils.readString(buffer));
            case RAW:
            case TIP:
            case SYSTEM:
                packet.setMessage(BedrockUtils.readString(buffer));
                break;
            case TRANSLATION:
            case POPUP:
            case JUKEBOX_POPUP:
                packet.setMessage(BedrockUtils.readString(buffer));
                BedrockUtils.readArray(buffer, packet.getParameters(), BedrockUtils::readString);
                break;
        }
        packet.setXuid(BedrockUtils.readString(buffer));
        packet.setPlatformChatId(BedrockUtils.readString(buffer));
    }
}
