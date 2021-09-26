//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.managers;

import dev.nuker.pyro.deobfuscated.main.Wrapper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;

public class BlockManager
{
    private static Minecraft mc;
    private static BlockPos _currBlock;
    private static boolean _started;
    
    public static void SetCurrentBlock(final BlockPos block) {
        BlockManager._currBlock = block;
        BlockManager._started = false;
    }
    
    public static BlockPos GetCurrBlock() {
        return BlockManager._currBlock;
    }
    
    public static boolean GetState() {
        return BlockManager._currBlock != null && IsDoneBreaking(BlockManager.mc.world.getBlockState(BlockManager._currBlock));
    }
    
    private static boolean IsDoneBreaking(final IBlockState blockState) {
        return blockState.getBlock() == Blocks.BEDROCK || blockState.getBlock() == Blocks.AIR || blockState.getBlock() instanceof BlockLiquid;
    }
    
    public static boolean Update(final float range, final boolean rayTrace) {
        if (BlockManager._currBlock == null) {
            return false;
        }
        final IBlockState state = BlockManager.mc.world.getBlockState(BlockManager._currBlock);
        if (IsDoneBreaking(state) || BlockManager.mc.player.getDistanceSq(BlockManager._currBlock) > Math.pow(range, range)) {
            BlockManager._currBlock = null;
            return false;
        }
        BlockManager.mc.player.swingArm(EnumHand.MAIN_HAND);
        EnumFacing facing = EnumFacing.UP;
        if (rayTrace) {
            final RayTraceResult result = BlockManager.mc.world.rayTraceBlocks(new Vec3d(BlockManager.mc.player.posX, BlockManager.mc.player.posY + BlockManager.mc.player.getEyeHeight(), BlockManager.mc.player.posZ), new Vec3d(BlockManager._currBlock.getX() + 0.5, BlockManager._currBlock.getY() - 0.5, BlockManager._currBlock.getZ() + 0.5));
            if (result != null && result.sideHit != null) {
                facing = result.sideHit;
            }
        }
        if (!BlockManager._started) {
            BlockManager._started = true;
            BlockManager.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, BlockManager._currBlock, facing));
        }
        else {
            BlockManager.mc.playerController.onPlayerDamageBlock(BlockManager._currBlock, facing);
        }
        return true;
    }
    
    static {
        BlockManager.mc = Wrapper.GetMC();
        BlockManager._currBlock = null;
        BlockManager._started = false;
    }
}
