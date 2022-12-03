package me.korinku.ediblestructures.items.tools;

import me.korinku.ediblestructures.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;

public class EssenceVacuum extends Item {

    public EssenceVacuum(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        MinecraftServer server = player.getServer();
        if (server != null) {
            ServerWorld serverWorld = server.getWorld(world.getRegistryKey());
            if (serverWorld != null) {
                StructureFeature.STRUCTURES.values().stream().filter(structureFeature -> {
                    BlockPos location = serverWorld.locateStructure(structureFeature, player.getBlockPos(), 1, false);
                    return (location != null && location.mutableCopy().setY(player.getBlockY())
                            .isWithinDistance(player.getBlockPos(), 50));
                }).findAny().ifPresent((structureFeature) -> {
                    String structureName = structureFeature.getName();
                    if (structureName != null) {
                        if (structureName.equals(StructureFeature.DESERT_PYRAMID.getName())) {
                            player.getInventory().insertStack(new ItemStack(ModItems.DESERT_TEMPLE_ESSENCE));
                        } else if (structureName.equals(StructureFeature.BASTION_REMNANT.getName())) {
                            player.getInventory().insertStack(new ItemStack(ModItems.BASTION_ESSENCE));
                        } else if (structureName.equals(StructureFeature.SHIPWRECK.getName())) {
                            player.getInventory().insertStack(new ItemStack(ModItems.SHIPWRECK_ESSENCE));
                        } else if (structureName.equals(StructureFeature.RUINED_PORTAL.getName())) {
                            player.getInventory().insertStack(new ItemStack(ModItems.RUINED_PORTAL_ESSENCE));
                        } else if (structureName.equals(StructureFeature.FORTRESS.getName())) {
                            player.getInventory().insertStack(new ItemStack(ModItems.NETHER_FORTRESS_ESSENCE));
                        } else if (structureName.equals(StructureFeature.MINESHAFT.getName())) {
                            player.getInventory().insertStack(new ItemStack(ModItems.MINESHAFT_ESSENCE));
                        } else if (structureName.equals(StructureFeature.VILLAGE.getName())) {
                            player.getInventory().insertStack(new ItemStack(ModItems.VILLAGE_ESSENCE));
                        }
                    }
                });
                if (getNearbyBlocks(world, Blocks.BEDROCK, player.getBlockPos()) &&
                        getNearbyBlocks(world, Blocks.END_PORTAL, player.getBlockPos())) {
                    player.getInventory().insertStack(new ItemStack(ModItems.DRAGON_PERCH_ESSENCE));
                } else if (getNearbyBlocks(world, Blocks.END_PORTAL_FRAME, player.getBlockPos()) &&
                        getNearbyBlocks(world, Blocks.END_PORTAL, player.getBlockPos())) {
                    player.getInventory().insertStack(new ItemStack(ModItems.END_PORTAL_ESSENCE));
                } else if (getNearbyBlocks(world, Blocks.AMETHYST_BLOCK, player.getBlockPos()) &&
                        getNearbyBlocks(world, Blocks.BUDDING_AMETHYST, player.getBlockPos())) {
                    player.getInventory().insertStack(new ItemStack(ModItems.GEODE_ESSENCE));
                }
            }
        }
        return TypedActionResult.success(player.getStackInHand(hand));
    }

    public static boolean getNearbyBlocks(World world, Block targetBlock, BlockPos pos) {
        int rad = 3;
        for (int y = -rad; y < rad; y++)
            for (int x = -rad; x < rad; x++)
                for (int z = -rad; z < rad; z++)
                    if (Math.sqrt((x * x) + (y * y) + (z * z)) <= rad) {
                        BlockPos newBlockPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                        Block block = world.getBlockState(newBlockPos).getBlock();
                        if (block.getDefaultState().equals(targetBlock.getDefaultState())) {
                            System.out.println("TEste2");
                            return true;
                        }
                    }
        return false;
    }
}
