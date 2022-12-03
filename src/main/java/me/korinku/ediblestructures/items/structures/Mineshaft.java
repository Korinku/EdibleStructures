package me.korinku.ediblestructures.items.structures;

import me.korinku.ediblestructures.init.ModItems;
import me.korinku.ediblestructures.items.EnchantedItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.text.Text;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.util.Formatting;
import java.util.List;
import net.minecraft.text.TranslatableText;

public class Mineshaft extends EnchantedItem {

	public Mineshaft(Settings settings) {
		super(settings);
	}

	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (this.isFood()) {
			if (user instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) user;
				ItemStack itemStack = new ItemStack(ModItems.MINING_HELMET);
				player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, SoundCategory.PLAYERS, 1, 1);
				player.equipStack(EquipmentSlot.HEAD, itemStack);
				player.getScoreboardTags().add("estr.mineshaft");
			}
			return user.eatFood(world, stack);
		}
		return stack;
	}

	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
		tooltip.add(new TranslatableText("item.estr.mineshaft").formatted(Formatting.DARK_PURPLE));
	}

}
