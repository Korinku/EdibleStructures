package me.korinku.ediblestructures.items.tools;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MiningHelmet extends ArmorItem {

	public MiningHelmet(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
		super(material, slot, settings);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		PlayerEntity player = (PlayerEntity) entity;
		ItemStack equippedFeet = player.getEquippedStack(EquipmentSlot.HEAD);

		if (stack.equals(equippedFeet)) {
			if (world.getLightLevel(player.getBlockPos()) < 3) {
				world.setBlockState(player.getBlockPos(), Blocks.TORCH.getDefaultState());
			}
		}

	}

}
