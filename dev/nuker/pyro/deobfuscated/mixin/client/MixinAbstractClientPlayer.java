//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerGetLocationCape;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import dev.nuker.pyro.deobfuscated.mixin.client.entity.MixinEntityPlayer;

@Mixin({ AbstractClientPlayer.class })
public abstract class MixinAbstractClientPlayer extends MixinEntityPlayer
{
    @Shadow
    protected abstract boolean isSpectator();
    
    @Inject(method = { "getLocationCape" }, at = { @At("RETURN") }, cancellable = true)
    public void getCape(final CallbackInfoReturnable<ResourceLocation> callbackInfo) {
        final EventPlayerGetLocationCape l_Event = new EventPlayerGetLocationCape((AbstractClientPlayer)this);
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            callbackInfo.setReturnValue((Object)l_Event.GetResourceLocation());
        }
    }
}
