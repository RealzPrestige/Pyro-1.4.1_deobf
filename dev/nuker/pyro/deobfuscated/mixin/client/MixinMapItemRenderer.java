// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderMap;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.storage.MapData;
import net.minecraft.client.gui.MapItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ MapItemRenderer.class })
public class MixinMapItemRenderer
{
    @Inject(method = { "renderMap" }, at = { @At("HEAD") }, cancellable = true)
    public void render(final MapData mapdataIn, final boolean noOverlayRendering, final CallbackInfo p_Callback) {
        final EventRenderMap l_Event = new EventRenderMap();
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Callback.cancel();
        }
    }
}
