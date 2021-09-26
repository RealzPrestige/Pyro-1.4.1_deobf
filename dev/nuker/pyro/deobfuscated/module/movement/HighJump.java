//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerTravel;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class HighJump extends Module
{
    public final Value<Boolean> InAir;
    public final Value<Float> Height;
    @EventHandler
    private Listener<EventPlayerTravel> OnTravel;
    
    public HighJump() {
        super("HighJump", new String[] { "AW" }, "Jump way higher than a normal jump", "NONE", 12723419, ModuleType.MOVEMENT);
        this.InAir = new Value<Boolean>("InAir", new String[] { "Air", "OnGroundOnly", "OnGround", "GroundOnly", "Ground" }, "Should you be able to jump in air", true);
        this.Height = new Value<Float>("Height", new String[] { "Height", "Heigh", "Hight", "High", "Size", "Scaling", "Scale" }, "Height to increase", 1.4f, 0.0f, 10.0f, 1.0f);
        this.OnTravel = new Listener<EventPlayerTravel>(event -> {
            if (!event.isCancelled()) {
                if (this.mc.player != null && !this.mc.player.isRiding() && !this.mc.player.isElytraFlying()) {
                    if ((this.mc.player.movementInput.jump && this.InAir.getValue()) || this.mc.player.onGround) {
                        event.cancel();
                        this.mc.player.motionY = this.Height.getValue();
                    }
                }
            }
        });
    }
}
