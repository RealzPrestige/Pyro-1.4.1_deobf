// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.render;

import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class RenderEvent extends MinecraftEvent
{
    private float _partialTicks;
    
    public RenderEvent(final float partialTicks) {
        this._partialTicks = partialTicks;
    }
    
    @Override
    public float getPartialTicks() {
        return this._partialTicks;
    }
}
