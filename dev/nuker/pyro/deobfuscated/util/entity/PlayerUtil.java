//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util.entity;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemElytra;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.Minecraft;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFood;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import java.text.DecimalFormat;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.util.MinecraftInstance;

public class PlayerUtil extends MinecraftInstance
{
    private static Entity en;
    static final DecimalFormat Formatter;
    
    public static int GetItemSlot(final Item input) {
        if (PlayerUtil.mc.player == null) {
            return 0;
        }
        for (int i = 0; i < PlayerUtil.mc.player.inventoryContainer.getInventory().size(); ++i) {
            if (i != 0 && i != 5 && i != 6 && i != 7) {
                if (i != 8) {
                    final ItemStack s = (ItemStack)PlayerUtil.mc.player.inventoryContainer.getInventory().get(i);
                    if (!s.isEmpty()) {
                        if (s.getItem() == input) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    public static int GetRecursiveItemSlot(final Item input) {
        if (PlayerUtil.mc.player == null) {
            return 0;
        }
        for (int i = PlayerUtil.mc.player.inventoryContainer.getInventory().size() - 1; i > 0; --i) {
            if (i != 0 && i != 5 && i != 6 && i != 7) {
                if (i != 8) {
                    final ItemStack s = (ItemStack)PlayerUtil.mc.player.inventoryContainer.getInventory().get(i);
                    if (!s.isEmpty()) {
                        if (s.getItem() == input) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    public static int GetItemSlotNotHotbar(final Item input) {
        if (PlayerUtil.mc.player == null) {
            return 0;
        }
        for (int i = 9; i < 36; ++i) {
            final Item item = PlayerUtil.mc.player.inventory.getStackInSlot(i).getItem();
            if (item == input) {
                return i;
            }
        }
        return -1;
    }
    
    public static int GetItemCount(final Item input) {
        if (PlayerUtil.mc.player == null) {
            return 0;
        }
        int items = 0;
        for (int i = 0; i < 45; ++i) {
            final ItemStack stack = PlayerUtil.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == input) {
                items += stack.getCount();
            }
        }
        return items;
    }
    
    public static boolean CanSeeBlock(final BlockPos p_Pos) {
        if (PlayerUtil.mc.player == null) {
            return false;
        }
        if (PlayerUtil.en == null && PlayerUtil.mc.world != null) {
            PlayerUtil.en = (Entity)new EntityChicken(PlayerUtil.mc.player.world);
        }
        PlayerUtil.en.setPosition(p_Pos.getX() + 0.5, p_Pos.getY() + 0.5, p_Pos.getZ() + 0.5);
        return PlayerUtil.mc.world.rayTraceBlocks(new Vec3d(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.posY + PlayerUtil.mc.player.getEyeHeight(), PlayerUtil.mc.player.posZ), new Vec3d(PlayerUtil.en.posX, PlayerUtil.en.posY, PlayerUtil.en.posZ), false, true, false) == null;
    }
    
    public static boolean isCurrentViewEntity() {
        return PlayerUtil.mc.getRenderViewEntity() == PlayerUtil.mc.player;
    }
    
    public static boolean IsEating() {
        return PlayerUtil.mc.player != null && PlayerUtil.mc.player.getHeldItemMainhand().getItem() instanceof ItemFood && PlayerUtil.mc.player.isHandActive();
    }
    
    public static int GetItemInHotbar(final Item p_Item) {
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = PlayerUtil.mc.player.inventory.getStackInSlot(l_I);
            if (l_Stack != ItemStack.EMPTY && l_Stack.getItem() == p_Item) {
                return l_I;
            }
        }
        return -1;
    }
    
    public static BlockPos GetLocalPlayerPosFloored() {
        if (PlayerUtil.mc.player == null) {
            return BlockPos.ORIGIN;
        }
        return new BlockPos(Math.floor(PlayerUtil.mc.player.posX), Math.floor(PlayerUtil.mc.player.posY), Math.floor(PlayerUtil.mc.player.posZ));
    }
    
    public static BlockPos EntityPosToFloorBlockPos(final Entity e) {
        return new BlockPos(Math.floor(e.posX), Math.floor(e.posY), Math.floor(e.posZ));
    }
    
    public static float GetHealthWithAbsorption() {
        return PlayerUtil.mc.player.getHealth() + PlayerUtil.mc.player.getAbsorptionAmount();
    }
    
    public static boolean IsPlayerInHole() {
        final BlockPos blockPos = GetLocalPlayerPosFloored();
        final IBlockState blockState = PlayerUtil.mc.world.getBlockState(blockPos);
        if (blockState.getBlock() != Blocks.AIR) {
            return false;
        }
        if (PlayerUtil.mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR) {
            return false;
        }
        if (PlayerUtil.mc.world.getBlockState(blockPos.down()).getBlock() == Blocks.AIR) {
            return false;
        }
        final BlockPos[] touchingBlocks = { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west() };
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = PlayerUtil.mc.world.getBlockState(touching);
            if (touchingState.getBlock() != Blocks.AIR && touchingState.isFullBlock()) {
                ++validHorizontalBlocks;
            }
        }
        return validHorizontalBlocks >= 4;
    }
    
    public static boolean IsPlayerInHole(final EntityPlayer who) {
        final BlockPos blockPos = new BlockPos(Math.floor(who.posX), Math.floor(who.posY), Math.floor(who.posZ));
        final IBlockState blockState = PlayerUtil.mc.world.getBlockState(blockPos);
        if (blockState.getBlock() != Blocks.AIR) {
            return false;
        }
        if (PlayerUtil.mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR) {
            return false;
        }
        if (PlayerUtil.mc.world.getBlockState(blockPos.down()).getBlock() == Blocks.AIR) {
            return false;
        }
        final BlockPos[] touchingBlocks = { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west() };
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = PlayerUtil.mc.world.getBlockState(touching);
            if (touchingState.getBlock() != Blocks.AIR && touchingState.isFullBlock()) {
                ++validHorizontalBlocks;
            }
        }
        return validHorizontalBlocks >= 4;
    }
    
    public static boolean isPlayerInHole(final Block block) {
        final BlockPos blockPos = GetLocalPlayerPosFloored();
        final BlockPos[] touchingBlocks = { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west() };
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = PlayerUtil.mc.world.getBlockState(touching);
            if (touchingState.getBlock() != Blocks.AIR && touchingState.isFullBlock()) {
                if (block.equals(Blocks.OBSIDIAN)) {
                    if (touchingState.getBlock().equals(Blocks.OBSIDIAN) || touchingState.getBlock().equals(Blocks.BEDROCK)) {
                        ++validHorizontalBlocks;
                    }
                }
                else if (touchingState.getBlock().equals(block)) {
                    ++validHorizontalBlocks;
                }
            }
        }
        return validHorizontalBlocks >= 4;
    }
    
    public static boolean isEntityInHole(final EntityPlayer who, final Block block) {
        final BlockPos blockPos = new BlockPos(Math.floor(who.posX), Math.floor(who.posY), Math.floor(who.posZ));
        final BlockPos[] touchingBlocks = { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west() };
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = PlayerUtil.mc.world.getBlockState(touching);
            if (touchingState.getBlock() != Blocks.AIR && touchingState.isFullBlock()) {
                if (block.equals(Blocks.OBSIDIAN)) {
                    if (touchingState.getBlock().equals(Blocks.OBSIDIAN) || touchingState.getBlock().equals(Blocks.BEDROCK)) {
                        ++validHorizontalBlocks;
                    }
                }
                else if (touchingState.getBlock().equals(block)) {
                    ++validHorizontalBlocks;
                }
            }
        }
        return validHorizontalBlocks >= 4;
    }
    
    public static boolean IsPlayerTrapped() {
        final BlockPos l_PlayerPos = GetLocalPlayerPosFloored();
        final BlockPos[] array;
        final BlockPos[] l_TrapPositions = array = new BlockPos[] { l_PlayerPos.down(), l_PlayerPos.up().up(), l_PlayerPos.north(), l_PlayerPos.south(), l_PlayerPos.east(), l_PlayerPos.west(), l_PlayerPos.north().up(), l_PlayerPos.south().up(), l_PlayerPos.east().up(), l_PlayerPos.west().up() };
        for (final BlockPos l_Pos : array) {
            final IBlockState l_State = PlayerUtil.mc.world.getBlockState(l_Pos);
            if (l_State.getBlock() != Blocks.OBSIDIAN && PlayerUtil.mc.world.getBlockState(l_Pos).getBlock() != Blocks.BEDROCK) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean IsEntityTrapped(final Entity e) {
        final BlockPos l_PlayerPos = EntityPosToFloorBlockPos(e);
        final BlockPos[] array;
        final BlockPos[] l_TrapPositions = array = new BlockPos[] { l_PlayerPos.up().up(), l_PlayerPos.north(), l_PlayerPos.south(), l_PlayerPos.east(), l_PlayerPos.west(), l_PlayerPos.north().up(), l_PlayerPos.south().up(), l_PlayerPos.east().up(), l_PlayerPos.west().up() };
        for (final BlockPos l_Pos : array) {
            final IBlockState l_State = PlayerUtil.mc.world.getBlockState(l_Pos);
            if (l_State.getBlock() != Blocks.OBSIDIAN && PlayerUtil.mc.world.getBlockState(l_Pos).getBlock() != Blocks.BEDROCK) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isBlockAbovePlayerHead(final EntityPlayer who, final boolean tall) {
        final BlockPos l_PlayerPos = new BlockPos(Math.floor(who.posX), Math.floor(who.posY), Math.floor(who.posZ));
        final BlockPos posOne = l_PlayerPos.up().up();
        final BlockPos posTwo = l_PlayerPos.up().up().up();
        final IBlockState stateOne = PlayerUtil.mc.world.getBlockState(posOne);
        final IBlockState stateTwo = PlayerUtil.mc.world.getBlockState(posTwo);
        final Block blockOne = stateOne.getBlock();
        final Block blockTwo = stateTwo.getBlock();
        return (blockOne != Blocks.AIR && stateOne.isFullBlock()) || (tall && blockTwo != Blocks.AIR && stateTwo.isFullBlock());
    }
    
    public static void switchToItem(final Item item) {
        if (PlayerUtil.mc.player.getHeldItemMainhand().getItem() != item) {
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = PlayerUtil.mc.player.inventory.getStackInSlot(i);
                if (!stack.isEmpty() && stack.getItem() == item) {
                    PlayerUtil.mc.player.inventory.currentItem = i;
                    PlayerUtil.mc.playerController.updateController();
                    break;
                }
            }
        }
    }
    
    public static FacingDirection GetFacing() {
        switch (MathHelper.floor(PlayerUtil.mc.player.rotationYaw * 8.0f / 360.0f + 0.5) & 0x7) {
            case 0:
            case 1: {
                return FacingDirection.South;
            }
            case 2:
            case 3: {
                return FacingDirection.West;
            }
            case 4:
            case 5: {
                return FacingDirection.North;
            }
            case 6:
            case 7: {
                return FacingDirection.East;
            }
            default: {
                return FacingDirection.North;
            }
        }
    }
    
    public static CardinalFacingDirection getCardinalFacingDirection() {
        switch (MathHelper.floor(PlayerUtil.mc.player.rotationYaw * 8.0f / 360.0f + 0.5) & 0x7) {
            case 0: {
                return CardinalFacingDirection.South;
            }
            case 1: {
                return CardinalFacingDirection.SouthWest;
            }
            case 2: {
                return CardinalFacingDirection.West;
            }
            case 3: {
                return CardinalFacingDirection.NorthWest;
            }
            case 4: {
                return CardinalFacingDirection.North;
            }
            case 5: {
                return CardinalFacingDirection.NorthEast;
            }
            case 6: {
                return CardinalFacingDirection.East;
            }
            case 7: {
                return CardinalFacingDirection.SouthEast;
            }
            default: {
                return CardinalFacingDirection.North;
            }
        }
    }
    
    public static float getSpeedInKM() {
        final double deltaX = PlayerUtil.mc.player.posX - PlayerUtil.mc.player.prevPosX;
        final double deltaZ = PlayerUtil.mc.player.posZ - PlayerUtil.mc.player.prevPosZ;
        final float l_Distance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        final double l_KMH = Math.floor(l_Distance / 1000.0f / 1.3888889E-5f);
        String l_Formatter = PlayerUtil.Formatter.format(l_KMH);
        if (!l_Formatter.contains(".")) {
            l_Formatter += ".0";
        }
        return Float.valueOf(l_Formatter);
    }
    
    public static boolean isInLiquid() {
        if (Wrapper.GetPlayer() == null) {
            return false;
        }
        boolean inLiquid = false;
        final int y = (int)(Wrapper.GetPlayer().getEntityBoundingBox().minY + 0.02);
        for (int x = MathHelper.floor(Wrapper.GetPlayer().getEntityBoundingBox().minX); x < MathHelper.floor(Wrapper.GetPlayer().getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor(Wrapper.GetPlayer().getEntityBoundingBox().minZ); z < MathHelper.floor(Wrapper.GetPlayer().getEntityBoundingBox().maxZ) + 1; ++z) {
                final Block block = Wrapper.GetMC().world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }
    
    public static boolean isOnLiquid() {
        final float offset = 0.05f;
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null) {
            return false;
        }
        if (mc.player.fallDistance >= 3.0f) {
            return false;
        }
        if (mc.player != null) {
            final AxisAlignedBB bb = (mc.player.getRidingEntity() != null) ? mc.player.getRidingEntity().getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(0.0, -0.05000000074505806, 0.0) : mc.player.getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(0.0, -0.05000000074505806, 0.0);
            boolean onLiquid = false;
            final int y = (int)bb.minY;
            for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX + 1.0); ++x) {
                for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ + 1.0); ++z) {
                    final Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != Blocks.AIR) {
                        if (!(block instanceof BlockLiquid)) {
                            return false;
                        }
                        onLiquid = true;
                    }
                }
            }
            return onLiquid;
        }
        return false;
    }
    
    public static boolean isWearingUseableElytra() {
        final ItemStack itemstack = PlayerUtil.mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        return itemstack.getItem() == Items.ELYTRA && ItemElytra.isUsable(itemstack);
    }
    
    public static int GetItemSlotInHotbar(final Block web) {
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = PlayerUtil.mc.player.inventory.getStackInSlot(l_I);
            if (l_Stack != ItemStack.EMPTY && l_Stack.getItem() instanceof ItemBlock) {
                final ItemBlock block = (ItemBlock)l_Stack.getItem();
                if (block.getBlock().equals(web)) {
                    return l_I;
                }
            }
        }
        return -1;
    }
    
    public static void sendMovementPackets(final EventPlayerMotionUpdate event) {
        final Minecraft mc = Wrapper.GetMC();
        final boolean flag = mc.player.isSprinting();
        if (flag != mc.player.serverSprintState) {
            if (flag) {
                mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            mc.player.serverSprintState = flag;
        }
        final boolean flag2 = mc.player.isSneaking();
        if (flag2 != mc.player.serverSneakState) {
            if (flag2) {
                mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            mc.player.serverSneakState = flag2;
        }
        if (mc.getRenderViewEntity() == mc.player) {
            final double d0 = mc.player.posX - mc.player.lastReportedPosX;
            final double d2 = event.getY() - mc.player.lastReportedPosY;
            final double d3 = mc.player.posZ - mc.player.lastReportedPosZ;
            final double d4 = event.getYaw() - mc.player.lastReportedYaw;
            final double d5 = event.getPitch() - mc.player.lastReportedPitch;
            final EntityPlayerSP player = mc.player;
            ++player.positionUpdateTicks;
            boolean flag3 = d0 * d0 + d2 * d2 + d3 * d3 > 9.0E-4 || mc.player.positionUpdateTicks >= 20;
            final boolean flag4 = d4 != 0.0 || d5 != 0.0;
            if (mc.player.isRiding()) {
                mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(mc.player.motionX, -999.0, mc.player.motionZ, event.getYaw(), event.getPitch(), mc.player.onGround));
                flag3 = false;
            }
            else if (flag3 && flag4) {
                mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(mc.player.posX, event.getY(), mc.player.posZ, event.getYaw(), event.getPitch(), mc.player.onGround));
            }
            else if (flag3) {
                mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, event.getY(), mc.player.posZ, mc.player.onGround));
            }
            else if (flag4) {
                mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(event.getYaw(), event.getPitch(), mc.player.onGround));
            }
            else if (mc.player.prevOnGround != mc.player.onGround) {
                mc.player.connection.sendPacket((Packet)new CPacketPlayer(mc.player.onGround));
            }
            if (flag3) {
                mc.player.lastReportedPosX = mc.player.posX;
                mc.player.lastReportedPosY = event.getY();
                mc.player.lastReportedPosZ = mc.player.posZ;
                mc.player.positionUpdateTicks = 0;
            }
            if (flag4) {
                mc.player.lastReportedYaw = event.getYaw();
                mc.player.lastReportedPitch = event.getPitch();
            }
            mc.player.prevOnGround = mc.player.onGround;
            mc.player.autoJumpEnabled = mc.player.mc.gameSettings.autoJump;
        }
    }
    
    static {
        PlayerUtil.en = null;
        Formatter = new DecimalFormat("#.#");
    }
    
    public enum FacingDirection
    {
        North, 
        South, 
        East, 
        West;
    }
    
    public enum CardinalFacingDirection
    {
        North, 
        NorthWest, 
        NorthEast, 
        South, 
        SouthWest, 
        SouthEast, 
        East, 
        West;
    }
}
