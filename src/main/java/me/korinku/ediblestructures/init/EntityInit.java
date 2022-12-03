package me.korinku.ediblestructures.init;

import me.korinku.ediblestructures.EdibleStructures;
import me.korinku.ediblestructures.entities.BabyPiglinEntity;
import me.korinku.ediblestructures.items.CannonFishEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityInit {

	public static final EntityType<BabyPiglinEntity> BABYPIGLIN = FabricEntityTypeBuilder
			.createMob().spawnGroup(SpawnGroup.CREATURE)
			.entityFactory(
					BabyPiglinEntity::new)
			.dimensions(EntityDimensions.fixed(0.3f,
					0.975f))
			.build();

	public static final EntityType<CannonFishEntity> CANNON_FISH = FabricEntityTypeBuilder
			.<CannonFishEntity>create().spawnGroup(SpawnGroup.MISC)
			.entityFactory(
					CannonFishEntity::new)
			.dimensions(EntityDimensions.fixed(.4f, .6f))
			.build();

	public static void registerEntities() {

		Registry.register(Registry.ENTITY_TYPE, new Identifier(EdibleStructures.MOD_ID, "babypiglin_entity"),
				BABYPIGLIN);

		Registry.register(Registry.ENTITY_TYPE, new Identifier(EdibleStructures.MOD_ID, "cannon_fish"),
				CANNON_FISH);

	}

}
