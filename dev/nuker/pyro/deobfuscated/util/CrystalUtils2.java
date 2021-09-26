//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import java.util.concurrent.CopyOnWriteArrayList;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import net.minecraft.entity.item.EntityEnderCrystal;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import net.minecraft.util.math.MathHelper;
import dev.nuker.pyro.deobfuscated.module.combat.AutoCrystal;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import net.minecraft.client.Minecraft;

public class CrystalUtils2
{
    private static Minecraft mc;
    public static List<BlockPos> obbyRock;
    public static List<BlockPos> crystalBlocks;
    
    public static boolean canPlaceCrystalAt(final BlockPos blockpos, final IBlockState state) {
        final World worldIn = (World)CrystalUtils2.mc.world;
        final BlockPos blockpos2 = blockpos.up();
        final BlockPos blockpos3 = blockpos.up().up();
        boolean flag = !worldIn.isAirBlock(blockpos2) && !worldIn.getBlockState(blockpos).getBlock().isReplaceable((IBlockAccess)worldIn, blockpos2);
        flag |= (!worldIn.isAirBlock(blockpos3) && !worldIn.getBlockState(blockpos3).getBlock().isReplaceable((IBlockAccess)worldIn, blockpos3));
        if (flag) {
            return false;
        }
        final double d0 = blockpos.getX();
        final double d2 = blockpos.getY();
        final double d3 = blockpos.getZ();
        return worldIn.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(d0, d2, d3, d0 + 1.0, d2 + 2.0, d3 + 1.0)).isEmpty();
    }
    
    public static void onSetBlockState(final BlockPos pos, final IBlockState newState, final int flags) {
        if (newState.getBlock() == Blocks.OBSIDIAN || newState.getBlock() == Blocks.BEDROCK) {
            if (!CrystalUtils2.obbyRock.contains(pos)) {
                CrystalUtils2.obbyRock.add(pos);
            }
        }
        else {
            CrystalUtils2.obbyRock.remove(pos);
            if (CrystalUtils2.crystalBlocks.contains(pos)) {
                CrystalUtils2.crystalBlocks.remove(pos);
            }
        }
    }
    
    public static void updateList(final BlockPos pos, final IBlockState newState) {
        if (newState.getBlock() == Blocks.OBSIDIAN || newState.getBlock() == Blocks.BEDROCK) {
            if (canPlaceCrystalAt(pos, newState)) {
                if (!CrystalUtils2.crystalBlocks.contains(pos)) {
                    CrystalUtils2.crystalBlocks.add(pos);
                    Pyro.SendMessage("Added a block at " + pos.toString());
                }
            }
            else {
                Pyro.SendMessage("removed a block " + pos.toString());
                CrystalUtils2.crystalBlocks.remove(pos);
            }
        }
        else {
            CrystalUtils2.crystalBlocks.remove(pos);
        }
    }
    
    public static void loadWorld() {
        CrystalUtils2.crystalBlocks.clear();
    }
    
    public static void markBlockRangeForRenderUpdate(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        final long ms = System.currentTimeMillis();
        final int minX = (x1 < x2) ? x1 : x2;
        final int minY = (y1 < y2) ? y1 : y2;
        final int minZ = (z1 < z2) ? z1 : z2;
        final int maxX = (x2 > x1) ? x2 : x1;
        final int maxY = (y2 > y1) ? y2 : y1;
        final int maxZ = (z2 > z1) ? z2 : z1;
        for (int x3 = minX; x3 < maxX; ++x3) {
            int y3 = minY;
            while (y1 < maxY) {
                int z3 = minZ;
                while (z1 < maxZ) {
                    final BlockPos pos = new BlockPos(x3, y3, z3);
                    final IBlockState state = CrystalUtils2.mc.world.getBlockState(pos);
                    if (state.getBlock() != Blocks.OBSIDIAN && state.getBlock() != Blocks.BEDROCK) {
                        CrystalUtils2.obbyRock.remove(pos);
                    }
                    else if (!CrystalUtils2.obbyRock.contains(pos)) {
                        CrystalUtils2.obbyRock.add(pos);
                    }
                    ++z3;
                }
                ++y3;
            }
        }
        System.out.println("[2] Took " + (System.currentTimeMillis() - ms) + " ms to execute.");
    }
    
    public static void onUpdate() {
        if (CrystalUtils2.mc.player == null) {
            return;
        }
        if (PyroStatic.AUTOCRYSTAL == null || !PyroStatic.AUTOCRYSTAL.isEnabled()) {
            return;
        }
        final long ms = System.currentTimeMillis();
        for (final BlockPos pos : CrystalUtils2.obbyRock) {
            final IBlockState state = CrystalUtils2.mc.world.getBlockState(pos);
            final boolean alreadyContains = CrystalUtils2.crystalBlocks.contains(pos);
            if (state.getBlock() != Blocks.OBSIDIAN && state.getBlock() != Blocks.BEDROCK) {
                CrystalUtils2.crystalBlocks.remove(pos);
                CrystalUtils2.obbyRock.remove(pos);
            }
            else {
                final float dist = (float)CrystalUtils2.mc.player.getDistance(pos.getX() + 0.5, (double)pos.getY(), pos.getZ() + 0.5);
                if (dist > AutoCrystal.PlaceRadius.getValue()) {
                    if (alreadyContains) {
                        CrystalUtils2.crystalBlocks.remove(pos);
                    }
                    CrystalUtils2.obbyRock.remove(pos);
                }
                else if (!alreadyContains && canPlaceCrystalAt(pos, CrystalUtils2.mc.world.getBlockState(pos))) {
                    if (!AutoCrystal.VerifyCrystalBlocks(CrystalUtils2.mc, pos)) {
                        continue;
                    }
                    CrystalUtils2.crystalBlocks.add(pos);
                }
                else {
                    if (!alreadyContains || (canPlaceCrystalAt(pos, CrystalUtils2.mc.world.getBlockState(pos)) && AutoCrystal.VerifyCrystalBlocks(CrystalUtils2.mc, pos))) {
                        continue;
                    }
                    CrystalUtils2.crystalBlocks.remove(pos);
                }
            }
        }
        final int flooredRadius = MathHelper.floor((float)AutoCrystal.PlaceRadius.getValue()) + 1;
        final BlockPos playerPosFloored = PlayerUtil.GetLocalPlayerPosFloored();
        for (int x = playerPosFloored.getX() - flooredRadius; x <= playerPosFloored.getX() + flooredRadius; ++x) {
            for (int y = playerPosFloored.getY() - flooredRadius; y <= playerPosFloored.getY() + flooredRadius; ++y) {
                for (int z = playerPosFloored.getZ() - flooredRadius; z <= playerPosFloored.getZ() + flooredRadius; ++z) {
                    final BlockPos pos2 = new BlockPos(x, y, z);
                    if (!CrystalUtils2.obbyRock.contains(pos2)) {
                        final float dist2 = (float)CrystalUtils2.mc.player.getDistance(pos2.getX() + 0.5, (double)pos2.getY(), pos2.getZ() + 0.5);
                        if (dist2 <= AutoCrystal.PlaceRadius.getValue()) {
                            final IBlockState state2 = CrystalUtils2.mc.world.getBlockState(pos2);
                            if (state2.getBlock() == Blocks.OBSIDIAN || state2.getBlock() == Blocks.BEDROCK) {
                                CrystalUtils2.obbyRock.add(pos2);
                            }
                        }
                    }
                }
            }
        }
        final long diff = System.currentTimeMillis() - ms;
    }
    
    public static void onEntityRemoved(final Entity entity) {
        if (entity instanceof EntityEnderCrystal && CrystalUtils2.mc.player.getDistance(entity) <= AutoCrystal.PlaceRadius.getValue()) {
            final BlockPos pos = entity.getPosition().down();
            final IBlockState state = CrystalUtils2.mc.world.getBlockState(pos);
            if (state.getBlock() == Blocks.OBSIDIAN || state.getBlock() == Blocks.BEDROCK) {
                CrystalUtils2.crystalBlocks.add(pos);
            }
        }
    }
    
    static {
        CrystalUtils2.mc = Wrapper.GetMC();
        CrystalUtils2.obbyRock = new CopyOnWriteArrayList<BlockPos>();
        CrystalUtils2.crystalBlocks = new CopyOnWriteArrayList<BlockPos>();
    }
}
