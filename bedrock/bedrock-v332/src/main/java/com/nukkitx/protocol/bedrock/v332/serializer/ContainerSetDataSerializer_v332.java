package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ContainerSetDataPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import com.nukkitx.protocol.util.TIntHashBiMap;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.packet.ContainerSetDataPacket.Property;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContainerSetDataSerializer_v332 implements PacketSerializer<ContainerSetDataPacket> {
    public static final ContainerSetDataSerializer_v332 INSTANCE = new ContainerSetDataSerializer_v332();

    private static final TIntHashBiMap<Property> properties = new TIntHashBiMap<>();

    static {
        properties.put(0, Property.FURNACE_TICK_COUNT);
        // TODO:
    }

    @Override
    public void serialize(ByteBuf buffer, ContainerSetDataPacket packet) {
        buffer.writeByte(packet.getWindowId());
        VarInts.writeInt(buffer, properties.get(packet.getProperty()));
        VarInts.writeInt(buffer, packet.getValue());
    }

    @Override
    public void deserialize(ByteBuf buffer, ContainerSetDataPacket packet) {
        packet.setWindowId(buffer.readByte());
        packet.setProperty(properties.get(VarInts.readInt(buffer)));
        packet.setValue(VarInts.readInt(buffer));
    }
}
