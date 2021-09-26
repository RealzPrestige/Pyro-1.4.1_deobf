//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import java.math.RoundingMode;
import java.math.BigDecimal;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;

public final class MathUtil
{
    private static Minecraft mc;
    
    public static double calculateDistanceWithPartialTicks(final double n, final double n2, final float renderPartialTicks) {
        return n2 + (n - n2) * MathUtil.mc.getRenderPartialTicks();
    }
    
    public static Vec3d interpolateEntityClose(final Entity entity, final float renderPartialTicks) {
        return new Vec3d(calculateDistanceWithPartialTicks(entity.posX, entity.lastTickPosX, renderPartialTicks) - MathUtil.mc.getRenderManager().renderPosX, calculateDistanceWithPartialTicks(entity.posY, entity.lastTickPosY, renderPartialTicks) - MathUtil.mc.getRenderManager().renderPosY, calculateDistanceWithPartialTicks(entity.posZ, entity.lastTickPosZ, renderPartialTicks) - MathUtil.mc.getRenderManager().renderPosZ);
    }
    
    public static Vec3d interpolateVec3dPos(final Vec3d pos, final float renderPartialTicks) {
        return new Vec3d(calculateDistanceWithPartialTicks(pos.x, pos.x, renderPartialTicks) - MathUtil.mc.getRenderManager().renderPosX, calculateDistanceWithPartialTicks(pos.y, pos.y - 0.021, renderPartialTicks) - MathUtil.mc.getRenderManager().renderPosY, calculateDistanceWithPartialTicks(pos.z, pos.z, renderPartialTicks) - MathUtil.mc.getRenderManager().renderPosZ);
    }
    
    public static Vec3d interpolateEntity(final Entity entity, final float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
    }
    
    public static double radToDeg(final double rad) {
        return rad * 57.295780181884766;
    }
    
    public static double degToRad(final double deg) {
        return deg * 0.01745329238474369;
    }
    
    public static Vec3d direction(final float yaw) {
        return new Vec3d(Math.cos(degToRad(yaw + 90.0f)), 0.0, Math.sin(degToRad(yaw + 90.0f)));
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static double[] directionSpeed(final double speed) {
        final Minecraft mc = Minecraft.getMinecraft();
        float forward = mc.player.movementInput.moveForward;
        float side = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public static double[] directionSpeedNoForward(final double speed) {
        final Minecraft mc = Minecraft.getMinecraft();
        float forward = 1.0f;
        float side = 0.0f;
        float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public static Vec3d mult(final Vec3d factor, final Vec3d multiplier) {
        return new Vec3d(factor.x * multiplier.x, factor.y * multiplier.y, factor.z * multiplier.z);
    }
    
    public static Vec3d mult(final Vec3d factor, final float multiplier) {
        return new Vec3d(factor.x * multiplier, factor.y * multiplier, factor.z * multiplier);
    }
    
    public static Vec3d div(final Vec3d factor, final Vec3d divisor) {
        return new Vec3d(factor.x / divisor.x, factor.y / divisor.y, factor.z / divisor.z);
    }
    
    public static Vec3d div(final Vec3d factor, final float divisor) {
        return new Vec3d(factor.x / divisor, factor.y / divisor, factor.z / divisor);
    }
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            return value;
        }
        return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static float clamp(float val, final float min, final float max) {
        if (val <= min) {
            val = min;
        }
        if (val >= max) {
            val = max;
        }
        return val;
    }
    
    public static float wrap(float val) {
        val %= 360.0f;
        if (val >= 180.0f) {
            val -= 360.0f;
        }
        if (val < -180.0f) {
            val += 360.0f;
        }
        return val;
    }
    
    public static double map(double value, final double a, final double b, final double c, final double d) {
        value = (value - a) / (b - a);
        return c + value * (d - c);
    }
    
    public static double linear(final double from, final double to, final double incline) {
        return (from < to - incline) ? (from + incline) : ((from > to + incline) ? (from - incline) : to);
    }
    
    public static double parabolic(final double from, final double to, final double incline) {
        return from + (to - from) / incline;
    }
    
    public static double getDistance(final Vec3d pos, final double x, final double y, final double z) {
        final double deltaX = pos.x - x;
        final double deltaY = pos.y - y;
        final double deltaZ = pos.z - z;
        return MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }
    
    public static double[] calcIntersection(final double[] line, final double[] line2) {
        final double a1 = line[3] - line[1];
        final double b1 = line[0] - line[2];
        final double c1 = a1 * line[0] + b1 * line[1];
        final double a2 = line2[3] - line2[1];
        final double b2 = line2[0] - line2[2];
        final double c2 = a2 * line2[0] + b2 * line2[1];
        final double delta = a1 * b2 - a2 * b1;
        return new double[] { (b2 * c1 - b1 * c2) / delta, (a1 * c2 - a2 * c1) / delta };
    }
    
    public static double calculateAngle(final double x1, final double y1, final double x2, final double y2) {
        double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
        angle += Math.ceil(-angle / 360.0) * 360.0;
        return angle;
    }
    
    static {
        MathUtil.mc = Minecraft.getMinecraft();
    }
}
