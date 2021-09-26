//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import dev.nuker.pyro.deobfuscated.managers.RotationManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.injection.Redirect;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import dev.nuker.pyro.deobfuscated.util.CameraUtils;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderEntityName;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderPlayer.class })
public class MixinRenderPlayer
{
    private float renderPitch;
    private float renderYaw;
    private float renderHeadYaw;
    private float prevRenderHeadYaw;
    private float lastRenderHeadYaw;
    private float prevRenderPitch;
    private float lastRenderPitch;
    
    public MixinRenderPlayer() {
        this.lastRenderHeadYaw = 0.0f;
        this.lastRenderPitch = 0.0f;
    }
    
    @Inject(method = { "renderEntityName" }, at = { @At("HEAD") }, cancellable = true)
    public void renderLivingLabel(final AbstractClientPlayer entityIn, final double x, final double y, final double z, final String name, final double distanceSq, final CallbackInfo info) {
        final EventRenderEntityName l_Event = new EventRenderEntityName(entityIn, x, y, z, name, distanceSq);
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "doRender" }, require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;isUser()Z"))
    private boolean overrideIsUser(final AbstractClientPlayer entity) {
        return (!CameraUtils.freecamEnabled() || entity != Wrapper.GetPlayer()) && entity.isUser();
    }
    
    @Inject(method = { "doRender" }, at = { @At("HEAD") })
    private void rotateBegin(final AbstractClientPlayer entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo ci) {
        if (entity == Minecraft.getMinecraft().player) {
            this.prevRenderHeadYaw = entity.prevRotationYawHead;
            this.prevRenderPitch = entity.prevRotationPitch;
            this.renderPitch = entity.rotationPitch;
            this.renderYaw = entity.rotationYaw;
            this.renderHeadYaw = entity.rotationYawHead;
            entity.rotationPitch = RotationManager.Get().getPitchForMixin(entity.rotationPitch);
            entity.prevRotationPitch = this.lastRenderPitch;
            entity.rotationYaw = RotationManager.Get().getYawForMixin(entity.rotationYaw);
            entity.rotationYawHead = RotationManager.Get().getYawForMixin(entity.rotationYawHead);
            entity.prevRotationYawHead = this.lastRenderHeadYaw;
        }
    }
    
    @Inject(method = { "doRender" }, at = { @At("RETURN") })
    private void rotateEnd(final AbstractClientPlayer entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo ci) {
        if (entity == Minecraft.getMinecraft().player) {
            this.lastRenderHeadYaw = entity.rotationYawHead;
            this.lastRenderPitch = entity.rotationPitch;
            entity.rotationPitch = this.renderPitch;
            entity.rotationYaw = this.renderYaw;
            entity.rotationYawHead = this.renderHeadYaw;
            entity.prevRotationYawHead = this.prevRenderHeadYaw;
            entity.prevRotationPitch = this.prevRenderPitch;
        }
    }
}
