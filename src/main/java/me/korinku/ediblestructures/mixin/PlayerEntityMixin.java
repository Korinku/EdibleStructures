package me.korinku.ediblestructures.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import me.korinku.ediblestructures.init.EntityInit;
import me.korinku.ediblestructures.init.ModItems;
import me.korinku.ediblestructures.utils.PlayerSpecialAbilities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion.DestructionType;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin({ PlayerEntity.class })
public abstract class PlayerEntityMixin extends LivingEntity {

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
	public void isInvulnerableTo(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {

		PlayerEntity player = (PlayerEntity) (Object) this;

		if (player.getScoreboardTags().contains("estr.desert")
				|| player.getScoreboardTags().contains("estr.end_portal")) {
			if (damageSource.isExplosive()) {
				cir.setReturnValue(true);
			}
		}
	}

	private int ticksEndCrystal;
	private int ticksWither;
	private boolean spawnedDragon = false;
	private int rangeSuffering = 10;
	private int damageSuffering = 1;
	private Vec3d oldPos;

	@Inject(at = @At("HEAD"), method = "tick")
	private void tick(CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;

		if (player.getScoreboardTags().contains("estr.desert")) {
			if (world.getBlockState(player.getBlockPos()).isIn(BlockTags.WOODEN_PRESSURE_PLATES)) {
				world.createExplosion(null, DamageSource.GENERIC, null, player.getX(), player.getY(), player.getZ(), 10,
						false, DestructionType.BREAK);
			}
		}

		if (player.getScoreboardTags().contains("estr.ruinedportal")) {
			Float pitch = player.getPitch();

			if (player.isSneaking()) {
				if (pitch >= 80 && pitch <= 90) {

					Text title = new LiteralText("Set Warp Location");
					Text subtitle = new LiteralText("Jump to Confirm");

					MinecraftServer server = player.getServer();
					if (server != null) {
						sendTitle(server, player.getUuid(), title, subtitle, 0, 20, 0);
					}

					if (world.getBlockState(player.getBlockPos().add(0, -1, 0)).isAir()
							&& !world.getBlockState(player.getBlockPos().add(0, -2, 0)).isAir()) {
						// ? DOWN -> SAVE THE ACTUAL PLAYER POS
						oldPos = player.getPos();
						player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1, 1);
						player.setPitch(0);
					}
				}
				if (pitch <= -80 && pitch >= -90) {

					Text title = new LiteralText("Warp?");
					Text subtitle = new LiteralText("Jump to Confirm");

					MinecraftServer server = player.getServer();
					if (server != null) {
						sendTitle(server, player.getUuid(), title, subtitle, 0, 20, 0);
					}

					if (world.getBlockState(player.getBlockPos().add(0, -1, 0)).isAir()
							&& !world.getBlockState(player.getBlockPos().add(0, -2, 0)).isAir()) {
						if (oldPos != null) {
							// ? UP -> TELEPORT NO LOCATION
							player.setPitch(0);
							player.teleport(oldPos.getX(), oldPos.getY(), oldPos.getZ());
							player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 1, 1);

						} else {
							// ? -> IF NO OLD POS, WARNS THE PLAYER
							player.sendMessage(new LiteralText("YOU NEED TO SET A WARP LOCATION FIRST"), true);
							player.playSound(SoundEvents.BLOCK_ANVIL_BREAK, SoundCategory.BLOCKS, 1, 1);
						}
					}
				}
			}
		}

		if (player.getScoreboardTags().contains("estr.geode")) {
			if (player.isSneaking()) {
				if (player.getMainHandStack().isEmpty()) {
					// ? DAR ITEM PARA A MÃO PRINCIPAL DO PLAYER
					player.getInventory().setStack(player.getInventory().selectedSlot,
							new ItemStack(ModItems.THROWABLE_AMETHYST_SHARD));
				}
			} else {
				// ? LIMPAR SLOT QUE TENHA O ITEM QUANDO LEVANTAR
				if (player.getInventory().getSlotWithStack(new ItemStack(ModItems.THROWABLE_AMETHYST_SHARD)) != -1)
					player.getInventory().removeStack(
							player.getInventory().getSlotWithStack(new ItemStack(ModItems.THROWABLE_AMETHYST_SHARD)));
			}
		}

		if (player.getScoreboardTags().contains("estr.endportal")) {
			if (player.isSneaking()) {
				ticksEndCrystal++;
				if (ticksEndCrystal == 60) {
					EndCrystalEntity entity = new EndCrystalEntity(player.getWorld(), player.getX(), player.getY(),
							player.getZ());
					world.spawnEntity(entity);
					ticksEndCrystal = 0;
				}
			} else {
				ticksEndCrystal = 0;
			}

		}

		if (player.getScoreboardTags().contains("estr.dragon")) {
			if (player.isSneaking()) {
				// - IF PLAYER HAS "estr.dragon" WHEN HE SNEAKS SUMMONS A PET STRUCTURE
			}
		}

		if (player.getScoreboardTags().contains("estr.nether")) {
			BlockPos pos = player.getBlockPos();

			// - SUMMON WITHER PET TO FOLLOW THE PLAYER
			// - https://github.com/Safrodev/HoverPets/

			if (ticksWither == 20) {
				PlayerSpecialAbilities.inflictSuffering(world, pos, rangeSuffering, damageSuffering);
				ticksWither = 0;
			}

			ticksWither++;
		}

		if (player.getScoreboardTags().contains("estr.village")) {
			if (player.getScoreboard().getPlayerTeam(player.getName().asString()) == null) {
				player.getScoreboard().addTeam("village");
				player.getScoreboard().addPlayerToTeam(player.getName().getString(),
						player.getScoreboard().getTeams().stream().findFirst().get());
			}
		}

		if (player.getScoreboardTags().contains("estr.dragonpearch")) {
			if (spawnedDragon == false) {
				if (player.isSneaking()) {
					var dragon = EntityType.ENDER_DRAGON.create(world);
					ScaleUtils.setScaleOnSpawn(dragon, 0.2f);
					dragon.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(),
							this.getYaw(), this.getPitch());
					world.spawnEntity(dragon);
					spawnedDragon = true;
				}
			}
		}
	}

	@Inject(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageTracker;onDamage(Lnet/minecraft/entity/damage/DamageSource;FF)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	private void onTakeDamage(DamageSource source, float amount, CallbackInfo ci, float originalHealth) {

		var piglin = EntityInit.BABYPIGLIN.create(world);
		var piglin1 = EntityInit.BABYPIGLIN.create(world);
		var piglin2 = EntityInit.BABYPIGLIN.create(world);
		piglin.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(),
				this.getYaw(), this.getPitch());
		piglin.setBaby(true);
		piglin1.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(),
				this.getYaw(), this.getPitch());
		piglin1.setBaby(true);
		piglin2.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(),
				this.getYaw(), this.getPitch());
		piglin2.setBaby(true);

		if (this.getScoreboardTags().contains("estr.bastion")) {
			if (source.getSource() != null) {
				// WOLF WITH PIGLIN TEXTURE AND MODEL
				world.spawnEntity(piglin);
				world.spawnEntity(piglin1);
				world.spawnEntity(piglin2);
				piglin.setTarget((LivingEntity) source.getAttacker());
				piglin1.setTarget((LivingEntity) source.getAttacker());
				piglin2.setTarget((LivingEntity) source.getAttacker());
			}
		}

	}

	private void sendTitle(MinecraftServer server, UUID uuid, Text title, Text subtitle, int fadeIn, int stay,
			int fadeOut) {
		server.getPlayerManager().getPlayer(uuid).networkHandler.sendPacket(new TitleS2CPacket(title));
		server.getPlayerManager().getPlayer(uuid).networkHandler.sendPacket(new SubtitleS2CPacket(subtitle));
		server.getPlayerManager().getPlayer(uuid).networkHandler
				.sendPacket(new TitleFadeS2CPacket(fadeIn, stay, fadeOut));
	}

}
