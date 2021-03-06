package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.Attribute;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateAttributesPacket extends BedrockPacket {
    private long runtimeEntityId;
    private List<Attribute> attributes = new ArrayList<>();

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
