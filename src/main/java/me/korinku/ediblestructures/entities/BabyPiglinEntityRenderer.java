package me.korinku.ediblestructures.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PiglinEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BabyPiglinEntityRenderer
		extends LivingEntityRenderer<BabyPiglinEntity, PiglinEntityModel<BabyPiglinEntity>> {

	private static final Identifier TEXTURE = new Identifier("minecraft",
			"textures/entity/piglin/piglin.png");

	public BabyPiglinEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new PiglinEntityModel<BabyPiglinEntity>(
				context.getPart(EntityModelLayers.PIGLIN)), 0.3f);
	}

	public Identifier getTexture(BabyPiglinEntity zombieEntity) {
		return TEXTURE;
	}
}
