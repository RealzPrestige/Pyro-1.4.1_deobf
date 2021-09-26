//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement.speed.modes;

import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerJump;
import net.minecraft.client.entity.EntityPlayerSP;
import dev.nuker.pyro.deobfuscated.util.MovementUtils;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.util.TickTimer;
import dev.nuker.pyro.deobfuscated.module.movement.speed.SpeedMode;

public class Frame extends SpeedMode
{
    private int motionTicks;
    private boolean move;
    private final TickTimer tickTimer;
    
    public Frame() {
        this.tickTimer = new TickTimer();
    }
    
    @Override
    public void onPlayerUpdate(final EventPlayerUpdate event) {
    }
    
    @Override
    public void onMotionUpdates(final EventPlayerMotionUpdate event) {
        if (event.getStage() != MinecraftEvent.Stage.Pre) {
            return;
        }
        if (Frame.mc.player.movementInput.moveForward > 0.0f || Frame.mc.player.movementInput.moveStrafe > 0.0f) {
            final double speed = 4.25;
            if (Frame.mc.player.onGround) {
                Frame.mc.player.jump();
                if (this.motionTicks == 1) {
                    this.tickTimer.reset();
                    if (this.move) {
                        Frame.mc.player.motionX = 0.0;
                        Frame.mc.player.motionZ = 0.0;
                        this.move = false;
                    }
                    this.motionTicks = 0;
                }
                else {
                    this.motionTicks = 1;
                }
            }
            else if (!this.move && this.motionTicks == 1 && this.tickTimer.hasTimePassed(5)) {
                final EntityPlayerSP player = Frame.mc.player;
                player.motionX *= 4.25;
                final EntityPlayerSP player2 = Frame.mc.player;
                player2.motionZ *= 4.25;
                this.move = true;
            }
            if (!Frame.mc.player.onGround) {
                MovementUtils.strafe();
            }
            this.tickTimer.update();
        }
    }
    
    @Override
    public void onPlayerJump(final EventPlayerJump event) {
    }
    
    @Override
    public void onPlayerMove(final EventPlayerMove event) {
    }
}
