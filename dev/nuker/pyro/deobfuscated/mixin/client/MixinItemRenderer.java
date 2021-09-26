// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderUpdateEquippedItem;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ItemRenderer.class })
public class MixinItemRenderer
{
    @Inject(method = { "updateEquippedItem" }, at = { @At("HEAD") }, cancellable = true)
    public void updateEquippedItem(final CallbackInfo p_Info) {
        final EventRenderUpdateEquippedItem l_Event = new EventRenderUpdateEquippedItem();
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
}
