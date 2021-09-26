// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.render;

import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventRenderEntity extends MinecraftEvent
{
    private Entity entity;
    
    public EventRenderEntity(final Entity entityIn, final ICamera camera, final double camX, final double camY, final double camZ) {
        this.entity = entityIn;
    }
    
    public Entity GetEntity() {
        return this.entity;
    }
}
