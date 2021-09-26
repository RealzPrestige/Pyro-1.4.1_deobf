// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.render;

import net.minecraft.client.entity.AbstractClientPlayer;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventRenderEntityName extends MinecraftEvent
{
    public AbstractClientPlayer Entity;
    public double X;
    public double Y;
    public double Z;
    public String Name;
    public double DistanceSq;
    
    public EventRenderEntityName(final AbstractClientPlayer entityIn, double x, double y, double z, final String name, final double distanceSq) {
        this.Entity = entityIn;
        x = this.X;
        y = this.Y;
        z = this.Z;
        this.Name = name;
        this.DistanceSq = distanceSq;
    }
}
