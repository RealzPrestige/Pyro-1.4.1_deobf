// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.events.world.EventChunkLoad;
import dev.nuker.pyro.deobfuscated.PyroMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Chunk.class })
public class MixinChunk
{
    @Inject(method = { "onUnload" }, at = { @At("RETURN") })
    public void onUnload(final CallbackInfo info) {
        PyroMod.EVENT_BUS.post(new EventChunkLoad(EventChunkLoad.Type.UNLOAD, (Chunk)this));
    }
}
