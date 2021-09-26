// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import dev.nuker.pyro.deobfuscated.events.entity.EventHorseSaddled;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.entity.EventSteerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.passive.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ AbstractHorse.class })
public class MixinAbstractHorse
{
    @Inject(method = { "canBeSteered" }, at = { @At("HEAD") }, cancellable = true)
    public void canBeSteered(final CallbackInfoReturnable<Boolean> cir) {
        final EventSteerEntity l_Event = new EventSteerEntity();
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            cir.cancel();
            cir.setReturnValue((Object)true);
        }
    }
    
    @Inject(method = { "isHorseSaddled" }, at = { @At("HEAD") }, cancellable = true)
    public void isHorseSaddled(final CallbackInfoReturnable<Boolean> cir) {
        final EventHorseSaddled l_Event = new EventHorseSaddled();
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            cir.cancel();
            cir.setReturnValue((Object)true);
        }
    }
}
