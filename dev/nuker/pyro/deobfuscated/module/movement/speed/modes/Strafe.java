//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement.speed.modes;

import net.minecraft.init.MobEffects;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerJump;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.module.movement.Speed;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.module.movement.speed.SpeedMode;

public class Strafe extends SpeedMode
{
    private float GetRotationYawForCalc() {
        float rotationYaw = Strafe.mc.player.rotationYaw;
        if (Strafe.mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n = 1.0f;
        if (Strafe.mc.player.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (Strafe.mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (Strafe.mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (Strafe.mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
    
    @Override
    public void onPlayerUpdate(final EventPlayerUpdate event) {
        if (Strafe.mc.player.isRiding()) {
            return;
        }
        if ((Strafe.mc.player.isInWater() || Strafe.mc.player.isInLava()) && !Speed.SpeedInWater.getValue()) {
            return;
        }
        if (Speed.UseTimer.getValue()) {
            PyroStatic.TIMER.SetOverrideSpeed(1.088f);
        }
        if (Strafe.mc.player.moveForward != 0.0f || Strafe.mc.player.moveStrafing != 0.0f) {
            if (Speed.AutoSprint.getValue()) {
                Strafe.mc.player.setSprinting(true);
            }
            if (Strafe.mc.player.onGround) {
                if (Speed.AutoJump.getValue()) {
                    Strafe.mc.player.motionY = 0.4050000011920929;
                }
                final float yaw = this.GetRotationYawForCalc();
                final EntityPlayerSP player = Strafe.mc.player;
                player.motionX -= MathHelper.sin(yaw) * 0.2f;
                final EntityPlayerSP player2 = Strafe.mc.player;
                player2.motionZ += MathHelper.cos(yaw) * 0.2f;
            }
        }
        if (Strafe.mc.player.movementInput.jump && Strafe.mc.player.onGround) {
            Strafe.mc.player.motionY = 0.4050000011920929;
        }
    }
    
    @Override
    public void onMotionUpdates(final EventPlayerMotionUpdate event) {
    }
    
    @Override
    public void onPlayerJump(final EventPlayerJump event) {
        event.cancel();
    }
    
    @Override
    public void onPlayerMove(final EventPlayerMove event) {
        if (event.getStage() != MinecraftEvent.Stage.Pre || event.isCancelled()) {
            return;
        }
        if ((Strafe.mc.player.isInWater() || Strafe.mc.player.isInLava()) && !Speed.SpeedInWater.getValue()) {
            return;
        }
        if (Strafe.mc.player.capabilities != null) {
            if (Strafe.mc.player.capabilities.isFlying || PyroStatic.FLIGHT.isEnabled() || Strafe.mc.player.isElytraFlying()) {
                return;
            }
            if (Strafe.mc.player.onGround) {
                return;
            }
            float playerSpeed = 0.2873f;
            float moveForward = Strafe.mc.player.movementInput.moveForward;
            float moveStrafe = Strafe.mc.player.movementInput.moveStrafe;
            float rotationYaw = Strafe.mc.player.rotationYaw;
            if (Strafe.mc.player.isPotionActive(MobEffects.SPEED)) {
                final int amplifier = Strafe.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                playerSpeed *= 1.0f + 0.2f * (amplifier + 1);
            }
            if (!Speed.Strict.getValue()) {
                playerSpeed *= 1.0064f;
            }
            if (moveForward == 0.0f && moveStrafe == 0.0f) {
                event.setX(0.0);
                event.setZ(0.0);
            }
            else {
                if (moveForward != 0.0f) {
                    if (moveStrafe > 0.0f) {
                        rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
                    }
                    else if (moveStrafe < 0.0f) {
                        rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
                    }
                    moveStrafe = 0.0f;
                    if (moveForward > 0.0f) {
                        moveForward = 1.0f;
                    }
                    else if (moveForward < 0.0f) {
                        moveForward = -1.0f;
                    }
                }
                event.setX(moveForward * playerSpeed * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + moveStrafe * playerSpeed * Math.sin(Math.toRadians(rotationYaw + 90.0f)));
                event.setZ(moveForward * playerSpeed * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - moveStrafe * playerSpeed * Math.cos(Math.toRadians(rotationYaw + 90.0f)));
            }
            event.cancel();
        }
    }
}
