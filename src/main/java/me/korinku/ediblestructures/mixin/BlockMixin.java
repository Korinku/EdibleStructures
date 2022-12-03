package me.korinku.ediblestructures.mixin;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin({ Block.class })
public class BlockMixin {

	@Inject(at = {
			@At("TAIL") }, method = "onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)V")
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity p, CallbackInfo cir) {
		if (p.getScoreboardTags().contains("estr.mineshaft")) {
			int blocksUp = 2;
			int blocksOut = 2;
			int blocksAcross = 2;

			List<BlockPos> area = getBlocks(p, world, state, pos, blocksUp, blocksOut, blocksAcross);

			for (BlockPos b : area) {
				world.breakBlock(b, true, p);
			}
		}

	}

	private static List<BlockPos> getBlocks(PlayerEntity e, World world, BlockState state, BlockPos pos, int blocksUp,
			int blocksOut, int blocksAcross) {
		return getBlockPos(e, pos, blocksUp, blocksOut, blocksAcross);
	}

	@NotNull
	private static List<BlockPos> getBlockPos(PlayerEntity e, BlockPos pos, int blocksUp, int blocksOut,
			int blocksAcross) {
		int minedX = 0, minedY = 0, minedZ = 0;
		List<BlockPos> area = new ArrayList<>();

		minedX = pos.getX() - blocksAcross / 2;
		minedY = pos.getY() - blocksUp;
		minedZ = pos.getZ() - blocksAcross / 2;

		for (int x = 0; x <= blocksAcross; x++) {
			for (int y = 0; y <= blocksUp && minedY + y <= 256; y++) {
				for (int z = 0; z <= blocksOut; z++) {
					BlockPos b = new BlockPos(minedX + x, minedY + y, minedZ + z);
					area.add(b);
				}
			}
		}

		return area;
	}

}
