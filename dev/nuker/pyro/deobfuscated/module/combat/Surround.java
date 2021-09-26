//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.function.Consumer;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import java.util.Iterator;
import dev.nuker.pyro.deobfuscated.util.BlockInteractionHelper;
import net.minecraft.init.Blocks;
import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.util.math.Vec3d;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Surround extends Module
{
    public final Value<String> Mode;
    public final Value<Boolean> ToggleOffGround;
    public final Value<String> CenterMode;
    public final Value<Boolean> ActivateOnlyOnShift;
    private Vec3d Center;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public Surround() {
        super("Surround", new String[] { "NoCrystal" }, "Automatically surrounds you with obsidian in the four cardinal direrctions", "NONE", 5448923, ModuleType.COMBAT);
        this.Mode = new Value<String>("Mode", new String[] { "M" }, "Mode of surrounding to use", "Normal");
        this.ToggleOffGround = new Value<Boolean>("ToggleOffGround", new String[] { "Toggles", "Disables" }, "Will toggle off after a place", false);
        this.CenterMode = new Value<String>("Center", new String[] { "Center" }, "Moves you to center of block", "NCP");
        this.ActivateOnlyOnShift = new Value<Boolean>("ActivateOnlyOnShift", new String[] { "AoOS" }, "Activates only when shift is pressed.", false);
        this.Center = Vec3d.ZERO;
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> this.setMetaData(this.getMetaData()));
        double l_XDiff;
        double l_ZDiff;
        double l_MotionX;
        double l_MotionZ;
        Vec3d pos;
        BlockPos interpPos;
        ArrayList<BlockPos> surroundBlocks;
        int slot;
        int lastSlot;
        final Iterator<BlockPos> iterator;
        BlockPos l_Pos;
        BlockInteractionHelper.ValidResult l_Result;
        BlockPos[] array;
        BlockPos[] l_Test;
        int length;
        int i = 0;
        BlockPos l_Pos2;
        BlockInteractionHelper.ValidResult l_Result2;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getStage() != MinecraftEvent.Stage.Pre || p_Event.isCancelled()) {
                return;
            }
            else {
                if (this.ActivateOnlyOnShift.getValue()) {
                    if (!this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                        this.Center = Vec3d.ZERO;
                        return;
                    }
                    else if (this.Center == Vec3d.ZERO) {
                        this.Center = GetCenter(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
                        if (!this.CenterMode.getValue().equals("None")) {
                            this.mc.player.motionX = 0.0;
                            this.mc.player.motionZ = 0.0;
                        }
                        if (this.CenterMode.getValue().equals("Teleport")) {
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Center.x, this.Center.y, this.Center.z, true));
                            this.mc.player.setPosition(this.Center.x, this.Center.y, this.Center.z);
                        }
                    }
                }
                if (this.Center != Vec3d.ZERO && this.CenterMode.getValue().equals("NCP")) {
                    l_XDiff = Math.abs(this.Center.x - this.mc.player.posX);
                    l_ZDiff = Math.abs(this.Center.z - this.mc.player.posZ);
                    if (l_XDiff <= 0.1 && l_ZDiff <= 0.1) {
                        this.Center = Vec3d.ZERO;
                    }
                    else {
                        l_MotionX = this.Center.x - this.mc.player.posX;
                        l_MotionZ = this.Center.z - this.mc.player.posZ;
                        this.mc.player.motionX = l_MotionX / 2.0;
                        this.mc.player.motionZ = l_MotionZ / 2.0;
                    }
                }
                if (!this.mc.player.onGround && !this.mc.player.prevOnGround && !this.ActivateOnlyOnShift.getValue() && this.ToggleOffGround.getValue()) {
                    this.toggle();
                    Pyro.SendMessage("[Surround]: You are off ground! toggling!");
                    return;
                }
                else {
                    pos = MathUtil.interpolateEntity((Entity)this.mc.player, this.mc.getRenderPartialTicks());
                    interpPos = new BlockPos(pos.x, pos.y, pos.z);
                    surroundBlocks = new ArrayList<BlockPos>();
                    surroundBlocks.add(interpPos.north());
                    surroundBlocks.add(interpPos.south());
                    surroundBlocks.add(interpPos.east());
                    surroundBlocks.add(interpPos.west());
                    if (this.Mode.getValue().equals("Full")) {
                        surroundBlocks.add(interpPos.north().east());
                        surroundBlocks.add(interpPos.north().west());
                        surroundBlocks.add(interpPos.south().east());
                        surroundBlocks.add(interpPos.south().west());
                    }
                    if (this.IsSurrounded()) {
                        return;
                    }
                    else {
                        slot = this.findStackHotbar(Blocks.OBSIDIAN);
                        if ((this.hasStack(Blocks.OBSIDIAN) || slot != -1) && this.mc.player.onGround) {
                            lastSlot = this.mc.player.inventory.currentItem;
                            this.mc.player.inventory.currentItem = slot;
                            this.mc.playerController.updateController();
                            surroundBlocks.iterator();
                            while (iterator.hasNext()) {
                                l_Pos = iterator.next();
                                l_Result = BlockInteractionHelper.valid(l_Pos);
                                if (l_Result == BlockInteractionHelper.ValidResult.AlreadyBlockThere && !this.mc.world.getBlockState(l_Pos).getMaterial().isReplaceable()) {
                                    continue;
                                }
                                else if (l_Result == BlockInteractionHelper.ValidResult.NoEntityCollision) {
                                    continue;
                                }
                                else if (l_Result == BlockInteractionHelper.ValidResult.NoNeighbors) {
                                    l_Test = (array = new BlockPos[] { l_Pos.down(), l_Pos.north(), l_Pos.south(), l_Pos.east(), l_Pos.west(), l_Pos.up() });
                                    length = array.length;
                                    while (i < length) {
                                        l_Pos2 = array[i];
                                        l_Result2 = BlockInteractionHelper.valid(l_Pos2);
                                        if (l_Result2 != BlockInteractionHelper.ValidResult.NoNeighbors && l_Result2 != BlockInteractionHelper.ValidResult.NoEntityCollision) {
                                            this.placeAtPos(p_Event, l_Pos2, lastSlot);
                                            return;
                                        }
                                        else {
                                            ++i;
                                        }
                                    }
                                }
                                else {
                                    this.placeAtPos(p_Event, l_Pos, lastSlot);
                                    return;
                                }
                            }
                            if (!p_Event.isCancelled()) {
                                if (!this.slotEqualsBlock(lastSlot, Blocks.OBSIDIAN)) {
                                    this.mc.player.inventory.currentItem = lastSlot;
                                }
                                this.mc.playerController.updateController();
                            }
                        }
                        return;
                    }
                }
            }
        });
        this.setMetaData(this.getMetaData());
        this.Mode.addString("Normal");
        this.Mode.addString("Full");
        this.CenterMode.addString("Teleport");
        this.CenterMode.addString("NCP");
        this.CenterMode.addString("None");
    }
    
    @Override
    public String getMetaData() {
        return this.CenterMode.getValue().toString();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.player == null) {
            this.toggle();
            return;
        }
        if (this.ActivateOnlyOnShift.getValue()) {
            return;
        }
        this.Center = GetCenter(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
        if (!this.CenterMode.getValue().equals("None")) {
            this.mc.player.motionX = 0.0;
            this.mc.player.motionZ = 0.0;
        }
        if (this.CenterMode.getValue().equals("Teleport")) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Center.x, this.Center.y, this.Center.z, true));
            this.mc.player.setPosition(this.Center.x, this.Center.y, this.Center.z);
        }
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    private void placeAtPos(final EventPlayerMotionUpdate event, final BlockPos pos, final int lastSlot) {
        final Vec3d eyesPos = new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (this.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(this.mc.world.getBlockState(neighbor), false)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.distanceTo(hitVec) <= 5.0) {
                    final float[] rotations = BlockInteractionHelper.getFacingRotations(pos.getX(), pos.getY(), pos.getZ(), side);
                    event.cancel();
                    event.setPitch(rotations[1]);
                    event.setYaw(rotations[0]);
                    break;
                }
            }
        }
        if (event.isCancelled()) {
            final Consumer<EntityPlayerSP> post = p -> {
                BlockInteractionHelper.place(pos, 5.0f, false, false);
                if (!this.slotEqualsBlock(lastSlot, Blocks.OBSIDIAN)) {
                    this.mc.player.inventory.currentItem = lastSlot;
                }
                this.mc.playerController.updateController();
                return;
            };
            event.setFunct(post);
        }
    }
    
    public boolean IsSurrounded() {
        final Vec3d localPos = MathUtil.interpolateEntity((Entity)this.mc.player, this.mc.getRenderPartialTicks());
        final BlockPos interloptedPos = new BlockPos(localPos.x, localPos.y, localPos.z);
        final ArrayList<BlockPos> surroundBlocks = new ArrayList<BlockPos>();
        surroundBlocks.add(interloptedPos.north());
        surroundBlocks.add(interloptedPos.south());
        surroundBlocks.add(interloptedPos.east());
        surroundBlocks.add(interloptedPos.west());
        if (this.Mode.getValue().equals("Full")) {
            surroundBlocks.add(interloptedPos.north().east());
            surroundBlocks.add(interloptedPos.north().west());
            surroundBlocks.add(interloptedPos.south().east());
            surroundBlocks.add(interloptedPos.south().west());
        }
        for (final BlockPos l_Pos : surroundBlocks) {
            if (BlockInteractionHelper.valid(l_Pos) != BlockInteractionHelper.ValidResult.AlreadyBlockThere) {
                return false;
            }
        }
        return true;
    }
    
    public boolean hasStack(final Block type) {
        if (this.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.inventory.getCurrentItem().getItem();
            return block.getBlock() == type;
        }
        return false;
    }
    
    private boolean slotEqualsBlock(final int slot, final Block type) {
        if (this.mc.player.inventory.getStackInSlot(slot).getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.inventory.getStackInSlot(slot).getItem();
            return block.getBlock() == type;
        }
        return false;
    }
    
    private int findStackHotbar(final Block type) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemBlock) {
                final ItemBlock block = (ItemBlock)stack.getItem();
                if (block.getBlock() == type) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static Vec3d GetCenter(final double posX, final double posY, final double posZ) {
        final double x = Math.floor(posX) + 0.5;
        final double y = Math.floor(posY);
        final double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
    
    public boolean HasObsidian() {
        return this.findStackHotbar(Blocks.OBSIDIAN) != -1;
    }
}
