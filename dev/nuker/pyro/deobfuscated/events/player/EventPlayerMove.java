// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.player;

import net.minecraft.entity.MoverType;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventPlayerMove extends MinecraftEvent
{
    public MoverType Type;
    public double X;
    public double Y;
    public double Z;
    
    public EventPlayerMove(final Stage post, final MoverType p_Type, final double p_X, final double p_Y, final double p_Z) {
        this.setEra(post);
        this.Type = p_Type;
        this.X = p_X;
        this.Y = p_Y;
        this.Z = p_Z;
    }
    
    public void setY(final double y) {
        this.Y = y;
    }
    
    public double getY() {
        return this.Y;
    }
    
    public void setX(final double x) {
        this.X = x;
    }
    
    public void setZ(final double z) {
        this.Z = z;
    }
    
    public void zeroXZ() {
        this.cancel();
        this.X = 0.0;
        this.Z = 0.0;
    }
}
