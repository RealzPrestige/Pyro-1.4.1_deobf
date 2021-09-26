// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class Hole extends Vec3i
{
    private BlockPos blockPos;
    private boolean tall;
    private HoleTypes HoleType;
    
    public Hole(final int x, final int y, final int z, final BlockPos pos, final HoleTypes p_Type) {
        super(x, y, z);
        this.blockPos = pos;
        this.SetHoleType(p_Type);
    }
    
    public Hole(final int x, final int y, final int z, final BlockPos pos, final HoleTypes p_Type, final boolean tall) {
        super(x, y, z);
        this.blockPos = pos;
        this.tall = true;
        this.SetHoleType(p_Type);
    }
    
    public boolean isTall() {
        return this.tall;
    }
    
    public BlockPos GetBlockPos() {
        return this.blockPos;
    }
    
    public HoleTypes GetHoleType() {
        return this.HoleType;
    }
    
    public void SetHoleType(final HoleTypes holeType) {
        this.HoleType = holeType;
    }
    
    public enum HoleTypes
    {
        None, 
        Normal, 
        Obsidian, 
        Bedrock;
    }
}
