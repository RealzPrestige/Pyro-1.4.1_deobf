//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement.speed.modes;

import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerJump;
import net.minecraft.client.entity.EntityPlayerSP;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.util.MovementUtils;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.module.movement.speed.SpeedMode;

public class OnGround extends SpeedMode
{
    private boolean flag;
    
    public OnGround() {
        this.flag = false;
    }
    
    @Override
    public void onPlayerUpdate(final EventPlayerUpdate event) {
    }
    
    @Override
    public void onMotionUpdates(final EventPlayerMotionUpdate event) {
        if (!MovementUtils.isMoving()) {
            return;
        }
        if (OnGround.mc.player.fallDistance > 3.994) {
            return;
        }
        if (OnGround.mc.player.isInWater() || OnGround.mc.player.isOnLadder() || OnGround.mc.player.collidedHorizontally) {
            return;
        }
        if (OnGround.mc.player.onGround) {
            final EntityPlayerSP player = OnGround.mc.player;
            player.motionX *= 1.590000033378601;
            final EntityPlayerSP player2 = OnGround.mc.player;
            player2.motionZ *= 1.590000033378601;
            PyroStatic.TIMER.SetOverrideSpeed(1.199f);
            event.cancel();
            event.setPitch(OnGround.mc.player.rotationPitch);
            event.setYaw(OnGround.mc.player.rotationYaw);
            if (this.flag) {
                event.setY(event.getY() + 0.4);
            }
            this.flag = !this.flag;
            event.setOnGround(true);
        }
    }
    
    @Override
    public void onPlayerJump(final EventPlayerJump event) {
    }
    
    @Override
    public void onPlayerMove(final EventPlayerMove event) {
    }
}
