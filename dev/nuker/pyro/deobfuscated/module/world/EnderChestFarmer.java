//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockEnderChest;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerDamageBlock;
import dev.nuker.pyro.deobfuscated.PyroMod;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.util.BlockInteractionHelper;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import dev.nuker.pyro.deobfuscated.managers.BlockManager;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class EnderChestFarmer extends Module
{
    public static Value<String> Mode;
    public static Value<Integer> Radius;
    public Value<Float> Delay;
    private Timer PlaceTimer;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public EnderChestFarmer() {
        super("ObsidianMaker", new String[] { "EChestFarmer" }, "Automatically places enderchests around you, and attempts to mine it", "NONE", -1, ModuleType.WORLD);
        this.Delay = new Value<Float>("Delay", new String[] { "D" }, "Timed delay for each place of ender chest", 1.0f, 0.0f, 10.0f, 1.0f);
        this.PlaceTimer = new Timer();
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> this.setMetaData(this.getMetaData()));
        double[] l_Pos;
        BlockPos l_ClosestPos;
        boolean hasPickaxe;
        int i;
        ItemStack stack;
        double[] l_Pos2;
        int slot;
        final Iterator<BlockPos> iterator;
        BlockPos pos;
        BlockInteractionHelper.PlaceResult result;
        double[] rotations;
        int slot2;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getStage() != MinecraftEvent.Stage.Pre || p_Event.isCancelled()) {
                return;
            }
            else if ((EnderChestFarmer.Mode.getValue().equals("Normal") || EnderChestFarmer.Mode.getValue().equals("PvP")) && BlockManager.Update(EnderChestFarmer.Radius.getValue(), true)) {
                p_Event.cancel();
                l_Pos = EntityUtil.calculateLookAt(BlockManager.GetCurrBlock().getX() + 0.5, BlockManager.GetCurrBlock().getY() - 0.5, BlockManager.GetCurrBlock().getZ() + 0.5, (EntityPlayer)this.mc.player);
                p_Event.setPitch(l_Pos[1]);
                p_Event.setYaw(l_Pos[0]);
                return;
            }
            else {
                l_ClosestPos = BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), EnderChestFarmer.Radius.getValue(), EnderChestFarmer.Radius.getValue(), false, true, 0).stream().filter(p_Pos -> this.IsValidBlockPos(p_Pos)).min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock((Entity)this.mc.player, p_Pos))).orElse(null);
                if (l_ClosestPos != null) {
                    hasPickaxe = (this.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE);
                    if (!hasPickaxe) {
                        for (i = 0; i < 9; ++i) {
                            stack = this.mc.player.inventory.getStackInSlot(i);
                            if (!stack.isEmpty()) {
                                if (stack.getItem() == Items.DIAMOND_PICKAXE) {
                                    hasPickaxe = true;
                                    this.mc.player.inventory.currentItem = i;
                                    this.mc.playerController.updateController();
                                    break;
                                }
                            }
                        }
                    }
                    if (!(!hasPickaxe)) {
                        if (EnderChestFarmer.Mode.getValue().equals("Packet")) {
                            p_Event.cancel();
                            l_Pos2 = EntityUtil.calculateLookAt(l_ClosestPos.getX() + 0.5, l_ClosestPos.getY() - 0.5, l_ClosestPos.getZ() + 0.5, (EntityPlayer)this.mc.player);
                            p_Event.setPitch(l_Pos2[1]);
                            p_Event.setYaw(l_Pos2[0]);
                            this.mc.player.swingArm(EnumHand.MAIN_HAND);
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, l_ClosestPos, EnumFacing.UP));
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, l_ClosestPos, EnumFacing.UP));
                            PyroMod.EVENT_BUS.post(new EventPlayerDamageBlock(l_ClosestPos, EnumFacing.UP));
                        }
                        else {
                            BlockManager.SetCurrentBlock(l_ClosestPos);
                        }
                    }
                }
                else if (!EnderChestFarmer.Mode.getValue().equals("PvP")) {
                    if (!(!this.PlaceTimer.passed(this.Delay.getValue() * 1000.0f))) {
                        this.PlaceTimer.reset();
                        if (!this.IsCurrItemEnderChest()) {
                            slot = this.GetEnderChestSlot();
                            if (slot == -1) {
                                return;
                            }
                            else {
                                this.mc.player.inventory.currentItem = slot;
                                this.mc.playerController.updateController();
                            }
                        }
                        BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), EnderChestFarmer.Radius.getValue(), EnderChestFarmer.Radius.getValue(), false, true, 0).iterator();
                        while (iterator.hasNext()) {
                            pos = iterator.next();
                            result = BlockInteractionHelper.place(pos, EnderChestFarmer.Radius.getValue(), true, false);
                            if (result == BlockInteractionHelper.PlaceResult.Placed) {
                                p_Event.cancel();
                                rotations = EntityUtil.calculateLookAt(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5, (EntityPlayer)this.mc.player);
                                p_Event.setPitch(rotations[1]);
                                p_Event.setYaw(rotations[0]);
                            }
                        }
                    }
                }
                else if (EnderChestFarmer.Mode.getValue().equals("PvP")) {
                    if (!this.IsCurrItemEnderChest()) {
                        slot2 = this.GetEnderChestSlot();
                        if (slot2 == -1) {
                            return;
                        }
                        else {
                            this.mc.player.inventory.currentItem = slot2;
                            this.mc.playerController.updateController();
                        }
                    }
                    if (this.IsCurrItemEnderChest()) {
                        KeyBinding.onTick(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode());
                    }
                }
                return;
            }
        });
        this.setMetaData(this.getMetaData());
        EnderChestFarmer.Mode.addString("Packet");
        EnderChestFarmer.Mode.addString("Normal");
        EnderChestFarmer.Mode.addString("PvP");
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(EnderChestFarmer.Mode.getValue());
    }
    
    private boolean IsValidBlockPos(final BlockPos p_Pos) {
        final IBlockState l_State = this.mc.world.getBlockState(p_Pos);
        return l_State.getBlock() instanceof BlockEnderChest;
    }
    
    private boolean IsCurrItemEnderChest() {
        if (this.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.getHeldItemMainhand().getItem();
            return block.getBlock() == Blocks.ENDER_CHEST;
        }
        return false;
    }
    
    private int GetEnderChestSlot() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemBlock) {
                    final ItemBlock block = (ItemBlock)stack.getItem();
                    if (block.getBlock() == Blocks.ENDER_CHEST) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    static {
        EnderChestFarmer.Mode = new Value<String>("Mode", new String[] { "M" }, "Mode to use for mining", "Packet");
        EnderChestFarmer.Radius = new Value<Integer>("Radius", new String[] { "R" }, "Radius to search for enderchests, and place them", 4, 0, 10, 1);
    }
}
