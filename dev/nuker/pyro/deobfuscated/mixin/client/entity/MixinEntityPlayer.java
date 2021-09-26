//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client.entity;

import dev.nuker.pyro.deobfuscated.events.player.EventPlayerPushedByWater;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerApplyCollision;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.entity.MoverType;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerTravel;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { EntityPlayer.class }, priority = Integer.MAX_VALUE)
public abstract class MixinEntityPlayer extends MixinEntityLivingBase
{
    @Shadow
    public void onUpdate() {
    }
    
    @Inject(method = { "travel" }, at = { @At("HEAD") }, cancellable = true)
    public void travel(final float strafe, final float vertical, final float forward, final CallbackInfo info) {
        final EntityPlayer us = (EntityPlayer)this;
        if (!(us instanceof EntityPlayerSP)) {
            return;
        }
        final EventPlayerTravel event = new EventPlayerTravel(strafe, vertical, forward);
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            info.cancel();
        }
    }
    
    @Inject(method = { "applyEntityCollision" }, at = { @At("HEAD") }, cancellable = true)
    public void applyEntityCollision(final Entity p_Entity, final CallbackInfo info) {
        final EventPlayerApplyCollision l_Event = new EventPlayerApplyCollision(p_Entity);
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "isPushedByWater()Z" }, at = { @At("HEAD") }, cancellable = true)
    public void isPushedByWater(final CallbackInfoReturnable<Boolean> ci) {
        final EventPlayerPushedByWater l_Event = new EventPlayerPushedByWater();
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            ci.setReturnValue((Object)false);
        }
    }
}
