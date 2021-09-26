// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.player;

import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventPlayerTravel extends MinecraftEvent
{
    public float Strafe;
    public float Vertical;
    public float Forward;
    
    public EventPlayerTravel(final float p_Strafe, final float p_Vertical, final float p_Forward) {
        this.Strafe = p_Strafe;
        this.Vertical = p_Vertical;
        this.Forward = p_Forward;
    }
}
