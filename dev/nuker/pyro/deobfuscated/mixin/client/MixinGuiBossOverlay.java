// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderBossHealth;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiBossOverlay;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiBossOverlay.class })
public class MixinGuiBossOverlay
{
    @Inject(method = { "renderBossHealth" }, at = { @At("HEAD") }, cancellable = true)
    public void renderBossHealth(final CallbackInfo p_Info) {
        final EventRenderBossHealth l_Event = new EventRenderBossHealth();
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
}
