package me.korinku.ediblestructures.items.structures;

import me.korinku.ediblestructures.items.EnchantedItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.text.Text;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.util.Formatting;
import java.util.List;
import net.minecraft.text.TranslatableText;

public class EndPortal extends EnchantedItem {

	public EndPortal(Settings settings) {
		super(settings);
	}

	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (this.isFood()) {
			if (user instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) user;
				player.getScoreboardTags().add("estr.endportal");
			}
			return user.eatFood(world, stack);
		}
		return stack;
	}

	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
		tooltip.add(new TranslatableText("item.estr.endportal").formatted(Formatting.DARK_PURPLE));
		tooltip.add(new TranslatableText("item.estr.endportal2").formatted(Formatting.DARK_PURPLE));
	}

}
