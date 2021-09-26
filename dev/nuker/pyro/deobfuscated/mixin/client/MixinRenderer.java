//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import org.spongepowered.asm.mixin.Final;
import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.renderer.entity.Render;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.Entity;

@Mixin({ Render.class })
abstract class MixinRenderer<T extends Entity>
{
    @Shadow
    protected boolean renderOutlines;
    @Shadow
    @Final
    protected RenderManager renderManager;
    
    @Shadow
    protected abstract boolean bindEntityTexture(final T p0);
    
    @Shadow
    protected abstract int getTeamColor(final T p0);
}
