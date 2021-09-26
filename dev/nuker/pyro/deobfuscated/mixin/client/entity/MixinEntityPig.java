// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client.entity;

import dev.nuker.pyro.deobfuscated.events.entity.EventHorseSaddled;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.entity.EventSteerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.passive.EntityPig;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityPig.class })
public class MixinEntityPig
{
    @Inject(method = { "canBeSteered" }, at = { @At("HEAD") }, cancellable = true)
    public void canBeSteered(final CallbackInfoReturnable<Boolean> cir) {
        final EventSteerEntity event = new EventSteerEntity();
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            cir.cancel();
            cir.setReturnValue((Object)true);
        }
    }
    
    @Inject(method = { "getSaddled" }, at = { @At("HEAD") }, cancellable = true)
    public void getSaddled(final CallbackInfoReturnable<Boolean> cir) {
        final EventHorseSaddled event = new EventHorseSaddled();
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            cir.cancel();
            cir.setReturnValue((Object)true);
        }
    }
}
