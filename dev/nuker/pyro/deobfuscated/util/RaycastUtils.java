//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;
import com.google.common.base.Predicates;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.Entity;

public final class RaycastUtils extends MinecraftInstance
{
    public static Entity raycastEntity(final double range, final IEntityFilter entityFilter) {
        return raycastEntity(range, RotationUtils.getThis().serverRotation.getYaw(), RotationUtils.getThis().serverRotation.getPitch(), entityFilter);
    }
    
    private static Entity raycastEntity(final double range, final float yaw, final float pitch, final IEntityFilter entityFilter) {
        final Entity renderViewEntity = RaycastUtils.mc.getRenderViewEntity();
        if (renderViewEntity != null && RaycastUtils.mc.world != null) {
            double blockReachDistance = range;
            final Vec3d eyePosition = renderViewEntity.getPositionEyes(1.0f);
            final float yawCos = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
            final float yawSin = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
            final float pitchCos = -MathHelper.cos(-pitch * 0.017453292f);
            final float pitchSin = MathHelper.sin(-pitch * 0.017453292f);
            final Vec3d entityLook = new Vec3d((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
            final Vec3d vector = eyePosition.add(entityLook.x * blockReachDistance, entityLook.y * blockReachDistance, entityLook.z * blockReachDistance);
            final List<Entity> entityList = (List<Entity>)RaycastUtils.mc.world.getEntitiesInAABBexcluding(renderViewEntity, renderViewEntity.getEntityBoundingBox().expand(entityLook.x * blockReachDistance, entityLook.y * blockReachDistance, entityLook.z * blockReachDistance).expand(1.0, 1.0, 1.0), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
            Entity pointedEntity = null;
            for (final Entity entity : entityList) {
                if (!entityFilter.canRaycast(entity)) {
                    continue;
                }
                final float collisionBorderSize = entity.getCollisionBorderSize();
                final AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox().expand((double)collisionBorderSize, (double)collisionBorderSize, (double)collisionBorderSize);
                final RayTraceResult movingObjectPosition = axisAlignedBB.calculateIntercept(eyePosition, vector);
                if (axisAlignedBB.contains(eyePosition)) {
                    if (blockReachDistance < 0.0) {
                        continue;
                    }
                    pointedEntity = entity;
                    blockReachDistance = 0.0;
                }
                else {
                    if (movingObjectPosition == null) {
                        continue;
                    }
                    final double eyeDistance = eyePosition.distanceTo(movingObjectPosition.hitVec);
                    if (eyeDistance >= blockReachDistance && blockReachDistance != 0.0) {
                        continue;
                    }
                    if (entity == renderViewEntity.getRidingEntity() && !renderViewEntity.canRiderInteract()) {
                        if (blockReachDistance != 0.0) {
                            continue;
                        }
                        pointedEntity = entity;
                    }
                    else {
                        pointedEntity = entity;
                        blockReachDistance = eyeDistance;
                    }
                }
            }
            return pointedEntity;
        }
        return null;
    }
    
    public interface IEntityFilter
    {
        boolean canRaycast(final Entity p0);
    }
}
