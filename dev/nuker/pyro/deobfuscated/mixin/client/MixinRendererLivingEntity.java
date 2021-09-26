//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.EntityLivingBase;

@Mixin({ RenderLivingBase.class })
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends MixinRenderer<T>
{
    @Shadow
    protected ModelBase mainModel;
    
    @Inject(method = { "renderModel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V") })
    private void renderModelWrapper(final EntityLivingBase entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final CallbackInfo ci) {
        if (PyroStatic.ENTITYESP != null && PyroStatic.ENTITYESP.isEnabled()) {
            PyroStatic.ENTITYESP.doRenderOutlines(this.mainModel, (Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        }
    }
}
