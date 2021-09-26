// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client.blocks;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.blocks.EventBlockGetRenderLayer;
import net.minecraft.block.Block;
import net.minecraft.util.BlockRenderLayer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.block.BlockBed;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ BlockBed.class })
public class MixinBlockBed
{
    @Inject(method = { "getRenderLayer" }, at = { @At("HEAD") }, cancellable = true)
    public void getRenderLayer(final CallbackInfoReturnable<BlockRenderLayer> callback) {
        final EventBlockGetRenderLayer event = new EventBlockGetRenderLayer((Block)this);
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callback.cancel();
            callback.setReturnValue((Object)event.getBlockRenderLayer());
        }
    }
}
