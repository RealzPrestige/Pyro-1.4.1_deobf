//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.nio.ByteOrder;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderPutColorMultiplier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.nio.IntBuffer;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.renderer.BufferBuilder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ BufferBuilder.class })
public abstract class MixinBufferBuilder
{
    @Shadow
    private boolean noColor;
    @Shadow
    private IntBuffer rawIntBuffer;
    
    @Shadow
    public abstract int getColorIndex(final int p0);
    
    @Inject(method = { "putColorMultiplier" }, at = { @At("HEAD") }, cancellable = true)
    public void putColorMultiplier(final float red, final float green, final float blue, final int vertexIndex, final CallbackInfo info) {
        final EventRenderPutColorMultiplier event = new EventRenderPutColorMultiplier();
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
            final int i = this.getColorIndex(vertexIndex);
            int j = -1;
            final float newAlpha = event.getOpacity();
            if (!this.noColor) {
                j = this.rawIntBuffer.get(i);
                if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                    final int k = (int)((j & 0xFF) * red);
                    final int l = (int)((j >> 8 & 0xFF) * green);
                    final int i2 = (int)((j >> 16 & 0xFF) * blue);
                    final int alpha = (int)((j >> 24 & 0xFF) * newAlpha);
                    j = (alpha << 24 | i2 << 16 | l << 8 | k);
                }
                else {
                    final int j2 = (int)((j >> 24 & 0xFF) * red);
                    final int k2 = (int)((j >> 16 & 0xFF) * green);
                    final int l2 = (int)((j >> 8 & 0xFF) * blue);
                    final int alpha = (int)((j & 0xFF) * newAlpha);
                    j = (j2 << 24 | k2 << 16 | l2 << 8 | alpha);
                }
            }
            this.rawIntBuffer.put(i, j);
        }
    }
}
