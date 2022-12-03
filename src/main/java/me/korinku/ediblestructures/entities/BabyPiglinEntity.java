package me.korinku.ediblestructures.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class BabyPiglinEntity extends WolfEntity {

	public BabyPiglinEntity(EntityType<? extends WolfEntity> entityType, World world) {
		super(entityType, world);
		this.setCustomNameVisible(false);
	}

	private AttributeContainer attributeContainer;

	@Override
	public AttributeContainer getAttributes() {
		if (attributeContainer == null)
			attributeContainer = new AttributeContainer(WolfEntity.createMobAttributes()
					.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.5f).add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
					.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
					.build());
		return attributeContainer;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (this.hasAngerTime()) {
			return null;
		}
		if (this.random.nextInt(3) == 0) {
			if (this.isTamed() && this.getHealth() < 10.0f) {
				return null;
			}
			return null;
		}
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return null;
	}

}
