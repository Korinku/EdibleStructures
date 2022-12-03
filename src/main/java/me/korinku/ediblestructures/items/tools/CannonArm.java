package me.korinku.ediblestructures.items.tools;

import me.korinku.ediblestructures.init.EntityInit;
import me.korinku.ediblestructures.items.CannonFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CannonArm extends Item {

	public CannonArm(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISH_SWIM,
				SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
		if (!world.isClient) {
			CannonFishEntity proj = new CannonFishEntity(EntityInit.CANNON_FISH, world);
			proj.setOwner(user);
			proj.setPos(user.getX(), user.getY() + 1, user.getZ());
			proj.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, .1f);
			world.spawnEntity(proj);
		}
		return TypedActionResult.success(itemStack, world.isClient());
	}
}
