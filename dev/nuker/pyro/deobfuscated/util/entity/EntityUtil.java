//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util.entity;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import org.apache.commons.io.IOUtils;
import java.net.URL;
import java.util.UUID;
import net.minecraft.util.math.Vec3i;
import javax.annotation.Nullable;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityBoat;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.Entity;

public class EntityUtil
{
    public static boolean isPassive(final Entity e) {
        return (!(e instanceof EntityWolf) || !((EntityWolf)e).isAngry()) && (e instanceof EntityAnimal || e instanceof EntityAgeable || e instanceof EntityTameable || e instanceof EntityAmbientCreature || e instanceof EntitySquid || (e instanceof EntityIronGolem && ((EntityIronGolem)e).getRevengeTarget() == null));
    }
    
    public static boolean isLiving(final Entity e) {
        return e instanceof EntityLivingBase;
    }
    
    public static boolean isFakeLocalPlayer(final Entity entity) {
        return entity != null && entity.getEntityId() == -100 && Minecraft.getMinecraft().player != entity;
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, 0.0 * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final Vec3d vec) {
        return getInterpolatedAmount(entity, vec.x, vec.y, vec.z);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    public static boolean isMobAggressive(final Entity entity) {
        if (entity instanceof EntityPigZombie) {
            if (((EntityPigZombie)entity).isArmsRaised() || ((EntityPigZombie)entity).isAngry()) {
                return true;
            }
        }
        else {
            if (entity instanceof EntityWolf) {
                return ((EntityWolf)entity).isAngry() && !Minecraft.getMinecraft().player.equals((Object)((EntityWolf)entity).getOwner());
            }
            if (entity instanceof EntityEnderman) {
                return ((EntityEnderman)entity).isScreaming();
            }
        }
        return isHostileMob(entity);
    }
    
    public static boolean isNeutralMob(final Entity entity) {
        return entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman;
    }
    
    public static boolean isFriendlyMob(final Entity entity) {
        return (entity.isCreatureType(EnumCreatureType.CREATURE, false) && !isNeutralMob(entity)) || entity.isCreatureType(EnumCreatureType.AMBIENT, false) || entity instanceof EntityVillager || entity instanceof EntityIronGolem || (isNeutralMob(entity) && !isMobAggressive(entity));
    }
    
    public static boolean isHostileMob(final Entity entity) {
        return (entity.isCreatureType(EnumCreatureType.MONSTER, false) && !isNeutralMob(entity)) || entity instanceof EntitySpider;
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }
    
    public static Vec3d getInterpolatedRenderPos(final Entity entity, final float ticks) {
        return getInterpolatedPos(entity, ticks).subtract(Minecraft.getMinecraft().getRenderManager().renderPosX, Minecraft.getMinecraft().getRenderManager().renderPosY, Minecraft.getMinecraft().getRenderManager().renderPosZ);
    }
    
    public static boolean isInWater(final Entity entity) {
        if (entity == null) {
            return false;
        }
        final double y = entity.posY + 0.01;
        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); ++x) {
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, (int)y, z);
                if (Minecraft.getMinecraft().world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isDrivenByPlayer(final Entity entityIn) {
        return Minecraft.getMinecraft().player != null && entityIn != null && entityIn.equals((Object)Minecraft.getMinecraft().player.getRidingEntity());
    }
    
    public static boolean isAboveWater(final Entity entity) {
        return isAboveWater(entity, false);
    }
    
    public static boolean isAboveWater(final Entity entity, final boolean packet) {
        if (entity == null) {
            return false;
        }
        final double y = entity.posY - (packet ? 0.03 : (isPlayer(entity) ? 0.2 : 0.5));
        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); ++x) {
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);
                if (Minecraft.getMinecraft().world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static double[] calculateLookAt(final double px, final double py, final double pz, final EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        return new double[] { yaw, pitch };
    }
    
    public static boolean isPlayer(final Entity entity) {
        return entity instanceof EntityPlayer;
    }
    
    public static double getRelativeX(final float yaw) {
        return MathHelper.sin(-yaw * 0.017453292f);
    }
    
    public static double getRelativeZ(final float yaw) {
        return MathHelper.cos(yaw * 0.017453292f);
    }
    
    public static int GetPlayerMS(final EntityPlayer p_Player) {
        if (p_Player.getUniqueID() == null) {
            return 0;
        }
        final NetworkPlayerInfo playerInfo = Minecraft.getMinecraft().getConnection().getPlayerInfo(p_Player.getUniqueID());
        if (playerInfo == null) {
            return 0;
        }
        return playerInfo.getResponseTime();
    }
    
    public static Vec3d CalculateExpectedPosition(final EntityPlayer p_Player) {
        final Vec3d l_Position = new Vec3d(p_Player.posX, p_Player.posY, p_Player.posZ);
        if (p_Player.lastTickPosX != p_Player.posX && p_Player.lastTickPosY != p_Player.posY && p_Player.lastTickPosZ != p_Player.posZ) {
            return l_Position;
        }
        final int l_PlayerMS = GetPlayerMS(p_Player);
        final double deltaX = p_Player.posX - p_Player.prevPosX;
        final double deltaZ = p_Player.posZ - p_Player.prevPosZ;
        final float tickRate = Minecraft.getMinecraft().timer.tickLength / 1000.0f;
        final float l_Distance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        final double l_Facing = MathUtil.calculateAngle(p_Player.posX, p_Player.posZ, p_Player.lastTickPosX, p_Player.lastTickPosZ) / 45.0;
        return new Vec3d(p_Player.posX + Math.cos(l_Facing) * l_Distance, p_Player.posY, p_Player.posZ + Math.sin(l_Facing) * l_Distance);
    }
    
    public static double GetDistance(final double p_X, final double p_Y, final double p_Z, final double x, final double y, final double z) {
        final double d0 = p_X - x;
        final double d2 = p_Y - y;
        final double d3 = p_Z - z;
        return MathHelper.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
    }
    
    public static double GetDistanceOfEntityToBlock(final Entity p_Entity, final BlockPos p_Pos) {
        return GetDistance(p_Entity.posX, p_Entity.posY, p_Entity.posZ, p_Pos.getX(), p_Pos.getY(), p_Pos.getZ());
    }
    
    public static boolean IsVehicle(final Entity entity) {
        return entity instanceof EntityBoat || entity instanceof EntityMinecart;
    }
    
    public static BlockPos GetPositionVectorBlockPos(final Entity entity, @Nullable final BlockPos toAdd) {
        final Vec3d v = entity.getPositionVector();
        if (toAdd == null) {
            return new BlockPos(v.x, v.y, v.z);
        }
        return new BlockPos(v.x, v.y, v.z).add((Vec3i)toAdd);
    }
    
    public static String getNameFromUUID(final UUID id) {
        final String url = "https://api.mojang.com/user/profiles/" + id.toString().replace("-", "") + "/names";
        try {
            final String nameJson = IOUtils.toString(new URL(url));
            if (nameJson != null && nameJson.length() > 0) {
                final JSONArray jsonArray = (JSONArray)JSONValue.parseWithException(nameJson);
                if (jsonArray != null) {
                    final JSONObject latestName = jsonArray.get(jsonArray.size() - 1);
                    if (latestName != null) {
                        return latestName.get("name").toString();
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "unk";
    }
}
