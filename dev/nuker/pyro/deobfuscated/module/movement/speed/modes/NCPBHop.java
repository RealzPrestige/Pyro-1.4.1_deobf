//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement.speed.modes;

import java.math.RoundingMode;
import java.math.BigDecimal;
import net.minecraft.util.MovementInput;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.util.MovementUtils;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.module.movement.Speed;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerJump;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.module.movement.speed.SpeedMode;

public class NCPBHop extends SpeedMode
{
    private int level;
    private double moveSpeed;
    private double lastDist;
    private int timerDelay;
    
    public NCPBHop() {
        this.level = 1;
        this.moveSpeed = 0.2873;
    }
    
    @Override
    public void onPlayerUpdate(final EventPlayerUpdate event) {
    }
    
    @Override
    public void onMotionUpdates(final EventPlayerMotionUpdate event) {
        if (event.getStage() != MinecraftEvent.Stage.Pre) {
            return;
        }
        final double xDist = NCPBHop.mc.player.posX - NCPBHop.mc.player.prevPosX;
        final double zDist = NCPBHop.mc.player.posZ - NCPBHop.mc.player.prevPosZ;
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }
    
    @Override
    public void onPlayerJump(final EventPlayerJump event) {
    }
    
    @Override
    public void onPlayerMove(final EventPlayerMove event) {
        if (event.getStage() != MinecraftEvent.Stage.Pre || event.isCancelled()) {
            return;
        }
        event.cancel();
        ++this.timerDelay;
        this.timerDelay %= 5;
        if (this.timerDelay != 0) {
            if (Speed.UseTimer.getValue()) {
                PyroStatic.TIMER.SetOverrideSpeed(1.0f);
            }
        }
        else if (MovementUtils.isMoving()) {
            if (Speed.UseTimer.getValue()) {
                PyroStatic.TIMER.SetOverrideSpeed(1.3f);
            }
            final EntityPlayerSP player = NCPBHop.mc.player;
            player.motionX *= 1.0199999809265137;
            final EntityPlayerSP player2 = NCPBHop.mc.player;
            player2.motionZ *= 1.0199999809265137;
        }
        if (NCPBHop.mc.player.onGround && MovementUtils.isMoving()) {
            this.level = 2;
        }
        if (this.round(NCPBHop.mc.player.posY - (int)NCPBHop.mc.player.posY) == this.round(0.138)) {
            final EntityPlayerSP player3;
            final EntityPlayerSP player = player3 = NCPBHop.mc.player;
            player3.motionY -= 0.08;
            event.setY(event.getY() - 0.09316090325960147);
            final EntityPlayerSP entityPlayerSP = player;
            entityPlayerSP.posY -= 0.09316090325960147;
        }
        if (this.level == 1 && (NCPBHop.mc.player.moveForward != 0.0f || NCPBHop.mc.player.moveStrafing != 0.0f)) {
            this.level = 2;
            this.moveSpeed = 1.35 * getDefaultSpeed() - 0.01;
        }
        else if (this.level == 2) {
            this.level = 3;
            event.setY(NCPBHop.mc.player.motionY = 0.399399995803833);
            this.moveSpeed *= 2.149;
        }
        else if (this.level == 3) {
            this.level = 4;
            final double difference = 0.66 * (this.lastDist - getDefaultSpeed());
            this.moveSpeed = this.lastDist - difference;
        }
        else {
            if (NCPBHop.mc.world.getCollisionBoxes((Entity)NCPBHop.mc.player, NCPBHop.mc.player.getEntityBoundingBox().offset(0.0, NCPBHop.mc.player.motionY, 0.0)).size() > 0 || NCPBHop.mc.player.collidedVertically) {
                this.level = 1;
            }
            this.moveSpeed = this.lastDist - this.lastDist / 159.0;
        }
        this.moveSpeed = Math.max(this.moveSpeed, SpeedMode.getDefaultSpeed());
        final MovementInput movementInput = NCPBHop.mc.player.movementInput;
        float forward = movementInput.moveForward;
        float strafe = movementInput.moveStrafe;
        float yaw = NCPBHop.mc.player.rotationYaw;
        if (forward == 0.0f && strafe == 0.0f) {
            event.setX(0.0);
            event.setZ(0.0);
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
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double mx2 = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz2 = Math.sin(Math.toRadians(yaw + 90.0f));
        event.setX(forward * this.moveSpeed * mx2 + strafe * this.moveSpeed * mz2);
        event.setZ(forward * this.moveSpeed * mz2 - strafe * this.moveSpeed * mx2);
        NCPBHop.mc.player.stepHeight = 0.6f;
        if (forward == 0.0f && strafe == 0.0f) {
            event.setX(0.0);
            event.setZ(0.0);
        }
    }
    
    private double round(final double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
