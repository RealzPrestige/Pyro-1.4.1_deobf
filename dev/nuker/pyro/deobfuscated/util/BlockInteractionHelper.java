//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import java.util.Arrays;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumActionResult;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.block.BlockSlab;
import java.util.ArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;
import java.util.List;

public class BlockInteractionHelper
{
    public static final List<Block> blackList;
    public static final List<Block> shulkerList;
    private static final Minecraft mc;
    
    public static void placeBlockScaffold(final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight(), Minecraft.getMinecraft().player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) <= 18.0625) {
                    faceVectorPacketInstant(hitVec);
                    processRightClickBlock(neighbor, side2, hitVec);
                    Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
                    BlockInteractionHelper.mc.rightClickDelayTimer = 4;
                    return;
                }
            }
        }
    }
    
    public static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { Minecraft.getMinecraft().player.rotationYaw + MathHelper.wrapDegrees(yaw - Minecraft.getMinecraft().player.rotationYaw), Minecraft.getMinecraft().player.rotationPitch + MathHelper.wrapDegrees(pitch - Minecraft.getMinecraft().player.rotationPitch) };
    }
    
    private static Vec3d getEyesPos() {
        return new Vec3d(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight(), Minecraft.getMinecraft().player.posZ);
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getLegitRotations(vec);
        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], Minecraft.getMinecraft().player.onGround));
    }
    
    private static void processRightClickBlock(final BlockPos pos, final EnumFacing side, final Vec3d hitVec) {
        getPlayerController().processRightClickBlock(Minecraft.getMinecraft().player, BlockInteractionHelper.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    private static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    private static PlayerControllerMP getPlayerController() {
        return Minecraft.getMinecraft().playerController;
    }
    
    private static IBlockState getState(final BlockPos pos) {
        return Minecraft.getMinecraft().world.getBlockState(pos);
    }
    
    public static boolean checkForNeighbours(final BlockPos blockPos) {
        if (!hasNeighbour(blockPos)) {
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbour = blockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    public static boolean hasNeighbour(final BlockPos blockPos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = blockPos.offset(side);
            if (!Minecraft.getMinecraft().world.getBlockState(neighbour).getMaterial().isReplaceable()) {
                return true;
            }
        }
        return false;
    }
    
    public static List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        circleblocks.add(new BlockPos(x, y + plus_y, z));
                    }
                }
            }
        }
        return circleblocks;
    }
    
    public static ValidResult valid(final BlockPos pos) {
        return valid(pos, false);
    }
    
    public static PlaceResult place(final BlockPos pos, final float p_Distance, final boolean p_Rotate, final boolean p_UseSlabRule) {
        return place(pos, p_Distance, p_Rotate, p_UseSlabRule, false);
    }
    
    public static PlaceResult place(final BlockPos pos, final float p_Distance, final boolean p_Rotate, final boolean p_UseSlabRule, final boolean packetSwing) {
        final IBlockState l_State = BlockInteractionHelper.mc.world.getBlockState(pos);
        final boolean l_Replaceable = l_State.getMaterial().isReplaceable();
        final boolean l_IsSlabAtBlock = l_State.getBlock() instanceof BlockSlab;
        if (!l_Replaceable && !l_IsSlabAtBlock) {
            return PlaceResult.NotReplaceable;
        }
        if (!checkForNeighbours(pos)) {
            return PlaceResult.Neighbors;
        }
        if (!l_IsSlabAtBlock) {
            final ValidResult l_Result = valid(pos);
            if (l_Result != ValidResult.Ok && !l_Replaceable) {
                return PlaceResult.CantPlace;
            }
        }
        if (p_UseSlabRule && l_IsSlabAtBlock && !l_State.isFullCube()) {
            return PlaceResult.CantPlace;
        }
        final Vec3d eyesPos = new Vec3d(BlockInteractionHelper.mc.player.posX, BlockInteractionHelper.mc.player.posY + BlockInteractionHelper.mc.player.getEyeHeight(), BlockInteractionHelper.mc.player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (BlockInteractionHelper.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(BlockInteractionHelper.mc.world.getBlockState(neighbor), false)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.distanceTo(hitVec) <= p_Distance) {
                    final Block neighborPos = BlockInteractionHelper.mc.world.getBlockState(neighbor).getBlock();
                    final boolean activated = neighborPos.onBlockActivated((World)BlockInteractionHelper.mc.world, pos, BlockInteractionHelper.mc.world.getBlockState(pos), (EntityPlayer)BlockInteractionHelper.mc.player, EnumHand.MAIN_HAND, side, 0.0f, 0.0f, 0.0f);
                    if (BlockInteractionHelper.blackList.contains(neighborPos) || BlockInteractionHelper.shulkerList.contains(neighborPos) || activated) {
                        BlockInteractionHelper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockInteractionHelper.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    if (p_Rotate) {
                        faceVectorPacketInstant(hitVec);
                    }
                    final EnumActionResult l_Result2 = BlockInteractionHelper.mc.playerController.processRightClickBlock(BlockInteractionHelper.mc.player, BlockInteractionHelper.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                    if (l_Result2 != EnumActionResult.FAIL) {
                        if (packetSwing) {
                            BlockInteractionHelper.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        }
                        else {
                            BlockInteractionHelper.mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                        if (activated) {
                            BlockInteractionHelper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockInteractionHelper.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        }
                        return PlaceResult.Placed;
                    }
                }
            }
        }
        return PlaceResult.CantPlace;
    }
    
    public static boolean IsLiquidOrAir(final BlockPos p_Pos) {
        final IBlockState l_State = BlockInteractionHelper.mc.world.getBlockState(p_Pos);
        return l_State.getBlock() == Blocks.WATER || l_State.getBlock() == Blocks.LAVA || l_State.getBlock() == Blocks.AIR;
    }
    
    public static float[] getFacingRotations(final int x, final int y, final int z, final EnumFacing facing) {
        return getFacingRotations(x, y, z, facing, 1.0);
    }
    
    public static float[] getFacingRotations(final int x, final int y, final int z, final EnumFacing facing, final double width) {
        return getRotationsForPosition(x + 0.5 + facing.getDirectionVec().getX() * width / 2.0, y + 0.5 + facing.getDirectionVec().getY() * width / 2.0, z + 0.5 + facing.getDirectionVec().getZ() * width / 2.0);
    }
    
    public static float[] getRotationsForPosition(final double x, final double y, final double z) {
        return getRotationsForPosition(x, y, z, BlockInteractionHelper.mc.player.posX, BlockInteractionHelper.mc.player.posY + BlockInteractionHelper.mc.player.getEyeHeight(), BlockInteractionHelper.mc.player.posZ);
    }
    
    public static float[] getRotationsForPosition(final double x, final double y, final double z, final double sourceX, final double sourceY, final double sourceZ) {
        final double deltaX = x - sourceX;
        final double deltaY = y - sourceY;
        final double deltaZ = z - sourceZ;
        double yawToEntity;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        final double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        yawToEntity = wrapAngleTo180((float)yawToEntity);
        pitchToEntity = wrapAngleTo180((float)pitchToEntity);
        yawToEntity = (Double.isNaN(yawToEntity) ? 0.0 : yawToEntity);
        pitchToEntity = (Double.isNaN(pitchToEntity) ? 0.0 : pitchToEntity);
        return new float[] { (float)yawToEntity, (float)pitchToEntity };
    }
    
    public static float wrapAngleTo180(float angle) {
        for (angle %= 360.0f; angle >= 180.0f; angle -= 360.0f) {}
        while (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }
    
    public static ValidResult valid(final BlockPos pos, final boolean ignoreEntityCollision) {
        if (BlockInteractionHelper.mc.world == null) {
            return ValidResult.NoEntityCollision;
        }
        if (!BlockInteractionHelper.mc.world.checkNoEntityCollision(new AxisAlignedBB(pos)) && !ignoreEntityCollision) {
            return ValidResult.NoEntityCollision;
        }
        if (!checkForNeighbours(pos)) {
            return ValidResult.NoNeighbors;
        }
        final IBlockState l_State = BlockInteractionHelper.mc.world.getBlockState(pos);
        if (l_State.getBlock() == Blocks.AIR) {
            final BlockPos[] array;
            final BlockPos[] l_Blocks = array = new BlockPos[] { pos.north(), pos.south(), pos.east(), pos.west(), pos.up(), pos.down() };
            for (final BlockPos l_Pos : array) {
                final IBlockState l_State2 = BlockInteractionHelper.mc.world.getBlockState(l_Pos);
                if (l_State2.getBlock() != Blocks.AIR) {
                    for (final EnumFacing side : EnumFacing.values()) {
                        final BlockPos neighbor = pos.offset(side);
                        if (BlockInteractionHelper.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(BlockInteractionHelper.mc.world.getBlockState(neighbor), false)) {
                            return ValidResult.Ok;
                        }
                    }
                }
            }
            return ValidResult.NoNeighbors;
        }
        return ValidResult.AlreadyBlockThere;
    }
    
    static {
        blackList = Arrays.asList(Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR, Blocks.ENCHANTING_TABLE);
        shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
        mc = Minecraft.getMinecraft();
    }
    
    public enum ValidResult
    {
        NoEntityCollision, 
        AlreadyBlockThere, 
        NoNeighbors, 
        Ok;
    }
    
    public enum PlaceResult
    {
        NotReplaceable, 
        Neighbors, 
        CantPlace, 
        Placed;
    }
}
