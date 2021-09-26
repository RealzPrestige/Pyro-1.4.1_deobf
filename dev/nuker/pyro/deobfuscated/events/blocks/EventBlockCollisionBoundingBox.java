// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.blocks;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventBlockCollisionBoundingBox extends MinecraftEvent
{
    private BlockPos _pos;
    private AxisAlignedBB _boundingBox;
    
    public EventBlockCollisionBoundingBox(final BlockPos pos) {
        this._pos = pos;
    }
    
    public BlockPos getPos() {
        return this._pos;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this._boundingBox;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this._boundingBox = boundingBox;
    }
}
