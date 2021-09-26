// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.render;

import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventRenderHand extends MinecraftEvent
{
    public float PartialTicks;
    public int Pass;
    
    public EventRenderHand(final float partialTicks, final int pass) {
        this.PartialTicks = partialTicks;
        this.Pass = pass;
    }
}
