package me.korinku.ediblestructures.init;

import net.minecraft.item.FoodComponent;

public class ModFoodComponents {

	public static final FoodComponent STRUCTURE;

	static {
		STRUCTURE = (new FoodComponent.Builder())
				.alwaysEdible()
				.hunger(2)
				.build();
	}

}
