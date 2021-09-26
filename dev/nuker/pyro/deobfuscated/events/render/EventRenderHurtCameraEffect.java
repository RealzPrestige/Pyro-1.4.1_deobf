// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.render;

import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventRenderHurtCameraEffect extends MinecraftEvent
{
    public float Ticks;
    
    public EventRenderHurtCameraEffect(final float p_Ticks) {
        this.Ticks = p_Ticks;
    }
}
