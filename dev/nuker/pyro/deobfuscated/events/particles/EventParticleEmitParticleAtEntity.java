// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.particles;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventParticleEmitParticleAtEntity extends MinecraftEvent
{
    public Entity entity;
    public EnumParticleTypes Type;
    public int Amount;
    
    public EventParticleEmitParticleAtEntity(final Entity p_Entity, final EnumParticleTypes p_Type, final int p_Amount) {
        this.entity = p_Entity;
        this.Type = p_Type;
        this.Amount = p_Amount;
    }
}
