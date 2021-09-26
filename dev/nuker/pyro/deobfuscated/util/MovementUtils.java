//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import dev.nuker.pyro.deobfuscated.main.Wrapper;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import net.minecraft.util.MovementInput;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;

public class MovementUtils
{
    private static final Minecraft mc;
    
    public static float getSpeed() {
        return (float)Math.sqrt(MovementUtils.mc.player.motionX * MovementUtils.mc.player.motionX + MovementUtils.mc.player.motionZ * MovementUtils.mc.player.motionZ);
    }
    
    public static void strafe() {
        strafe(getSpeed());
    }
    
    public static boolean isMoving() {
        return MovementUtils.mc.player != null && (MovementUtils.mc.player.movementInput.moveForward != 0.0f || MovementUtils.mc.player.movementInput.moveStrafe != 0.0f);
    }
    
    public static boolean hasMotion() {
        return MovementUtils.mc.player.motionX != 0.0 && MovementUtils.mc.player.motionZ != 0.0 && MovementUtils.mc.player.motionY != 0.0;
    }
    
    public static void strafe(final float speed) {
        if (!isMoving()) {
            return;
        }
        final double yaw = getDirection();
        MovementUtils.mc.player.motionX = -Math.sin(yaw) * speed;
        MovementUtils.mc.player.motionZ = Math.cos(yaw) * speed;
    }
    
    public static void forward(final double length) {
        final double yaw = Math.toRadians(MovementUtils.mc.player.rotationYaw);
        MovementUtils.mc.player.setPosition(MovementUtils.mc.player.posX + -Math.sin(yaw) * length, MovementUtils.mc.player.posY, MovementUtils.mc.player.posZ + Math.cos(yaw) * length);
    }
    
    public static double getDirection() {
        float rotationYaw = MovementUtils.mc.player.rotationYaw;
        if (MovementUtils.mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (MovementUtils.mc.player.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (MovementUtils.mc.player.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (MovementUtils.mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (MovementUtils.mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }
    
    public static EntityPlayerSP player() {
        return MovementUtils.mc.player;
    }
    
    public static MovementInput movementInput() {
        return player().movementInput;
    }
    
    public static double x() {
        return player().posX;
    }
    
    public static void x(final double x) {
        player().posX = x;
    }
    
    public static double y() {
        return player().posY;
    }
    
    public static void y(final double y) {
        player().posY = y;
    }
    
    public static double z() {
        return player().posZ;
    }
    
    public static void z(final double z) {
        player().posZ = z;
    }
    
    public static float yaw() {
        return player().rotationYaw;
    }
    
    public static void yaw(final float yaw) {
        player().rotationYaw = yaw;
    }
    
    public static float pitch() {
        return player().rotationPitch;
    }
    
    public static void pitch(final float pitch) {
        player().rotationPitch = pitch;
    }
    
    public static void setMoveSpeed(final EventPlayerMove event, final double speed) {
        double forward = movementInput().moveForward;
        double strafe = movementInput().moveStrafe;
        float yaw = yaw();
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
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
            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }
    
    static {
        mc = Wrapper.GetMC();
    }
}
