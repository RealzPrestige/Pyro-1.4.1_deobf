//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class SafeWalk extends Module
{
    public final Value<Integer> height;
    @EventHandler
    private Listener<EventPlayerMove> OnPlayerMove;
    
    public SafeWalk() {
        super("SafeWalk", new String[] { "SWalk" }, "Prevents you from walking off certain blocks", "NONE", 7021787, ModuleType.MOVEMENT);
        this.height = new Value<Integer>("Height", new String[] { "Hei", "H" }, "The distance from the player on the Y-axis to run safe-walk checks for.", 1, 0, 32, 1);
        double x;
        final double y;
        double z;
        double increment;
        this.OnPlayerMove = new Listener<EventPlayerMove>(p_Event -> {
            x = p_Event.X;
            y = p_Event.Y;
            z = p_Event.Z;
            if (this.mc.player.onGround && !this.mc.player.noClip) {
                increment = 0.05;
                while (x != 0.0 && this.isOffsetBBEmpty(x, -this.height.getValue(), 0.0)) {
                    if (x < increment && x >= -increment) {
                        x = 0.0;
                    }
                    else if (x > 0.0) {
                        x -= increment;
                    }
                    else {
                        x += increment;
                    }
                }
                while (z != 0.0 && this.isOffsetBBEmpty(0.0, -this.height.getValue(), z)) {
                    if (z < increment && z >= -increment) {
                        z = 0.0;
                    }
                    else if (z > 0.0) {
                        z -= increment;
                    }
                    else {
                        z += increment;
                    }
                }
                while (x != 0.0 && z != 0.0 && this.isOffsetBBEmpty(x, -this.height.getValue(), z)) {
                    if (x < increment && x >= -increment) {
                        x = 0.0;
                    }
                    else if (x > 0.0) {
                        x -= increment;
                    }
                    else {
                        x += increment;
                    }
                    if (z < increment && z >= -increment) {
                        z = 0.0;
                    }
                    else if (z > 0.0) {
                        z -= increment;
                    }
                    else {
                        z += increment;
                    }
                }
            }
            p_Event.X = x;
            p_Event.Y = y;
            p_Event.Z = z;
            p_Event.cancel();
        });
    }
    
    private boolean isOffsetBBEmpty(final double x, final double y, final double z) {
        return this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(x, y, z)).isEmpty();
    }
}
