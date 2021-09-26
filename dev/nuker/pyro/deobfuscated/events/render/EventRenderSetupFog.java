// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.render;

import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventRenderSetupFog extends MinecraftEvent
{
    public int StartCoords;
    public float PartialTicks;
    
    public EventRenderSetupFog(final int startCoords, final float partialTicks) {
        this.StartCoords = startCoords;
        this.PartialTicks = partialTicks;
    }
}
