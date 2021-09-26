// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.player;

import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventPlayerPushOutOfBlocks extends MinecraftEvent
{
    public double X;
    public double Y;
    public double Z;
    
    public EventPlayerPushOutOfBlocks(final double p_X, final double p_Y, final double p_Z) {
        this.X = p_X;
        this.Y = p_Y;
        this.Z = p_Z;
    }
}
