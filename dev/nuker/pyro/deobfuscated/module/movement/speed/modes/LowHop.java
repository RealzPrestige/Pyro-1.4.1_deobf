//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement.speed.modes;

import java.math.RoundingMode;
import java.math.BigDecimal;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.module.movement.Speed;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerJump;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.module.movement.speed.SpeedMode;

public class LowHop extends SpeedMode
{
    private int tick;
    private double prevDistance;
    private double movementSpeed;
    
    @Override
    public void onPlayerUpdate(final EventPlayerUpdate event) {
    }
    
    @Override
    public void onMotionUpdates(final EventPlayerMotionUpdate event) {
        if (event.getStage() != MinecraftEvent.Stage.Pre) {
            return;
        }
        final double deltaX = LowHop.mc.player.posX - LowHop.mc.player.prevPosX;
        final double deltaZ = LowHop.mc.player.posZ - LowHop.mc.player.prevPosZ;
        this.prevDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
    }
    
    @Override
    public void onPlayerJump(final EventPlayerJump event) {
    }
    
    @Override
    public void onPlayerMove(final EventPlayerMove event) {
        event.cancel();
        if (LowHop.mc.player.onGround && (LowHop.mc.player.moveForward != 0.0f || LowHop.mc.player.moveStrafing != 0.0f)) {
            if (Speed.UseTimer.getValue()) {
                PyroStatic.TIMER.SetOverrideSpeed(1.5f);
            }
        }
        else {
            PyroStatic.TIMER.SetOverrideSpeed(1.0f);
        }
        if (roundToPlace(LowHop.mc.player.posY - (int)LowHop.mc.player.posY, 3) == roundToPlace(0.4, 3)) {
            event.setY(LowHop.mc.player.motionY = 0.31);
        }
        else if (roundToPlace(LowHop.mc.player.posY - (int)LowHop.mc.player.posY, 3) == roundToPlace(0.71, 3)) {
            event.setY(LowHop.mc.player.motionY = 0.05);
        }
        else if (roundToPlace(LowHop.mc.player.posY - (int)LowHop.mc.player.posY, 3) == roundToPlace(0.75, 3)) {
            event.setY(LowHop.mc.player.motionY = -0.5);
        }
        else if (roundToPlace(LowHop.mc.player.posY - (int)LowHop.mc.player.posY, 3) == roundToPlace(0.55, 3)) {
            event.setY(LowHop.mc.player.motionY = -0.2);
        }
        else if (roundToPlace(LowHop.mc.player.posY - (int)LowHop.mc.player.posY, 3) == roundToPlace(0.41, 3)) {
            event.setY(LowHop.mc.player.motionY = -0.2);
        }
        if (this.tick == 1 && (LowHop.mc.player.moveForward != 0.0f || LowHop.mc.player.moveStrafing != 0.0f)) {
            this.movementSpeed = 2.0 * getDefaultSpeed() - 0.01;
        }
        else if (this.tick == 2 && (LowHop.mc.player.moveForward != 0.0f || LowHop.mc.player.moveStrafing != 0.0f)) {
            event.setY(LowHop.mc.player.motionY = 0.4);
            this.movementSpeed *= ((LowHop.mc.player.hurtResistantTime <= 3) ? 1.47 : 2.1);
        }
        else if (this.tick == 3) {
            final double difference = 0.66 * (this.prevDistance - getDefaultSpeed());
            this.movementSpeed = this.prevDistance - difference;
        }
        else {
            if (LowHop.mc.player.collidedVertically && this.tick > 0) {
                this.tick = ((LowHop.mc.player.moveForward != 0.0f || LowHop.mc.player.moveStrafing != 0.0f) ? 1 : 0);
            }
            this.movementSpeed = this.prevDistance - this.prevDistance / 200.0;
        }
        this.setMoveSpeed(event, this.movementSpeed = Math.max(this.movementSpeed, SpeedMode.getDefaultSpeed()));
        if (LowHop.mc.player.moveForward != 0.0f || LowHop.mc.player.moveStrafing != 0.0f) {
            ++this.tick;
        }
    }
    
    public void setMoveSpeed(final EventPlayerMove event, final double speed) {
        double forward = LowHop.mc.player.moveForward;
        double strafe = LowHop.mc.player.moveStrafing;
        float yaw = LowHop.mc.player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.X = 0.0;
            event.Z = 0.0;
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.X = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            event.Z = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }
    
    public static double roundToPlace(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
