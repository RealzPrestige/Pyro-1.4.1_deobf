//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util.blocks;

import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.math.MathHelper;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dev.nuker.pyro.deobfuscated.util.MinecraftInstance;

@SideOnly(Side.CLIENT)
public final class BlockUtils extends MinecraftInstance
{
    public static Block getBlock(final BlockPos blockPos) {
        return BlockUtils.mc.world.getBlockState(blockPos).getBlock();
    }
    
    public static Material getMaterial(final BlockPos blockPos) {
        return getState(blockPos).getMaterial();
    }
    
    public static boolean isReplaceable(final BlockPos blockPos) {
        return getMaterial(blockPos).isReplaceable();
    }
    
    public static IBlockState getState(final BlockPos pos) {
        return BlockUtils.mc.world.getBlockState(pos);
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    public static String getBlockName(final int id) {
        return Block.getBlockById(id).getLocalizedName();
    }
    
    public static boolean isFullBlock(final BlockPos blockPos) {
        final AxisAlignedBB axisAlignedBB = getState(blockPos).getCollisionBoundingBox((IBlockAccess)BlockUtils.mc.world, blockPos);
        return axisAlignedBB != null && axisAlignedBB.maxX - axisAlignedBB.minX == 1.0 && axisAlignedBB.maxY - axisAlignedBB.minY == 1.0 && axisAlignedBB.maxZ - axisAlignedBB.minZ == 1.0;
    }
    
    public static double getCenterDistance(final BlockPos blockPos) {
        return BlockUtils.mc.player.getDistance(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
    }
    
    public static Map<BlockPos, Block> searchBlocks(final int radius) {
        final Map<BlockPos, Block> blocks = new HashMap<BlockPos, Block>();
        for (int x = radius; x > -radius; --x) {
            for (int y = radius; y > -radius; --y) {
                for (int z = radius; z > -radius; --z) {
                    final BlockPos blockPos = new BlockPos((int)BlockUtils.mc.player.posX + x, (int)BlockUtils.mc.player.posY + y, (int)BlockUtils.mc.player.posZ + z);
                    final Block block = getBlock(blockPos);
                    blocks.put(blockPos, block);
                }
            }
        }
        return blocks;
    }
    
    public static boolean collideBlock(final AxisAlignedBB axisAlignedBB, final ICollide collide) {
        for (int x = MathHelper.floor(BlockUtils.mc.player.getEntityBoundingBox().minX); x < MathHelper.floor(BlockUtils.mc.player.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor(BlockUtils.mc.player.getEntityBoundingBox().minZ); z < MathHelper.floor(BlockUtils.mc.player.getEntityBoundingBox().maxZ) + 1; ++z) {
                final Block block = getBlock(new BlockPos((double)x, axisAlignedBB.minY, (double)z));
                if (block != null && !collide.collideBlock(block)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean collideBlockIntersects(final AxisAlignedBB axisAlignedBB, final ICollide collide) {
        for (int x = MathHelper.floor(BlockUtils.mc.player.getEntityBoundingBox().minX); x < MathHelper.floor(BlockUtils.mc.player.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor(BlockUtils.mc.player.getEntityBoundingBox().minZ); z < MathHelper.floor(BlockUtils.mc.player.getEntityBoundingBox().maxZ) + 1; ++z) {
                final BlockPos blockPos = new BlockPos((double)x, axisAlignedBB.minY, (double)z);
                final Block block = getBlock(blockPos);
                if (block != null && collide.collideBlock(block)) {
                    final AxisAlignedBB boundingBox = getState(blockPos).getCollisionBoundingBox((IBlockAccess)BlockUtils.mc.world, blockPos);
                    if (boundingBox != null && BlockUtils.mc.player.getEntityBoundingBox().intersects(boundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static List<BlockPos> getBlocksInfront(final int distance, final int width, final int height) {
        final List<BlockPos> blocks = new ArrayList<BlockPos>();
        final BlockPos base = PlayerUtil.GetLocalPlayerPosFloored();
        final BlockPos north = base.north();
        final BlockPos south = base.south();
        final BlockPos east = base.east();
        final BlockPos west = base.west();
        switch (PlayerUtil.getCardinalFacingDirection()) {
            case North: {
                for (int i = 0; i < distance; ++i) {
                    for (int w = 0; w < width; ++w) {
                        for (int h = 0; h < height; ++h) {
                            blocks.add(north.add(w, h, -i));
                        }
                    }
                }
                break;
            }
            case South: {
                for (int i = 0; i < distance; ++i) {
                    for (int w = 0; w < width; ++w) {
                        for (int h = 0; h < height; ++h) {
                            blocks.add(south.add(w, h, i));
                        }
                    }
                }
                break;
            }
            case East: {
                for (int i = 0; i < distance; ++i) {
                    for (int w = 0; w < width; ++w) {
                        for (int h = 0; h < height; ++h) {
                            blocks.add(east.add(i, h, w));
                        }
                    }
                }
                break;
            }
            case West: {
                for (int i = 0; i < distance; ++i) {
                    for (int w = 0; w < width; ++w) {
                        for (int h = 0; h < height; ++h) {
                            blocks.add(west.add(-i, h, w));
                        }
                    }
                }
                break;
            }
            case NorthEast: {
                for (int i = 0; i < distance; ++i) {
                    for (int w = 0; w < width; ++w) {
                        for (int h = 0; h < height; ++h) {
                            blocks.add(east.add(i, h, w));
                            blocks.add(north.add(w, h, -i));
                        }
                    }
                }
                break;
            }
            case NorthWest: {
                for (int i = 0; i < distance; ++i) {
                    for (int w = 0; w < width; ++w) {
                        for (int h = 0; h < height; ++h) {
                            blocks.add(west.add(-i, h, w));
                            blocks.add(north.add(w, h, -i));
                        }
                    }
                }
                break;
            }
            case SouthEast: {
                for (int i = 0; i < distance; ++i) {
                    for (int w = 0; w < width; ++w) {
                        for (int h = 0; h < height; ++h) {
                            blocks.add(east.add(i, h, w));
                            blocks.add(south.add(w, h, i));
                        }
                    }
                }
                break;
            }
            case SouthWest: {
                for (int i = 0; i < distance; ++i) {
                    for (int w = 0; w < width; ++w) {
                        for (int h = 0; h < height; ++h) {
                            blocks.add(west.add(-i, h, w));
                            blocks.add(south.add(w, h, i));
                        }
                    }
                }
                break;
            }
        }
        return blocks;
    }
    
    public interface ICollide
    {
        boolean collideBlock(final Block p0);
    }
}
