package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.StructureEditorData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StructureBlockUpdatePacket extends BedrockPacket {
    private Vector3i blockPosition;
    private StructureEditorData editorData;
    private boolean powered;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Type {
        NONE,
        SAVE,
        LOAD,
    }
}
