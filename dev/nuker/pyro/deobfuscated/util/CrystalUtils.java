//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import net.minecraft.world.World;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import net.minecraft.util.NonNullList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import net.minecraft.util.math.BlockPos;

public class CrystalUtils
{
    public static boolean CanPlaceCrystalIfObbyWasAtPos(final BlockPos pos) {
        final Minecraft mc = Wrapper.GetMC();
        final Block floor = mc.world.getBlockState(pos.add(0, 1, 0)).getBlock();
        final Block ceil = mc.world.getBlockState(pos.add(0, 2, 0)).getBlock();
        return floor == Blocks.AIR && ceil == Blocks.AIR && mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.add(0, 1, 0))).isEmpty();
    }
    
    public static boolean canPlaceCrystal(final BlockPos pos) {
        final Minecraft mc = Wrapper.GetMC();
        final Block block = mc.world.getBlockState(pos).getBlock();
        if (block == Blocks.OBSIDIAN || block == Blocks.BEDROCK) {
            final Block floor = mc.world.getBlockState(pos.add(0, 1, 0)).getBlock();
            final Block ceil = mc.world.getBlockState(pos.add(0, 2, 0)).getBlock();
            if (floor == Blocks.AIR && ceil == Blocks.AIR && mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.add(0, 1, 0))).isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    public static BlockPos GetPlayerPosFloored(final EntityPlayer p_Player) {
        return new BlockPos(Math.floor(p_Player.posX), Math.floor(p_Player.posY), Math.floor(p_Player.posZ));
    }
    
    public static List<BlockPos> findCrystalBlocks(final EntityPlayer p_Player, final float p_Range) {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.create();
        positions.addAll((Collection)BlockInteractionHelper.getSphere(GetPlayerPosFloored(p_Player), p_Range, (int)p_Range, false, true, 0).stream().filter((Predicate<? super Object>)CrystalUtils::canPlaceCrystal).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    public static float calculateDamage(final World p_World, final double posX, final double posY, final double posZ, final Entity entity, final int p_InterlopedAmount) {
        if (entity == Wrapper.GetPlayer() && Wrapper.GetPlayer().capabilities.isCreativeMode) {
            return 0.0f;
        }
        final float doubleExplosionSize = 12.0f;
        double l_Distance = entity.getDistance(posX, posY, posZ);
        if (l_Distance > doubleExplosionSize) {
            return 0.0f;
        }
        if (p_InterlopedAmount > 0) {
            final Vec3d l_Interloped = EntityUtil.getInterpolatedAmount(entity, p_InterlopedAmount);
            l_Distance = EntityUtil.GetDistance(l_Interloped.x, l_Interloped.y, l_Interloped.z, posX, posY, posZ);
        }
        final double distancedsize = l_Distance / doubleExplosionSize;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(p_World, damage), new Explosion(p_World, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    public static float getBlastReduction(final EntityLivingBase entity, float damage, final Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            final int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage -= damage / 4.0f;
            }
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }
    
    private static float getDamageMultiplied(final World p_World, final float damage) {
        final int diff = p_World.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public static float calculateDamage(final World p_World, final EntityEnderCrystal crystal, final Entity entity) {
        return calculateDamage(p_World, crystal.posX, crystal.posY, crystal.posZ, entity, 0);
    }
}
