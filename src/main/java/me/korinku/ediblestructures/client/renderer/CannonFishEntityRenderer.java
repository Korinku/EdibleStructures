package me.korinku.ediblestructures.client.renderer;

import me.korinku.ediblestructures.init.ModItems;
import me.korinku.ediblestructures.items.CannonFishEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.SalmonEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class CannonFishEntityRenderer extends EntityRenderer<CannonFishEntity> {

	public static final ItemStack STACK = new ItemStack(ModItems.SALMON_FISH);

	public CannonFishEntityRenderer(Context ctx) {
		super(ctx);
	}

	@Override
	public void render(CannonFishEntity entity, float yaw, float tickDelta, MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light) {

		MinecraftClient.getInstance().getItemRenderer().renderItem(
				STACK,
				ModelTransformation.Mode.FIXED,
				light,
				OverlayTexture.DEFAULT_UV,
				matrices,
				vertexConsumers, 0);

		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}

	@Override
	public Identifier getTexture(CannonFishEntity var1) {
		return null;
	}

}
