package me.korinku.ediblestructures.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedItem extends Item {

	public EnchantedItem(Settings settings) {
		super(settings);
	}

	public boolean hasGlint(ItemStack stack) {
		return true;
	}

}
