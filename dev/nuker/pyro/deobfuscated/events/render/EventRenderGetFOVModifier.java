// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.render;

import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventRenderGetFOVModifier extends MinecraftEvent
{
    public float PartialTicks;
    public boolean UseFOVSetting;
    private float FOV;
    
    public EventRenderGetFOVModifier(final float p_PartialTicks, final boolean p_UseFOVSetting) {
        this.PartialTicks = p_PartialTicks;
        this.UseFOVSetting = p_UseFOVSetting;
    }
    
    public void SetFOV(final float p_FOV) {
        this.FOV = p_FOV;
    }
    
    public float GetFOV() {
        return this.FOV;
    }
}
