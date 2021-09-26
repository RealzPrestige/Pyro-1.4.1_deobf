// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.blocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import dev.nuker.pyro.deobfuscated.events.bus.Cancellable;

public class EventCanPlaceCheck extends Cancellable
{
    public World World;
    public BlockPos Pos;
    public Class<?> Block;
    
    public EventCanPlaceCheck(final World world, final BlockPos pos, final Class<?> block) {
        this.World = world;
        this.Pos = pos;
        this.Block = block;
    }
}
