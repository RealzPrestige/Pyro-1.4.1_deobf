//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client.entity;

import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.entity.EventEntityCollisionBorderSize;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.util.CameraUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.MoverType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Entity.class })
public abstract class MixinEntity
{
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    public double prevPosX;
    @Shadow
    public double prevPosY;
    @Shadow
    public double prevPosZ;
    @Shadow
    public double lastTickPosX;
    @Shadow
    public double lastTickPosY;
    @Shadow
    public double lastTickPosZ;
    @Shadow
    public float prevRotationYaw;
    @Shadow
    public float prevRotationPitch;
    @Shadow
    public float rotationPitch;
    @Shadow
    public float rotationYaw;
    @Shadow
    public boolean onGround;
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    @Shadow
    public World world;
    
    @Shadow
    @Override
    public abstract boolean equals(final Object p0);
    
    @Shadow
    public abstract boolean isSprinting();
    
    @Shadow
    public abstract boolean isRiding();
    
    @Shadow
    public void move(final MoverType type, final double x, final double y, final double z) {
    }
    
    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();
    
    @Shadow
    public abstract boolean getFlag(final int p0);
    
    @Shadow
    public abstract Entity getLowestRidingEntity();
    
    @Inject(method = { "turn" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationPitch:F", ordinal = 0) })
    private void overrideYaw(final float yawChange, final float pitchChange, final CallbackInfo ci) {
        if (this instanceof EntityPlayerSP && CameraUtils.shouldPreventPlayerMovement()) {
            this.rotationYaw = this.prevRotationYaw;
            this.rotationPitch = this.prevRotationPitch;
            CameraUtils.updateCameraRotations(yawChange, pitchChange);
        }
    }
    
    @Inject(method = { "getCollisionBorderSize" }, at = { @At("HEAD") }, cancellable = true)
    public void getCollisionBorderSize(final CallbackInfoReturnable<Float> callback) {
        final EventEntityCollisionBorderSize event = new EventEntityCollisionBorderSize();
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callback.cancel();
            callback.setReturnValue((Object)event.getSize());
        }
    }
}
