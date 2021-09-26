//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util.blocks;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public final class PlaceInfo
{
    private final BlockPos blockPos;
    private final EnumFacing enumFacing;
    private final Vec3d vec3;
    
    private PlaceInfo(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.blockPos = blockPos;
        this.enumFacing = enumFacing;
        this.vec3 = new Vec3d(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
    }
    
    private PlaceInfo(final BlockPos blockPos, final EnumFacing enumFacing, final Vec3d Vec3d) {
        this.blockPos = blockPos;
        this.enumFacing = enumFacing;
        this.vec3 = Vec3d;
    }
    
    public BlockPos getBlockPos() {
        return this.blockPos;
    }
    
    public EnumFacing getEnumFacing() {
        return this.enumFacing;
    }
    
    public Vec3d getVec3d() {
        return this.vec3;
    }
    
    public static PlaceInfo get(final BlockPos blockPos) {
        if (BlockUtils.canBeClicked(blockPos.add(0, -1, 0))) {
            return new PlaceInfo(blockPos.add(0, -1, 0), EnumFacing.UP);
        }
        if (BlockUtils.canBeClicked(blockPos.add(-1, 0, 0))) {
            return new PlaceInfo(blockPos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (BlockUtils.canBeClicked(blockPos.add(1, 0, 0))) {
            return new PlaceInfo(blockPos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (BlockUtils.canBeClicked(blockPos.add(0, 0, -1))) {
            return new PlaceInfo(blockPos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (BlockUtils.canBeClicked(blockPos.add(0, 0, 1))) {
            return new PlaceInfo(blockPos.add(0, 0, 1), EnumFacing.NORTH);
        }
        return null;
    }
}
