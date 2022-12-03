package me.korinku.ediblestructures.init;

import me.korinku.ediblestructures.EdibleStructures;
import me.korinku.ediblestructures.items.structures.Bastion;
import me.korinku.ediblestructures.items.structures.DesertTemple;
import me.korinku.ediblestructures.items.structures.DragonPearch;
import me.korinku.ediblestructures.items.structures.EndPortal;
import me.korinku.ediblestructures.items.structures.Geode;
import me.korinku.ediblestructures.items.structures.Mineshaft;
import me.korinku.ediblestructures.items.structures.NetherFortress;
import me.korinku.ediblestructures.items.structures.RuinedPortal;
import me.korinku.ediblestructures.items.structures.Shipwreck;
import me.korinku.ediblestructures.items.structures.Village;
import me.korinku.ediblestructures.items.tools.CannonArm;
import me.korinku.ediblestructures.items.tools.EssenceVacuum;
import me.korinku.ediblestructures.items.tools.MiningHelmet;
import me.korinku.ediblestructures.items.tools.MiningHelmetMaterial;
import me.korinku.ediblestructures.items.tools.ThrowableAmethystShard;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {

	// TOOLS

	public static final Item SALMON_FISH = registerItem("salmon_fish",
			new Item(new FabricItemSettings().group(ItemGroup.MISC)));

	public static final Item CANNON_ARM = registerItem("cannon_arm",
			new CannonArm(new FabricItemSettings().group(ItemGroup.MISC)));

	public static final Item ESSENCE_VACUUM = registerItem("essence_vacuum", new EssenceVacuum(
			new FabricItemSettings().group(ItemGroup.MISC)));

	public static final ArmorMaterial MATERIAL = new MiningHelmetMaterial();

	public static final Item MINING_HELMET = registerItem("mining_helmet", new MiningHelmet(ArmorMaterials.GOLD,
			EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.MISC)));

	public static final Item THROWABLE_AMETHYST_SHARD = registerItem("throwable_amethyst_shard",
			new ThrowableAmethystShard(new FabricItemSettings().group(ItemGroup.MISC)));

	// ESSENCE

	public static final Item DESERT_TEMPLE_ESSENCE = registerItem("desert_temple_essence",
			new Item(new FabricItemSettings()));
	public static final Item BASTION_ESSENCE = registerItem("bastion_essence",
			new Item(new FabricItemSettings()));
	public static final Item END_PORTAL_ESSENCE = registerItem("end_portal_essence",
			new Item(new FabricItemSettings()));
	public static final Item SHIPWRECK_ESSENCE = registerItem("shipwreck_essence",
			new Item(new FabricItemSettings()));
	public static final Item GEODE_ESSENCE = registerItem("geode_essence",
			new Item(new FabricItemSettings()));
	public static final Item RUINED_PORTAL_ESSENCE = registerItem("ruined_portal_essence",
			new Item(new FabricItemSettings()));
	public static final Item NETHER_FORTRESS_ESSENCE = registerItem("nether_fortress_essence",
			new Item(new FabricItemSettings()));
	public static final Item DRAGON_PERCH_ESSENCE = registerItem("dragon_pearch_essence",
			new Item(new FabricItemSettings()));
	public static final Item MINESHAFT_ESSENCE = registerItem("mineshaft_essence",
			new Item(new FabricItemSettings()));
	public static final Item VILLAGE_ESSENCE = registerItem("village_essence",
			new Item(new FabricItemSettings()));

	// STRUCTURE

	public static final Item DESERT_TEMPLE_STRUCTURE = registerItem("desert_temple_structure",
			new DesertTemple(new FabricItemSettings().food(ModFoodComponents.STRUCTURE).group(ItemGroup.MISC)
					.rarity(Rarity.RARE)));
	public static final Item BASTION_STRUCTURE = registerItem("bastion_structure",
			new Bastion(new FabricItemSettings().food(ModFoodComponents.STRUCTURE).group(ItemGroup.MISC)
					.rarity(Rarity.RARE)));
	public static final Item DRAGON_PEARCH_STRUCTURE = registerItem("dragon_pearch_structure",
			new DragonPearch(
					new FabricItemSettings().food(ModFoodComponents.STRUCTURE).group(ItemGroup.MISC)
							.rarity(Rarity.RARE)));
	public static final Item END_PORTAL_STRUCTURE = registerItem("end_portal_structure",
			new EndPortal(
					new FabricItemSettings().food(ModFoodComponents.STRUCTURE).group(ItemGroup.MISC)
							.rarity(Rarity.RARE)));
	public static final Item GEODE_STRUCTURE = registerItem(
			"geode_structure",
			new Geode(new FabricItemSettings()
					.food(ModFoodComponents.STRUCTURE).group(ItemGroup.MISC)
					.rarity(Rarity.RARE)));
	public static final Item MINESHAFT_STRUCTURE = registerItem("mineshaft_structure",
			new Mineshaft(new FabricItemSettings().food(ModFoodComponents.STRUCTURE).group(ItemGroup.MISC)
					.rarity(Rarity.RARE)));
	public static final Item NETHER_FORTRESS_STRUCTURE = registerItem("nether_fortress_structure",
			new NetherFortress(
					new FabricItemSettings().food(ModFoodComponents.STRUCTURE).group(ItemGroup.MISC)
							.rarity(Rarity.RARE)));
	public static final Item RUINED_PORTAL_STRUCTURE = registerItem("ruined_portal_structure",
			new RuinedPortal(
					new FabricItemSettings().food(ModFoodComponents.STRUCTURE).group(ItemGroup.MISC)
							.rarity(Rarity.RARE)));
	public static final Item SHIPWRECK_STRUCTURE = registerItem("shipwreck_structure",
			new Shipwreck(new FabricItemSettings()
					.food(ModFoodComponents.STRUCTURE).group(ItemGroup.MISC)
					.rarity(Rarity.RARE)));
	public static final Item VILLAGE_STRUCTURE = registerItem(
			"village_structure",
			new Village(new FabricItemSettings()
					.food(ModFoodComponents.STRUCTURE).group(ItemGroup.MISC)
					.rarity(Rarity.RARE)));

	private static Item registerItem(String name, Item item) {
		return Registry.register(Registry.ITEM, new Identifier(EdibleStructures.MOD_ID, name), item);
	}

	public static void init() {
		EdibleStructures.LOGGER.info("Registering Mod Items for " + EdibleStructures.MOD_ID);
	}

}
