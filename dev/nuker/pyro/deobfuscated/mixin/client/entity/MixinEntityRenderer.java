//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client.entity;

import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraft.world.World;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderHurtCameraEffect;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderHand;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.ArrayList;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderGetEntitiesINAABBexcluding;
import java.util.List;
import com.google.common.base.Predicate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderSetupFog;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityRenderer.class })
public abstract class MixinEntityRenderer
{
    @Shadow
    @Final
    public Minecraft mc;
    @Shadow
    public float thirdPersonDistancePrev;
    @Shadow
    public boolean cloudFog;
    
    @Inject(method = { "setupFog" }, at = { @At("HEAD") }, cancellable = true)
    public void setupFog(final int startCoords, final float partialTicks, final CallbackInfo p_Info) {
        final EventRenderSetupFog l_Event = new EventRenderSetupFog(startCoords, partialTicks);
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(final WorldClient worldClient, final Entity entityIn, final AxisAlignedBB boundingBox, final Predicate predicate) {
        final EventRenderGetEntitiesINAABBexcluding l_Event = new EventRenderGetEntitiesINAABBexcluding(worldClient, entityIn, boundingBox, predicate);
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            return new ArrayList<Entity>();
        }
        return (List<Entity>)worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
    }
    
    @Inject(method = { "renderHand" }, at = { @At("HEAD") }, cancellable = true)
    private void renderHand(final float partialTicks, final int pass, final CallbackInfo p_Info) {
        final EventRenderHand l_Event = new EventRenderHand(partialTicks, pass);
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCameraEffect(final float ticks, final CallbackInfo info) {
        final EventRenderHurtCameraEffect l_Event = new EventRenderHurtCameraEffect(ticks);
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = At.Shift.AFTER) })
    private void renderWorldPassPost(final int pass, final float partialTicks, final long finishTimeNano, final CallbackInfo callbackInfo) {
        RenderUtil.updateModelViewProjectionMatrix();
    }
    
    @Overwrite
    public void orientCamera(final float partialTicks) {
        final Entity entity = this.mc.getRenderViewEntity();
        float f = entity.getEyeHeight();
        double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        double d2 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
        double d3 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping()) {
            ++f;
            GlStateManager.translate(0.0f, 0.3f, 0.0f);
            if (!this.mc.gameSettings.debugCamEnable) {
                final BlockPos blockpos = new BlockPos(entity);
                final IBlockState iblockstate = this.mc.world.getBlockState(blockpos);
                ForgeHooksClient.orientBedCamera((IBlockAccess)this.mc.world, blockpos, iblockstate, entity);
                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0f, 0.0f, -1.0f, 0.0f);
                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0f, 0.0f, 0.0f);
            }
        }
        else if (this.mc.gameSettings.thirdPersonView > 0) {
            double d4 = (double)((PyroStatic.VIEWCLIP != null && PyroStatic.VIEWCLIP.isEnabled()) ? PyroStatic.VIEWCLIP.Distance.getValue() : ((double)(this.thirdPersonDistancePrev + (4.0f - this.thirdPersonDistancePrev) * partialTicks)));
            if (this.mc.gameSettings.debugCamEnable) {
                GlStateManager.translate(0.0f, 0.0f, (float)(-d4));
            }
            else {
                final float f2 = entity.rotationYaw;
                float f3 = entity.rotationPitch;
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    f3 += 180.0f;
                }
                final double d5 = -MathHelper.sin(f2 * 0.017453292f) * MathHelper.cos(f3 * 0.017453292f) * d4;
                final double d6 = MathHelper.cos(f2 * 0.017453292f) * MathHelper.cos(f3 * 0.017453292f) * d4;
                final double d7 = -MathHelper.sin(f3 * 0.017453292f) * d4;
                for (int i = 0; i < 8; ++i) {
                    float f4 = (float)((i & 0x1) * 2 - 1);
                    float f5 = (float)((i >> 1 & 0x1) * 2 - 1);
                    float f6 = (float)((i >> 2 & 0x1) * 2 - 1);
                    f4 *= 0.1f;
                    f5 *= 0.1f;
                    f6 *= 0.1f;
                    if (PyroStatic.VIEWCLIP == null || !PyroStatic.VIEWCLIP.isEnabled()) {
                        final RayTraceResult raytraceresult = this.mc.world.rayTraceBlocks(new Vec3d(d0 + f4, d2 + f5, d3 + f6), new Vec3d(d0 - d5 + f4 + f6, d2 - d7 + f5, d3 - d6 + f6));
                        if (raytraceresult != null) {
                            final double d8 = raytraceresult.hitVec.distanceTo(new Vec3d(d0, d2, d3));
                            if (d8 < d4) {
                                d4 = d8;
                            }
                        }
                    }
                }
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.rotate(entity.rotationPitch - f3, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(entity.rotationYaw - f2, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, (float)(-d4));
                GlStateManager.rotate(f2 - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(f3 - entity.rotationPitch, 1.0f, 0.0f, 0.0f);
            }
        }
        else {
            GlStateManager.translate(0.0f, 0.0f, 0.05f);
        }
        if (!this.mc.gameSettings.debugCamEnable) {
            float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0f;
            final float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            final float roll = 0.0f;
            if (entity instanceof EntityAnimal) {
                final EntityAnimal entityanimal = (EntityAnimal)entity;
                yaw = entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0f;
            }
            final IBlockState state = ActiveRenderInfo.getBlockStateAtEntityViewpoint((World)this.mc.world, entity, partialTicks);
            final EntityViewRenderEvent.CameraSetup event = new EntityViewRenderEvent.CameraSetup((EntityRenderer)this, entity, state, (double)partialTicks, yaw, pitch, roll);
            MinecraftForge.EVENT_BUS.post((Event)event);
            GlStateManager.rotate(event.getRoll(), 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(event.getPitch(), 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(event.getYaw(), 0.0f, 1.0f, 0.0f);
        }
        GlStateManager.translate(0.0f, -f, 0.0f);
        d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        d2 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
        d3 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
        this.cloudFog = this.mc.renderGlobal.hasCloudFog(d0, d2, d3, partialTicks);
    }
}
