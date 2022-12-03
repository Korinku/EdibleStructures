package me.korinku.ediblestructures.client;

import java.util.UUID;

import me.korinku.ediblestructures.client.renderer.CannonFishEntityRenderer;
import me.korinku.ediblestructures.client.renderer.MiningHelmetArmorRenderer;
import me.korinku.ediblestructures.entities.BabyPiglinEntityRenderer;
import me.korinku.ediblestructures.entities.EntityCreatePacket;
import me.korinku.ediblestructures.init.EntityInit;
import me.korinku.ediblestructures.init.ModItems;
import me.korinku.ediblestructures.items.CannonFishEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemConvertible;

@Environment(EnvType.CLIENT)
public class EdibleStructuresClient implements ClientModInitializer {

    public static final MiningHelmetArmorRenderer MINING_HELMET_ARMOR_RENDERER = new MiningHelmetArmorRenderer();

    @Override
    public void onInitializeClient() {

        ClientPlayNetworking.registerGlobalReceiver(EntityCreatePacket.ID, EntityCreatePacket::onPacket);

        ClientSidePacketRegistry.INSTANCE.register(CannonFishEntity.SPAWN_PACKET, (context, packet) -> {

            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                CannonFishEntity proj = new CannonFishEntity(MinecraftClient.getInstance().world, x, y, z, entityID,
                        entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityID, proj);
            });
        });

        EntityRendererRegistry.register(
                EntityInit.BABYPIGLIN,
                (context) -> new BabyPiglinEntityRenderer(context));

        EntityRendererRegistry.register(EntityInit.CANNON_FISH, (context) -> new CannonFishEntityRenderer(context));

        ArmorRenderer.register(MINING_HELMET_ARMOR_RENDERER, new ItemConvertible[] { ModItems.MINING_HELMET });
    }
}
