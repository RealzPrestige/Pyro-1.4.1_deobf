//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement.speed.modes;

import dev.nuker.pyro.deobfuscated.util.MovementUtils;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerJump;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.module.movement.speed.SpeedMode;

public class Boost extends SpeedMode
{
    private int motionDelay;
    private float ground;
    
    @Override
    public void onPlayerUpdate(final EventPlayerUpdate event) {
    }
    
    @Override
    public void onMotionUpdates(final EventPlayerMotionUpdate event) {
        if (event.getStage() != MinecraftEvent.Stage.Pre) {
            return;
        }
        double speed = 3.1981;
        double offset = 4.69;
        boolean shouldOffset = true;
        for (final Object o : Boost.mc.world.getCollisionBoxes((Entity)Boost.mc.player, Boost.mc.player.getEntityBoundingBox().offset(Boost.mc.player.motionX / offset, 0.0, Boost.mc.player.motionZ / offset))) {
            if (o instanceof AxisAlignedBB) {
                shouldOffset = false;
                break;
            }
        }
        if (Boost.mc.player.onGround && this.ground < 1.0f) {
            this.ground += 0.2f;
        }
        if (!Boost.mc.player.onGround) {
            this.ground = 0.0f;
        }
        if (this.ground == 1.0f && this.shouldSpeedUp()) {
            if (!Boost.mc.player.isSprinting()) {
                offset += 0.8;
            }
            if (Boost.mc.player.moveStrafing != 0.0f) {
                speed -= 0.1;
                offset += 0.5;
            }
            if (Boost.mc.player.isInWater()) {
                speed -= 0.1;
            }
            switch (++this.motionDelay) {
                case 1: {
                    final EntityPlayerSP player = Boost.mc.player;
                    player.motionX *= speed;
                    final EntityPlayerSP player2 = Boost.mc.player;
                    player2.motionZ *= speed;
                    break;
                }
                case 2: {
                    final EntityPlayerSP player3 = Boost.mc.player;
                    player3.motionX /= 1.458;
                    final EntityPlayerSP player4 = Boost.mc.player;
                    player4.motionZ /= 1.458;
                    break;
                }
                case 4: {
                    if (shouldOffset) {
                        Boost.mc.player.setPosition(Boost.mc.player.posX + Boost.mc.player.motionX / offset, Boost.mc.player.posY, Boost.mc.player.posZ + Boost.mc.player.motionZ / offset);
                    }
                    this.motionDelay = 0;
                    break;
                }
            }
        }
    }
    
    @Override
    public void onPlayerJump(final EventPlayerJump event) {
    }
    
    @Override
    public void onPlayerMove(final EventPlayerMove event) {
    }
    
    private boolean shouldSpeedUp() {
        return !Boost.mc.player.isInWater() && !Boost.mc.player.isOnLadder() && !Boost.mc.player.isSneaking() && MovementUtils.isMoving();
    }
}
