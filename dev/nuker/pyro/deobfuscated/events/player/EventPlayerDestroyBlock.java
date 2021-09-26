// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.player;

import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventPlayerDestroyBlock extends MinecraftEvent
{
    public BlockPos Location;
    
    public EventPlayerDestroyBlock(final BlockPos loc) {
        this.Location = loc;
    }
}
