//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import java.util.Iterator;
import net.minecraft.util.math.Vec3d;
import dev.nuker.pyro.deobfuscated.util.BlockInteractionHelper;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import net.minecraft.init.Blocks;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AntiCityBoss extends Module
{
    public final Value<Boolean> TrapCheck;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AntiCityBoss() {
        super("AntiCityBoss", new String[] { "AntiTrap" }, "Automatically places 4 obsidian in the direction your facing to prevent getting crystaled", "NONE", -1, ModuleType.COMBAT);
        this.TrapCheck = new Value<Boolean>("TrapCheck", new String[] { "HC" }, "Only functions if you're trapped", false);
        int slot;
        BlockPos l_CenterPos;
        ArrayList<BlockPos> BlocksToFill;
        BlockPos l_PosToFill;
        final Iterator<BlockPos> iterator;
        BlockPos l_Pos;
        BlockInteractionHelper.ValidResult l_Result;
        int lastSlot;
        float[] rotations;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getStage() == MinecraftEvent.Stage.Pre) {
                if (!this.TrapCheck.getValue() || PlayerUtil.IsPlayerTrapped()) {
                    slot = this.findStackHotbar(Blocks.OBSIDIAN);
                    if (slot != -1) {
                        l_CenterPos = PlayerUtil.GetLocalPlayerPosFloored();
                        BlocksToFill = new ArrayList<BlockPos>();
                        switch (PlayerUtil.GetFacing()) {
                            case East: {
                                BlocksToFill.add(l_CenterPos.east().east());
                                BlocksToFill.add(l_CenterPos.east().east().up());
                                BlocksToFill.add(l_CenterPos.east().east().east());
                                BlocksToFill.add(l_CenterPos.east().east().east().up());
                                break;
                            }
                            case North: {
                                BlocksToFill.add(l_CenterPos.north().north());
                                BlocksToFill.add(l_CenterPos.north().north().up());
                                BlocksToFill.add(l_CenterPos.north().north().north());
                                BlocksToFill.add(l_CenterPos.north().north().north().up());
                                break;
                            }
                            case South: {
                                BlocksToFill.add(l_CenterPos.south().south());
                                BlocksToFill.add(l_CenterPos.south().south().up());
                                BlocksToFill.add(l_CenterPos.south().south().south());
                                BlocksToFill.add(l_CenterPos.south().south().south().up());
                                break;
                            }
                            case West: {
                                BlocksToFill.add(l_CenterPos.west().west());
                                BlocksToFill.add(l_CenterPos.west().west().up());
                                BlocksToFill.add(l_CenterPos.west().west().west());
                                BlocksToFill.add(l_CenterPos.west().west().west().up());
                                break;
                            }
                        }
                        l_PosToFill = null;
                        BlocksToFill.iterator();
                        while (iterator.hasNext()) {
                            l_Pos = iterator.next();
                            l_Result = BlockInteractionHelper.valid(l_Pos);
                            if (l_Result != BlockInteractionHelper.ValidResult.Ok) {
                                continue;
                            }
                            else {
                                l_PosToFill = l_Pos;
                                break;
                            }
                        }
                        if (l_PosToFill != null) {
                            lastSlot = this.mc.player.inventory.currentItem;
                            this.mc.player.inventory.currentItem = slot;
                            this.mc.playerController.updateController();
                            p_Event.cancel();
                            rotations = BlockInteractionHelper.getLegitRotations(new Vec3d((double)l_PosToFill.getX(), (double)l_PosToFill.getY(), (double)l_PosToFill.getZ()));
                            p_Event.setPitch(rotations[1]);
                            p_Event.setYaw(rotations[0]);
                            BlockInteractionHelper.place(l_PosToFill, 5.0f, false, false);
                            this.Finish(lastSlot);
                        }
                    }
                }
            }
        });
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    private void Finish(final int p_LastSlot) {
        if (!this.slotEqualsBlock(p_LastSlot, Blocks.OBSIDIAN)) {
            this.mc.player.inventory.currentItem = p_LastSlot;
        }
        this.mc.playerController.updateController();
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
}
