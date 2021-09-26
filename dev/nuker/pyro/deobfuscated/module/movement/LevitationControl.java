//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import dev.nuker.pyro.deobfuscated.util.MathUtil;
import net.minecraft.init.MobEffects;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerTravel;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class LevitationControl extends Module
{
    @EventHandler
    private Listener<EventPlayerTravel> OnTravel;
    
    public LevitationControl() {
        super("LevitationControl", new String[] { "NoLevitate" }, "Allows you to control your levitation", "NONE", 13433594, ModuleType.MOVEMENT);
        double[] dir;
        this.OnTravel = new Listener<EventPlayerTravel>(event -> {
            if (this.mc.player != null && !this.mc.player.isRiding()) {
                if (!(!this.mc.player.isPotionActive(MobEffects.LEVITATION))) {
                    this.mc.player.setVelocity(0.0, 0.0, 0.0);
                    dir = MathUtil.directionSpeed(0.10000000149011612);
                    if (this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f) {
                        this.mc.player.motionX = dir[0];
                        this.mc.player.motionZ = dir[1];
                    }
                    if (this.mc.player.movementInput.jump && !this.mc.player.isElytraFlying()) {
                        this.mc.player.motionY = 0.10000000149011612;
                    }
                    if (this.mc.player.movementInput.sneak) {
                        this.mc.player.motionY = -0.10000000149011612;
                    }
                    event.cancel();
                    this.mc.player.prevLimbSwingAmount = 0.0f;
                    this.mc.player.limbSwingAmount = 0.0f;
                    this.mc.player.limbSwing = 0.0f;
                }
            }
        });
    }
}
