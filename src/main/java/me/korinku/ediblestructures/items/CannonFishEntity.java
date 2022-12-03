package me.korinku.ediblestructures.items;

import java.util.UUID;

import io.netty.buffer.Unpooled;
import me.korinku.ediblestructures.EdibleStructures;
import me.korinku.ediblestructures.init.EntityInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class CannonFishEntity extends ThrownEntity {

	public static final Identifier SPAWN_PACKET = new Identifier(EdibleStructures.MOD_ID, "cannon_fish");

	public CannonFishEntity(
			EntityType<? extends ThrownEntity> entityType, World world) {
		super(entityType, world);
	}

	@Environment(EnvType.CLIENT)
	public CannonFishEntity(World world, double x, double y, double z, int id, UUID uuid) {
		super(EntityInit.CANNON_FISH, world);
		updatePosition(x, y, z);
		updateTrackedPosition(x, y, z);
		setId(id);
		setUuid(uuid);
	}

	@Override
	protected void initDataTracker() {

	}

	@Override
	public Packet<?> createSpawnPacket() {
		PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

		// entity position
		packet.writeDouble(getX());
		packet.writeDouble(getY());
		packet.writeDouble(getZ());

		packet.writeInt(getId());
		packet.writeUuid(getUuid());

		return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 5);
		var salmon = EntityType.SALMON.create(world);
		salmon.setPosition(entity.getPos());
		world.spawnEntity(salmon);
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.world.isClient) {
			this.world.sendEntityStatus(this, (byte) 3);
			var salmon = EntityType.SALMON.create(world);
			salmon.setPosition(hitResult.getPos());
			world.spawnEntity(salmon);
			this.discard();
		}
	}

}
