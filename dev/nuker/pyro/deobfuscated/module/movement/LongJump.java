//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import net.minecraft.util.math.AxisAlignedBB;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.module.movement.speed.SpeedMode;
import dev.nuker.pyro.deobfuscated.util.MovementUtils;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class LongJump extends Module
{
    private static final double SPEED_BASE = 0.2873;
    private double moveSpeed;
    private double lastDist;
    private int stage;
    public static int settingUpTicks;
    private final Value<Double> speedInc;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onWalking;
    @EventHandler
    private Listener<EventPlayerMove> onPlayerMove;
    
    public LongJump() {
        super("LongJump", new String[] { "LJ" }, "Allows you to jump longer", "NONE", -1, ModuleType.MOVEMENT);
        this.speedInc = new Value<Double>("speedInc", new String[0], "SpeedInc", 2.13, 0.0, 3.0, 1.0);
        double xDist;
        double zDist;
        this.onWalking = new Listener<EventPlayerMotionUpdate>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                xDist = this.mc.player.posX - this.mc.player.prevPosX;
                zDist = this.mc.player.posZ - this.mc.player.prevPosZ;
                this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
                return;
            }
        });
        final EntityPlayerSP entityPlayerSP;
        final double motionY;
        double difference;
        List collidingList;
        List collidingList2;
        final EntityPlayerSP entityPlayerSP2;
        final double motionY2;
        final EntityPlayerSP entityPlayerSP3;
        final double motionY3;
        this.onPlayerMove = new Listener<EventPlayerMove>(event -> {
            if (!event.isCancelled() && event.getStage() == MinecraftEvent.Stage.Pre) {
                if (MovementUtils.player().collidedHorizontally || (MovementUtils.player().moveForward == 0.0f && MovementUtils.player().moveStrafing == 0.0f)) {
                    this.stage = 0;
                    LongJump.settingUpTicks = 2;
                    event.cancel();
                    event.setX(0.0);
                    event.setZ(0.0);
                }
                else {
                    if (LongJump.settingUpTicks > 0 && (MovementUtils.player().moveForward != 0.0f || MovementUtils.player().moveStrafing != 0.0f)) {
                        this.moveSpeed = 0.09;
                        --LongJump.settingUpTicks;
                    }
                    else if (this.stage == 1 && MovementUtils.player().collidedVertically && (MovementUtils.player().moveForward != 0.0f || MovementUtils.player().moveStrafing != 0.0f)) {
                        this.moveSpeed = 1.0 + SpeedMode.getDefaultSpeed() - 0.05;
                    }
                    else if (this.stage == 2 && MovementUtils.player().collidedVertically && (MovementUtils.player().moveForward != 0.0f || MovementUtils.player().moveStrafing != 0.0f)) {
                        MovementUtils.player();
                        event.setY(entityPlayerSP.motionY = motionY);
                        this.moveSpeed *= this.speedInc.getValue();
                    }
                    else if (this.stage == 3) {
                        difference = 0.66 * (this.lastDist - SpeedMode.getDefaultSpeed());
                        this.moveSpeed = this.lastDist - difference;
                    }
                    else {
                        this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                    }
                    event.cancel();
                    MovementUtils.setMoveSpeed(event, this.moveSpeed);
                    collidingList = this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(0.0, this.mc.player.motionY, 0.0));
                    collidingList2 = this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(0.0, -0.4, 0.0));
                    if (!MovementUtils.player().collidedVertically && (collidingList.size() > 0 || collidingList2.size() > 0) && this.stage > 10) {
                        if (this.stage >= 38) {
                            MovementUtils.player();
                            event.setY(entityPlayerSP2.motionY = motionY2);
                            this.stage = 0;
                            LongJump.settingUpTicks = 5;
                        }
                        else {
                            MovementUtils.player();
                            event.setY(entityPlayerSP3.motionY = motionY3);
                        }
                    }
                    if (LongJump.settingUpTicks <= 0 && (MovementUtils.player().moveForward != 0.0f || MovementUtils.player().moveStrafing != 0.0f)) {
                        ++this.stage;
                    }
                }
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.stage = 0;
        LongJump.settingUpTicks = 2;
    }
}
