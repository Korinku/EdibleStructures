package me.korinku.ediblestructures;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.korinku.ediblestructures.init.EntityInit;
import me.korinku.ediblestructures.init.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EdibleStructures implements ModInitializer {

    public static final String MOD_ID = "estr";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        ModItems.init();
        EntityInit.registerEntities();

        modifyLootTables();

    }

    private void modifyLootTables() {
        int size = Registry.ENTITY_TYPE.size();

        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) -> {
            for (int i = 0; i < size; i++) {
                EntityType<?> randomMob = Registry.ENTITY_TYPE.get(i);
                Identifier TABLE_IDS = randomMob.getLootTableId();
                if (TABLE_IDS.equals(id)) {
                    FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                            .withCondition(
                                    DamageSourcePropertiesLootCondition
                                            .builder(DamageSourcePredicate.Builder.create()
                                                    .sourceEntity(EntityPredicate.Builder.create().team("village")))
                                            .build())
                            .rolls(ConstantLootNumberProvider.create(2))
                            .withFunction(
                                    SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)).build())
                            .with(ItemEntry.builder(Items.IRON_INGOT))
                            .with(ItemEntry.builder(Items.CARROT))
                            .with(ItemEntry.builder(Items.APPLE))
                            .with(ItemEntry.builder(Items.BREAD))
                            .with(ItemEntry.builder(Items.DIAMOND))
                            .with(ItemEntry.builder(Items.COPPER_INGOT))
                            .with(ItemEntry.builder(Items.EMERALD))
                            .with(ItemEntry.builder(Items.GOLD_INGOT));

                    table.withPool(poolBuilder.build());
                }
            }
        });
    }
}
