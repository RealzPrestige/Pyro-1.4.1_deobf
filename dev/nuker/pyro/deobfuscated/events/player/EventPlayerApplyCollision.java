// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.player;

import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventPlayerApplyCollision extends MinecraftEvent
{
    public Entity entity;
    
    public EventPlayerApplyCollision(final Entity p_Entity) {
        this.entity = p_Entity;
    }
}
