//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerTravel;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Parkour extends Module
{
    @EventHandler
    private Listener<EventPlayerTravel> onTravel;
    
    public Parkour() {
        super("Parkour", new String[] { "Parkour", "EdgeJump", "Parkourmaster", "Parkuur", "Park" }, "Jump at the edge of a block.", "NONE", -1, ModuleType.MOVEMENT);
        this.onTravel = new Listener<EventPlayerTravel>(event -> {
            if (this.mc.player.onGround && !this.mc.player.isSneaking()) {
                if (this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(0.0, -0.5, 0.0).expand(-0.001, 0.0, -0.001)).isEmpty()) {
                    this.mc.player.jump();
                }
            }
        });
    }
}
