//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement.speed.modes;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.module.movement.Speed;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerJump;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.module.movement.speed.SpeedMode;

public class BunnyHop extends SpeedMode
{
    private int tick;
    private double prevDistance;
    private double movementSpeed;
    private boolean speed;
    
    public BunnyHop() {
        this.speed = true;
        this.tick = 1;
        this.movementSpeed = SpeedMode.getDefaultSpeed();
    }
    
    @Override
    public void onPlayerUpdate(final EventPlayerUpdate event) {
    }
    
    @Override
    public void onMotionUpdates(final EventPlayerMotionUpdate event) {
        if (event.getStage() != MinecraftEvent.Stage.Pre) {
            return;
        }
        final double deltaX = BunnyHop.mc.player.posX - BunnyHop.mc.player.prevPosX;
        final double deltaZ = BunnyHop.mc.player.posZ - BunnyHop.mc.player.prevPosZ;
        this.prevDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
    }
    
    @Override
    public void onPlayerJump(final EventPlayerJump event) {
    }
    
    @Override
    public void onPlayerMove(final EventPlayerMove event) {
        if (event.getStage() != MinecraftEvent.Stage.Pre || event.isCancelled()) {
            return;
        }
        if (BunnyHop.mc.player.isSneaking()) {
            return;
        }
        if (BunnyHop.mc.player.fallDistance > 4.0f) {
            return;
        }
        if (MathUtil.round(BunnyHop.mc.player.posY - (int)BunnyHop.mc.player.posY, 3) == MathUtil.round(0.138, 3)) {
            final EntityPlayerSP player = BunnyHop.mc.player;
            --player.motionY;
            event.setY(event.getY() - 0.0931);
            event.cancel();
            final EntityPlayerSP player2 = BunnyHop.mc.player;
            player2.posY -= 0.0931;
        }
        if (this.tick == 2 && (BunnyHop.mc.player.moveForward != 0.0f || BunnyHop.mc.player.moveStrafing != 0.0f)) {
            event.setY(0.4);
            event.cancel();
            BunnyHop.mc.player.motionY = 0.39936;
            this.speed = !this.speed;
            this.movementSpeed *= (this.speed ? 1.685 : 1.395);
        }
        else if (this.tick == 3) {
            final double difference = 0.66 * (this.prevDistance - getDefaultSpeed());
            this.movementSpeed = this.prevDistance - difference;
            if (Speed.UseTimer.getValue()) {
                PyroStatic.TIMER.SetOverrideSpeed(this.speed ? 1.125f : 1.088f);
            }
        }
        else {
            if (BunnyHop.mc.world.getCollisionBoxes((Entity)BunnyHop.mc.player, BunnyHop.mc.player.getEntityBoundingBox().offset(0.0, BunnyHop.mc.player.motionY, 0.0)).size() > 0 || BunnyHop.mc.player.collidedVertically) {
                this.tick = 1;
            }
            this.movementSpeed = this.prevDistance - this.prevDistance / 159.0;
        }
        this.movementSpeed = Math.max(this.movementSpeed, SpeedMode.getDefaultSpeed());
        event.cancel();
        this.movementSpeed = Math.max(this.movementSpeed, SpeedMode.getDefaultSpeed());
        float forward = BunnyHop.mc.player.movementInput.moveForward;
        float strafe = BunnyHop.mc.player.movementInput.moveStrafe;
        float yaw = BunnyHop.mc.player.rotationYaw;
        if (forward == 0.0f && strafe == 0.0f) {
            event.X = 0.0;
            event.Z = 0.0;
        }
        else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
                strafe = 0.0f;
            }
            else if (strafe <= -1.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
                strafe = 0.0f;
            }
        }
        if (forward > 0.0f) {
            forward = 1.0f;
        }
        else if (forward < 0.0f) {
            forward = -1.0f;
        }
        if (strafe > 0.0f) {
            strafe = 1.0f;
        }
        else if (strafe < 0.0f) {
            strafe = -1.0f;
        }
        final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        event.X = forward * this.movementSpeed * mx + strafe * this.movementSpeed * mz;
        event.Z = forward * this.movementSpeed * mz - strafe * this.movementSpeed * mx;
        ++this.tick;
    }
}
