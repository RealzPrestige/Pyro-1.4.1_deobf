// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.world;

import net.minecraft.util.math.Vec3d;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventGetSkyColor extends MinecraftEvent
{
    private Vec3d color;
    
    public void setColor(final Vec3d color) {
        this.color = color;
    }
    
    public Vec3d getVec3d() {
        return this.color;
    }
}
