package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.GamePublishSetting;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.v313.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;

import static com.nukkitx.protocol.bedrock.packet.StartGamePacket.BlockPaletteEntry;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StartGameSerializer_v313 implements PacketSerializer<StartGamePacket> {
    public static final StartGameSerializer_v313 INSTANCE = new StartGameSerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, StartGamePacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        VarInts.writeInt(buffer, packet.getPlayerGamemode());
        BedrockUtils.writeVector3f(buffer, packet.getPlayerPosition());
        BedrockUtils.writeVector2f(buffer, packet.getRotation());
        // Level settings start
        VarInts.writeInt(buffer, packet.getSeed());
        VarInts.writeInt(buffer, packet.getDimensionId());
        VarInts.writeInt(buffer, packet.getGeneratorId());
        VarInts.writeInt(buffer, packet.getLevelGamemode());
        VarInts.writeInt(buffer, packet.getDifficulty());
        BedrockUtils.writeBlockPosition(buffer, packet.getDefaultSpawn());
        buffer.writeBoolean(packet.isAcheivementsDisabled());
        VarInts.writeInt(buffer, packet.getTime());
        buffer.writeBoolean(packet.isEduLevel());
        buffer.writeBoolean(packet.isEduFeaturesEnabled());
        buffer.writeFloatLE(packet.getRainLevel());
        buffer.writeFloatLE(packet.getLightningLevel());
        buffer.writeBoolean(packet.isMultiplayerGame());
        buffer.writeBoolean(packet.isBroadcastingToLan());
        buffer.writeBoolean(packet.getXblBroadcastMode() != GamePublishSetting.NO_MULTI_PLAY);
        buffer.writeBoolean(packet.isCommandsEnabled());
        buffer.writeBoolean(packet.isTexturePacksRequired());
        BedrockUtils.writeArray(buffer, packet.getGamerules(), BedrockUtils::writeGameRule);
        buffer.writeBoolean(packet.isBonusChestEnabled());
        buffer.writeBoolean(packet.isStartingWithMap());
        buffer.writeBoolean(packet.isTrustingPlayers());
        VarInts.writeInt(buffer, packet.getDefaultPlayerPermission());
        VarInts.writeInt(buffer, packet.getXblBroadcastMode().ordinal());
        buffer.writeIntLE(packet.getServerChunkTickRange());
        buffer.writeBoolean(packet.getPlatformBroadcastMode() != GamePublishSetting.NO_MULTI_PLAY);
        VarInts.writeInt(buffer, packet.getPlatformBroadcastMode().ordinal());
        buffer.writeBoolean(packet.getXblBroadcastMode() != GamePublishSetting.NO_MULTI_PLAY);
        buffer.writeBoolean(packet.isBehaviorPackLocked());
        buffer.writeBoolean(packet.isResourcePackLocked());
        buffer.writeBoolean(packet.isFromLockedWorldTemplate());
        buffer.writeBoolean(packet.isUsingMsaGamertagsOnly());
        buffer.writeBoolean(packet.isFromWorldTemplate());
        buffer.writeBoolean(packet.isWorldTemplateOptionLocked());
        // Level settings end
        BedrockUtils.writeString(buffer, packet.getLevelId());
        BedrockUtils.writeString(buffer, packet.getWorldName());
        BedrockUtils.writeString(buffer, packet.getPremiumWorldTemplateId());
        buffer.writeBoolean(packet.isTrial());
        buffer.writeLongLE(packet.getCurrentTick());
        VarInts.writeInt(buffer, packet.getEnchantmentSeed());

        // cache palette for fast writing
        if (packet.getCachedPalette() != null) {
            buffer.writeBytes(packet.getCachedPalette());
            packet.getCachedPalette().release();
        } else {
            Collection<BlockPaletteEntry> paletteEntries = packet.getPaletteEntries();
            VarInts.writeUnsignedInt(buffer, paletteEntries.size());
            for (BlockPaletteEntry entry : paletteEntries) {
                BedrockUtils.writeString(buffer, entry.getIdentifier());
                buffer.writeShortLE(entry.getMeta());
            }
        }

        BedrockUtils.writeString(buffer, packet.getMultiplayerCorrelationId());
    }

    @Override
    public void deserialize(ByteBuf buffer, StartGamePacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPlayerGamemode(VarInts.readInt(buffer));
        packet.setPlayerPosition(BedrockUtils.readVector3f(buffer));
        packet.setRotation(BedrockUtils.readVector2f(buffer));
        // Level settings start
        packet.setSeed(VarInts.readInt(buffer));
        packet.setDimensionId(VarInts.readInt(buffer));
        packet.setGeneratorId(VarInts.readInt(buffer));
        packet.setLevelGamemode(VarInts.readInt(buffer));
        packet.setDifficulty(VarInts.readInt(buffer));
        packet.setDefaultSpawn(BedrockUtils.readBlockPosition(buffer));
        packet.setAcheivementsDisabled(buffer.readBoolean());
        packet.setTime(VarInts.readInt(buffer));
        packet.setEduLevel(buffer.readBoolean());
        packet.setEduFeaturesEnabled(buffer.readBoolean());
        packet.setRainLevel(buffer.readFloatLE());
        packet.setLightningLevel(buffer.readFloatLE());
        packet.setMultiplayerGame(buffer.readBoolean());
        packet.setBroadcastingToLan(buffer.readBoolean());
        buffer.readBoolean(); // broadcasting to XBL
        packet.setCommandsEnabled(buffer.readBoolean());
        packet.setTexturePacksRequired(buffer.readBoolean());
        BedrockUtils.readArray(buffer, packet.getGamerules(), BedrockUtils::readGameRule);
        packet.setBonusChestEnabled(buffer.readBoolean());
        packet.setStartingWithMap(buffer.readBoolean());
        packet.setTrustingPlayers(buffer.readBoolean());
        packet.setDefaultPlayerPermission(VarInts.readInt(buffer));
        packet.setXblBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
        packet.setServerChunkTickRange(buffer.readIntLE());
        buffer.readBoolean(); // broadcasting to Platform
        packet.setPlatformBroadcastMode(GamePublishSetting.byId(VarInts.readInt(buffer)));
        buffer.readBoolean(); // Intent to broadcast XBL
        packet.setBehaviorPackLocked(buffer.readBoolean());
        packet.setResourcePackLocked(buffer.readBoolean());
        packet.setFromLockedWorldTemplate(buffer.readBoolean());
        packet.setUsingMsaGamertagsOnly(buffer.readBoolean());
        packet.setFromWorldTemplate(buffer.readBoolean());
        packet.setWorldTemplateOptionLocked(buffer.readBoolean());
        // Level settings end
        packet.setLevelId(BedrockUtils.readString(buffer));
        packet.setWorldName(BedrockUtils.readString(buffer));
        packet.setPremiumWorldTemplateId(BedrockUtils.readString(buffer));
        packet.setTrial(buffer.readBoolean());
        packet.setCurrentTick(buffer.readLongLE());
        packet.setEnchantmentSeed(VarInts.readInt(buffer));

        BedrockUtils.readArray(buffer, packet.getPaletteEntries(), buf -> {
            String identifier = BedrockUtils.readString(buffer);
            short meta = buffer.readShortLE();
            return new BlockPaletteEntry(identifier, meta);
        });

        packet.setMultiplayerCorrelationId(BedrockUtils.readString(buffer));
    }
}
